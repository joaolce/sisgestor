/*
 * Projeto: sisgestor
 * Cria��o: 23/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.ConstantesContexto;
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
		try {
			this.armazenaUsuarioNaSessao(request);
			super.doFilterInternal(request, response, chain);
		} finally {
			HibernateUtil.getInstancia().closeSession();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		Utils.setContexto(this.getServletContext());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String url = request.getRequestURI();
		return (url.contains("/css/") || url.contains("/imagens/") || url.contains("/js/")
				|| url.contains("/paginas/") || url.contains("/relatorios/"));
	}

	/**
	 * Armazena o usu�rio logado na sess�o web.
	 * 
	 * @param request {@link HttpServletRequest} atual
	 */
	private void armazenaUsuarioNaSessao(HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute(ConstantesContexto.USUARIO_SESSAO);
		Utils.setUsuario(usuario);
	}
}
