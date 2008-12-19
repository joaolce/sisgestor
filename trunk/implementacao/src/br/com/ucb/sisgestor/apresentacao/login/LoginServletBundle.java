/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public final class LoginServletBundle {

	private static Log				logger;

	private static final String	TITULO_LOGIN_MASK				= "#TITULO_LOGIN#";

	private static final String	TITULO_LOGOUT_MASK			= "#TITULO_LOGOUT#";

	private static final String	AUTOTAB_MASK					= "#UNIDADE_AUTOTAB#";

	private static final String	AUTOTAB_NO_BRANCH_OFFICE	= "document.authDataForm.operador";

	private static final String	AUTOTAB_BRANCH_OFFICE		= "document.authDataForm.dependencia";

	private static final String	BRANCH_OFFICE_MASK			= "#OPCAO_DEPENDENCIA#";

	private static final String	SHOW_DEPENDENCIA_MASK		= "#SHOW_DEPENDENCIA#";

	private static final String	SHOW_DEPENDENCIA				= "show_table_row('linha_dependencia');";

	private static final String	HIDE_DEPENDENCIA				= "";

	private static final String	HIDE_BRANCH_OFFICE_FIELD	= "style=\"display:none;\"";

	private static final String	SHOW_BRANCH_OFFICE_FIELD	= "";

	private static final String	PHONE_MASK						= "#TELEFONE#";

	private static final String	PICTURE_MASK					= "#FIGURA_SISTEMA#";

	private static final String	CHANGE_PWD_MASK				= "#MUDASENHA#";

	private static final String	HIDE_CHANGE_PWD_MSG			= "style=\"display:none;\"";

	private static final String	SHOW_CHANGE_PWD_MSG			= "";

	private static final String	MESSAGE_MASK					= "#MENSAGEM#";

	private static final String	ERROR_MSG_CLASS_MASK			= "#CLASSE_MSG_ERRO#";

	private static final String	SHOW_ERROR_MSG_CLASS			= "msgErro";

	private static final String	HIDE_ERROR_MSG_CLASS			= "bclogin_esconde_msgErro";

	private static final String	DAYS_TO_EXPIRE_MASK			= "#PWD_EXPIRATION#";

	private static final String	PRIVACY_POLICY_MASK			= "#POLITICA#";

	private static final String	ENVIRONMENT_MASK				= "#MARCA_AMBIENTE#";

	private static final String	DESEN_ENV						=
																						"style=\"background-image:url(/img/logoDesenv.gif); background-position:top left; background-repeat:no-repeat;\"";

	private static final String	HOMO_ENV							=
																						"style=\"background-image:url(/img/logoHomologa.gif); background-position:top left; background-repeat:no-repeat;\"";

	private static final String	PROD_ENV							= "";

	private static final String	LOGIN_RESOURCE					= "login_page.html";

	private static final String	JBOSS_LOGIN_RESOURCE			= "login_page_jboss.html";

	private static final String	LOGOUT_RESOURCE				= "logout_page.html";

	private static final String	CHANGE_PWD_RESOURCE			= "change_pwd_page.html";

	private static final String	ERROR_RESOURCE					= "error_page.html";
	private static final String	PWD_WILL_EXPIRE_RESOURCE	= "pwd_will_expire.html";
	private static final String	HELP_RESOURCE					= "help_page.html";
	private static final String	LOCAL_RESOURCE					= "resources/";
	private StringBuffer				loginPage;
	private StringBuffer				jbossLoginPage;
	private StringBuffer				logoutPage;
	private StringBuffer				errorPage;
	private StringBuffer				changePwdPage;
	private StringBuffer				passwordWillExpirePage;
	private StringBuffer				helpPage;
	private String						imageURL;
	private String						homeAfterLogin;
	private String						homeAfterLogout;
	private boolean					dependencia;
	private String						titulo;
	private String						tituloLogout;
	private String						telefone;
	private String						politicaURL;
	private String						telefonePadrao;
	private String						tituloPadrao;
	private String						imagemPadrao;
	private String						politicaPadrao;
	static {
		logger = LogFactory.getLog(LoginServletBundle.class);
	}

	/**
	 * Cria uma nova instância do tipo LoginServletBundle
	 * 
	 * @param parametros
	 */
	public LoginServletBundle(Map parametros) {
		this.loginPage = null;
		this.jbossLoginPage = null;
		this.logoutPage = null;
		this.errorPage = null;
		this.changePwdPage = null;
		this.passwordWillExpirePage = null;
		this.helpPage = null;
		this.telefonePadrao = "";
		this.tituloPadrao = "";
		this.imagemPadrao = "";
		this.politicaPadrao = "";
		this.loadProperties();
		this.processaParametros(parametros);
		this.loadLoginPage();
		this.loadLogoutPage();
		this.loadChangePasswordPage();
		this.loadErrorPage();
		this.loadPasswordWillExpirePage();
		this.loadHelpPage();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getChangePasswordPage() {
		return this.getChangePasswordPage("", "", "", "", "");
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param message
	 * @param unidade
	 * @param dependencia
	 * @param operador
	 * @param cpf
	 * @return d
	 */
	public String getChangePasswordPage(String message, String unidade, String dependencia, String operador,
			String cpf) {
		StringBuffer page = new StringBuffer(this.changePwdPage.toString());
		page = this.replaceText(page, "#MENSAGEM#", message);
		if ("".equals(message)) {
			page = this.replaceText(page, "#CLASSE_MSG_ERRO#", "bclogin_esconde_msgErro");
		} else {
			page = this.replaceText(page, "#CLASSE_MSG_ERRO#", "msgErro");
		}
		page = this.replaceText(page, "#UNIDADE#", unidade);
		page = this.replaceText(page, "#DEPENDENCIA#", dependencia);
		page = this.replaceText(page, "#OPERADOR#", operador);
		page = this.replaceText(page, "#CPF#", cpf);
		return page.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getErrorPage() {
		return this.errorPage.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getHelpPage() {
		return this.helpPage.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getHomeAfterLogin() {
		return this.homeAfterLogin;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getHomeAfterLogout() {
		return this.homeAfterLogout;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param enableDoMudaSenha
	 * @return d
	 */
	public String getLoginPage(boolean enableDoMudaSenha) {
		return this.getLoginPage("", enableDoMudaSenha, "", "", "", "");
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param message
	 * @param enableDoMudaSenha
	 * @param unidade
	 * @param dependencia
	 * @param operador
	 * @param cpf
	 * @return d
	 */
	public String getLoginPage(String message, boolean enableDoMudaSenha, String unidade, String dependencia,
			String operador, String cpf) {
		StringBuffer page;
		if (LoginHelperFactory.getApplicationServer() == 0) {
			page = new StringBuffer(this.jbossLoginPage.toString());
		} else {
			page = new StringBuffer(this.loginPage.toString());
		}
		page = this.replaceText(page, "#MENSAGEM#", message);
		if ("".equals(message)) {
			page = this.replaceText(page, "#CLASSE_MSG_ERRO#", "bclogin_esconde_msgErro");
		} else {
			page = this.replaceText(page, "#CLASSE_MSG_ERRO#", "msgErro");
		}
		page = this.replaceText(page, "#UNIDADE#", unidade);
		page = this.replaceText(page, "#DEPENDENCIA#", dependencia);
		page = this.replaceText(page, "#OPERADOR#", operador);
		page = this.replaceText(page, "#CPF#", cpf);
		if (enableDoMudaSenha) {
			page = this.replaceText(page, "#MUDASENHA#", "");
		} else {
			page = this.replaceText(page, "#MUDASENHA#", "style=\"display:none;\"");
		}
		return page.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getLogoutPage() {
		return this.logoutPage.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param dias
	 * @return d
	 */
	public String getPasswordWillExpirePage(String dias) {
		StringBuffer page = new StringBuffer(this.passwordWillExpirePage.toString());
		page = this.replaceText(page, "#PWD_EXPIRATION#", dias);
		return page.toString();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param depend
	 */
	public void setDependencia(boolean depend) {
		this.dependencia = depend;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param hal
	 */
	public void setHomeAfterLogin(String hal) {
		this.homeAfterLogin = hal;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param hal
	 */
	public void setHomeAfterLogout(String hal) {
		this.homeAfterLogout = hal;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param url
	 */
	public void setImageURL(String url) {
		this.imageURL = url;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param value
	 */
	public void setPolitica(String value) {
		this.politicaURL = value;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param value
	 */
	public void setTelefone(String value) {
		this.telefone = value;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo.trim();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param tituloLogout
	 */
	public void setTituloLogout(String tituloLogout) {
		this.tituloLogout = tituloLogout.trim();
	}

	private String getAttributeValue(Map parameters, String param, String defaultValue) {
		if (parameters.containsKey(param)) {
			return (String) parameters.get(param);
		} else {
			return defaultValue;
		}
	}

	private void loadChangePasswordPage() {
		this.changePwdPage = this.loadFile("resources/change_pwd_page.html");
		this.changePwdPage = this.performBasicReplacement(this.changePwdPage);
	}

	private void loadErrorPage() {
		this.errorPage = this.loadFile("resources/error_page.html");
		this.errorPage = this.performBasicReplacement(this.errorPage);
	}

	private StringBuffer loadFile(String filename) {
		logger.debug("<LoginServletBundle :: loadFile> Obtendo o recurso: " + filename);
		InputStream is = (LoginServletBundle.class).getResourceAsStream(filename);
		byte buffer[] = new byte[256];
		int cnt = 0;
		StringBuffer lp = new StringBuffer();
		do {
			try {
				cnt = is.read(buffer);
			} catch (IOException e) {
				logger.error("<LoginServletBundle :: loadFile> Erro na leitura dos dados.", e);
				cnt = 0;
			}
			if (cnt > 0) {
				for (int i = 0; i < cnt; i++) {
					char c = (char) buffer[i];
					lp.append(c);
				}

			}
		} while (cnt > 0);
		return lp;
	}

	private void loadHelpPage() {
		this.helpPage = this.loadFile("resources/help_page.html");
		this.helpPage = this.performBasicReplacement(this.helpPage);
	}

	private void loadLoginPage() {
		this.loginPage = this.loadFile("resources/login_page.html");
		this.jbossLoginPage = this.loadFile("resources/login_page_jboss.html");
		this.loginPage = this.performBasicReplacement(this.loginPage);
		this.jbossLoginPage = this.performBasicReplacement(this.jbossLoginPage);
	}

	private void loadLogoutPage() {
		this.logoutPage = this.loadFile("resources/logout_page.html");
		this.logoutPage = this.performBasicReplacement(this.logoutPage);
	}

	private void loadPasswordWillExpirePage() {
		this.passwordWillExpirePage = this.loadFile("resources/pwd_will_expire.html");
		this.passwordWillExpirePage = this.performBasicReplacement(this.passwordWillExpirePage);
	}

	private void loadProperties() {
		ResourceBundle bundle = ResourceBundle.getBundle("bc_login");
		this.tituloPadrao = bundle.getString("bacen.security.titulo.default");
		this.imagemPadrao = bundle.getString("bacen.security.imagem.default");
		this.telefonePadrao = bundle.getString("bacen.security.telefone.default");
		this.politicaPadrao = bundle.getString("bacen.security.politica.default");
	}

	private StringBuffer performBasicReplacement(StringBuffer page) {
		StringBuffer modifiedPage = new StringBuffer(page.toString());
		modifiedPage = this.replaceText(modifiedPage, "#TITULO_LOGIN#", this.titulo);
		modifiedPage = this.replaceText(modifiedPage, "#TITULO_LOGOUT#", this.tituloLogout);
		modifiedPage = this.replaceText(modifiedPage, "#TELEFONE#", this.telefone);
		modifiedPage = this.replaceText(modifiedPage, "#POLITICA#", this.politicaURL);
		modifiedPage = this.replaceText(modifiedPage, "#FIGURA_SISTEMA#", this.imageURL);
		if (this.dependencia) {
			modifiedPage = this.replaceText(modifiedPage, "#OPCAO_DEPENDENCIA#", "");
			modifiedPage =
					this.replaceText(modifiedPage, "#UNIDADE_AUTOTAB#", "document.authDataForm.dependencia");
			modifiedPage =
					this.replaceText(modifiedPage, "#SHOW_DEPENDENCIA#", "show_table_row('linha_dependencia');");
		} else {
			modifiedPage = this.replaceText(modifiedPage, "#OPCAO_DEPENDENCIA#", "style=\"display:none;\"");
			modifiedPage = this.replaceText(modifiedPage, "#UNIDADE_AUTOTAB#", "document.authDataForm.operador");
			modifiedPage = this.replaceText(modifiedPage, "#SHOW_DEPENDENCIA#", "");
		}
		if (LoginHelperFactory.isDesenvolvimento()) {
			modifiedPage =
					this
							.replaceText(
									modifiedPage,
									"#MARCA_AMBIENTE#",
									"style=\"background-image:url(/img/logoDesenv.gif); background-position:top left; background-repeat:no-repeat;\"");
		} else if (LoginHelperFactory.isHomologacao()) {
			modifiedPage =
					this
							.replaceText(
									modifiedPage,
									"#MARCA_AMBIENTE#",
									"style=\"background-image:url(/img/logoHomologa.gif); background-position:top left; background-repeat:no-repeat;\"");
		} else {
			modifiedPage = this.replaceText(modifiedPage, "#MARCA_AMBIENTE#", "");
		}
		return modifiedPage;
	}

	private void processaParametros(Map parametros) {
		String value = this.getAttributeValue(parametros, "bacen.security.imagemsistema", null);
		if (value == null) {
			value = this.getAttributeValue(parametros, "imgbigURL", this.imagemPadrao);
		}
		this.setImageURL(value);
		value = this.getAttributeValue(parametros, "bacen.security.homeafterlogout", null);
		this.setHomeAfterLogout(value);
		value = this.getAttributeValue(parametros, "bacen.security.homeafterlogin", null);
		this.setHomeAfterLogin(value);
		value = this.getAttributeValue(parametros, "bacen.security.semdependencia", "false");
		this.setDependencia(!Boolean.valueOf(value).booleanValue());
		value = this.getAttributeValue(parametros, "bacen.security.nomesistema", null);
		if (value == null) {
			value = this.getAttributeValue(parametros, "bacen.security.title", this.tituloPadrao);
			this.setTitulo(value);
			value = this.getAttributeValue(parametros, "bacen.security.title_logout", this.tituloPadrao);
			this.setTituloLogout(value);
		} else {
			this.setTitulo(value);
			this.setTituloLogout(value);
		}
		value = this.getAttributeValue(parametros, "bacen.security.telefone", this.telefonePadrao);
		this.setTelefone(value);
		value =
				this.getAttributeValue(parametros, "bacen.security.urlpoliticaprivacidade", this.politicaPadrao);
		this.setPolitica(value);
	}

	private StringBuffer replaceText(StringBuffer buffer, String subs, String texto) {
		StringBuffer changedText = new StringBuffer(buffer.toString());
		int tam = subs.length();
		int pos = 0;
		do {
			pos = changedText.indexOf(subs, pos);
			if (pos > 0) {
				changedText.replace(pos, pos + tam, texto);
			}
		} while (pos > 0);
		return changedText;
	}
}
