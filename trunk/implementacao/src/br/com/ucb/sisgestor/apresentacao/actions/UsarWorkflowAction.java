/*
 * Projeto: sisgestor
 * Criação: 18/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.UsarWorkflowActionForm;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para ultilizar um {@link Workflow}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowAction extends BaseAction {

	private UsoWorkflowBO	usoWorkflowBO;
	private WorkflowBO		workflowBO;

	/**
	 * Exclui o(s) anexo(s) selecionado(s)
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward excluirAnexo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//TODO Implementar

		this.addMessageKey("mensagem.excluir", "Anexo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre popup para iniciar um workflow
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupIniciarWorkflow(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;

		form.setListaWorkflows(this.workflowBO.recuperarPendentesIniciar());

		return this.findForward("popupIniciarWorkflow");
	}

	/**
	 * Abre popup para visualizar os anexos
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupVisualizarAnexos(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;

		form.setListaAnexos(this.usoWorkflowBO.getAnexos(form.getId()));

		return this.findForward("popupVisualizarAnexos");
	}

	/**
	 * Atribui o BO de {@link UsoWorkflowBO}.
	 * 
	 * @param usoWorkflowBO BO de {@link UsoWorkflowBO}
	 */
	@Autowired
	public void setUsoWorkflowBO(UsoWorkflowBO usoWorkflowBO) {
		this.usoWorkflowBO = usoWorkflowBO;
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
}
