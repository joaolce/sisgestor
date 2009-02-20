/*
 * Projeto: sisgestor
 * Cria��o: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Tipo;
import br.com.ucb.sisgestor.persistencia.TipoDAO;
import org.springframework.stereotype.Repository;

/**
 * Implementa��o da interface de acesso aos dados de {@link Tipo}.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
@Repository("tipoDAO")
public class TipoDAOImpl extends BaseDAOImpl<Tipo, Integer> implements TipoDAO {

	/**
	 * Cria uma nova inst�ncia do tipo {@link TipoDAOImpl}
	 */
	public TipoDAOImpl() {
		super(Tipo.class);
	}
}
