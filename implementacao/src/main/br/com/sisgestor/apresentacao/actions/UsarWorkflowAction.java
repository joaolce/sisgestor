/*
 * Projeto: sisgestor
 * Criação: 18/03/2009 por Gustavo
 */
package br.com.sisgestor.apresentacao.actions;

import br.com.sisgestor.util.DataUtil;
import br.com.sisgestor.util.constantes.ConstantesContexto;
import br.com.sisgestor.negocio.TarefaBO;
import br.com.sisgestor.negocio.UsoWorkflowBO;
import br.com.sisgestor.negocio.WorkflowBO;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.UsoWorkflow;
import br.com.sisgestor.entidade.Workflow;
import br.com.sisgestor.apresentacao.forms.UsarWorkflowActionForm;
import br.com.sisgestor.apresentacao.dwr.utils.AjaxResponse;
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
	 * Salva as alterações em {@link UsoWorkflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return <code>null</code>
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward confirmar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		Integer idUsoWorkflow = form.getId();
		this.usoWorkflowBO.salvarValoresCampos(form.getValor(), idUsoWorkflow);
		this.addMessageKey("mensagem.alterar", "Registro");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.setSessionAttribute(ConstantesContexto.REGISTROS_FINALIZADOS, Boolean.FALSE);
		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Abre a tela inicial de entrada para visualizar os {@link UsoWorkflow} finalizados.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward entradaFinalizados(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) actionForm;
		form.setListaWorkflows(this.workflowBO.obterTodos());
		this.setSessionAttribute(ConstantesContexto.REGISTROS_FINALIZADOS, Boolean.TRUE);
		return this.findForward("entradaFinalizados");
	}

	/**
	 * Dá inicio a tarefa do {@link UsoWorkflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return <code>null</code>
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward iniciarTarefa(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(form.getId());
		this.usoWorkflowBO.iniciarTarefa(usoWorkflow);

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setStatus(true);
		ajaxResponse.putValorRetorno("dataHora", DataUtil.DATA_HORA_MEDIUM.format(usoWorkflow
				.getDataHoraInicio()));
		return this.sendAJAXResponse(ajaxResponse);
	}

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
	 * Abre popup para alterar a anotação de um {@link UsoWorkflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupAnotacao(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(form.getId());
		form.setAnotacao(usoWorkflow.getAnotacao());
		return this.findForward("popupAnotacao");
	}

	/**
	 * Abre popup para iniciar um {@link UsoWorkflow}.
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
	 * Abre o popup das próximas tarefas disponíveis.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward} do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupProximasTarefas(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(form.getId());
		form.setProximasTarefas(this.tarefaBO.recuperarProximasTarefas(usoWorkflow));
		return this.findForward("popupProximasTarefas");
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
	 * Modifica a tarefa atual do {@link UsoWorkflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return <code>null</code>
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward proximaTarefa(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;

		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(form.getId());
		Integer idTarefa = form.getTarefa();

		this.usoWorkflowBO.modificarTarefa(usoWorkflow, idTarefa);

		if (idTarefa == -1) {
			this.addMessageKey("mensagem.usoWorkflow.finalizado");
		} else {
			this.addMessageKey("mensagem.usoWorkflow.tarefaModificada");
		}
		return this.sendAJAXResponse(true);
	}

	/**
	 * Salva a anotação do {@link UsoWorkflow}.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return <code>null</code>
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward salvarAnotacao(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsarWorkflowActionForm form = (UsarWorkflowActionForm) formulario;
		Integer idUsoWorkflow = form.getId();
		this.usoWorkflowBO.salvarAnotacao(idUsoWorkflow, form.getAnotacao());
		this.addMessageKey("mensagem.alterar.a", "Anotação");
		return this.sendAJAXResponse(true);
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
