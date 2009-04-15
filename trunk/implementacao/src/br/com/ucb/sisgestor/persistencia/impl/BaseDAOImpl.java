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
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementa��o da interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
public abstract class BaseDAOImpl<T extends ObjetoPersistente> implements BaseDAO<T> {

	private SessionFactory	sessionFactory;
	private Class<T>			classePersistente;
	private HibernateUtil	hibernateUtil;

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
		this.getSession().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(T obj) {
		this.getSession().delete(obj);
		this.getSession().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public T obter(Integer pk) {
		return this.classePersistente.cast(this.getSession().get(this.classePersistente, pk));
	}

	/**
	 * {@inheritDoc}
	 */
	public T obterAntigo(Integer pk) {
		return this.classePersistente.cast(this.hibernateUtil.getNewSession().get(this.classePersistente, pk));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> obterTodos() {
		Criteria criteria = this.createCriteria(this.classePersistente);
		Order order = this.getOrdemLista();
		if (order != null) {
			criteria.addOrder(order);
		}
		return GenericsUtil.checkedList(criteria.list(), this.classePersistente);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer salvar(T obj) {
		Integer id = (Integer) this.getSession().save(obj);
		this.getSession().flush();
		return id;
	}

	/**
	 * Atribui o utilit�rio {@link HibernateUtil}.
	 * 
	 * @param hibernateUtil {@link HibernateUtil}
	 */
	@Autowired
	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	/**
	 * Atribui a f�brica de sess�es do hibernate.
	 * 
	 * @param sessionFactory f�brica de sess�es do hibernate
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
	 * Cria um {@link Criteria} para a classe informada.
	 * 
	 * @param clazz classe a criar o criteria
	 * @return criteria da classe
	 */
	protected Criteria createCriteria(Class<? extends Serializable> clazz) {
		return this.getSession().createCriteria(clazz);
	}

	/**
	 * Cria um {@link Criteria} para a classe informada.
	 * 
	 * @param clazz classe a criar o criteria
	 * @param alias alias para o objeto
	 * @return criteria da classe
	 */
	protected Criteria createCriteria(Class<? extends Serializable> clazz, String alias) {
		return this.getSession().createCriteria(clazz, alias);
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
		return this.sessionFactory.getCurrentSession();
	}
}
