/*
 * Projeto: sisgestor
 * Criação: 23/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * Utilitário para o hibernate.
 * 
 * @author João Lúcio
 * @since 23/03/2009
 */
public class HibernateUtil {

	private static HibernateUtil			instancia		= null;

	private SessionFactory					sessionFactory;
	private final ThreadLocal<Session>	sessionThread	= new ThreadLocal<Session>();

	/**
	 * Cria uma nova instância do tipo {@link HibernateUtil}.
	 */
	private HibernateUtil() {
		//singleton
	}

	/**
	 * Recupera a única instância de {@link HibernateUtil}.
	 * 
	 * @return {@link HibernateUtil}
	 */
	public static HibernateUtil getInstancia() {
		if (instancia == null) { //NOPMD by João Lúcio - primeira invocação será do spring
			instancia = new HibernateUtil();
		}
		return instancia;
	}


	/**
	 * Fecha a sessão criada na mão do hibernate.
	 */
	public void closeSession() {
		Session session = this.sessionThread.get();
		if ((session != null) && session.isOpen()) {
			this.sessionThread.set(null);
			session.close();
		}
	}

	/**
	 * Recupera a sessão criada na mão do hibernate.
	 * 
	 * @return sessão criada
	 */
	public Session getSessionManual() {
		//recupera o objeto session do threadLocal
		Session session = this.sessionThread.get();
		//abre uma nova sessão para a thread atual somente se a mesma estiver nula ou fechada
		if ((session == null) || !session.isOpen()) {
			session = SessionFactoryUtils.getNewSession(this.sessionFactory);
			this.sessionThread.set(session);
		}
		return session;
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
}
