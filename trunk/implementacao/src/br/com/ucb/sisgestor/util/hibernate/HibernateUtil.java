/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
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
 * Gerenciamento de transação e boot do hibernate foi utilizado um {@link ThreadLocal} para a sessão e outro
 * para a transação, dessa forma cada Thread que acessar esses métodos estáticos, obterão uma única e
 * particular instância da {@link Transaction} e/ou da {@link Session} até que que a sessão seja fechada ou a
 * transação commitada.
 * 
 * @author João Lúcio
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
	 * Fecha a sessão atual do hibernate
	 */
	public static void closeSession() {
		Session session = sessionThread.get();
		if ((session != null) && session.isOpen()) {
			sessionThread.set(null);
			session.close();
		}
	}

	/**
	 * Executa um commit na transação
	 * 
	 * @param transaction transação a executar o commit
	 */
	public static void commit(Transaction transaction) {
		if ((transaction != null) && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.commit();
		}
	}

	/**
	 * Recupera o nome da coluna da propriedade e classe passada <br />
	 * Atenção: este método só está recuperando o primeiro nome da coluna da propriedade
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
	 * Recupera valor da propriedade de configuração do hibernate
	 * 
	 * @param key chave da propriedade
	 * 
	 * @return valor da propriedade
	 */
	public static Object getHibernateConfigurationProperty(String key) {
		return configuracao.getProperties().get(key);
	}

	/**
	 * Recupera a sessão atual do hibernate
	 * 
	 * @return sessão atual
	 */
	public static Session getSession() {
		//recupera o objeto session do threadLocal
		Session session = sessionThread.get();
		//abre uma nova sessão para a thread atual somente se a mesma estiver nula ou fechada
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
	 * Executa um rollback na transação
	 * 
	 * @param transaction transação a executar o rollback
	 */
	public static void rollback(Transaction transaction) {
		if ((transaction != null) && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.rollback();
		}
	}
}
