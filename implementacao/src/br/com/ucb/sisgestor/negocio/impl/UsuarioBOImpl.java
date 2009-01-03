/*
 * Projeto: sisgestor
 * Cria��o: 28/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
import br.com.ucb.sisgestor.persistencia.impl.UsuarioDAOImpl;

/**
 * Objeto de neg�cio para {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 28/12/2008
 */
public class UsuarioBOImpl extends BaseBOImpl implements UsuarioBO {

	private static final UsuarioBO	instancia	= new UsuarioBOImpl();
	private UsuarioDAO					dao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link UsuarioBOImpl}.
	 */
	private UsuarioBOImpl() {
		this.dao = UsuarioDAOImpl.getInstancia();
	}

	/**
	 * Recupera a inst�ncia de {@link UsuarioBO}. pattern singleton.
	 * 
	 * @return {@link UsuarioBO}
	 */
	public static UsuarioBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario recuperarPorLogin(String login) throws NegocioException {
		Usuario usuario = this.dao.recuperarPorLogin(login);
		if (usuario == null) {
			throw new NegocioException("erro.usuarioNaoEncontrado");
		} else {
			return usuario;
		}
	}
}
