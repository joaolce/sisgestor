/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
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
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public class PrincipalAction extends BaseAction {

	/**
	 * P�gina de entrada da aplica��o
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
	 * Quando ocorrer qualquer erro inesperado na aplica��o esse m�todo ser� invocado para exibi��o de uma
	 * p�gina de erro amig�vel com alguns detalhes do erro pra facilitar a depura��o
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
