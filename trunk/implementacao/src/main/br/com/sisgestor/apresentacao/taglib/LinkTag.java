package br.com.sisgestor.apresentacao.taglib;

import br.com.sisgestor.util.Utils;
import br.com.sisgestor.util.constantes.ConstantesContexto;
import br.com.sisgestor.entidade.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * Classe escrita para sobrescrever a LinkTag original para desabilitar o link caso o usuário não possua uma
 * das permissões de acesso
 * 
 * @author João Lúcio
 * @since 05/02/2009
 */
public class LinkTag extends org.apache.struts.taglib.html.LinkTag {

	private String	roles;

	/**
	 * Cria uma nova instância do tipo {@link LinkTag}.
	 */
	public LinkTag() {
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
	 * Release any acquired resources.
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
	protected String calculateURL() throws JspException {
		if (this.usuarioTemPermissao()) {
			return super.calculateURL();
		}
		return "#";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareMouseEvents(StringBuffer handlers) {
		if (this.usuarioTemPermissao()) {
			this.prepareAttribute(handlers, "onclick", this.getOnclick());
		}
		this.prepareAttribute(handlers, "ondblclick", this.getOndblclick());
		this.prepareAttribute(handlers, "onmouseover", this.getOnmouseover());
		this.prepareAttribute(handlers, "onmouseout", this.getOnmouseout());
		this.prepareAttribute(handlers, "onmousemove", this.getOnmousemove());
		this.prepareAttribute(handlers, "onmousedown", this.getOnmousedown());
		this.prepareAttribute(handlers, "onmouseup", this.getOnmouseup());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String prepareStyles() throws JspException {
		StringBuffer styles = new StringBuffer();
		boolean errorsExist = this.doErrorsExist();
		if (errorsExist && (this.getErrorStyleId() != null)) {
			this.prepareAttribute(styles, "id", this.getErrorStyleId());
		} else {
			this.prepareAttribute(styles, "id", this.getStyleId());
		}
		if (errorsExist && (this.getErrorStyle() != null)) {
			this.prepareAttribute(styles, "style", this.getErrorStyle());
		} else {
			this.prepareAttribute(styles, "style", this.getStyle());
		}
		if (this.usuarioTemPermissao()) {
			if (errorsExist && (this.getErrorStyleClass() != null)) {
				this.prepareAttribute(styles, "class", this.getErrorStyleClass());
			} else {
				this.prepareAttribute(styles, "class", this.getStyleClass());
			}
		} else {
			this.prepareAttribute(styles, "class", "btDesativado");
		}
		this.prepareAttribute(styles, "title", this.message(this.getTitle(), this.getTitleKey()));
		this.prepareAttribute(styles, "alt", this.message(this.getAlt(), this.getAltKey()));
		this.prepareInternationalization(styles);
		return styles.toString();
	}

	/**
	 * Verifica se o usuário possui pelo menos uma das permissões informadas.
	 * 
	 * @return <code>true</code> caso tenha alguma permissão, <code>false</code> caso contrário
	 */
	private boolean usuarioTemPermissao() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Usuario usuario = (Usuario) request.getSession().getAttribute(ConstantesContexto.USUARIO_SESSAO);
		return Utils.usuarioTemPermissao(usuario, this.roles);
	}
}
