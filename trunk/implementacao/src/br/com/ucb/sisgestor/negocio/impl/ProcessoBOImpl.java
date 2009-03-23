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
	 * Realiza as validações das {@link TransacaoProcesso} informadas.
	 * 
	 * @param transacoes Transações definidas pelo usuário
	 * @param idWorkflow Código identificador do workflow
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	private void validarFluxo(List<TransacaoProcesso> transacoes, Integer idWorkflow) throws NegocioException {
		boolean inicio = false;
		boolean fim = false;
		HashMap<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		List<Processo> listaProcessos = this.processoDAO.getByWorkflow(idWorkflow);
		int quantidadeProcessos = listaProcessos.size();

		//Valida para que haja definição de fluxos
		if ((listaProcessos != null) && !listaProcessos.isEmpty() && transacoes.isEmpty()) {
			throw new NegocioException("erro.fluxo.definicao.processo");
		}
		//Valida para que um processo, ou fluxo de processos, não fique isolado
		if (transacoes.size() < (quantidadeProcessos - 1)) {
			throw new NegocioException("erro.fluxo.isolado.processo");
		}

		for (Processo processo : listaProcessos) {
			mapPosteriores.put(processo.getId(), null);
			mapAnteriores.put(processo.getId(), null);
		}

		for (TransacaoProcesso transacaoProcesso : transacoes) {
			mapAnteriores.remove(transacaoProcesso.getPosterior().getId());
			mapAnteriores.put(transacaoProcesso.getPosterior().getId(), transacaoProcesso.getAnterior().getId());

			mapPosteriores.remove(transacaoProcesso.getAnterior().getId());
			mapPosteriores
					.put(transacaoProcesso.getAnterior().getId(), transacaoProcesso.getPosterior().getId());
		}

		//Valida para que haja ao menos um processo final
		for (Processo processo : listaProcessos) {
			if (mapPosteriores.get(processo.getId()) == null) {
				fim = true;
				break;
			}
		}

		if (!fim) {
			throw new NegocioException("erro.fluxo.final.processo");
		}

		//Valida para conter apenas um processo inicial
		for (Processo processo : listaProcessos) {
			if (mapAnteriores.get(processo.getId()) == null) {
				if (inicio) {
					throw new NegocioException("erro.fluxo.inicial.processo");
				}
				inicio = true;
			}
		}
		//Se...
		if (!mapAnteriores.containsValue(null)) {
			throw new NegocioException("erro.fluxo.inicial.processo");
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
