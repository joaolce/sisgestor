/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.persistencia.impl.AtividadeDAOImpl;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;

/**
 * Objeto de negócio para {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class AtividadeBOImpl extends BaseBOImpl<Atividade, Integer> implements AtividadeBO {

	private static final AtividadeBO	instancia	= new AtividadeBOImpl();
	private AtividadeDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link AtividadeBOImpl}.
	 */
	private AtividadeBOImpl() {
		this.dao = AtividadeDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link AtividadeBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link AtividadeBO}
	 */
	public static AtividadeBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Atividade atividade) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(atividade);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Atividade atividade) throws NegocioException {
		this.atualizar(atividade);
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> getByNomeDescricao(String nome, String descricao, Integer idProcesso,
			Integer paginaAtual) {
		return this.dao.getByNomeDescricao(nome, descricao, idProcesso, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	public Atividade obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Atividade atividade) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvar(atividade);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}
}
