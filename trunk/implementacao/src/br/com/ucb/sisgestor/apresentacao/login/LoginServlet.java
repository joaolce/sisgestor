/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;

import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ResourceBundle;
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

	private static final String	CHARSET			= "ISO-8859-1";
	private static final String	HTML_MIME_TYPE	= "text/html";
	private static final String	ERRO				= "erro";
	private static final String	LOGIN_ERROR		= "loginerror";
	private static final String	LOGOUT			= "logout";
	private static final Log		LOG				= LogFactory.getLog(LoginServlet.class);

	private LoginBundle				loginBundle;
	private LoginHelper				loginHelper;
	private ResourceBundle			properties;

	private UsuarioBO					usuarioBO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		this.loginBundle = new LoginBundle();
		this.loginHelper = new LoginHelper();
		try {
			this.properties = ResourceBundle.getBundle("sisgestor");
		} catch (Exception e) {
			LOG.warn("Erro ao capturar properties", e);
		}
		this.usuarioBO = Utils.getBean(UsuarioBO.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String param;
		if (request.getParameter(LOGOUT) == null) {
			param = request.getParameter(ERRO);
			if (param == null) {
				LOG.debug("Página de login.");
				this.escrevePagina(response, this.loginBundle.getLoginPage(""));
			} else {
				LOG.debug("Página de acesso negado.");
				int erro = "".equals(param) ? 0 : Integer.parseInt(param);
				String pagina;
				if ((erro == HttpServletResponse.SC_FORBIDDEN)
						|| (erro == HttpServletResponse.SC_METHOD_NOT_ALLOWED)) {
					pagina = this.loginBundle.getErroPage(this.getMensagem("erro.acessoNegado"));
				} else {
					pagina = this.loginBundle.getErroPage(this.getMensagem("erro.desconhecido"));
				}
				this.escrevePagina(response, this.ajustarReferenciasPaginaErro(request, pagina));
			}
		} else {
			LOG.debug("Efetuando logout.");
			this.loginHelper.doLogout(request);
			response.sendRedirect(".");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		if (request.getParameter(LOGIN_ERROR) == null) {
			String operacao = request.getParameter("opr");
			LOG.debug("Operação: " + operacao);
			if ("paginaAvisarSenha".equals(operacao)) {
				LOG.debug("Página de lembrete de senha.");
				this.escrevePagina(response, this.loginBundle.getSenhaPage(""));
			} else if ("lembrarSenha".equals(operacao)) {
				LOG.debug("Página de lembrete de senha, enviando a senha.");
				this.enviarLembreteDeSenha(request, response);
			} else {
				LOG.debug("Operação não identificada. Redirecionando para a raiz da aplicação.");
				response.sendRedirect(".");
			}
		} else {
			LOG.debug("Página de login negado.");
			this
					.escrevePagina(response, this.loginBundle.getLoginPage(this
							.getMensagem("erro.loginSemSucesso")));
		}
	}

	/**
	 * Ajusta referências para imagens e css para que possa ser exibido corretamente. <br />
	 * obs: isto ocorre por cauxa de acesso direto, para página de erro.
	 * 
	 * @param request request atual
	 * @param pagina página a ser exibida
	 * @return html da página com as referências atualizadas
	 */
	private String ajustarReferenciasPaginaErro(HttpServletRequest request, String pagina) {
		String servlet = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if (servlet.equalsIgnoreCase("default")) {
			String novaPagina = pagina.replaceAll("css/", "/sisgestor/css/");
			return novaPagina.replaceAll("imagens/", "/sisgestor/imagens/");
		}
		return pagina;
	}

	/**
	 * Envia a senha do usuário para o seu e-mail.
	 * 
	 * @param request request atual
	 * @param response response atual
	 * @throws IOException
	 */
	private void enviarLembreteDeSenha(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String login = this.getEncodedParamValue(request, "login", 15);
		try {
			boolean ok = this.usuarioBO.enviarLembreteDeSenha(login);
			if (ok) {
				this.escrevePagina(response, this.loginBundle.getSenhaPage(this
						.getMensagem("mensagem.senhaEnviada")));
			} else {
				this.escrevePagina(response, this.loginBundle.getSenhaPage(this
						.getMensagem("erro.senhaNaoEnviada")));
			}
		} catch (NegocioException ne) {
			this
					.escrevePagina(response, this.loginBundle.getSenhaPage(this
							.getMensagem("erro.senhaNaoEnviada")));
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
		writer.print(pagina);
		writer.flush();
	}

	/**
	 * Recupera um parâmetro enviado no request feito.
	 * 
	 * @param request request atual
	 * @param paramName nome do parâmetro
	 * @param maxLength tamanho máximo do valor
	 * @return valor do parâmetro
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
		if ((maxLength > 0) && (maxLength < valorParametro.length())) {
			valorParametro = valorParametro.substring(0, maxLength);
		}
		return valorParametro;
	}

	/**
	 * Recupera uma mensagem no properties.
	 * 
	 * @param key chave da mensagem
	 * @return mensagem
	 */
	private String getMensagem(String key) {
		return this.properties.getString(key);
	}
}
