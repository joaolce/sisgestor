/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterUsuarioActionForm;
import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.negocio.impl.PermissaoBOImpl;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;
import java.util.ArrayList;
import java.util.List;
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
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) actionForm;
		Usuario usuario = usuarioBO.obter(form.getId());
		this.copyProperties(usuario, form);
		usuario.setPermissoes(this.getPermissoes(form.getPermissoes()));

		//Usuário pode atualizar os seus dados
		if (!this.getUser().getId().equals(usuario.getId())
				&& !request.isUserInRole(ConstantesRoles.MANTER_USUARIO)) {
			this.addMessageKey("erro.acessoNegado");
			return this.sendAJAXResponse(false);
		}
		usuarioBO.atualizar(usuario);

		this.addMessageKey("mensagem.usuario.alterar");

		/*Se usuário está atualizando os próprios dados, deverá
		 *efetuar novo login para as alterações surtirem efeito
		 */
		if (this.getUser().getId().equals(usuario.getId())) {
			this.doUsuario(true);
			this.addMessageKey("mensagem.alteracao");
		}

		return this.sendAJAXResponse(true);
	}

	/**
	 * Atualiza a senha do usuário.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward atualizarSenha(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ManterUsuarioActionForm form = (ManterUsuarioActionForm) formulario;
		Usuario usuario = super.getUser();
		usuario.setSenha(form.getNovaSenha());

		usuarioBO.atualizar(usuario);
		this.doUsuario(true);

		this.addMessageKey("mensagem.usuario.senha");
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
		form.setPermissoesDisponiveis(PermissaoBOImpl.getInstancia().obterTodos());

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui um usuário.
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
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) formulario;

		Usuario usuario = usuarioBO.obter(form.getId());

		if (this.getUser().getId().equals(usuario.getId())) {
			this.addMessageKey("erro.excluir.naoPermitido");
			return this.sendAJAXResponse(false);
		}

		usuarioBO.excluir(usuario);

		this.addMessageKey("mensagem.usuario.excluir");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para editar senha.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupEditarSenha(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward("popupEditarSenha");
	}

	/**
	 * Abre o popup para incluir um novo usuário.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovoUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm frm = (ManterUsuarioActionForm) form;

		frm.setListaDepartamentos(DepartamentoBOImpl.getInstancia().obterTodos());
		frm.setPermissoesDisponiveis(PermissaoBOImpl.getInstancia().obterTodos());

		return this.findForward("popupNovoUsuario");
	}


	/**
	 * Salva um usuário.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ManterUsuarioActionForm form = (ManterUsuarioActionForm) formulario;
		Usuario usuario = new Usuario();
		this.copyProperties(usuario, form);

		usuario.setPermissoes(this.getPermissoes(form.getPermissoes()));

		usuarioBO.salvar(usuario);

		this.addMessageKey("mensagem.usuario.salvar");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Recupera um {@link List} de {@link Permissao} a partir dos id´s.
	 * 
	 * @param permissoes identificador das permissões
	 * @return Lista de permissões
	 */
	private List<Permissao> getPermissoes(Integer[] permissoes) {
		List<Permissao> list = new ArrayList<Permissao>();
		if (permissoes == null) {
			return list;
		}
		Permissao permissao;
		for (Integer key : permissoes) {
			permissao = new Permissao();
			permissao.setId(key);
			list.add(permissao);
		}
		return list;
	}
}
