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
	private static final String	ERROR_RESOURCE	= "error_page.html";
	private static final String	LOGIN_RESOURCE	= "login_page.html";

	private StringBuilder			loginPage;
	private StringBuilder			errorPage;

	static {
		logger = LogFactory.getLog(LoginBundle.class);
	}

	/**
	 * Cria uma nova instância do tipo {@link LoginBundle}
	 */
	public LoginBundle() {
		this.loginPage = null;
		this.errorPage = null;
		this.loadLoginPage();
		this.loadErrorPage();
	}

	/**
	 * Recupera a página de erro.
	 * 
	 * @return html da página de erro
	 */
	public String getErrorPage() {
		return this.errorPage.toString();
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
	 * Carrega a página de erro.
	 */
	private void loadErrorPage() {
		this.errorPage = this.loadResourceFile(LOCAL_RESOURCE + ERROR_RESOURCE);
	}

	/**
	 * Carrega a página de login.
	 */
	private void loadLoginPage() {
		this.loginPage = this.loadResourceFile(LOCAL_RESOURCE + LOGIN_RESOURCE);
	}

	private StringBuilder loadResourceFile(String filename) {
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

	private StringBuilder replaceText(StringBuilder buffer, String subs, String texto) {
		StringBuilder changedText = new StringBuilder(buffer.toString());
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
