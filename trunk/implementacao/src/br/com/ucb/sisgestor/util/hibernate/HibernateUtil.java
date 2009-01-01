/*
 * Projeto: SisGestor
 * Cria��o: 23/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;

/**
 * Gerenciamento de transa��o e boot do hibernate foi utilizado um {@link ThreadLocal} para a sess�o e outro
 * para a transa��o, dessa forma cada Thread que acessar esses m�todos est�ticos, obter�o uma �nica e
 * particular inst�ncia da {@link Transaction} e/ou da {@link Session} at� que que a sess�o seja fechada ou a
 * transa��o commitada.
 * 
 * @author Jo�o L�cio
 * @since 23/10/2008
 */
public final class HibernateUtil {

	private static final ConfiguracaoHibernate	configuracao	= ConfiguracaoHibernate.getConfiguracao();
	private static SessionFactory						factory;
	private static final ThreadLocal<Session>		sessionThread	= new ThreadLocal<Session>();

	/**
	 * Compila o {@link SessionFactory}
	 */
	static {
		try {
			factory = configuracao.buildSessionFactory();
			configuracao.recriarBancoDeDados();
		} catch (Exception e) {
			LogFactory.getLog(HibernateUtil.class).error(e.getMessage(), e);
		}
	}

	/**
	 * Fecha a sess�o atual do hibernate
	 */
	public static void closeSession() {
		Session session = sessionThread.get();
		if ((session != null) && session.isOpen()) {
			sessionThread.set(null);
			session.close();
		}
	}

	/**
	 * Executa um commit na transa��o
	 * 
	 * @param transaction transa��o a executar o commit
	 */
	public static void commit(Transaction transaction) {
		if ((transaction != null) && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.commit();
		}
	}

	/**
	 * Recupera o nome da coluna da propriedade e classe passada <br />
	 * Aten��o: este m�todo s� est� recuperando o primeiro nome da coluna da propriedade
	 * 
	 * @param classe classe persistente
	 * @param propriedade nome da propriedade da classe
	 * @return nome da coluna
	 */
	public static String getCollumnNameByProperty(Class<? extends ObjetoPersistente> classe, String propriedade) {
		RootClass rootClass = (RootClass) configuracao.getClassMapping(classe.getName());
		Property property = rootClass.getProperty(propriedade);
		Column column = (Column) property.getColumnIterator().next();
		return column.getName();
	}

	/**
	 * Recupera valor da propriedade de configura��o do hibernate
	 * 
	 * @param key chave da propriedade
	 * 
	 * @return valor da propriedade
	 */
	public static Object getHibernateConfigurationProperty(String key) {
		return configuracao.getProperties().get(key);
	}

	/**
	 * Recupera a sess�o atual do hibernate
	 * 
	 * @return sess�o atual
	 */
	public static Session getSession() {
		//recupera o objeto session do threadLocal
		Session session = sessionThread.get();
		//abre uma nova sess�o para a thread atual somente se a mesma estiver nula ou fechada
		if ((session == null) || !session.isOpen()) {
			session = factory.openSession();
			sessionThread.set(session);
		}
		return session;
	}

	/**
	 * Recupera a {@link SessionFactory} do hibernate.
	 * 
	 * @return factory atual
	 */
	public static SessionFactory getSessionFactory() {
		return factory;
	}

	/**
	 * Recupera o nome da tabela da classe informada
	 * 
	 * @param classe classe que deseja pegar o nome da tabela
	 * @return nome da tabela
	 */
	public static String getTableNameByClass(Class<? extends ObjetoPersistente> classe) {
		RootClass rootClass = (RootClass) configuracao.getClassMapping(classe.getName());
		return rootClass.getTable().getName();
	}

	/**
	 * Executa um rollback na transa��o
	 * 
	 * @param transaction transa��o a executar o rollback
	 */
	public static void rollback(Transaction transaction) {
		if ((transaction != null) && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.rollback();
		}
	}
}
