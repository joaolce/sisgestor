/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
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
 * Implementação da interface que representa um objeto de negócio (Business Object)
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public class BaseBOImpl implements BaseBO {

	/** Atributo para uso de loggers */
	protected Log	log;

	/**
	 * Cria uma nova instância do tipo BaseBOImpl
	 * 
	 * @param clazz classe herdada de {@link BaseBO}
	 */
	public BaseBOImpl(Class<? extends BaseBOImpl> clazz) {
		log = LogFactory.getLog(clazz);
	}

	/**
	 * Inicia a transação da sessão para a thread em execução
	 * 
	 * @return transação da sessão
	 */
	protected Transaction beginTransaction() {
		return getSession().beginTransaction();
	}

	/**
	 * Tira a persistência do objeto
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sessão salvando os objetos que não estavam persistidos
	 */
	protected void flush() {
		getSession().flush();
	}

	/**
	 * Retorna a sessão de hibernate associada a thread atual
	 * 
	 * @return sessão atual do hibernate
	 */
	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	/**
	 * Dá um refresh no objeto na sessão do hibernate
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(ObjetoPersistente objeto) {
		getSession().refresh(objeto);
	}
}
