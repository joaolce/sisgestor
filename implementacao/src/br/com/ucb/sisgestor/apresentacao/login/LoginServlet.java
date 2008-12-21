/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet de login do usuário.
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public final class LoginServlet extends HttpServlet {

	private static Log				logger;
	private static final String	CHARSET			= "ISO-8859-1";
	private static final String	HTML_MIME_TYPE	= "text/html";
	private static final String	LOGIN_ERROR		= "loginerror";
	private static final String	LOGOUT			= "logout";

	private LoginHelper				loginHelper;
	private LoginBundle				loginBundle;

	static {
		logger = LogFactory.getLog(LoginServlet.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws ServletException {
		this.loginBundle = new LoginBundle();
		this.loginHelper = new LoginHelper();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String pagina = null;
		if (null != request.getParameter(LOGIN_ERROR)) {
			logger.debug("Página de acesso negado.");
			pagina = this.loginBundle.getErrorPage();
		} else if (null != request.getParameter(LOGOUT)) {
			logger.debug("Efetuando logout.");
			this.loginHelper.doLogout(request, response);
			logger.debug("Redirecionando para tela de login.");
			pagina = this.loginBundle.getLoginPage();
		} else {
			logger.debug("Página de login.");
			pagina = this.loginBundle.getLoginPage();
		}
		this.escrevePagina(response, pagina);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		if (null != request.getParameter(LOGIN_ERROR)) {
			logger.debug("Página de acesso negado.");
			this.escrevePagina(response, this.loginBundle.getErrorPage());
		} else {
			String nextURL = "";
			String operation = request.getParameter("opr");
			logger.debug("Operação: " + operation);
			if ("paginaAvisarSenha".equals(operation)) {
				// código
			} else if ("lembrarSenha".equals(operation)) {
				// código
			} else {
				logger.debug("Operação não identificada. Redirecionando para a raiz da aplicação.");
				nextURL = ".";
			}
			logger.debug("Efetuando redirecionamento.");
			response.sendRedirect(nextURL);
		}
	}

	/**
	 * Escreve a página html no browser.
	 * 
	 * @param response response atual
	 * @param pagina html a escrever
	 * @throws IOException
	 */
	private void escrevePagina(HttpServletResponse response, String pagina) throws IOException {
		response.setContentType(HTML_MIME_TYPE);
		PrintWriter writer = response.getWriter();
		writer.println(pagina);
		writer.flush();
	}

	private String getEncodedParamValue(HttpServletRequest request, String paramName, int maxLength) {
		String valorParametro = request.getParameter(paramName);
		if (valorParametro == null) {
			valorParametro = "";
		} else {
			try {
				valorParametro = URLEncoder.encode(valorParametro.trim(), CHARSET);
			} catch (UnsupportedEncodingException ex) {
				valorParametro = "";
			}
		}
		if (maxLength > 0) {
			if (maxLength > valorParametro.length()) {
				maxLength = valorParametro.length();
			}
			valorParametro = valorParametro.substring(0, maxLength);
		}
		return valorParametro;
	}
}
