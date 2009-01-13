/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
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
 * Filtro para gerenciar abertura e fechamento de sessões a cada requisição a {@link ActionServlet} A sessão
 * somente será fechada após a execução de todo o fluxo:
 * <p>
 * requisição -> filtro(abreSessão) -> ActionServlet -> action -> objetoNegócio -> acessoADados
 * </p>
 * <p>
 * resposta <- filtro(fechaSessão) <- JSP <- ActionServlet <- action <- objetoNegócio <- acessoADados
 * </p>
 * 
 * @author João Lúcio
 * @since 23/10/2008
 */
public class HibernateFilter implements Filter {

	/**
	 * Executado na destruição do filtro
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * Executado a cada requisição
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
	 * Executado na inicialização do filtro
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
