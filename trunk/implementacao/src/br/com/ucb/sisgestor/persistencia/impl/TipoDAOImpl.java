/*
 * Projeto: sisgestor
 * Cria��o: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Tipo;
import br.com.ucb.sisgestor.persistencia.TipoDAO;

/**
 * Implementa��o da interface de acesso aos dados de {@link Tipo}.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
public class TipoDAOImpl extends BaseDAOImpl<Tipo, Integer> implements TipoDAO {

	private static final TipoDAO	instancia	= new TipoDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link TipoDAOImpl}
	 */
	private TipoDAOImpl() {
		super(Tipo.class);
	}

	/**
	 * Recupera a inst�ncia de {@link TipoDAO}. pattern singleton.
	 * 
	 * @return {@link TipoDAO}
	 */
	public static TipoDAO getInstancia() {
		return instancia;
	}
}
