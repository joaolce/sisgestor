/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public class LoginServlet extends HttpServlet {

	private HttpServletRequest		request;

	private static final long		serialVersionUID				= 1L;

	private static Log				logger;

	private static final String	HTML_MIME_TYPE					= "text/html";

	private static final String	TRUE								= "true";

	private static final String	PREFIXO_CPF						= "980000001";

	private static final String	SEPARADOR						= ".";

	private static final String	ATRIBUTO_MENSAGEM_ERRO		= "bacen.security.msgErro";

	private static final String	ATRIBUTO_DIAS_PARA_VENCER	= "bacen.security.diasParaVencer";

	private LoginServletBundle		loginBundle;

	private LoginHelper				loginHelper;

	private static final String	CHARSET							= "ISO-8859-1";

	private static final String	CTX_APP_SERVER_ATTR			= "APP_SERVER";

	private static final String	WAS_ATTR_VALUE					= "WAS";

	private static final String	NON_WAS_ATTR_VALUE			= "NON-WAS";

	private static final String	CTX_SEPARADOR_ATTR			= "BC_SEPARADOR_LOGIN";

	static {
		logger = LogFactory.getLog(LoginServlet.class);
	}

	/**
	 * Cria uma nova instância do tipo LoginServlet
	 * 
	 */
	public LoginServlet() {
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
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		Map parametros = this.loadInitParameters(config);
		this.loginBundle = new LoginServletBundle(parametros);
		this.loginHelper = LoginHelperFactory.getLoginHelper();
		int appServer = LoginHelperFactory.getApplicationServer();
		String appServerId;
		if (appServer == 1) {
			appServerId = "WAS";
		} else {
			appServerId = "NON-WAS";
		}
		config.getServletContext().setAttribute("APP_SERVER", appServerId);
		config.getServletContext().setAttribute("BC_SEPARADOR_LOGIN", this.loginHelper.getSeparador());
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.request = request;
		String pagina = null;
		logger.debug("<LoginServlet :: doGet> Requisi\347\343o: " + request.getQueryString());
		String message = this.pop(request.getSession(), "bacen.security.msgErro");
		String unidadeAtt = this.getAttr(request.getSession(), "bacen.security.unidade");
		String dependenciaAtt = this.getAttr(request.getSession(), "bacen.security.dependencia");
		String operadorAtt = this.getAttr(request.getSession(), "bacen.security.operador");
		String cpfAtt = this.getAttr(request.getSession(), "bacen.security.cpf");
		if ("true".equals(request.getParameter("redirect"))) {
			logger.debug("<LoginServlet :: doGet> A\347\343o de redirecionamento.");
			this.redirectPageHomeAfterLogin(request, response);
		} else if ("true".equals(request.getParameter("logout"))) {
			logger.debug("<LoginServlet :: doGet> P\341gina de logout.");
			pagina = this.loginBundle.getLogoutPage();
		} else if ("true".equals(request.getParameter("mudasenha"))) {
			if (this.loginHelper.implementsDoMudaSenha()) {
				logger.debug("<LoginServlet :: doGet> P\341gina de mudan\347a de senha.");
				pagina =
						this.loginBundle.getChangePasswordPage(message, unidadeAtt, dependenciaAtt, operadorAtt,
								cpfAtt);
			} else {
				logger
						.debug("<LoginServlet :: doGet> P\341gina de login  (helper n\343o efetua troca de senha).");
				pagina = this.loginBundle.getLoginPage(false);
			}
		} else if (null != request.getParameter("error")) {
			logger.debug("<LoginServlet :: doGet> P\341gina de acesso negado.");
			pagina = this.loginBundle.getErrorPage();
		} else if ("true".equals(request.getParameter("expire"))) {
			String dias = this.getAttr(request.getSession(), "bacen.security.diasParaVencer");
			if ("".equals(dias)) {
				this.redirectPageHomeAfterLogin(request, response);
			} else {
				pagina = this.loginBundle.getPasswordWillExpirePage(dias);
			}
		} else if ("true".equals(request.getParameter("ajuda"))) {
			logger.debug("<LoginServlet :: doGet> P\341gina de ajuda.");
			pagina = this.loginBundle.getHelpPage();
		} else {
			logger.debug("<LoginServlet :: doGet> P\341gina de login.");
			pagina =
					this.loginBundle.getLoginPage(message, this.loginHelper.implementsDoMudaSenha(), unidadeAtt,
							dependenciaAtt, operadorAtt, cpfAtt);
		}
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println(pagina);
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String nextURL = "";
		String operation = request.getParameter("operacao");
		logger.debug("<LoginServlet :: doPost> Opera\347\343o: " + operation);
		this.loginHelper.storeInitialURL(request, this.loginBundle.getHomeAfterLogin());
		if ("login".equals(operation)) {
			boolean validInfo = true;
			String username = "";
			String senha = "";
			try {
				username = this.getUsername(request);
				senha = this.getEncodedParamValue(request, "senha", "", 8);
				if ((senha == null) || "".equals(senha)) {
					throw new IllegalArgumentException("Senha inv\341lida.");
				}
			} catch (IllegalArgumentException iaex) {
				logger.warn("<LoginServlet :: doPost> Dados de login inv\341lidos", iaex);
				this.write(request.getSession(), "Identifica&ccedil;&atilde;o e/ou senha inv&aacute;lidas",
						"bacen.security.msgErro");
				nextURL = "dologin";
				validInfo = false;
			}
			if (validInfo) {
				try {
					this.loginHelper.doLogin(request, response, username, senha);
					logger.info("<LoginServlet :: doPost> Login realizado com sucesso.");
					if (this.loginHelper.implementsDiasParaVencSenha()) {
						int diasParaVencSenha = this.loginHelper.getDiasVencimentoSenha(username);
						if ((diasParaVencSenha >= 0) && (diasParaVencSenha <= 3)) {
							logger.debug("<LoginServlet :: doPost> A senha vencer\341 em breve.");
							this
									.write(request.getSession(), "" + diasParaVencSenha,
											"bacen.security.diasParaVencer");
							nextURL = "dologin?expire=true";
						} else {
							nextURL = "dologin?redirect=true";
						}
					} else {
						nextURL = "dologin?redirect=true";
					}
				} catch (LoginException ex) {
					logger.warn("<LoginServlet :: doPost> N\343o foi poss\355vel efetuar Login.", ex);
					String errorMsg;
					if (ex.getErrorCode() == 3) {
						errorMsg = "A senha venceu e deve ser trocada.";
						nextURL = "dologin?mudasenha=true";
					} else {
						errorMsg = "Identifica&ccedil;&atilde;o e/ou senha inv&aacute;lidas";
						nextURL = "dologin";
					}
					this.write(request.getSession(), errorMsg, "bacen.security.msgErro");
				}
			}
		} else if ("altera".equals(operation)) {
			if (null != request.getParameter("cancelar_troca")) {
				logger.debug("<LoginServlet :: doPost> Troca de senha cancelada.");
				nextURL = "dologin?redirect=true";
			} else if (null != request.getParameter("changePwdOpt")) {
				String opcaoTrocaSenha = request.getParameter("changePwdOpt");
				opcaoTrocaSenha = opcaoTrocaSenha.trim().toUpperCase();
				logger.debug("<LoginServlet :: doPost> Op\347\343o de troca de senha: " + opcaoTrocaSenha);
				if ("SIM".equals(opcaoTrocaSenha)) {
					logger
							.debug("<LoginServlet :: doPost> Usu\341rio escolheu mudar senha (est\341 para vencer).");
					nextURL = "dologin?mudasenha=true";
				} else {
					logger
							.debug("<LoginServlet :: doPost> Usu\341rio n\343o mudar\341 a senha (est\341 para vencer).");
					nextURL = "dologin?redirect=true";
				}
			} else {
				boolean validInfo = true;
				String username = "";
				String senha = "";
				String senhaNova = "";
				String senhaNova2 = "";
				try {
					username = this.getUsername(request);
					senha = this.getEncodedParamValue(request, "senha", "", 8);
					senhaNova = this.getEncodedParamValue(request, "senhanova", "", 8);
					senhaNova2 = this.getEncodedParamValue(request, "senhanova2", "", 8);
					if ((senha == null) || "".equals(senha)) {
						throw new IllegalArgumentException("A senha atual inv\341lida.");
					}
					if ((senhaNova == null) || "".equals(senhaNova)) {
						throw new IllegalArgumentException("A senha nova inv\341lida.");
					}
					if ((senhaNova2 == null) || "".equals(senhaNova2)) {
						throw new IllegalArgumentException("A confirma\347\343o de senha nova inv\341lida.");
					}
					if (!senhaNova.equals(senhaNova2)) {
						throw new IllegalArgumentException("Senhas inv\341lidas.");
					}
				} catch (IllegalArgumentException iaex) {
					logger.warn("<LoginServlet :: doPost> Dados para mudan\347a de senha inv\341lidos", iaex);
					this.write(request.getSession(), "N&atilde;o foi poss&iacute;vel trocar a senha.",
							"bacen.security.msgErro");
					nextURL = "dologin?mudasenha=true";
					validInfo = false;
				}
				if (validInfo) {
					try {
						logger.debug("<LoginServlet :: doPost> Efetuando troca de senha.");
						this.loginHelper.doMudaSenha(username, senha, senhaNova);
						this.loginHelper.doLogin(request, response, username, senhaNova);
						logger.info("<LoginServlet :: doPost> Troca de senha realizada com sucesso.");
						nextURL = "dologin?redirect=true";
					} catch (LoginException ex1) {
						logger.warn("<LoginServlet :: doPost> Erro ao trocar senha. " + ex1.getMessage());
						if ((ex1.getErrorCode() == 5)
								&& (LoginHelperFactory.isDesenvolvimento() || LoginHelperFactory.isHomologacao())) {
							try {
								this.loginHelper.doLogin(request, response, username, senhaNova);
								nextURL = "dologin?redirect=true";
							} catch (LoginException ex2) {
								this.write(request.getSession(), "N&atilde;o foi poss&iacute;vel trocar a senha.",
										"bacen.security.msgErro");
								nextURL = "dologin?mudasenha=true";
							}
						} else {
							this.write(request.getSession(), "N&atilde;o foi poss&iacute;vel trocar a senha.",
									"bacen.security.msgErro");
							nextURL = "dologin?mudasenha=true";
						}
					}
				}
			}
		} else if ("logout".equals(operation)) {
			logger.debug("<LoginServlet :: doPost> Efetuando logout.");
			this.loginHelper.doLogout(request, response);
			if ((this.loginBundle.getHomeAfterLogout() != null)
					&& !"".equals(this.loginBundle.getHomeAfterLogout())) {
				logger.debug("<LoginServlet :: doPost> Redirecionando para HomeAfterLogout.");
				nextURL = this.loginBundle.getHomeAfterLogout();
			} else {
				logger.debug("<LoginServlet :: doPost> Redirecionando para tela de login.");
				nextURL = "dologin";
			}
		} else {
			logger
					.debug("<LoginServlet :: doPost> Opera\347\343o n\343o identificada. Redirecionando para a raiz da aplica\347\343o.");
			nextURL = ".";
		}
		logger.debug("<LoginServlet :: doPost> Efetuando redirecionamento.");
		response.sendRedirect(nextURL);
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param request
	 * @return d
	 */
	protected String getUsername(HttpServletRequest request) {
		String unidade = this.getEncodedParamValue(request, "unidade", "", 5);
		String dependencia = this.getEncodedParamValue(request, "dependencia", "", 4);
		String operador = this.getEncodedParamValue(request, "operador", "", 10);
		String cpf = this.getEncodedParamValue(request, "cpf", "", 9);
		this.write(request.getSession(), cpf, "bacen.security.cpf");
		this.write(request.getSession(), unidade, "bacen.security.unidade");
		this.write(request.getSession(), dependencia, "bacen.security.dependencia");
		this.write(request.getSession(), operador, "bacen.security.operador");
		if (!"".equals(operador) && !"".equals(cpf)) {
			logger.warn("Apenas o CPF ou o operador deve ser fornecido.");
			throw new IllegalArgumentException("Apenas o CPF ou o operador deve ser fornecido.");
		}
		String username;
		if (!"".equals(cpf)) {
			username = this.getUsername(cpf);
		} else {
			username = this.getUsername(unidade, dependencia, operador);
		}
		return username;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param cpf
	 * @return d
	 */
	protected String getUsername(String cpf) {
		String pattern = "[0-9]+";
		if ((cpf == null) || "".equals(cpf) || !Pattern.matches(pattern, cpf)) {
			logger.warn("<LoginServlet :: getUsername> CPF inv\341lido.");
			throw new IllegalArgumentException("CPF inv\341lido.");
		} else {
			String username = "980000001." + cpf;
			return username;
		}
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param _unidade
	 * @param _dependencia
	 * @param operador
	 * @return d
	 */
	protected String getUsername(String _unidade, String _dependencia, String operador) {
		String pattern = "[a-zA-Z0-9\\-]+";
		String unidade = _unidade;
		String dependencia = _dependencia;
		if ((unidade == null) || "".equals(unidade)) {
			logger.debug("<LoginServlet :: getUsername> Unidade n\343o especificada.");
			unidade = "";
		} else if (!Pattern.matches(pattern, unidade)) {
			logger.warn("<LoginServlet :: getUsername> Unidade inv\341lida.");
			throw new IllegalArgumentException("Unidade inv\341lida.");
		}
		if ((dependencia == null) || "".equals(dependencia)) {
			logger.debug("<LoginServlet :: getUsername> Depend\352ncia n\343o especificada.");
			dependencia = "";
		} else if (!Pattern.matches(pattern, dependencia)) {
			logger.warn("<LoginServlet :: getUsername> Depend\352ncia inv\341lida.");
			throw new IllegalArgumentException("Depend\352ncia inv\341lida.");
		}
		if ((operador == null) || "".equals(operador) || !Pattern.matches(pattern, operador)) {
			logger.warn("<LoginServlet :: getUsername> Operador inv\341lido.");
			throw new IllegalArgumentException("Operador inv\341lido.");
		}
		String username;
		if (!"".equals(unidade)) {
			username = unidade + dependencia + "." + operador;
		} else {
			username = operador;
		}
		return username;
	}

	private void clear(HttpSession session, String attribute) {
		session.removeAttribute(attribute);
	}

	private String getAttr(HttpSession session, String attribute) {
		String value = (String) session.getAttribute(attribute);
		if (value == null) {
			value = "";
		}
		return value;
	}

	private String getEncodedParamValue(HttpServletRequest request, String paramName, String _defaultValue,
			int _maxLength) {
		String defaultValue = _defaultValue;
		int maxLength = _maxLength;
		if (defaultValue == null) {
			defaultValue = "";
		} else {
			defaultValue = defaultValue.trim();
		}
		String valorParametro = request.getParameter(paramName);
		if (valorParametro == null) {
			valorParametro = defaultValue;
		} else {
			try {
				valorParametro = URLEncoder.encode(valorParametro.trim(), "ISO-8859-1");
			} catch (UnsupportedEncodingException ex) {
				valorParametro = defaultValue;
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

	private Map loadInitParameters(ServletConfig config) {
		Map parametros = new HashMap();
		String chaves[] =
				{"imgbigURL", "bacen.security.homeafterlogin", "bacen.security.semdependencia",
						"bacen.security.title", "bacen.security.title_logout", "bacen.security.telefone",
						"bacen.security.imagemsistema", "bacen.security.nomesistema",
						"bacen.security.urlpoliticaprivacidade"};
		logger.debug("<LoginServlet :: loadInitParameters> Obtendo par\342metros da Servlet.");
		String valorParametro;
		for (String chave : chaves) {
			valorParametro = config.getInitParameter(chave);
			if (valorParametro != null) {
				logger.debug("<LoginServlet :: loadInitParameters> " + chave + ": " + valorParametro);
				parametros.put(chave, valorParametro);
			}
		}

		valorParametro = config.getInitParameter("HOME_AFTER_LOGOUT");
		if (valorParametro == null) {
			valorParametro = config.getInitParameter("bacen.security.homeafterlogout");
			logger.debug("<LoginServlet :: loadInitParameters> bacen.security.homeafterlogout: "
					+ valorParametro);
		} else {
			logger.debug("<LoginServlet :: loadInitParameters> HOME_AFTER_LOGOUT: " + valorParametro);
		}
		if (valorParametro != null) {
			parametros.put("bacen.security.homeafterlogout", valorParametro);
		}
		return parametros;
	}

	private String pop(HttpSession session, String attribute) {
		String value = this.getAttr(session, attribute);
		this.clear(session, attribute);
		return value;
	}

	private void redirectPage(HttpServletResponse response, String url) throws IOException {
		String redirectURL = url;
		if ((redirectURL == null) || "".equals(redirectURL)) {
			redirectURL = ".";
		}
		logger.debug("<LoginServlet :: redirectPage> Redirecionando: " + redirectURL);
		response.sendRedirect(redirectURL);
	}

	private void redirectPageHomeAfterLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		String redirectURL = (String) session.getAttribute("bacen.redirectURL");
		logger.debug("<LoginServlet :: redirectPageHomeAfterLogin> Atributo REDIRECT_URL_ATTR: " + redirectURL);
		session.removeAttribute("bacen.redirectURL");
		this.redirectPage(response, redirectURL);
	}

	private void write(HttpSession session, String _value, String attribute) {
		String value = _value;
		if (value == null) {
			value = "";
		}
		session.setAttribute(attribute, value);
	}
}
