/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import org.hibernate.criterion.Order;

/**
 * Implementação da interface de acesso a dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class WorkflowDAOImpl extends BaseDAOImpl<Workflow, Integer> implements WorkflowDAO {

	private static final WorkflowDAO	instancia	= new WorkflowDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link WorkflowDAOImpl}
	 */
	private WorkflowDAOImpl() {
		super(Workflow.class);
	}

	/**
	 * Recupera a instância de {@link WorkflowDAO}. pattern singleton.
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
