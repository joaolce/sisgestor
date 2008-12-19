/*
 * Projeto: SisGestor
 * Criação: 21/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.persistencia.BaseDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Implementação da interface que representa um DAO (Data Access Object)
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 21/10/2008
 */
public class BaseDAOImpl<T extends ObjetoPersistente, PK extends Serializable> implements BaseDAO<T, PK> {

	private Class<T>	classePersistente;
	private Session	session;

	/**
	 * Cria uma nova instância do tipo BaseDAOImpl
	 * 
	 * @param classePersistente classe utilizada
	 */
	public BaseDAOImpl(Class<T> classePersistente) {
		this.classePersistente = classePersistente;
	}

	/**
	 * Atualiza um objeto na base de dados
	 * 
	 * @param obj objeto persistente a atualizar
	 */
	public void atualizar(T obj) {
		getSession().update(obj);
	}

	/**
	 * Apaga um objeto na base de dados
	 * 
	 * @param obj objeto persistente a apagar
	 */
	public void excluir(T obj) {
		getSession().delete(obj);
	}

	/**
	 * Recupera o valor de session
	 * 
	 * @return session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Recupera um objeto a partir da sua chave primária
	 * 
	 * @param pk chave primária do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	public T obter(PK pk) {
		return classePersistente.cast(getSession().get(classePersistente, pk));
	}

	/**
	 * Recupera todos os objetos
	 * 
	 * @return um {@link List} de objeto
	 */
	public List<T> obterTodos() {
		Criteria criteria = getSession().createCriteria(classePersistente);

		return GenericsUtil.checkedList(criteria.list(), classePersistente);
	}

	/**
	 * Salva um objeto na base de dados
	 * 
	 * @param obj objeto persistente a salvar
	 */
	public void salvar(T obj) {
		getSession().save(obj);
	}
}
