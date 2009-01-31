/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.constantes;

import java.net.URL;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.RequestUtils;

/**
 * Preenchidas com dados da aplicação quando a mesma é acessada primeira vez Aqui teremos informações sobre a
 * URL completa da aplicação Ou até mesmo contextPath
 */
public class ConstantesAplicacao {

	private static final String	BIND_NAME_CONTEXT_PATH	= "java:contextPathAplicacao";
	private static final String	BIND_NAME_URL_APLICACAO	= "java:urlAplicacao";
	private static Boolean			constantesOk				= Boolean.FALSE;
	private static final Log		LOG							= LogFactory.getLog(ConstantesAplicacao.class);

	/**
	 * Recupera o contexto da aplicação.
	 * 
	 * @see HttpServletRequest#getContextPath()
	 * 
	 * @return {@link String} do contexto
	 * @throws NamingException caso NamingException seja lançada
	 */
	public static String getContextPath() throws NamingException {
		Context context = new InitialContext();
		return (String) context.lookup(BIND_NAME_CONTEXT_PATH);
	}

	/**
	 * Recupera a url da aplicação.
	 * 
	 * @return {@link String} da url
	 * @throws NamingException caso NamingException seja lançada
	 */
	public static String getUrlAplicacao() throws NamingException {
		Context context = new InitialContext();
		return (String) context.lookup(BIND_NAME_URL_APLICACAO);
	}

	/**
	 * Seta as constantes na primeira vez.
	 * 
	 * @param request request atual
	 * @throws Exception caso ocorra exceção ao setar variáveis
	 */
	public static void setConstantes(HttpServletRequest request) throws Exception {
		synchronized (constantesOk) {
			if (!constantesOk) {
				String contextPath = request.getContextPath();
				setUrlAplicacao(RequestUtils.serverURL(request), contextPath);
				setContextPath(contextPath);
				constantesOk = Boolean.TRUE;
			}
		}
	}

	/**
	 * Armazena o contexto da aplicação.
	 * 
	 * @param contextPath contexto da aplicação
	 * @throws NamingException caso NamingException seja lançada
	 */
	private static void setContextPath(String contextPath) throws NamingException {
		setJNDIAttribute(BIND_NAME_CONTEXT_PATH, contextPath);
	}

	/**
	 * Armazena um atributo no contexto.
	 * 
	 * @param name nome do atributo
	 * @param value valor do atributo
	 * @throws NamingException caso NamingException seja lançada
	 */
	private static void setJNDIAttribute(String name, String value) throws NamingException {
		Context context = new InitialContext();
		try {
			context.bind(name, value);
		} catch (NameAlreadyBoundException e) {
			LOG.warn(name + " já setado no contexto! valor: " + value);
		}
	}

	/**
	 * Armazena a url da aplicação.
	 * 
	 * @param serverURL {@link URL} da aplicação
	 * @param contextPath contexto da aplicação
	 * @throws NamingException caso NamingException seja lançada
	 */
	private static void setUrlAplicacao(URL serverURL, String contextPath) throws NamingException {
		String url = serverURL.toExternalForm() + contextPath;
		setJNDIAttribute(BIND_NAME_URL_APLICACAO, url);
	}
}
