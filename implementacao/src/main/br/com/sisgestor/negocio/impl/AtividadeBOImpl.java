/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2008 por Thiago
 */
package br.com.sisgestor.negocio.impl;

import br.com.sisgestor.entidade.Atividade;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.TransacaoAtividade;
import br.com.sisgestor.entidade.Workflow;
import br.com.sisgestor.negocio.AtividadeBO;
import br.com.sisgestor.negocio.ProcessoBO;
import br.com.sisgestor.negocio.TarefaBO;
import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.persistencia.AtividadeDAO;
import br.com.sisgestor.util.dto.PesquisaManterAtividadeDTO;
import br.com.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de neg�cio para {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
@Service("atividadeBO")
public class AtividadeBOImpl extends BaseWorkflowBOImpl<Atividade> implements AtividadeBO {

	private ProcessoBO processoBO;
	private TarefaBO tarefaBO;
	private AtividadeDAO atividadeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Atividade atividade) throws NegocioException {
		this.validarSePodeAlterarWorkflow(atividade.getProcesso().getWorkflow());
		Atividade atividadeDesatualizada = this.atividadeDAO.obterAntigo(atividade.getId());
		if (!atividadeDesatualizada.getDepartamento().equals(atividade.getDepartamento())) {
			//caso o departamento foi modificado, os usu�rios n�o poder�o ser os mesmos
			for (Tarefa tarefa : atividade.getTarefas()) {
				tarefa.setUsuario(null);
				this.getTarefaBO().atualizar(tarefa);
			}
		}
		this.atividadeDAO.atualizar(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizarTransacoes(Integer idProcesso, String[] fluxos, String[] posicoes) throws NegocioException {
		Workflow workflow = this.getProcessoBO().obter(idProcesso).getWorkflow();
		this.validarSePodeAlterarWorkflow(workflow);

		List<TransacaoAtividade> transacoes = this.getTransacoes(fluxos);
		this.validarFluxo(transacoes, idProcesso);

		List<TransacaoAtividade> atual = this.atividadeDAO.recuperarTransacoesDoProcesso(idProcesso);

		if (atual != null) {
			//excluindo as transa��es atuais
			for (TransacaoAtividade transacao : atual) {
				this.atividadeDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transa��es enviadas
		for (TransacaoAtividade transacao : transacoes) {
			this.atividadeDAO.salvarTransacao(transacao);
		}

		//Salvando as novas posi��es dos processos
		Atividade atividade;
		String[] atividades;
		for (String posicao : posicoes) {
			atividades = posicao.split(","); //Formato: <id>,<left>,<top>
			atividade = this.atividadeDAO.obter(Integer.valueOf(atividades[0]));

			atividade.setLeft(Integer.valueOf(atividades[1]));
			atividade.setTop(Integer.valueOf(atividades[2]));

			this.atividadeDAO.atualizar(atividade);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Atividade atividade) throws NegocioException {
		this.validarSePodeAlterarWorkflow(atividade.getProcesso().getWorkflow());
		List<Tarefa> lista = atividade.getTarefas();
		//N�o permite excluir uma atividade que cont�m tarefas
		if ((lista != null) && !lista.isEmpty()) {
			throw new NegocioException("erro.atividade.tarefas");
		}
		this.atividadeDAO.excluir(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual) {
		return this.atividadeDAO.getByNomeDescricaoDepartamento(nome, descricao, departamento, idProcesso, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Atividade> getByProcesso(Integer processo) {
		return this.atividadeDAO.getByProcesso(processo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterAtividadeDTO dto = (PesquisaManterAtividadeDTO) parametros;
		return this.atividadeDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getDepartamento(), dto
				.getIdProcesso());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Atividade obter(Integer pk) {
		return this.atividadeDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Atividade> obterTodos() {
		return this.atividadeDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Atividade atividade) throws NegocioException {
		Processo processo = this.getProcessoBO().obter(atividade.getProcesso().getId());
		this.validarSePodeAlterarWorkflow(processo.getWorkflow());
		return this.atividadeDAO.salvar(atividade);
	}

	/**
	 * Atribui o DAO de {@link Atividade}.
	 * 
	 * @param atividadeDAO DAO de {@link Atividade}
	 */
	@Autowired
	public void setAtividadeDAO(AtividadeDAO atividadeDAO) {
		this.atividadeDAO = atividadeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public boolean temFluxoDefinido(Integer idProcesso) {
		List<Atividade> atividades = this.getByProcesso(idProcesso);
		List<TransacaoAtividade> anteriores;
		List<TransacaoAtividade> posteriores;
		//caso tenha apenas uma atividade, ela j� � inicial e final.
		if ((atividades != null) && (atividades.size() > 1)) {
			for (Atividade atividade : atividades) {
				anteriores = atividade.getTransacoesAnteriores();
				posteriores = atividade.getTransacoesPosteriores();
				if (CollectionUtils.isEmpty(anteriores) && CollectionUtils.isEmpty(posteriores)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Recupera o BO de {@link Processo}.
	 * 
	 * @return BO de {@link Processo}
	 */
	private ProcessoBO getProcessoBO() {
		if (this.processoBO == null) {
			this.processoBO = utils.getBean(ProcessoBO.class);
		}
		return this.processoBO;
	}

	/**
	 * Recupera o BO de {@link Tarefa}.
	 * 
	 * @return BO de {@link Tarefa}
	 */
	private TarefaBO getTarefaBO() {
		if (this.tarefaBO == null) {
			this.tarefaBO = utils.getBean(TarefaBO.class);
		}
		return this.tarefaBO;
	}

	/**
	 * Recupera a lista de transa��es criadas para as atividades.
	 * 
	 * @param fluxos fluxos definidos pelo usu�rio
	 * @return {@link List} de {@link TransacaoAtividade}
	 */
	private List<TransacaoAtividade> getTransacoes(String[] fluxos) {
		List<TransacaoAtividade> lista = new ArrayList<TransacaoAtividade>();
		if (fluxos != null) {
			TransacaoAtividade transacao;
			Atividade atividadeAnterior;
			Atividade atividadePosterior;
			for (String fluxo : fluxos) {
				String[] atividades = fluxo.split(","); //Formato: <origem>,<destino>

				transacao = new TransacaoAtividade();
				atividadeAnterior = new Atividade();
				atividadePosterior = new Atividade();

				atividadeAnterior.setId(Integer.parseInt(atividades[0]));
				atividadePosterior.setId(Integer.parseInt(atividades[1]));

				transacao.setAnterior(atividadeAnterior);
				transacao.setPosterior(atividadePosterior);
				lista.add(transacao);
			}
		}
		return lista;
	}

	/**
	 * Inicializa os mapas para a valida��o do fluxo.
	 * 
	 * @param transacoes transa��es de atividades
	 * @param mapAnteriores {@link Map} para armazenar as transa��es anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transa��es posteriores
	 * @param listaAtividades {@link List} com as atividades
	 * @throws NegocioException caso n�o haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoAtividade> transacoes, Map<Integer, Integer> mapAnteriores,
			Map<Integer, Integer> mapPosteriores, List<Atividade> listaAtividades) throws NegocioException {
		//Valida para que haja defini��o de fluxos
		if ((listaAtividades != null) && !listaAtividades.isEmpty() && ((transacoes != null) && transacoes.isEmpty())) {
			throw new NegocioException("erro.fluxo.definicao.atividade");
		}

		for (Atividade atividade : listaAtividades) {
			mapAnteriores.put(atividade.getId(), null);
			mapPosteriores.put(atividade.getId(), null);
		}

		Integer anterior;
		Integer posterior;
		for (TransacaoAtividade transacaoAtividade : transacoes) {
			anterior = transacaoAtividade.getAnterior().getId();
			posterior = transacaoAtividade.getPosterior().getId();

			mapAnteriores.put(posterior, anterior);
			mapPosteriores.put(anterior, posterior);
		}
	}

	/**
	 * Realiza as valida��es das {@link TransacaoAtividade} informadas.
	 * 
	 * @param transacoes transa��es definidas pelo usu�rio
	 * @param idProcesso c�digo identificador do processo
	 * @throws NegocioException caso regra de neg�cio seja violada
	 */
	private void validarFluxo(List<TransacaoAtividade> transacoes, Integer idProcesso) throws NegocioException {
		List<Atividade> lista = this.atividadeDAO.getByProcesso(idProcesso);

		if ((lista == null) || lista.isEmpty()) {
			throw new NegocioException("erro.fluxo.definicao.atividade.vazia");
		}
		if (lista.size() == 1) {
			return;
		}

		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.atividade");
		NegocioException exceptionIsolado = new NegocioException("erro.fluxo.isolado.atividade");
		NegocioException exceptionFinal = new NegocioException("erro.fluxo.final.atividade");
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, lista);

		this.validarFluxos(lista, exceptionIsolado, exceptionInicial, exceptionFinal, mapAnteriores, mapPosteriores);
	}
}
