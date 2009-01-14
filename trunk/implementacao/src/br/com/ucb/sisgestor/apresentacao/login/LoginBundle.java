/*
 * Projeto: SisGestor
 * Cria��o: 09/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.login;

import br.com.ucb.sisgestor.util.constantes.Constantes;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe com os recursos utilizados para efetuar o login.
 * 
 * @author Jo�o L�cio
 * @since 09/12/2008
 */
public final class LoginBundle {

	private static Log				logger;
	private static final String	LOCAL_RESOURCE	= "resources/";
	private static final String	ERRO_RESOURCE	= "erro_page.html";
	private static final String	LOGIN_RESOURCE	= "login_page.html";
	private static final String	SENHA_RESOURCE	= "senha_page.html";
	private static final String	MENSAGEM			= "#MENSAGEM#";
	private static final String	VERSAO			= "#VERSAO#";
	private static final String	VERSAO_DATA		= "#VERSAO_DATA#";
	private static StringBuilder	erroPage;
	private static StringBuilder	loginPage;
	private static StringBuilder	senhaPage;

	static {
		logger = LogFactory.getLog(LoginBundle.class);
	}

	/**
	 * Cria uma nova inst�ncia do tipo {@link LoginBundle}
	 */
	public LoginBundle() {
		this.loadErroPage();
		this.loadLoginPage();
		this.loadSenhaPage();
	}

	/**
	 * Recupera a p�gina de erro.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da p�gina de acesso negado
	 */
	public String getErroPage(String mensagem) {
		return this.replaceText(erroPage, MENSAGEM, mensagem).toString();
	}

	/**
	 * Recupera a p�gina de login.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da p�gina de login
	 */
	public String getLoginPage(String mensagem) {
		return this.replaceText(loginPage, MENSAGEM, mensagem).toString();
	}

	/**
	 * Recupera a p�gina de recupera��o da senha.
	 * 
	 * @param mensagem mensagem a ser colocada no html
	 * @return html da p�gina de recupera��o da senha
	 */
	public String getSenhaPage(String mensagem) {
		return this.replaceText(senhaPage, MENSAGEM, mensagem).toString();
	}

	/**
	 * Carrega a p�gina de erro.
	 */
	private void loadErroPage() {
		erroPage = this.loadResourceFile(LOCAL_RESOURCE + ERRO_RESOURCE);
		erroPage = this.replaceText(erroPage, VERSAO, Constantes.VERSAO);
		erroPage = this.replaceText(erroPage, VERSAO_DATA, Constantes.VERSAO_DATA);
	}

	/**
	 * Carrega a p�gina de login.
	 */
	private void loadLoginPage() {
		loginPage = this.loadResourceFile(LOCAL_RESOURCE + LOGIN_RESOURCE);
		loginPage = this.replaceText(loginPage, VERSAO, Constantes.VERSAO);
		loginPage = this.replaceText(loginPage, VERSAO_DATA, Constantes.VERSAO_DATA);
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
	 * Carrega a p�gina de recupera��o da senha.
	 */
	private void loadSenhaPage() {
		senhaPage = this.loadResourceFile(LOCAL_RESOURCE + SENHA_RESOURCE);
		senhaPage = this.replaceText(senhaPage, VERSAO, Constantes.VERSAO);
		senhaPage = this.replaceText(senhaPage, VERSAO_DATA, Constantes.VERSAO_DATA);
	}

	/**
	 * Substitu� do html a mensagem informada
	 * 
	 * @param html html a substituir a mensagem
	 * @param subs string a substituir
	 * @param texto texto a ser colocado
	 * @return html modificado
	 */
	private StringBuilder replaceText(StringBuilder html, String subs, String texto) {
		StringBuilder sb = new StringBuilder(html.toString());
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
