/*
 * Projeto: SisGestor
 * Criação: 10/12/2008 por João Lúcio
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
 * Acessos a autenticação do JBoss 4.2.3.GA
 * 
 * @author João Lúcio
 * @since 10/12/2008
 */
public class LoginHelper {

	private static final Log		LOG					= LogFactory.getLog(LoginHelper.class);
	private static final String	LTPATOKEN_NAME		= "LtpaToken";
	private static final String	LTPATOKEN2_NAME	= "LtpaToken2";

	/**
	 * Efetua o login do usuário.
	 * 
	 * @param request request atual
	 * @param response response atual
	 * @param username nome do usuário a logar
	 * @param password senha do usuário a logar
	 * @throws LoginException caso login não efetuado com sucesso
	 */
	public void doLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) throws LoginException {
		LOG.debug("Efetuando autenticação.");
		WebAuthentication auth = new WebAuthentication();
		auth.login(username, password);
		LOG.debug("request.getUserPrincipal() = " + request.getUserPrincipal());
		if (request.getUserPrincipal() == null) {
			throw new LoginException("Login inválido");
		}
	}

	/**
	 * Efetua o logout do usuário.
	 * 
	 * @param request request atual
	 * @param response response atual
	 */
	public void doLogout(HttpServletRequest request, HttpServletResponse response) {
		WebAuthentication auth = new WebAuthentication();
		auth.logout();

		LOG.debug("Invalidando a sessão HTTP");
		request.getSession().invalidate();
		LOG.debug("Removendo cookies LtpaToken e LtpaToken2.");
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies) {
			if (LTPATOKEN_NAME.equals(cookie.getName()) || LTPATOKEN2_NAME.equals(cookie.getName())) {
				LOG.debug("Cookie " + cookie.getName() + " encontrado.");
				cookie.setMaxAge(0);
			}
		}
	}
}
