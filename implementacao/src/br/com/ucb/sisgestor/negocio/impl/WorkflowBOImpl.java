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
import java.util.List;

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
	public void atualizar(Workflow obj) throws NegocioException {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Workflow obj) throws NegocioException {
		// TODO Auto-generated method stub
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
	public void salvar(Workflow obj) throws NegocioException {
		// TODO Auto-generated method stub
	}
}
