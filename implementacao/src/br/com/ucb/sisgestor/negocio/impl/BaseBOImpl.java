/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave prim�ria do objeto persistente utilizado
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {

	/**
	 * Inicia a transa��o da sess�o para a thread em execu��o.
	 * 
	 * @return transa��o da sess�o
	 */
	protected Transaction beginTransaction() {
		return this.getSession().beginTransaction();
	}

	/**
	 * Tira a persist�ncia do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sess�o salvando os objetos que n�o estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * D� um refresh no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}

	/**
	 * Retorna a sess�o de hibernate associada a thread atual.
	 * 
	 * @return sess�o atual do hibernate
	 */
	private Session getSession() {
		return HibernateUtil.getSession();
	}
	
	/**
	 * 
	 * Remove um objeto da base de dados
	 * @throws Exception 
	 *
	 * @see br.com.ucb.sisgestor.negocio.BaseBO#excluir(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void excluir(T objeto) throws Exception{
		this.getSession().delete(objeto);
	}
	
	/**
	 * 
	 * Remove um objeto da base de dados
	 *
	 * @see br.com.ucb.sisgestor.negocio.BaseBO#atualizar(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void atualizar(T objeto) throws Exception{
		this.getSession().update(objeto);
	}
	
	/**
	 * 
	 * Salva um {@link Departamento} na base de dados
	 *
	 * @see br.com.ucb.sisgestor.negocio.BaseBO#salvar(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void salvar(T objeto) throws Exception{
		this.getSession().save(objeto);
	}
}
