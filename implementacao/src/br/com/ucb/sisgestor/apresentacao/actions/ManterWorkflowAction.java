/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterWorkflowActionForm;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.impl.WorkflowBOImpl;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manuten��es em {@link Workflow}.
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
	 * @return forward da atualiza��o
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterWorkflowActionForm form = (ManterWorkflowActionForm) actionForm;
		Workflow workflow = workflowBO.obter(form.getId());
		this.copyProperties(workflow, form);

		boolean temPermissao = request.isUserInRole(ConstantesRoles.MANTER_WORKFLOW);

		//Usu�rio que n�o tem permiss�o apenas pode visualizar o workflow
		if (!temPermissao) {
			this.addMessageKey("erro.acessoNegado");
			return this.sendAJAXResponse(false);
		}
		workflowBO.atualizar(workflow);

		this.addMessageKey("mensagem.alterar", "Workflow");

		return this.sendAJAXResponse(true);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui um workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da exclus�o
	 * @throws Exception caso ocorra erro na opera��o
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterWorkflowActionForm form = (ManterWorkflowActionForm) formulario;

		Workflow workflow = workflowBO.obter(form.getId());

		boolean temPermissao = request.isUserInRole(ConstantesRoles.MANTER_WORKFLOW);
		if (!temPermissao) {
			this.addMessageKey("erro.excluir.naoPermitido");
			return this.sendAJAXResponse(false);
		}

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
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupNovoWorkflow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.findForward("popupNovoWorkflow");
	}


	/**
	 * Salva um workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclus�o
	 * @throws Exception caso exce��o seja lan�ada
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