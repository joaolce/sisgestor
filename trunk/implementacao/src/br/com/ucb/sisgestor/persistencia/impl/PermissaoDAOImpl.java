/*
 * Projeto: sisgestor
 * Cria��o: 19/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.persistencia.PermissaoDAO;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * Implementa��o da interface de acesso a dados de {@link Permissao}.
 * 
 * @author Jo�o L�cio
 * @since 19/01/2009
 */
@Repository("permissaoDAO")
public class PermissaoDAOImpl extends BaseDAOImpl<Permissao, Integer> implements PermissaoDAO {

	/**
	 * Cria uma nova inst�ncia do tipo {@link PermissaoDAOImpl}.
	 */
	public PermissaoDAOImpl() {
		super(Permissao.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("descricao").ignoreCase();
	}
}
