/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.impl.WorkflowBOImpl;

/**
 * Action para manutenções em {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class ManterWorkflowAction extends BaseAction {

	private static WorkflowBO	workflowBO;

	static {
		workflowBO = WorkflowBOImpl.getInstancia();
	}
}
