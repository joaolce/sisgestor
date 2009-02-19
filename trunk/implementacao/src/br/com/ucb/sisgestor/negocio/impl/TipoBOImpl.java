/*
 * Projeto: sisgestor
 * Criação: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Tipo;
import br.com.ucb.sisgestor.negocio.TipoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TipoDAO;
import br.com.ucb.sisgestor.persistencia.impl.TipoDAOImpl;
import java.util.List;

/**
 * Objeto de negócio para {@link Tipo}.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
public class TipoBOImpl extends BaseBOImpl<Tipo, Integer> implements TipoBO {

	private static final TipoBO	instancia	= new TipoBOImpl();
	private TipoDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link TipoBOImpl}.
	 */
	private TipoBOImpl() {
		this.dao = TipoDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link TipoBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link TipoBO}
	 */
	public static TipoBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Tipo tipo) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Tipo tipo) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	public Tipo obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Tipo> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Tipo tipo) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}
}
