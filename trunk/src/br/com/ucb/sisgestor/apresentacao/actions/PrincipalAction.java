/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de entrada no sistema
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public class PrincipalAction extends BaseAction {

	/**
	 * Página de entrada da aplicação
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return {@link PrincipalAction}
	 * @throws Exception
	 */
	public ActionForward entrada(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNome("joaooo");
		usuario.setLogin("login");
		request.getSession().setAttribute(DadosContexto.USUARIOSESSAO, usuario);
		return mapping.findForward("entrada");
	}

	/**
	 * 
	 * Quando ocorrer qualquer erro inesperado na aplicação esse método será invocado para exibição de uma
	 * página de erro amigável com alguns detalhes do erro pra facilitar a depuração
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return {@link PrincipalAction}
	 * @throws Exception
	 */
	public ActionForward erro(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("errorContainer", Boolean.TRUE);
		return mapping.findForward("erro");
	}
}
