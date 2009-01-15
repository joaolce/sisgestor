/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterUsuarioActionForm;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manuten��es em {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 03/01/2009
 */
public class ManterUsuarioAction extends BaseAction {

	private static UsuarioBO	usuarioBO;

	static {
		usuarioBO = UsuarioBOImpl.getInstancia();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) actionForm;
		form.setUsuarios(usuarioBO.obterTodos());
		form.setListaDepartamentos(DepartamentoBOImpl.getInstancia().obterTodos());

		return this.findForward(FWD_ENTRADA);
	}
}
