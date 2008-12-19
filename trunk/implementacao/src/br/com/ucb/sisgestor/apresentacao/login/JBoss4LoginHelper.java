/*
 * Projeto: SisGestor
 * Criação: 10/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.web.tomcat.security.login.WebAuthentication;


/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 10/12/2008
 */
public class JBoss4LoginHelper extends BaseLoginHelper {

	private static Log	logger;

	static {
		logger = LogFactory.getLog(JBoss4LoginHelper.class);
	}

	/**
	 * Cria uma nova instância do tipo JBoss4LoginHelper
	 * 
	 */
	public JBoss4LoginHelper() {
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
	 * @see br.com.ucb.sisgestor.apresentacao.login.BaseLoginHelper#doLogin(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
	 */
	@Override
	public void doLogin(HttpServletRequest request, HttpServletResponse response, String _username,
			String password) throws LoginException {
		logger.debug("<JBoss4LoginHelper :: doLogin> Efetuando autentica\347\343o.");
		String username = _username.replaceAll("\\.", "\\_");
		WebAuthentication auth = new WebAuthentication();
		auth.login(username, password);
		logger.debug("<JBoss4LoginHelper :: doLogin> request.getUserPrincipal() = "
				+ request.getUserPrincipal());
		if (request.getUserPrincipal() == null) {
			throw new LoginException("Login inv\341lido", 1, "", "", username, null);
		} else {
			return;
		}
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.BaseLoginHelper#doLogout(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doLogout(HttpServletRequest request, HttpServletResponse response) {
		WebAuthentication auth = new WebAuthentication();
		auth.logout();
		super.doLogout(request, response);
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.BaseLoginHelper#doMudaSenha(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void doMudaSenha(String username, String password, String newPassword) throws LoginException {
		logger.warn("<JBossLoginHelper :: doMudaSenha> M\351todo n\343o implementado.");
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#getDiasVencimentoSenha(java.lang.String)
	 */
	public int getDiasVencimentoSenha(String username) {
		return -1;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#getSeparador()
	 */
	public String getSeparador() {
		return "_";
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#implementsDiasParaVencSenha()
	 */
	public boolean implementsDiasParaVencSenha() {
		return false;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.LoginHelper#implementsDoMudaSenha()
	 */
	public boolean implementsDoMudaSenha() {
		return false;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.apresentacao.login.BaseLoginHelper#storeInitialURL(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	@Override
	public void storeInitialURL(HttpServletRequest httpservletrequest, String s) {
	}
}
