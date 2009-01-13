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
import java.util.List;

/**
 * Objeto de neg�cio para {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 28/12/2008
 */
public class UsuarioBOImpl extends BaseBOImpl<Usuario, Integer> implements UsuarioBO {

	private static final UsuarioBO	instancia	= new UsuarioBOImpl();
	private UsuarioDAO					dao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link UsuarioBOImpl}.
	 */
	private UsuarioBOImpl() {
		this.dao = UsuarioDAOImpl.getInstancia();
	}

	/**
	 * Recupera a inst�ncia de {@link UsuarioBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link UsuarioBO}
	 */
	public static UsuarioBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Usuario obj) {
		// TODO: implementar regras de neg�cio
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Usuario obj) {
		// TODO: implementar regras de neg�cio
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> obterTodos() {
		return this.dao.obterTodos();
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

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Usuario obj) {
		// TODO: implementar regras de neg�cio
	}
}
