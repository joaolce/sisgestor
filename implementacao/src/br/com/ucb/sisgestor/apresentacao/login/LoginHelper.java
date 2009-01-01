/*
 * Projeto: SisGestor
 * Cria��o: 10/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.login;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.web.tomcat.security.login.WebAuthentication;

/**
 * Acessos a autentica��o do JBoss 4.2.3.GA
 * 
 * @author Jo�o L�cio
 * @since 10/12/2008
 */
public class LoginHelper {

	private static Log				logger;
	private static final String	LTPATOKEN_NAME		= "LtpaToken";
	private static final String	LTPATOKEN2_NAME	= "LtpaToken2";

	static {
		logger = LogFactory.getLog(LoginHelper.class);
	}

	/**
	 * Efetua o login do usu�rio.
	 * 
	 * @param request request atual
	 * @param response response atual
	 * @param username nome do usu�rio a logar
	 * @param password senha do usu�rio a logar
	 * @throws LoginException
	 */
	public void doLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) throws LoginException {
		logger.debug("Efetuando autentica��o.");
		WebAuthentication auth = new WebAuthentication();
		auth.login(username, password);
		logger.debug("request.getUserPrincipal() = " + request.getUserPrincipal());
		if (request.getUserPrincipal() == null) {
			throw new LoginException("Login inv�lido");
		}
	}

	/**
	 * Efetua o logout do usu�rio.
	 * 
	 * @param request request atual
	 * @param response response atual
	 */
	public void doLogout(HttpServletRequest request, HttpServletResponse response) {
		WebAuthentication auth = new WebAuthentication();
		auth.logout();

		logger.debug("Invalidando a sess�o HTTP");
		request.getSession().invalidate();
		logger.debug("Removendo cookies LtpaToken e LtpaToken2.");
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies) {
			if (LTPATOKEN_NAME.equals(cookie.getName()) || LTPATOKEN2_NAME.equals(cookie.getName())) {
				logger.debug("Cookie " + cookie.getName() + " encontrado.");
				cookie.setMaxAge(0);
			}
		}
	}
}
