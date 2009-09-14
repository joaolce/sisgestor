/*
 * Projeto: sisgestor
 * Criação: 19/01/2009 por João Lúcio
 */
package br.com.sisgestor.persistencia.impl;

import br.com.sisgestor.persistencia.PermissaoDAO;
import br.com.sisgestor.entidade.Permissao;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link Permissao}.
 * 
 * @author João Lúcio
 * @since 19/01/2009
 */
@Repository("permissaoDAO")
public class PermissaoDAOImpl extends BaseDAOImpl<Permissao> implements PermissaoDAO {

	/**
	 * Cria uma nova instância do tipo {@link PermissaoDAOImpl}.
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
