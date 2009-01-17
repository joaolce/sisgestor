/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterUsuarioActionForm;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.util.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manutenções em {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterUsuarioAction extends BaseAction {

	private static UsuarioBO	usuarioBO;

	static {
		usuarioBO = UsuarioBOImpl.getInstancia();
	}

	/**
	 * Atualiza um usuário.
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return forward da atualização
	 * @throws Exception
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) actionForm;
		Usuario usuario = new Usuario();
		Utils.copyProperties(usuario, form);

		usuarioBO.atualizar(usuario);

		this.addMessageKey("mensagem.usuario.alterar");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) actionForm;

		form.setListaDepartamentos(DepartamentoBOImpl.getInstancia().obterTodos());

		return this.findForward(FWD_ENTRADA);
	}
}
