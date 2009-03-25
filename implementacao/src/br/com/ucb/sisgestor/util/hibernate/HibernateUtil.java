/*
 * Projeto: sisgestor
 * Cria��o: 23/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * Utilit�rio para o hibernate.
 * 
 * @author Jo�o L�cio
 * @since 23/03/2009
 */
public class HibernateUtil {

	private static HibernateUtil			instancia		= null;

	private SessionFactory					sessionFactory;
	private final ThreadLocal<Session>	sessionThread	= new ThreadLocal<Session>();

	/**
	 * Cria uma nova inst�ncia do tipo {@link HibernateUtil}.
	 */
	private HibernateUtil() {
		//singleton
	}

	/**
	 * Recupera a �nica inst�ncia de {@link HibernateUtil}.
	 * 
	 * @return {@link HibernateUtil}
	 */
	public static HibernateUtil getInstancia() {
		if (instancia == null) { //NOPMD by Jo�o L�cio - primeira invoca��o ser� do spring
			instancia = new HibernateUtil();
		}
		return instancia;
	}


	/**
	 * Fecha a sess�o criada na m�o do hibernate.
	 */
	public void closeSession() {
		Session session = this.sessionThread.get();
		if ((session != null) && session.isOpen()) {
			this.sessionThread.set(null);
			session.close();
		}
	}

	/**
	 * Recupera a sess�o criada na m�o do hibernate.
	 * 
	 * @return sess�o criada
	 */
	public Session getSessionManual() {
		//recupera o objeto session do threadLocal
		Session session = this.sessionThread.get();
		//abre uma nova sess�o para a thread atual somente se a mesma estiver nula ou fechada
		if ((session == null) || !session.isOpen()) {
			session = SessionFactoryUtils.getNewSession(this.sessionFactory);
			this.sessionThread.set(session);
		}
		return session;
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
}
