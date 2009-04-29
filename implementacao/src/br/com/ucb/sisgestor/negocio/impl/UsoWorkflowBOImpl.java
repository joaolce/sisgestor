/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.CampoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TipoAcaoEnum;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.Constantes;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 31/03/2009
 */
@Service("usoWorkflowBO")
public class UsoWorkflowBOImpl extends BaseBOImpl<UsoWorkflow> implements UsoWorkflowBO {

	private UsoWorkflowDAO	usoWorkflowDAO;
	private TarefaBO			tarefaBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public void atualizar(UsoWorkflow usoWorkflow) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public void excluir(UsoWorkflow obj) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		return this.usoWorkflowDAO.getTotalRegistros(Utils.getUsuario());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void iniciarTarefa(UsoWorkflow usoWorkflow) throws NegocioException {
		usoWorkflow.setDataHoraInicio(DataUtil.getDataHoraAtual());
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.INICIO_TAREFA);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void modificarTarefa(UsoWorkflow usoWorkflow, Integer idTarefa) throws NegocioException {
		TipoAcaoEnum acaoOcorrida;
		if (idTarefa == -1) { //finalização do workflow
			usoWorkflow.setUsoFinalizado(Boolean.TRUE);
			acaoOcorrida = TipoAcaoEnum.FINALIZAR_USO;
		} else {
			usoWorkflow.setDataHoraInicio(null); // para que o novo usuário possa iniciar sua tarefa
			Tarefa novaTarefa = this.getTarefaBO().obter(idTarefa);
			this.notificarMudancaDepartamento(usoWorkflow, novaTarefa);
			usoWorkflow.setTarefa(novaTarefa);
			this.notificarMudancaUsuarioTarefa(usoWorkflow);
			acaoOcorrida = TipoAcaoEnum.FINALIZAR_TAREFA;
		}
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, acaoOcorrida);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public UsoWorkflow obter(Integer pk) {
		return this.usoWorkflowDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<UsoWorkflow> obterTodos() {
		return this.usoWorkflowDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual) {
		return this.usoWorkflowDAO.recuperarPendentesUsuario(Utils.getUsuario(), paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(UsoWorkflow usoWorkflow) throws NegocioException {
		this.adicionaCamposDoUso(usoWorkflow);
		usoWorkflow.setUsoFinalizado(Boolean.FALSE);
		this.gerarNumeroDoRegistro(usoWorkflow, DataUtil.getAno(DataUtil.getDataAtual()));
		Integer id = this.usoWorkflowDAO.salvar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.INICIO_USO);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void salvarAnotacao(Integer idUsoWorkflow, String anotacao) throws NegocioException {
		UsoWorkflow usoWorkflow = this.usoWorkflowDAO.obter(idUsoWorkflow);
		usoWorkflow.setAnotacao(anotacao);
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.ALTERACAO_ANOTACAO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow) {
		this.usoWorkflowDAO.salvarHistorico(historicoUsoWorkflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void salvarValoresCampos(String[] valores, Integer idUsoWorkflow) throws NegocioException {
		UsoWorkflow usoWorkflow = this.usoWorkflowDAO.obter(idUsoWorkflow);
		List<CampoUsoWorkflow> listaCampos = usoWorkflow.getCamposUsados();
		usoWorkflow.setCamposUsados(this.getListaCamposAtualizar(listaCampos, valores));
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.ALTERACAO_CAMPOS);
	}

	/**
	 * Atribui o DAO de {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflowDAO DAO de {@link UsoWorkflow}
	 */
	@Autowired
	public void setUsoWorkflowDAO(UsoWorkflowDAO usoWorkflowDAO) {
		this.usoWorkflowDAO = usoWorkflowDAO;
	}

	/**
	 * Adiciona os campos do {@link UsoWorkflow} com o valor padrão "".
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} sendo criado
	 */
	private void adicionaCamposDoUso(UsoWorkflow usoWorkflow) {
		List<CampoUsoWorkflow> camposDoUso = new ArrayList<CampoUsoWorkflow>();
		CampoUsoWorkflow campoUsoWorkflow;
		for (Campo campo : usoWorkflow.getWorkflow().getCampos()) {
			campoUsoWorkflow = new CampoUsoWorkflow();
			campoUsoWorkflow.setCampo(campo);
			campoUsoWorkflow.setUsoWorkflow(usoWorkflow);
			campoUsoWorkflow.setValor("");
			camposDoUso.add(campoUsoWorkflow);
		}
		usoWorkflow.setCamposUsados(camposDoUso);
	}

	/**
	 * Gera um registro de histórico para o {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a gerar histórico
	 * @para acao ação ocorrida no {@link UsoWorkflow}
	 */
	private void gerarHistorico(UsoWorkflow usoWorkflow, TipoAcaoEnum acao) {
		HistoricoUsoWorkflow historico = new HistoricoUsoWorkflow();
		historico.setUsoWorkflow(usoWorkflow);
		historico.setDataHora(DataUtil.getDataHoraAtual());
		historico.setUsuario(Utils.getUsuario());
		historico.setAcao(acao);
		this.salvarHistorico(historico);
	}

	/**
	 * Gera um número de registro para o {@link UsoWorkflow} a ser iniciado.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a ser iniciado
	 * @param ano Ano de início do uso do workflow
	 */
	private void gerarNumeroDoRegistro(UsoWorkflow usoWorkflow, int ano) {
		Integer ultimoNumero = this.usoWorkflowDAO.recuperarUltimoNumeroDoAno(ano);
		if (ultimoNumero == null) {
			ultimoNumero = 1;
		} else {
			ultimoNumero++;
		}
		usoWorkflow.setNumero(ultimoNumero);
	}

	/**
	 * Recupera o campo uso workflow do tipo radio ou checkbox com seu novo valor
	 * 
	 * @param camposRadioCheckbox Lista dos campos com seus valores
	 * @param listaCampos Lista de campos persistida atualmente
	 * @return um {@link List} de {@link CampoUsoWorkflow}
	 * @throws NegocioException caso o campo obrigatório não esteje preenchido
	 */
	private List<CampoUsoWorkflow> getCampoUsoWorkflowOpcoes(List<String> camposRadioCheckbox,
			List<CampoUsoWorkflow> listaCampos) throws NegocioException {

		String[] campo;
		Integer idcampo;
		Boolean checked;
		String valor;
		StringBuffer novoValor = new StringBuffer();
		List<CampoUsoWorkflow> listaCamposAtualizar = new ArrayList<CampoUsoWorkflow>();

		for (CampoUsoWorkflow campoUsoWorkflow : listaCampos) {
			Campo campoWorkflow = campoUsoWorkflow.getCampo();
			if (!campoWorkflow.getOpcoes().isEmpty()) {
				for (String campoRadioCheckbox : camposRadioCheckbox) {
					campo = campoRadioCheckbox.split("£");
					idcampo = Integer.valueOf(campo[0].substring(5)); // removendo o "campo" do id html
					checked = Boolean.parseBoolean(campo[2]);
					if (campoWorkflow.getId().equals(idcampo) && checked.booleanValue()) {
						novoValor.append(campo[1]);
					}
				}
				valor = novoValor.toString();

				this.validarValorCampo(valor, campoWorkflow.getObrigatorio(), campoWorkflow.getNome());

				campoUsoWorkflow.setValor(novoValor.toString());
				listaCamposAtualizar.add(campoUsoWorkflow);
				novoValor = new StringBuffer();
			}
		}
		return listaCamposAtualizar;
	}

	/**
	 * Recupera o campo uso workflow do tipo data, texto ou hora com seu novo valor
	 * 
	 * @param campo Campo com seu id e seu valor
	 * @param listaCampos Lista de campos persistida atualmente
	 * @return um {@link CampoUsoWorkflow}
	 * @throws NegocioException caso o campo obrigatório não esteje preenchido
	 */
	private CampoUsoWorkflow getCampoUsoWorkflowTexto(String[] campo, List<CampoUsoWorkflow> listaCampos)
			throws NegocioException {

		CampoUsoWorkflow novoCampoUsoWorkflow = null;
		Integer idCampo = Integer.valueOf(campo[0].substring(5)); // removendo o "campo" do inicio do id html
		String novoValor = "";

		for (CampoUsoWorkflow campoUsoWorkflow : listaCampos) {
			Campo campoWorkflow = campoUsoWorkflow.getCampo();
			if (campoWorkflow.getId().equals(idCampo)) {
				if (campo.length > 1) {
					novoValor = campo[1];
				}

				this.validarValorCampo(novoValor, campoWorkflow.getObrigatorio(), campoWorkflow.getNome());

				campoUsoWorkflow.setValor(novoValor);
				novoCampoUsoWorkflow = campoUsoWorkflow;
				break;
			}
		}
		return novoCampoUsoWorkflow;
	}

	/**
	 * Retorna um {@link List} de {@link CampoUsoWorkflow} de acordo com os valores informados
	 * 
	 * @param listaCampos Lista de campos persistida atualmente
	 * @param valores Novos valores a serem persistidos
	 * @return lista de campos do uso workflow a serem salvos
	 * @throws NegocioException caso o campo obrigatório não esteje preenchido
	 */
	private List<CampoUsoWorkflow> getListaCamposAtualizar(List<CampoUsoWorkflow> listaCampos, String[] valores)
			throws NegocioException {
		List<CampoUsoWorkflow> listaCamposAtualizar = new ArrayList<CampoUsoWorkflow>();
		List<String> camposRadioCheckbox = new ArrayList<String>();
		String[] campoParcial;

		for (String valor : valores) {
			campoParcial = valor.split("£");
			if (campoParcial.length <= 2) {
				//Campo texto, data ou hora. Basta recuperar o valor e atualizar.
				listaCamposAtualizar.add(this.getCampoUsoWorkflowTexto(campoParcial, listaCampos));
			} else {
				//Campo radio ou checkbox.
				camposRadioCheckbox.add(valor);
			}
		}
		if (!camposRadioCheckbox.isEmpty()) {
			listaCamposAtualizar.addAll(this.getCampoUsoWorkflowOpcoes(camposRadioCheckbox, listaCampos));
		}
		return listaCamposAtualizar;
	}

	/**
	 * Recupera o BO de {@link Tarefa}.
	 * 
	 * @return BO de {@link Tarefa}
	 */
	private TarefaBO getTarefaBO() {
		if (this.tarefaBO == null) {
			this.tarefaBO = Utils.getBean(TarefaBO.class);
		}
		return this.tarefaBO;
	}

	/**
	 * Notifica caso o departamento da nova tarefa for diferente do departamento da tarefa atual.
	 * 
	 * @param usoWorkflow antiga {@link Tarefa} do {@link UsoWorkflow}
	 * @param tarefaNova nova {@link Tarefa} do {@link UsoWorkflow}
	 */
	private void notificarMudancaDepartamento(UsoWorkflow usoWorkflow, Tarefa tarefaNova) {
		Departamento departamentoAtual = usoWorkflow.getTarefa().getAtividade().getDepartamento();
		Departamento departamentoNovo = tarefaNova.getAtividade().getDepartamento();
		if (!departamentoAtual.equals(departamentoNovo)) {
			String emailDepartamento = departamentoNovo.getEmail();
			if (StringUtils.isNotBlank(emailDepartamento)) {
				String corpoEmail =
						"A tarefa '" + tarefaNova.getNome()
								+ "' está sob responsabilidade deste departamento, referente ao registro: "
								+ usoWorkflow.getNumeroRegistro();
				this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR,
						Constantes.ASSUNTO_EMAIL_TAREFA_TRANSFERIDA, corpoEmail, emailDepartamento);
			}
		}
	}

	/**
	 * Notifica o novo usuário da tarefa, informando que há um novo registro pendente para ele.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} usado
	 */
	private void notificarMudancaUsuarioTarefa(UsoWorkflow usoWorkflow) {
		Tarefa tarefa = usoWorkflow.getTarefa();
		Usuario usuarioTarefa = tarefa.getUsuario();
		String emailUsuario = usuarioTarefa.getEmail();
		if (StringUtils.isNotBlank(emailUsuario)) {
			String corpoEmail =
					"A tarefa '" + tarefa.getNome() + "' está sob sua responsabilidade, referente ao registro: "
							+ usoWorkflow.getNumeroRegistro();
			this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR, Constantes.ASSUNTO_EMAIL_TAREFA_TRANSFERIDA,
					corpoEmail, emailUsuario);
		}
	}

	/**
	 * Efetua a validação do valor do campo
	 * 
	 * @param valor Valor a ser verificado
	 * @param obrigatorio Indicador para campo obrigatório
	 * @param nomeCampo Nome do campo a ser valiado
	 * @throws NegocioException caso o campo obrigatório não esteje preenchido
	 */
	private void validarValorCampo(String valor, boolean obrigatorio, String nomeCampo)
			throws NegocioException {
		if (StringUtils.isBlank(valor) && obrigatorio) {
			throw new NegocioException("erro.required", nomeCampo);
		}
		if (valor.length() > ConstantesDB.VALOR_CAMPO) {
			throw new NegocioException("erro.maxlength", nomeCampo, Integer.toString(ConstantesDB.VALOR_CAMPO));
		}
	}
}
