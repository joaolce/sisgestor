/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.persistencia.impl.TarefaDAOImpl;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;

/**
 * Objeto de negócio para {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class TarefaBOImpl extends BaseBOImpl<Tarefa, Integer> implements TarefaBO {

	private static final TarefaBO	instancia	= new TarefaBOImpl();
	private TarefaDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link TarefaBOImpl}.
	 */
	private TarefaBOImpl() {
		this.dao = TarefaDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link TarefaBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link TarefaBO}
	 */
	public static TarefaBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Tarefa tarefa) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(tarefa);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Tarefa tarefa) throws NegocioException {
		this.atualizar(tarefa);
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Tarefa> getByNomeDescricao(String nome, String descricao, Integer idAtividade,
			Integer paginaAtual) {
		return this.dao.getByNomeDescricao(nome, descricao, idAtividade, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	public Tarefa obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Tarefa> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Tarefa tarefa) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvar(tarefa);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}
}
