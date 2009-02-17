/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.persistencia.CampoDAO;

/**
 * Implementa��o da interface de acesso a dados de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class CampoDAOImpl extends BaseDAOImpl<Campo, Integer> implements CampoDAO {

	private static final CampoDAO	instancia	= new CampoDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link CampoDAOImpl}
	 */
	private CampoDAOImpl() {
		super(Campo.class);
	}

	/**
	 * Recupera a inst�ncia de {@link CampoDAO}. pattern singleton.
	 * 
	 * @return {@link CampoDAO}
	 */
	public static CampoDAO getInstancia() {
		return instancia;
	}
}
