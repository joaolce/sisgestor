/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaWorkflowDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@Service("workflowBO")
public class WorkflowBOImpl extends BaseBOImpl<Workflow, Integer> implements WorkflowBO {

	private WorkflowDAO	workflowDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Workflow workflow) throws NegocioException {
		this.workflowDAO.atualizar(workflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Workflow workflow) throws NegocioException {
		workflow.setAtivo(Boolean.FALSE);
		workflow.setDataHoraExclusao(DataUtil.getDataHoraAtual());
		this.atualizar(workflow);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo,
			Integer paginaAtual) {
		return this.workflowDAO.getByNomeDescricaoAtivo(nome, descricao, ativo, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaWorkflowDTO dto = (PesquisaWorkflowDTO) parametros;
		return this.workflowDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getAtivo());
	}

	/**
	 * {@inheritDoc}
	 */
	public Workflow obter(Integer pk) {
		return this.workflowDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> obterTodos() {
		return this.workflowDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Workflow workflow) throws NegocioException {
		this.workflowDAO.salvar(workflow);
	}

	/**
	 * Atribui o DAO de {@link Workflow}.
	 * 
	 * @param workflowDAO DAO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowDAO(WorkflowDAO workflowDAO) {
		this.workflowDAO = workflowDAO;
	}
}
