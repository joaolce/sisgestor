/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.CampoDAO;
import br.com.ucb.sisgestor.persistencia.impl.CampoDAOImpl;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;

/**
 * Objeto de negócio para {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class CampoBOImpl extends BaseBOImpl<Campo, Integer> implements CampoBO {

	private static final CampoBO	instancia	= new CampoBOImpl();
	private CampoDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link CampoBOImpl}.
	 */
	private CampoBOImpl() {
		this.dao = CampoDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link CampoBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link CampoBO}
	 */
	public static CampoBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Campo campo) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(campo);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Campo campo) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.excluir(campo);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Campo obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Campo campo) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvar(campo);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}
}
