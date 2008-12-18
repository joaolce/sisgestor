/*
 * Projeto: SisGestor
 * Criação: 10/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 10/12/2008
 */
public abstract class BaseLoginHelper implements LoginHelper {

	private static Log				logger;

	private static final String	LTPATOKEN_NAME		= "LtpaToken";

	private static final String	LTPATOKEN2_NAME	= "LtpaToken2";

	static {
		logger = LogFactory.getLog(BaseLoginHelper.class);
	}

	/**
	 * Cria uma nova instância do tipo BaseLoginHelper
	 * 
	 */
	public BaseLoginHelper() {
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param x0
	 * @return d
	 */
	static Class _mthclass$(String x0) {
		try {
			return Class.forName(x0);
		} catch (ClassNotFoundException x1) {
			throw new NoClassDefFoundError(x1.getMessage());
		}
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#doLogin(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
	 */
	public abstract void doLogin(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, String s, String s1) throws LoginException;

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#doLogout(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("<BaseLoginHelper :: doLogout> Invalidando a sess\343o HTTP.");
		request.getSession().invalidate();
		logger.debug("<BaseLoginHelper :: doLogout> Removendo cookies LtpaToken e LtpaToken2.");
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("LtpaToken".equals(cookie.getName()) || "LtpaToken2".equals(cookie.getName())) {
				logger.debug("<BaseLoginHelper :: doLogout> Cookie " + cookie.getName() + " encontrado.");
				cookie.setMaxAge(0);
			}
		}

	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#doMudaSenha(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public abstract void doMudaSenha(String s, String s1, String s2) throws LoginException;

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#storeInitialURL(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	public abstract void storeInitialURL(HttpServletRequest httpservletrequest, String s);
}
