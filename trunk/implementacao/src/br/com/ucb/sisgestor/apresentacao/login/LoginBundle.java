/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe com os recursos utilizados para efetuar o login.
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public final class LoginBundle {

	private static Log				logger;
	private static final String	LOCAL_RESOURCE	= "resources/";
	private static final String	LOGIN_RESOURCE	= "login_page.html";
	private static final String	SENHA_RESOURCE	= "senha_page.html";

	private StringBuilder			loginPage;
	private StringBuilder			loginErrorPage;
	private StringBuilder			senhaPage;
	private StringBuilder			senhaSucessoPage;
	private StringBuilder			senhaErroPage;

	static {
		logger = LogFactory.getLog(LoginBundle.class);
	}

	/**
	 * Cria uma nova instância do tipo {@link LoginBundle}
	 */
	public LoginBundle() {
		this.loadLoginPage();
		this.loadLoginErrorPage();
		this.loadSenhaPage();
		this.loadSenhaErroPage();
		this.loadSenhaSucessoPage();
	}

	/**
	 * Recupera a página de login com erro.
	 * 
	 * @return html da página de login com erro
	 */
	public String getLoginErrorPage() {
		return this.loginErrorPage.toString();
	}

	/**
	 * Recupera a página de login.
	 * 
	 * @return html da página de login
	 */
	public String getLoginPage() {
		return this.loginPage.toString();
	}

	/**
	 * Recupera a página de recuperação da senha com erro.
	 * 
	 * @return html da página de recuperação da senha com erro
	 */
	public String getSenhaErroPage() {
		return this.senhaErroPage.toString();
	}

	/**
	 * Recupera a página de recuperação da senha.
	 * 
	 * @return html da página de recuperação da senha
	 */
	public String getSenhaPage() {
		return this.senhaPage.toString();
	}

	/**
	 * Recupera a página de recuperação da senha com sucesso.
	 * 
	 * @return html da página de recuperação da senha com sucesso
	 */
	public String getSenhaSucessoPage() {
		return this.senhaSucessoPage.toString();
	}

	/**
	 * Carrega a página de login com erro.
	 */
	private void loadLoginErrorPage() {
		this.loginErrorPage = this.loadResourceFile(LOCAL_RESOURCE + LOGIN_RESOURCE);
		this.replaceText(this.loginErrorPage, "Login e/ou senha inválida.");
	}

	/**
	 * Carrega a página de login.
	 */
	private void loadLoginPage() {
		this.loginPage = this.loadResourceFile(LOCAL_RESOURCE + LOGIN_RESOURCE);
		this.replaceText(this.loginPage, "");
	}

	private synchronized StringBuilder loadResourceFile(String filename) {
		logger.debug("Obtendo o recurso: " + filename);
		InputStream is = (LoginBundle.class).getResourceAsStream(filename);
		byte buffer[] = new byte[256];
		int cnt = 0;
		StringBuilder lp = new StringBuilder();
		do {
			try {
				cnt = is.read(buffer);
			} catch (IOException e) {
				logger.error("Erro na leitura dos dados.", e);
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

	/**
	 * Carrega a página de recuperação da senha sem sucesso.
	 */
	private void loadSenhaErroPage() {
		this.senhaErroPage = this.loadResourceFile(LOCAL_RESOURCE + SENHA_RESOURCE);
		this.replaceText(this.senhaErroPage, "Erro no envio da senha.");
	}

	/**
	 * Carrega a página de recuperação da senha.
	 */
	private void loadSenhaPage() {
		this.senhaPage = this.loadResourceFile(LOCAL_RESOURCE + SENHA_RESOURCE);
		this.replaceText(this.senhaPage, "");
	}

	/**
	 * Carrega a página de recuperação da senha com sucesso.
	 */
	private void loadSenhaSucessoPage() {
		this.senhaSucessoPage = this.loadResourceFile(LOCAL_RESOURCE + SENHA_RESOURCE);
		this.replaceText(this.senhaSucessoPage, "Senha enviada com sucesso.");
	}

	private void replaceText(StringBuilder html, String texto) {
		String subs = "#MENSAGEM#";
		int tam = subs.length();
		int pos = 0;
		do {
			pos = html.indexOf(subs, pos);
			if (pos > 0) {
				html.replace(pos, pos + tam, texto);
			}
		} while (pos > 0);
	}
}
