/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public interface LoginHelper {

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param httpservletrequest
	 * @param httpservletresponse
	 * @param s
	 * @param s1
	 * @throws LoginException
	 */
	public abstract void doLogin(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, String s, String s1) throws LoginException;

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param httpservletrequest
	 * @param httpservletresponse
	 */
	public abstract void doLogout(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse);

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @throws LoginException
	 */
	public abstract void doMudaSenha(String s, String s1, String s2) throws LoginException;

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param s
	 * @return d
	 */
	public abstract int getDiasVencimentoSenha(String s);

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public abstract String getSeparador();

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public abstract boolean implementsDiasParaVencSenha();

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public abstract boolean implementsDoMudaSenha();

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param httpservletrequest
	 * @param s
	 */
	public abstract void storeInitialURL(HttpServletRequest httpservletrequest, String s);
}
