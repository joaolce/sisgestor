/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
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
 * @author João Lúcio
 * @since 27/10/2008
 */
public class PrincipalAction extends BaseAction {

	/**
	 * Quando ocorrer qualquer erro inesperado na aplicação esse método será invocado para exibição de uma
	 * página de erro amigável com alguns detalhes do erro pra facilitar a depuração
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward}
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward erro(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute(DadosContexto.ERRO_CONTAINER, Boolean.TRUE);
		return mapping.findForward("erro");
	}

	/**
	 * Sobrescrita do método execute. Apenas para verificar se o método chamado é o padrão de entrada, caso não
	 * seja não será feito os procedimentos genéricos.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward}
	 * @throws Exception caso exceção seja lançada
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
