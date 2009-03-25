/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaAtividadeDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
@Service("atividadeBO")
public class AtividadeBOImpl extends BaseBOImpl<Atividade, Integer> implements AtividadeBO {

	private AtividadeDAO	atividadeDAO;
	private WorkflowBO	workflowBO;
	private ProcessoBO	processoBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Atividade atividade) throws NegocioException {
		Processo processo = this.processoBO.obter(atividade.getProcesso().getId());
		this.validarSeWorkflowAtivo(processo.getWorkflow());
		this.atividadeDAO.atualizar(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idProcesso, List<TransacaoAtividade> transacoes)
			throws NegocioException {
		this.validarFluxo(transacoes, idProcesso);
		List<TransacaoAtividade> atual = this.atividadeDAO.recuperarTransacoesDoProcesso(idProcesso);
		if (atual != null) {
			//excluindo as transações atuais
			for (TransacaoAtividade transacao : atual) {
				this.atividadeDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transações enviadas
		for (TransacaoAtividade transacao : transacoes) {
			this.atividadeDAO.salvarTransacao(transacao);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Atividade atividade) throws NegocioException {
		this.validarSeWorkflowAtivo(atividade.getProcesso().getWorkflow());
		List<Tarefa> lista = atividade.getTarefas();
		//Não permite excluir uma atividade que contém tarefas
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
		Processo processo = this.processoBO.obter(atividade.getProcesso().getId());
		this.validarSeWorkflowAtivo(processo.getWorkflow());
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
	 * Atribui o BO de {@link Processo}.
	 * 
	 * @param processoBO BO de {@link Processo}
	 */
	@Autowired
	public void setProcessoBO(ProcessoBO processoBO) {
		this.processoBO = processoBO;
	}

	/**
	 * Atribui o BO de {@link Workflow}.
	 * 
	 * @param workflowBO BO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowBO(WorkflowBO workflowBO) {
		this.workflowBO = workflowBO;
	}

	/**
	 * Inicializa os mapas para a validação do fluxo.
	 * 
	 * @param transacoes transações de atividades
	 * @param mapAnteriores {@link Map} para armazenar as transações anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transações posteriores
	 * @param listaAtividades {@link List} com as atividades
	 * @throws NegocioException caso não haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoAtividade> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores,
			List<Atividade> listaAtividades) throws NegocioException {
		//Valida para que haja definição de fluxos
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
	 * Realiza as validações das {@link TransacaoAtividade} informadas.
	 * 
	 * @param transacoes transações definidas pelo usuário
	 * @param idProcesso código identificador do processo
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	private void validarFluxo(List<TransacaoAtividade> transacoes, Integer idProcesso) throws NegocioException {
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		boolean inicio = false;
		boolean fim = false;
		List<Atividade> listaAtividades = this.atividadeDAO.getByProcesso(idProcesso);

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, listaAtividades);

		//Valida para que haja ao menos um processo final, e exatamente um inicial
		Integer id;
		Integer atividadeAnterior;
		Integer atividadePosterior;
		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.atividade");
		for (Atividade atividade : listaAtividades) {
			id = atividade.getId();
			atividadeAnterior = mapAnteriores.get(id);
			atividadePosterior = mapPosteriores.get(id);
			if ((atividadeAnterior == null) && (atividadePosterior == null)) {
				NegocioException ex = new NegocioException("erro.fluxo.isolado.atividade");
				ex.putValorDevolvido("id", id);
				throw ex;
			}
			if (atividadeAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (inicio) {
					throw exceptionInicial;
				}
				inicio = true;
			}
			if (atividadePosterior == null) {
				fim = true;
			}
		}
		if (!inicio) {
			throw exceptionInicial;
		}
		if (!fim) {
			throw new NegocioException("erro.fluxo.final.atividade");
		}
	}

	/**
	 * Verifica se o {@link Workflow} está ativo, caso esteja não pode ocorrer alteração nos processos.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} esteja ativo
	 */
	private void validarSeWorkflowAtivo(Workflow workflow) throws NegocioException {
		Workflow workflowAtivo = this.workflowBO.obter(workflow.getId());
		if (workflowAtivo.getAtivo()) {
			throw new NegocioException("erro.workflowAtivo");
		}
	}
}
