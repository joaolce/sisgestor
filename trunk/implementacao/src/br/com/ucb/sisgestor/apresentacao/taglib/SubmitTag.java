package br.com.ucb.sisgestor.apresentacao.taglib;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.taglib.html.FormTag;

/**
 * Classe escrita para sobrescrever a SubmitTag original para desabilitar o botão caso o usuário não possua
 * uma das permissões de acesso
 * 
 * @author João Lúcio
 * @since 05/02/2009
 */
public class SubmitTag extends org.apache.struts.taglib.html.SubmitTag {

	private String	roles;

	/**
	 * Cria uma nova instância do tipo {@link SubmitTag}.
	 */
	public SubmitTag() {
		this.roles = "";
	}

	/**
	 * Recupera o valor de roles.
	 * 
	 * @return roles
	 */
	public String getRoles() {
		return this.roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void release() {
		super.release();
		this.roles = "";
	}

	/**
	 * Atribui roles.
	 * 
	 * @param roles o valor a ajustar em roles
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareFocusEvents(StringBuffer handlers) {
		this.prepareAttribute(handlers, "onblur", this.getOnblur());
		this.prepareAttribute(handlers, "onfocus", this.getOnfocus());
		FormTag formTag = null;
		if ((this.doDisabled && !this.getDisabled()) || (this.doReadonly && !this.getReadonly())) {
			formTag = (FormTag) this.pageContext.getAttribute("org.apache.struts.taglib.html.FORM", 2);
		}
		this.verificaDisabled(handlers, formTag);
		this.verificaReadonly(handlers, formTag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareMouseEvents(StringBuffer handlers) {
		if (this.usuarioTemPermissao()) {
			this.prepareAttribute(handlers, "onclick", this.getOnclick());
		} else {
			this.prepareAttribute(handlers, "onclick", "return false;");
		}
		this.prepareAttribute(handlers, "ondblclick", this.getOndblclick());
		this.prepareAttribute(handlers, "onmouseover", this.getOnmouseover());
		this.prepareAttribute(handlers, "onmouseout", this.getOnmouseout());
		this.prepareAttribute(handlers, "onmousemove", this.getOnmousemove());
		this.prepareAttribute(handlers, "onmousedown", this.getOnmousedown());
		this.prepareAttribute(handlers, "onmouseup", this.getOnmouseup());
	}

	/**
	 * Verifica se o usuário possui pelo menos uma das permissões informadas.
	 * 
	 * @return <code>true</code> caso tenha alguma permissão, <code>false</code> caso contrário
	 */
	private boolean usuarioTemPermissao() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Usuario usuario = (Usuario) request.getSession().getAttribute(DadosContexto.USUARIOSESSAO);
		return Utils.usuarioTemPermissao(usuario, this.roles);
	}

	/**
	 * Verifica se o botão deve ser desabilitado.
	 * 
	 * @param handlers {@link StringBuffer} a adicionar as propriedades
	 * @param formTag form atual
	 */
	private void verificaDisabled(StringBuffer handlers, FormTag formTag) {
		if (this.doDisabled || !this.usuarioTemPermissao()) {
			boolean formDisabled = (formTag == null) ? false : formTag.isDisabled();
			if (formDisabled || this.getDisabled() || !this.usuarioTemPermissao()) {
				handlers.append(" disabled=\"disabled\"");
			}
		}
	}

	/**
	 * Verifica se o botão deve ser apenas readonly.
	 * 
	 * @param handlers {@link StringBuffer} a adicionar as propriedades
	 * @param formTag form atual
	 */
	private void verificaReadonly(StringBuffer handlers, FormTag formTag) {
		if (this.doReadonly) {
			boolean formReadOnly = (formTag == null) ? false : formTag.isReadonly();
			if (formReadOnly || this.getReadonly()) {
				handlers.append(" readonly=\"readonly\"");
			}
		}
	}
}
