/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.util.constantes.ConstantesAplicacao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de entrada no sistema.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public class PrincipalAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ConstantesAplicacao.setConstantes(request);
		this.populaParametrosAction(mapping, form, request, response);
		this.doUsuario(false);
		return mapping.findForward(FWD_ENTRADA);
	}

	/**
	 * Quando ocorrer qualquer erro inesperado na aplicação esse método será invocado para exibição de uma
	 * página de erro amigável com alguns detalhes do erro pra facilitar a depuração
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return {@link ActionForward}
	 * @throws Exception
	 */
	public ActionForward erro(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("errorContainer", Boolean.TRUE);
		return mapping.findForward("erro");
	}

	/**
	 * Quando ocorrer algum erro deve cair aqui.
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String method = request.getParameter(mapping.getParameter());
		ConstantesAplicacao.setConstantes(request);
		return this.dispatchMethod(mapping, form, request, response, method);
	}
}
