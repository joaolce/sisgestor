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
