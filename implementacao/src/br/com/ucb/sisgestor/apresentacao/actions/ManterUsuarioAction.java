/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterUsuarioActionForm;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.PermissaoBO;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções em {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterUsuarioAction extends BaseAction {

	private DepartamentoBO	departamentoBO;
	private PermissaoBO		permissaoBO;

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

		Usuario usuario = this.usuarioBO.obter(form.getId());
		//atualizando os dados do usuário
		this.copyProperties(usuario, form);

		usuario.setPermissoes(this.getPermissoes(form.getPermissoes()));

		Usuario usuarioLogado = this.getUser();
		boolean temPermissao = request.isUserInRole(ConstantesRoles.MANTER_USUARIO);

		//Usuário que não tem permissão apenas pode editar os próprios dados
		if (!usuarioLogado.getId().equals(usuario.getId()) && !temPermissao) {
			this.addMessageKey("erro.acessoNegado");
			return this.sendAJAXResponse(false);
		}
		this.usuarioBO.atualizar(usuario);

		this.addMessageKey("mensagem.alterar", "Usuário");

		/*
		 * Se usuário está atualizando os próprios dados e ele tem permissão para Manter Usuário, deverá
		 *	efetuar novo login para que as alterações sejam aplicadas.
		 *
		 *	Essa mesma condição é verificada no manterUsuario.js
		 */
		if (usuarioLogado.getId().equals(usuario.getId()) && temPermissao) {
			this.addMessageKey("mensagem.novoLogin");
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

		Usuario usuario = this.getUser();
		usuario.setSenha(form.getNovaSenha());

		this.usuarioBO.atualizar(usuario);
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

		form.setListaDepartamentos(this.departamentoBO.obterTodosAtivos());

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

		Usuario usuario = this.usuarioBO.obter(form.getId());

		// Usuário não pode excluir ele mesmo
		if (this.getUser().getId().equals(usuario.getId())) {
			this.addMessageKey("erro.excluir.naoPermitido");
			return this.sendAJAXResponse(false);
		}

		this.usuarioBO.excluir(usuario);

		this.addMessageKey("mensagem.excluir", "Usuário");
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

		frm.setListaDepartamentos(this.departamentoBO.obterTodosAtivos());
		frm.setPermissoesDisponiveis(this.permissaoBO.obterTodos());

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

		this.usuarioBO.salvar(usuario);

		this.addMessageKey("mensagem.salvar", "Usuário");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Atribui BO de {@link Departamento}.
	 * 
	 * @param departamentoBO BO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoBO(DepartamentoBO departamentoBO) {
		this.departamentoBO = departamentoBO;
	}

	/**
	 * Atribui o BO de {@link Permissao}.
	 * 
	 * @param permissaoBO BO de {@link Permissao}
	 */
	@Autowired
	public void setPermissaoBO(PermissaoBO permissaoBO) {
		this.permissaoBO = permissaoBO;
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
