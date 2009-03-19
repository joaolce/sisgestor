/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterDepartamentoActionForm;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções em {@link Departamento}.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterDepartamentoAction extends BaseAction {

	private DepartamentoBO	departamentoBO;

	/**
	 * Atualiza um departamento.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso ocorra erro na operação
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;

		Departamento departamento = this.departamentoBO.obter(form.getId());
		this.copyProperties(departamento, form);

		this.departamentoBO.atualizar(departamento);

		this.addMessageKey("mensagem.alterar", "Departamento");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) actionForm;

		form.setListaDepartamentos(this.departamentoBO.obterTodos());

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui um departamento.
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
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;

		Departamento departamento = this.departamentoBO.obter(form.getId());

		this.departamentoBO.excluir(departamento);

		this.addMessageKey("mensagem.excluir", "Departamento");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Tela de popup para incluir um novo departamento.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso ocorra erro na operação
	 */
	public ActionForward popupNovoDepartamento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm frm = (ManterDepartamentoActionForm) form;

		frm.setListaDepartamentos(this.departamentoBO.obterTodos());

		return this.findForward("popupNovoDepartamento");
	}

	/**
	 * Salva um departamento.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclusão
	 * @throws Exception caso ocorra erro na operação
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;

		Departamento departamento = new Departamento();
		this.copyProperties(departamento, form);

		this.departamentoBO.salvar(departamento);

		this.addMessageKey("mensagem.salvar", "Departamento");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Atribui o BO de {@link Departamento}.
	 * 
	 * @param departamentoBO BO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoBO(DepartamentoBO departamentoBO) {
		this.departamentoBO = departamentoBO;
	}
}
