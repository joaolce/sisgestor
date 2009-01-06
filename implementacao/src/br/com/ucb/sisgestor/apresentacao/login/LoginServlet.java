/*
 * Projeto: SisGestor
 * Cria��o: 09/12/2008 por Jo�o L�cio
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
 * Servlet de login do usu�rio.
 * 
 * @author Jo�o L�cio
 * @since 09/12/2008
 */
public final class LoginServlet extends HttpServlet {

	private static Log				logger;
	private static final String	CHARSET			= "ISO-8859-1";
	private static final String	HTML_MIME_TYPE	= "text/html";
	private static final String	ERROR				= "error";
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
		String param;
		if (null != request.getParameter(LOGOUT)) {
			logger.debug("Efetuando logout.");
			this.loginHelper.doLogout(request, response);
			response.sendRedirect(".");
		} else if (null != (param = request.getParameter(ERROR))) {
			logger.debug("P�gina de acesso negado.");
			int erro = param.equals("") ? 0 : Integer.parseInt(param);
			if ((erro == HttpServletResponse.SC_METHOD_NOT_ALLOWED)
					|| (erro == HttpServletResponse.SC_FORBIDDEN)) {
				this.escrevePagina(response, this.loginBundle.getErroPage("Acesso n�o autorizado."));
			} else {
				this.escrevePagina(response, this.loginBundle.getErroPage("Erro n�o reconhecido."));
			}
		} else {
			logger.debug("P�gina de login.");
			this.escrevePagina(response, this.loginBundle.getLoginPage(""));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		if (null != request.getParameter(LOGIN_ERROR)) {
			logger.debug("P�gina de login negado.");
			this.escrevePagina(response, this.loginBundle.getLoginPage("Login e/ou senha inv�lida."));
		} else {
			String operacao = request.getParameter("opr");
			logger.debug("Opera��o: " + operacao);
			if ("paginaAvisarSenha".equals(operacao)) {
				logger.debug("P�gina de lembrete de senha.");
				this.escrevePagina(response, this.loginBundle.getSenhaPage(""));
			} else if ("lembrarSenha".equals(operacao)) {
				logger.debug("P�gina de lembrete de senha, enviando a senha.");
				String login = this.getEncodedParamValue(request, "login", 15);
				//TODO: fazer opera��o de envio de senha
				logger.info("login: " + login);
				this.escrevePagina(response, this.loginBundle.getSenhaPage("Senha enviada."));
			} else {
				logger.debug("Opera��o n�o identificada. Redirecionando para a raiz da aplica��o.");
				response.sendRedirect(".");
			}
		}
	}

	/**
	 * Escreve a p�gina html no browser.
	 * 
	 * @param response response atual
	 * @param pagina html a escrever
	 * @throws IOException
	 */
	private void escrevePagina(HttpServletResponse response, String pagina) throws IOException {
		response.setContentType(HTML_MIME_TYPE);
		PrintWriter writer = response.getWriter();
		writer.print(pagina);
		writer.flush();
	}

	/**
	 * Recupera um par�metro enviado no request feito.
	 * 
	 * @param request request atual
	 * @param paramName nome do par�metro
	 * @param maxLength tamanho m�ximo do valor
	 * @return valor do par�metro
	 */
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
