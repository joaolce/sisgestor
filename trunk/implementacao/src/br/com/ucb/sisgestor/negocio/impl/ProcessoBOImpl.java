/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.ProcessoDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaProcessoDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
@Service("processoBO")
public class ProcessoBOImpl extends BaseBOImpl<Processo, Integer> implements ProcessoBO {

	private ProcessoDAO	processoDAO;
	private WorkflowBO	workflowBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Processo processo) throws NegocioException {
		this.validarSeWorkflowAtivo(processo.getWorkflow());
		this.processoDAO.atualizar(processo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idWorkflow, List<TransacaoProcesso> transacoes)
			throws NegocioException {
		this.validarFluxo(transacoes, idWorkflow);
		List<TransacaoProcesso> atual = this.processoDAO.recuperarTransacoesDoWorkflow(idWorkflow);
		if (atual != null) {
			//excluindo as transações atuais
			for (TransacaoProcesso transacao : atual) {
				this.processoDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transações enviadas
		for (TransacaoProcesso transacao : transacoes) {
			this.processoDAO.salvarTransacao(transacao);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Processo processo) throws NegocioException {
		this.validarSeWorkflowAtivo(processo.getWorkflow());
		List<Atividade> lista = processo.getAtividades();
		//Não permite excluir um processo que contém atividades
		if ((lista != null) && !lista.isEmpty()) {
			throw new NegocioException("erro.processo.atividades");
		}
		this.processoDAO.excluir(processo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow,
			Integer paginaAtual) {
		return this.processoDAO.getByNomeDescricao(nome, descricao, idWorkflow, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> getByWorkflow(Integer workflow) {
		return this.processoDAO.getByWorkflow(workflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaProcessoDTO dto = (PesquisaProcessoDTO) parametros;
		return this.processoDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getIdWorkflow());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Processo obter(Integer pk) {
		return this.processoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> obterTodos() {
		return this.processoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Processo processo) throws NegocioException {
		this.validarSeWorkflowAtivo(processo.getWorkflow());
		this.processoDAO.salvar(processo);
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
	 * @param transacoes transações de processo
	 * @param mapAnteriores {@link Map} para armazenar as transações anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transações posteriores
	 * @param listaProcessos {@link List} com os processos
	 * @throws NegocioException caso não haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoProcesso> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores,
			List<Processo> listaProcessos) throws NegocioException {
		//Valida para que haja definição de fluxos
		if ((listaProcessos != null) && !listaProcessos.isEmpty()
				&& ((transacoes != null) && transacoes.isEmpty())) {
			throw new NegocioException("erro.fluxo.definicao.processo");
		}

		for (Processo processo : listaProcessos) {
			mapAnteriores.put(processo.getId(), null);
			mapPosteriores.put(processo.getId(), null);
		}

		Integer anterior;
		Integer posterior;
		for (TransacaoProcesso transacaoProcesso : transacoes) {
			anterior = transacaoProcesso.getAnterior().getId();
			posterior = transacaoProcesso.getPosterior().getId();

			mapAnteriores.put(posterior, anterior);
			mapPosteriores.put(anterior, posterior);
		}
	}

	/**
	 * Realiza as validações das {@link TransacaoProcesso} informadas.
	 * 
	 * @param transacoes transações definidas pelo usuário
	 * @param idWorkflow código identificador do workflow
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	private void validarFluxo(List<TransacaoProcesso> transacoes, Integer idWorkflow) throws NegocioException {
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		boolean inicio = false;
		boolean fim = false;
		List<Processo> listaProcessos = this.processoDAO.getByWorkflow(idWorkflow);

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, listaProcessos);

		//Valida para que haja ao menos um processo final, e exatamente um inicial
		Integer id;
		Integer processoAnterior;
		Integer processoPosterior;
		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.processo");
		for (Processo processo : listaProcessos) {
			id = processo.getId();
			processoAnterior = mapAnteriores.get(id);
			processoPosterior = mapPosteriores.get(id);
			if ((processoAnterior == null) && (processoPosterior == null)) {
				NegocioException ex = new NegocioException("erro.fluxo.isolado.processo");
				ex.putValorDevolvido("id", id);
				throw ex;
			}
			if (processoAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (inicio) {
					throw exceptionInicial;
				}
				inicio = true;
			}
			if (processoPosterior == null) {
				fim = true;
			}
		}
		if (!inicio) {
			throw exceptionInicial;
		}
		if (!fim) {
			throw new NegocioException("erro.fluxo.final.processo");
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
