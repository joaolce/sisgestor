/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.persistencia.impl.WorkflowDAOImpl;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaWorkflowDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;

/**
 * Objeto de negócio para {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class WorkflowBOImpl extends BaseBOImpl<Workflow, Integer> implements WorkflowBO {

	private static final WorkflowBO	instancia	= new WorkflowBOImpl();
	private WorkflowDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link WorkflowBOImpl}.
	 */
	private WorkflowBOImpl() {
		this.dao = WorkflowDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link WorkflowBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link WorkflowBO}
	 */
	public static WorkflowBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Workflow workflow) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(workflow);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
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
		return this.dao.getByNomeDescricaoAtivo(nome, descricao, ativo, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaWorkflowDTO dto = (PesquisaWorkflowDTO) parametros;
		return this.dao.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getAtivo());
	}

	/**
	 * {@inheritDoc}
	 */
	public Workflow obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Workflow workflow) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvar(workflow);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}
}
