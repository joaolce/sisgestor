/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Implementação da interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {


	/**
	 * Cria uma nova instância do tipo {@link BaseBOImpl}
	 */
	protected BaseBOImpl() {
		//apenas para proteger no pacote
	}

	/**
	 * Inicia a transação da sessão para a thread em execução.
	 * 
	 * @return transação da sessão
	 */
	protected Transaction beginTransaction() {
		return this.getSession().beginTransaction();
	}

	/**
	 * Tira a persistência do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sessão salvando os objetos que não estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * Dá um refresh no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}

	/**
	 * Retorna a sessão de hibernate associada a thread atual.
	 * 
	 * @return sessão atual do hibernate
	 */
	private Session getSession() {
		return HibernateUtil.getSession();
	}
}
