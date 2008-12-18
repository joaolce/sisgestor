/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet para login do usu�rio
 * 
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public class LoginServlet extends HttpServlet {

	private static StringBuilder	html	= new StringBuilder("");

	/**
	 * Executado na inicia��o da servlet
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		carregaHTML();
	}

	/**
	 * Execu��o via m�todo http GET
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doPost(req, resp);
	}

	/**
	 * Execu��o via m�todo http POST
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		Writer writer = resp.getWriter();
		writer.write(html.toString());
		writer.flush();
	}

	/**
	 * Carrega o arquivo Html para ser usado na p�gina de login
	 */
	private synchronized void carregaHTML() {
		InputStreamReader reader =
				new InputStreamReader(getServletContext().getResourceAsStream("/paginas/login.jsp"));
		int size;
		char[] buf = new char[64];
		try {
			while ((size = reader.read(buf)) != -1) {
				html.append(buf, 0, size);
			}
		} catch (IOException e) {
			LogFactory.getLog(LoginServlet.class).error(e.getMessage(), e);
		}
		try {
			reader.close();
		} catch (Exception e) {
			LogFactory.getLog(LoginServlet.class).error(e.getMessage(), e);
		}
	}
}
