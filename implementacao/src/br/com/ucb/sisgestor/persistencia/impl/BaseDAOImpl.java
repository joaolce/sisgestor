/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.persistencia.BaseDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;

/**
 * Implementa��o da interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave prim�ria do objeto persistente utilizado
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
public class BaseDAOImpl<T extends ObjetoPersistente, PK extends Serializable> implements BaseDAO<T, PK> {

	private Class<T>	classePersistente;

	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseDAOImpl}.
	 * 
	 * @param classePersistente classe utilizada
	 */
	protected BaseDAOImpl(Class<T> classePersistente) {
		this.classePersistente = classePersistente;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(T obj) {
		this.getSession().update(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(T obj) {
		this.getSession().delete(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public T obter(PK pk) {
		return this.classePersistente.cast(this.getSession().get(this.classePersistente, pk));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> obterTodos() {
		Criteria criteria = this.getSession().createCriteria(this.classePersistente);
		Order order = this.getOrdemLista();
		if (order != null) {
			criteria.addOrder(order);
		}
		return GenericsUtil.checkedList(criteria.list(), this.classePersistente);
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(T obj) {
		this.getSession().save(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvarOuAtualizar(T obj) {
		this.getSession().saveOrUpdate(obj);
	}

	/**
	 * Adiciona pagina��o a consulta.
	 * 
	 * @param criteria criteria da consulta
	 * @param paginaAtual n�mero da p�gina atual
	 * @param maximoRegistros m�ximo de registros da p�gina
	 */
	protected void adicionarPaginacao(Criteria criteria, Integer paginaAtual, int maximoRegistros) {
		if (paginaAtual != null) {
			criteria.setFirstResult(paginaAtual.intValue() * maximoRegistros);
		}
		criteria.setMaxResults(maximoRegistros);
	}

	/**
	 * Recupera a ordena��o padr�o do m�todo {@link #obterTodos()}.
	 * 
	 * @return ordena��o padr�o
	 */
	protected Order getOrdemLista() {
		return null;
	}

	/**
	 * Recupera sess�o atual do hibernate.
	 * 
	 * @return session sess�o atual
	 */
	protected Session getSession() {
		return HibernateUtil.getSession();
	}
}
