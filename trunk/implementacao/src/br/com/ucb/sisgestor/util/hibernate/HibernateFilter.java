/*
 * Projeto: sisgestor
 * Cria��o: 23/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * Filtro open session in view para manipular a sess�o do hibernate que pode ser criada na m�o.
 * 
 * @author Jo�o L�cio
 * @since 23/03/2009
 */
public class HibernateFilter extends OpenSessionInViewFilter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// Caso n�o seja necess�rio usar uma sess�o do Hibernate, executa apenas os filtros restantes da cadeia
		if (this.isNoSessionRequired(request)) {
			chain.doFilter(request, response);
			return;
		}

		try {
			super.doFilterInternal(request, response, chain);
		} finally {
			HibernateUtil.getInstancia().closeSession();
		}
	}


	/**
	 * Verifica se � uma url conhecida por n�o necessitar de cria��o de sess�o do hibernate.
	 * 
	 * @param request objeto de request HTTP
	 * @return <code>true</code> caso n�o necessite, <code>false</code> caso contr�rio
	 */
	private boolean isNoSessionRequired(HttpServletRequest request) {
		String url = request.getRequestURI();
		return (url.contains("/css/") || url.contains("/imagens/") || url.contains("/js/")
				|| url.contains("/paginas/") || url.contains("/relatorios/"));
	}
}
