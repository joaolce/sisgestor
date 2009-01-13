/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.constantes;

import java.net.URL;
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

	private static String	BIND_NAME_CONTEXT_PATH	= "java:contextPathAplicacao";
	private static String	BIND_NAME_URL_APLICACAO	= "java:urlAplicacao";
	private static boolean	constantesOk				= false;
	private static Log		log							= LogFactory.getLog(ConstantesAplicacao.class);

	/**
	 * @see HttpServletRequest#getContextPath()
	 * 
	 * @return d
	 * @throws Exception
	 */
	public static String getContextPath() throws Exception {
		InitialContext context = new InitialContext();
		return (String) context.lookup(BIND_NAME_CONTEXT_PATH);
	}

	/**
	 * Url da aplicação
	 * 
	 * @return d
	 * @throws Exception
	 */
	public static String getUrlAplicacao() throws Exception {
		InitialContext context = new InitialContext();
		return (String) context.lookup(BIND_NAME_URL_APLICACAO);
	}

	/**
	 * Seta as constantes na primeira vez
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void setConstantes(HttpServletRequest request) throws Exception {
		if (!constantesOk) {
			String contextPath = request.getContextPath();
			setUrlAplicacao(RequestUtils.serverURL(request), contextPath);
			setContextPath(contextPath);
			constantesOk = true;
		}
	}

	private static void setContextPath(String contextPath) throws Exception {
		setJNDIAttribute(BIND_NAME_CONTEXT_PATH, contextPath);
	}

	private static void setJNDIAttribute(String name, String value) throws NamingException {
		InitialContext context = new InitialContext();
		try {
			context.bind(name, value);
		} catch (NameAlreadyBoundException e) {
			log.warn(name + " já setado no contexto! valor: " + value);
		}
	}

	private static void setUrlAplicacao(String url) throws Exception {
		setJNDIAttribute(BIND_NAME_URL_APLICACAO, url);
	}

	private static void setUrlAplicacao(URL serverURL, String contextPath) throws Exception {
		String location = serverURL.toExternalForm() + contextPath;
		log.info("URL APLICAÇÃO: " + location);
		setUrlAplicacao(location);
	}
}
