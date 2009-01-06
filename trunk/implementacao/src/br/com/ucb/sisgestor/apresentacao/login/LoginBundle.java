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
	private static final String	ERRO_RESOURCE	= "erro_page.html";
	private static final String	LOGIN_RESOURCE	= "login_page.html";
	private static final String	SENHA_RESOURCE	= "senha_page.html";
	private static StringBuilder	erroPage;
	private static StringBuilder	loginPage;
	private static StringBuilder	senhaPage;

	static {
		logger = LogFactory.getLog(LoginBundle.class);
	}

	/**
	 * Cria uma nova instância do tipo {@link LoginBundle}
	 */
	public LoginBundle() {
		this.loadErroPage();
		this.loadLoginPage();
		this.loadSenhaPage();
	}

	/**
	 * Recupera a página de erro.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da página de acesso negado
	 */
	public String getErroPage(String mensagem) {
		return this.replaceText(erroPage, mensagem).toString();
	}

	/**
	 * Recupera a página de login.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da página de login
	 */
	public String getLoginPage(String mensagem) {
		return this.replaceText(loginPage, mensagem).toString();
	}

	/**
	 * Recupera a página de recuperação da senha.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da página de recuperação da senha
	 */
	public String getSenhaPage(String mensagem) {
		return this.replaceText(senhaPage, mensagem).toString();
	}

	/**
	 * Carrega a página de erro.
	 */
	private void loadErroPage() {
		erroPage = this.loadResourceFile(LOCAL_RESOURCE + ERRO_RESOURCE);
	}

	/**
	 * Carrega a página de login.
	 */
	private void loadLoginPage() {
		loginPage = this.loadResourceFile(LOCAL_RESOURCE + LOGIN_RESOURCE);
	}

	private synchronized StringBuilder loadResourceFile(String filename) {
		logger.debug("Obtendo o recurso: " + filename);
		InputStream is = (LoginBundle.class).getResourceAsStream(filename);
		byte buffer[] = new byte[256];
		int cnt = 0;
		StringBuilder html = new StringBuilder();
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
					html.append(c);
				}
			}
		} while (cnt > 0);
		return html;
	}

	/**
	 * Carrega a página de recuperação da senha.
	 */
	private void loadSenhaPage() {
		senhaPage = this.loadResourceFile(LOCAL_RESOURCE + SENHA_RESOURCE);
	}

	/**
	 * Substituí do html a mensagem informada
	 * 
	 * @param html html a substituir a mensagem
	 * @param texto texto a ser colocado
	 * @return html modificado
	 */
	private StringBuilder replaceText(StringBuilder html, String texto) {
		StringBuilder sb = new StringBuilder(html.toString());
		String subs = "#MENSAGEM#";
		int tam = subs.length();
		int pos = 0;
		do {
			pos = sb.indexOf(subs, pos);
			if (pos > 0) {
				sb.replace(pos, pos + tam, texto);
			}
		} while (pos > 0);
		return sb;
	}
}
