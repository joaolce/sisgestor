/*
 * Projeto: SisGestor
 * Criação: 21/10/2008 por João Lúcio
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
 * Implementação da interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * 
 * @author João Lúcio
 * @since 21/10/2008
 */
public abstract class BaseDAOImpl<T extends ObjetoPersistente> implements BaseDAO<T> {

	private SessionFactory	sessionFactory;
	private Class<T>			classePersistente;
	private HibernateUtil	hibernateUtil;

	/**
	 * Cria uma nova instância do tipo {@link BaseDAOImpl}.
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
	 * Atribui o utilitário {@link HibernateUtil}.
	 * 
	 * @param hibernateUtil {@link HibernateUtil}
	 */
	@Autowired
	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	/**
	 * Atribui a fábrica de sessões do hibernate.
	 * 
	 * @param sessionFactory fábrica de sessões do hibernate
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Adiciona paginação a consulta.
	 * 
	 * @param criteria criteria da consulta
	 * @param paginaAtual número da página atual
	 * @param maximoRegistros máximo de registros da página
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
	 * Recupera a ordenação padrão do método {@link #obterTodos()}.
	 * 
	 * @return ordenação padrão
	 */
	protected Order getOrdemLista() {
		return null;
	}

	/**
	 * Recupera sessão atual do hibernate.
	 * 
	 * @return session sessão atual
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
}
