/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa��o da interface de acesso a dados de {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 01/01/2009
 */
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario, Integer> implements UsuarioDAO {

	private static final UsuarioDAO	instancia	= new UsuarioDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link UsuarioDAOImpl}
	 */
	private UsuarioDAOImpl() {
		super(Usuario.class);
	}

	/**
	 * Recupera a inst�ncia de {@link UsuarioDAO}. pattern singleton.
	 * 
	 * @return {@link UsuarioDAO}
	 */
	public static UsuarioDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario recuperarPorLogin(String login) {
		Criteria criteria = this.getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login).ignoreCase());
		return (Usuario) criteria.uniqueResult();
	}
}
