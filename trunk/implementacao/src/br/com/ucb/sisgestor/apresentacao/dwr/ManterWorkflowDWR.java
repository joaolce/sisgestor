/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.impl.WorkflowBOImpl;

/**
 * Objeto DWR de manter workflow do projeto.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class ManterWorkflowDWR extends BaseDWR {

	private static WorkflowBO	workflowBO;

	static {
		workflowBO = WorkflowBOImpl.getInstancia();
	}

	/**
	 * Pesquisa o {@link Workflow} pelo id.
	 * 
	 * @param id identificador do workflow
	 * @return workflow encontrado
	 */
	public Workflow getById(Integer id) {
		Workflow workflow = workflowBO.obter(id);
		return workflow;
	}
}
