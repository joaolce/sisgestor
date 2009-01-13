/*
 * Projeto: SisGestor
 * Cria��o: 23/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.struts.action.ActionServlet;

/**
 * Filtro para gerenciar abertura e fechamento de sess�es a cada requisi��o a {@link ActionServlet} A sess�o
 * somente ser� fechada ap�s a execu��o de todo o fluxo:
 * <p>
 * requisi��o -> filtro(abreSess�o) -> ActionServlet -> action -> objetoNeg�cio -> acessoADados
 * </p>
 * <p>
 * resposta <- filtro(fechaSess�o) <- JSP <- ActionServlet <- action <- objetoNeg�cio <- acessoADados
 * </p>
 * 
 * @author Jo�o L�cio
 * @since 23/10/2008
 */
public class HibernateFilter implements Filter {

	/**
	 * Executado na destrui��o do filtro
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * Executado a cada requisi��o
	 * 
	 * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Executado na inicializa��o do filtro
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
