/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterWorkflowActionForm;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.impl.WorkflowBOImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

	/**
	 * Atualiza um workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterWorkflowActionForm form = (ManterWorkflowActionForm) actionForm;

		Workflow workflow = new Workflow();
		this.copyProperties(workflow, form);

		workflowBO.atualizar(workflow);

		this.addMessageKey("mensagem.alterar", "Workflow");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Exclui um workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da exclusão
	 * @throws Exception caso ocorra erro na operação
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterWorkflowActionForm form = (ManterWorkflowActionForm) formulario;

		Workflow workflow = workflowBO.obter(form.getId());

		workflowBO.excluir(workflow);

		this.addMessageKey("mensagem.excluir", "Workflow");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir um novo workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovoWorkflow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward("popupNovoWorkflow");
	}

	/**
	 * Salva (inclui) um novo workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterWorkflowActionForm form = (ManterWorkflowActionForm) formulario;

		Workflow workflow = new Workflow();
		this.copyProperties(workflow, form);

		workflowBO.salvar(workflow);

		this.addMessageKey("mensagem.salvar", "Workflow");
		return this.sendAJAXResponse(true);
	}
}
