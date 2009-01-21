/*
 * Projeto: sisgestor
 * Criação: 19/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.persistencia.PermissaoDAO;
import org.hibernate.criterion.Order;

/**
 * Implementação da interface de acesso a dados de {@link Permissao}.
 * 
 * @author João Lúcio
 * @since 19/01/2009
 */
public class PermissaoDAOImpl extends BaseDAOImpl<Permissao, Integer> implements PermissaoDAO {

	private static final PermissaoDAO	instancia	= new PermissaoDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link PermissaoDAOImpl}.
	 */
	public PermissaoDAOImpl() {
		super(Permissao.class);
	}

	/**
	 * Recupera a instância de {@link PermissaoDAO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link PermissaoDAO}
	 */
	public static PermissaoDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("descricao").ignoreCase();
	}
}
