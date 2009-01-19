/*
 * Projeto: sisgestor
 * Cria��o: 19/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.persistencia.PermissaoDAO;

/**
 * Implementa��o da interface de acesso a dados de {@link Permissao}.
 * 
 * @author Jo�o L�cio
 * @since 19/01/2009
 */
public class PermissaoDAOImpl extends BaseDAOImpl<Permissao, Integer> implements PermissaoDAO {

	private static PermissaoDAO	instancia	= new PermissaoDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link PermissaoDAOImpl}.
	 */
	public PermissaoDAOImpl() {
		super(Permissao.class);
	}

	/**
	 * Recupera a inst�ncia de {@link PermissaoDAO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link PermissaoDAO}
	 */
	public static PermissaoDAO getInstancia() {
		return instancia;
	}
}
