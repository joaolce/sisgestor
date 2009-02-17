/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterCampoActionForm;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.negocio.impl.CampoBOImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manuten��es em {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoAction extends BaseAction {

	private static CampoBO	campoBO;

	static {
		campoBO = CampoBOImpl.getInstancia();
	}

	/**
	 * Atualiza um campo.
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
		ManterCampoActionForm form = (ManterCampoActionForm) actionForm;

		Campo campo = new Campo();

		this.copyProperties(campo, form);

		campoBO.atualizar(campo);

		this.addMessageKey("mensagem.alterar", "Campo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Exclui um campo.
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
		ManterCampoActionForm form = (ManterCampoActionForm) formulario;

		Campo campo = campoBO.obter(form.getId());

		campoBO.excluir(campo);

		this.addMessageKey("mensagem.excluir", "Campo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir um novo campo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupNovoCampo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.findForward("popupNovoCampo");
	}

	/**
	 * Salva um campo.
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

		ManterCampoActionForm form = (ManterCampoActionForm) formulario;
		Campo campo = new Campo();
		this.copyProperties(campo, form);

		campoBO.salvar(campo);

		this.addMessageKey("mensagem.salvar", "Campo");
		return this.sendAJAXResponse(true);
	}
}
