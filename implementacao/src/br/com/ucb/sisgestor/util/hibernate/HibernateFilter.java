/*
 * Projeto: sisgestor
 * Criação: 23/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * Filtro open session in view para manipular a sessão do hibernate que pode ser criada na mão.
 * 
 * @author João Lúcio
 * @since 23/03/2009
 */
public class HibernateFilter extends OpenSessionInViewFilter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// Caso não seja necessário usar uma sessão do Hibernate, executa apenas os filtros restantes da cadeia
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
	 * Verifica se é uma url conhecida por não necessitar de criação de sessão do hibernate.
	 * 
	 * @param request objeto de request HTTP
	 * @return <code>true</code> caso não necessite, <code>false</code> caso contrário
	 */
	private boolean isNoSessionRequired(HttpServletRequest request) {
		String url = request.getRequestURI();
		return (url.contains("/css/") || url.contains("/imagens/") || url.contains("/js/")
				|| url.contains("/paginas/") || url.contains("/relatorios/"));
	}
}
