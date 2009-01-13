/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterDepartamentoActionForm;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.util.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manutenções em {@link Departamento}.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterDepartamentoAction extends BaseAction {

	private static DepartamentoBO	departamentoBO;

	static {
		departamentoBO = DepartamentoBOImpl.getInstancia();
	}

	/**
	 * Atualiza um departamento.
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return forward da atualização
	 * @throws Exception
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		Departamento departamento = new Departamento();
		Utils.copyProperties(departamento, form);

		departamentoBO.atualizar(departamento);

		this.addMessageKey("mensagem.departamento.alterar");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) actionForm;
		form.setListaDepartamentos(departamentoBO.obterTodos());

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Excluí um departamento.
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return forward da exclusão
	 * @throws Exception
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		Departamento departamento = departamentoBO.obter(form.getId());

		departamentoBO.excluir(departamento);

		this.addMessageKey("mensagem.departamento.excluir");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Tela de popup para incluir um novo departamento.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward do popup
	 */
	public ActionForward popupNovoDepartamento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ManterDepartamentoActionForm frm = (ManterDepartamentoActionForm) form;

		frm.setListaDepartamentos(departamentoBO.obterTodos());

		return this.findForward("popupNovoDepartamento");
	}

	/**
	 * Salva um departamento.
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return forward da inclusão
	 * @throws Exception
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		Departamento departamento = new Departamento();
		Utils.copyProperties(departamento, form);

		departamentoBO.salvar(departamento);

		this.addMessageKey("mensagem.departamento.salvar");
		return this.sendAJAXResponse(true);
	}
}
