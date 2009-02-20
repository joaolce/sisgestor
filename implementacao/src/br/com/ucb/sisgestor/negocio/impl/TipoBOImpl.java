/*
 * Projeto: sisgestor
 * Criação: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Tipo;
import br.com.ucb.sisgestor.negocio.TipoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TipoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Objeto de negócio para {@link Tipo}.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
@Service("tipoBO")
public class TipoBOImpl extends BaseBOImpl<Tipo, Integer> implements TipoBO {

	private TipoDAO	tipoDAO;

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
		return this.tipoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Tipo> obterTodos() {
		return this.tipoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Tipo tipo) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * Atribui o DAO de {@link Tipo}.
	 * 
	 * @param tipoDAO DAO de {@link Tipo}
	 */
	@Autowired
	public void setTipoDAO(TipoDAO tipoDAO) {
		this.tipoDAO = tipoDAO;
	}
}
