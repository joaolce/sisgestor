/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de entrada no sistema.
 * 
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public class PrincipalAction extends BaseAction {

	/**
	 * Quando ocorrer qualquer erro inesperado na aplica��o esse m�todo ser� invocado para exibi��o de uma
	 * p�gina de erro amig�vel com alguns detalhes do erro pra facilitar a depura��o
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward}
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward erro(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute(DadosContexto.ERRO_CONTAINER, Boolean.TRUE);
		return mapping.findForward("erro");
	}

	/**
	 * Sobrescrita do m�todo execute. Apenas para verificar se o m�todo chamado � o padr�o de entrada, caso n�o
	 * seja n�o ser� feito os procedimentos gen�ricos.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward}
	 * @throws Exception caso exce��o seja lan�ada
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String method = request.getParameter(mapping.getParameter());
		if (method.equals(FWD_ENTRADA)) {
			return super.execute(mapping, form, request, response);
		} else {
			return this.dispatchMethod(mapping, form, request, response, method);
		}
	}
}
