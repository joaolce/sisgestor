/*
 * Projeto: sisgestor
 * Criação: 18/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.dwr.utils.AjaxResponse;
import br.com.ucb.sisgestor.apresentacao.forms.UsarWorkflowActionForm;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para utilizar um {@link Workflow}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowAction extends BaseAction {

	private UsoWorkflowBO	usoWorkflowBO;
	private WorkflowBO		workflowBO;
	private TarefaBO			tarefaBO;

	/**
	 * Dá início a um uso de {@link Workflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return <code>null</code>
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward iniciarUso(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		UsoWorkflow usoWorkflow = new UsoWorkflow();
		Workflow workflow = this.workflowBO.obter(form.getWorkflow());
		usoWorkflow.setWorkflow(workflow);
		usoWorkflow.setTarefa(this.tarefaBO.recuperarPrimeiraTarefa(workflow));
		Integer idUso = this.usoWorkflowBO.salvar(usoWorkflow);

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setGeneratedID(idUso);
		ajaxResponse.setStatus(true);
		return this.sendAJAXResponse(ajaxResponse);
	}

	/**
	 * Abre popup para iniciar um {@link Workflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupIniciarWorkflow(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;

		form.setListaWorkflows(this.workflowBO.recuperarPendentesIniciar());

		return this.findForward("popupIniciarWorkflow");
	}

	/**
	 * Abre o popup de um uso do {@link Workflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupUsoWorkflow(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.findForward("popupUsoWorkflow");
	}

	/**
	 * Atribui o BO de {@link Tarefa}.
	 * 
	 * @param tarefaBO BO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaBO(TarefaBO tarefaBO) {
		this.tarefaBO = tarefaBO;
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
