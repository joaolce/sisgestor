/*
 * Projeto: sisgestor
 * Criação: 26/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import java.util.Properties;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.transaction.JBossTransactionManagerLookup;
import org.hibernate.transaction.JTATransactionFactory;

/**
 * Classe com as propriedades de configuração do hibernate.
 * 
 * @author João Lúcio
 * @since 26/02/2009
 */
public final class ConfiguracaoHibernate {

	private static Properties propriedades = new Properties();

	static {
		propriedades.put(Environment.DIALECT, MySQL5InnoDBDialect.class.getName());
		propriedades.put(Environment.DEFAULT_SCHEMA, "sisgestor");
		propriedades.put(Environment.SHOW_SQL, "true");
		// propriedades.put(Environment.HBM2DDL_AUTO, "update");
		propriedades.put(Environment.POOL_SIZE, "10");
		propriedades.put(Environment.USE_REFLECTION_OPTIMIZER, "true");
		propriedades.put(Environment.TRANSACTION_STRATEGY, JTATransactionFactory.class.getName());
		propriedades.put(Environment.TRANSACTION_MANAGER_STRATEGY, JBossTransactionManagerLookup.class
				.getName());
		propriedades.put(Environment.USER_TRANSACTION, "java:comp/UserTransaction");
	}

	/**
	 * Gera a DDL do banco de dados.
	 */
	public static void gerarDDL() {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		for (Class<?> obj : ClassesAnotadas.getClassesAnotadas()) {
			cfg.addAnnotatedClass(obj);
		}
		cfg.setProperties(propriedades);
		cfg.setProperty(Environment.DATASOURCE, "java:/SisGestorDB");

		SchemaExport export = new SchemaExport(cfg);
		export.setOutputFile(".\\sisgestor-DDL.sql");
		export.setDelimiter(";");
		export.setFormat(true);
		export.drop(false, true);
		export.create(false, true);
	}

	/**
	 * Recupera as propriedades do hibernate.
	 * 
	 * @return {@link Properties} das propriedades
	 */
	public static Properties getPropriedades() {
		return propriedades;
	}
}
