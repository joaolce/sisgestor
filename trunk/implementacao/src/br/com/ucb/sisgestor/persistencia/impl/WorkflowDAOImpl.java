/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import org.hibernate.criterion.Order;

/**
 * Implementa��o da interface de acesso a dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class WorkflowDAOImpl extends BaseDAOImpl<Workflow, Integer> implements WorkflowDAO {

	private static final WorkflowDAO	instancia	= new WorkflowDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link WorkflowDAOImpl}
	 */
	private WorkflowDAOImpl() {
		super(Workflow.class);
	}

	/**
	 * Recupera a inst�ncia de {@link WorkflowDAO}. pattern singleton.
	 * 
	 * @return {@link WorkflowDAO}
	 */
	public static WorkflowDAO getInstancia() {
		return instancia;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}
}
