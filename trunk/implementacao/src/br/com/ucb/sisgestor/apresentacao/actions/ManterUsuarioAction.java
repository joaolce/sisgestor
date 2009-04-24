/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
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
 * Action para manuten��es em {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 03/01/2009
 */
public class ManterUsuarioAction extends BaseAction {

	private DepartamentoBO	departamentoBO;
	private PermissaoBO		permissaoBO;

	/**
	 * Atualiza um usu�rio.
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
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) actionForm;

		Usuario usuario = this.usuarioBO.obter(form.getId());
		//atualizando os dados do usu�rio
		this.copyProperties(usuario, form);

		usuario.setPermissoes(this.getPermissoes(form.getPermissoes()));

		Usuario usuarioLogado = this.getUser();
		boolean temPermissao = request.isUserInRole(ConstantesRoles.MANTER_USUARIO);

		//Usu�rio que n�o tem permiss�o apenas pode editar os pr�prios dados
		if (!usuarioLogado.getId().equals(usuario.getId()) && !temPermissao) {
			this.addMessageKey("erro.acessoNegado");
			return this.sendAJAXResponse(false);
		}
		this.usuarioBO.atualizar(usuario);

		this.addMessageKey("mensagem.alterar", "Usu�rio");

		/*
		 * Se usu�rio est� atualizando os pr�prios dados e ele tem permiss�o para Manter Usu�rio, dever�
		 *	efetuar novo login para que as altera��es sejam aplicadas.
		 *
		 *	Essa mesma condi��o � verificada no manterUsuario.js
		 */
		if (usuarioLogado.getId().equals(usuario.getId()) && temPermissao) {
			this.addMessageKey("mensagem.novoLogin");
		}

		return this.sendAJAXResponse(true);
	}

	/**
	 * Atualiza a senha do usu�rio.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclus�o
	 * @throws Exception caso exce��o seja lan�ada
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
	 * Exclui um usu�rio.
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
		ManterUsuarioActionForm form = (ManterUsuarioActionForm) formulario;

		Usuario usuario = this.usuarioBO.obter(form.getId());

		// Usu�rio n�o pode excluir ele mesmo
		if (this.getUser().getId().equals(usuario.getId())) {
			this.addMessageKey("erro.excluir.naoPermitido");
			return this.sendAJAXResponse(false);
		}

		this.usuarioBO.excluir(usuario);

		this.addMessageKey("mensagem.excluir", "Usu�rio");
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
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupEditarSenha(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward("popupEditarSenha");
	}

	/**
	 * Abre o popup para incluir um novo usu�rio.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupNovoUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterUsuarioActionForm frm = (ManterUsuarioActionForm) form;

		frm.setListaDepartamentos(this.departamentoBO.obterTodosAtivos());
		frm.setPermissoesDisponiveis(this.permissaoBO.obterTodos());

		return this.findForward("popupNovoUsuario");
	}

	/**
	 * Salva um usu�rio.
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

		ManterUsuarioActionForm form = (ManterUsuarioActionForm) formulario;
		Usuario usuario = new Usuario();
		this.copyProperties(usuario, form);

		usuario.setPermissoes(this.getPermissoes(form.getPermissoes()));

		this.usuarioBO.salvar(usuario);

		this.addMessageKey("mensagem.salvar", "Usu�rio");
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
	 * Recupera um {@link List} de {@link Permissao} a partir dos id�s.
	 * 
	 * @param permissoes identificador das permiss�es
	 * @return Lista de permiss�es
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
