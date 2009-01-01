/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação da interface de acesso a dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario, Integer> implements UsuarioDAO {

	private static final UsuarioDAO	instancia	= new UsuarioDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link UsuarioDAOImpl}
	 */
	private UsuarioDAOImpl() {
		super(Usuario.class);
	}

	/**
	 * Recupera a instância de {@link UsuarioDAO}. pattern singleton.
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
