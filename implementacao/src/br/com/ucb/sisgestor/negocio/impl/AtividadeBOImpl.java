/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.persistencia.ProcessoDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaAtividadeDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AtividadeBOImpl extends BaseBOImpl<Atividade, Integer> implements AtividadeBO {

	private AtividadeDAO	atividadeDAO;
	private ProcessoDAO	processoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Atividade atividade) throws NegocioException {
		this.validarSePodeAlterarWorkflow(atividade.getProcesso().getWorkflow());
		this.atividadeDAO.atualizar(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idProcesso, String[] fluxos, String[] posicoes)
			throws NegocioException {

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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
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
		return this.atividadeDAO.getByNomeDescricaoDepartamento(nome, descricao, departamento, idProcesso,
				paginaAtual);
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
		PesquisaAtividadeDTO dto = (PesquisaAtividadeDTO) parametros;
		return this.atividadeDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getDepartamento(),
				dto.getIdProcesso());
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Atividade atividade) throws NegocioException {
		Processo processo = this.processoDAO.obter(atividade.getProcesso().getId());
		this.validarSePodeAlterarWorkflow(processo.getWorkflow());
		this.atividadeDAO.salvar(atividade);
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
	 * Atribui o DAO de {@link Processo}.
	 * 
	 * @param processoDAO DAO de {@link Processo}
	 */
	@Autowired
	public void setProcessoDAO(ProcessoDAO processoDAO) {
		this.processoDAO = processoDAO;
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
				if (((anteriores == null) || anteriores.isEmpty())
						&& ((posteriores == null) || posteriores.isEmpty())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Finaliza a valida��o do fluxo e lan�a exce��o se fluxo for inv�lido
	 * 
	 * @param temAtividadesIsolados {@link boolean} Indicador para ocorr�ncia de atividades isoladas
	 * @param temInicio {@link boolean} Indicador para haver atividade inicial
	 * @param temMaisDeUmInicio {@link boolean} Indicador para a ocorr�ncia de mais de uma atividade inicial
	 * @param temFim {@link boolean} Indicador para a ocorr�ncia de pelo menos uma atividade final
	 * @param exceptionIsolado Exce��o para o indicador de atividades isoladas
	 * @param exceptionInicial Exce��o para o indicador de atividades iniciais
	 * @throws NegocioException Exce��o a ser lan�ada
	 */
	private void finalizarValidacaoFluxos(boolean temAtividadesIsoladas, boolean temInicio,
			boolean temMaisDeUmInicio, boolean temFim, NegocioException exceptionIsolado,
			NegocioException exceptionInicial) throws NegocioException {

		//N�o permite atividades isoladas
		if (temAtividadesIsoladas) {
			throw exceptionIsolado;
		}
		//N�o permite a inexist�ncia de um in�cio
		if (!temInicio) {
			throw exceptionInicial;
		}

		//N�o permite que se tenha mais de um in�cio
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//N�o permite a inexist�ncia de pelo menos um final
		if (!temFim) {
			throw new NegocioException("erro.fluxo.final.processo");
		}
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
	private void inicializarValidacaoFluxo(List<TransacaoAtividade> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores,
			List<Atividade> listaAtividades) throws NegocioException {
		//Valida para que haja defini��o de fluxos
		if ((listaAtividades != null) && !listaAtividades.isEmpty()
				&& ((transacoes != null) && transacoes.isEmpty())) {
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
		List<Atividade> listaAtividades = this.atividadeDAO.getByProcesso(idProcesso);

		if (listaAtividades.size() == 1) {
			return;
		}

		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.atividade");
		NegocioException exceptionIsolado = new NegocioException("erro.fluxo.isolado.atividade");
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		boolean temInicio = false;
		boolean temMaisDeUmInicio = false;
		boolean temFim = false;
		boolean temAtividadesIsolados = false;
		Integer id;
		Integer atividadeAnterior;
		Integer atividadePosterior;


		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, listaAtividades);

		for (Atividade atividade : listaAtividades) {
			id = atividade.getId();
			atividadeAnterior = mapAnteriores.get(id);
			atividadePosterior = mapPosteriores.get(id);
			if ((atividadeAnterior == null) && (atividadePosterior == null)) {
				temAtividadesIsolados = true;
				exceptionIsolado.putValorDevolvido("id" + id, id.toString());
			}
			if (atividadePosterior == null) {
				temFim = true;
			}
			if (atividadeAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (temInicio) {
					temMaisDeUmInicio = true;
				}
				temInicio = true;
			}
			if (atividadePosterior == null) {
				temFim = true;
			}
		}

		this.finalizarValidacaoFluxos(temAtividadesIsolados, temInicio, temMaisDeUmInicio, temFim,
				exceptionIsolado, exceptionInicial);
	}

	/**
	 * Verifica se o {@link Workflow} pode ser alterado, para n�o poder ocorrer altera��o nas atividades.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} n�o possa ser alterado
	 */
	private void validarSePodeAlterarWorkflow(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo() || (workflow.getDataHoraExclusao() != null)) {
			throw new NegocioException("erro.workflow.alterar");
		}
	}
}
