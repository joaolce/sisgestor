/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object)
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public class BaseBOImpl implements BaseBO {

	/** Atributo para uso de loggers */
	protected Log	log;

	/**
	 * Cria uma nova inst�ncia do tipo BaseBOImpl
	 * 
	 * @param clazz classe herdada de {@link BaseBO}
	 */
	public BaseBOImpl(Class<? extends BaseBOImpl> clazz) {
		log = LogFactory.getLog(clazz);
	}

	/**
	 * Inicia a transa��o da sess�o para a thread em execu��o
	 * 
	 * @return transa��o da sess�o
	 */
	protected Transaction beginTransaction() {
		return getSession().beginTransaction();
	}

	/**
	 * Tira a persist�ncia do objeto
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sess�o salvando os objetos que n�o estavam persistidos
	 */
	protected void flush() {
		getSession().flush();
	}

	/**
	 * Retorna a sess�o de hibernate associada a thread atual
	 * 
	 * @return sess�o atual do hibernate
	 */
	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	/**
	 * D� um refresh no objeto na sess�o do hibernate
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(ObjetoPersistente objeto) {
		getSession().refresh(objeto);
	}
}
