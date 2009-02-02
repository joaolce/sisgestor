/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import com.mysql.jdbc.Driver;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.transaction.JBossTransactionManagerLookup;
import org.hibernate.transaction.JTATransactionFactory;

/**
 * Classe de configuração do Hibernate.
 * 
 * Esta classe foi feita para substituir o arquivo hibernate.cfg.xml
 * 
 * @author João Lúcio
 * @since 23/10/2008
 */
public final class ConfiguracaoHibernate extends AnnotationConfiguration {

	private static final ConfiguracaoHibernate	configuracao		= new ConfiguracaoHibernate();
	private boolean										criarBancoDeDados	= false;
	private boolean										isGerarScript		= false;
	private boolean										isMostrarSQL		= true;

	private ConfiguracaoHibernate() {
		this.setProperty(Environment.DIALECT, MySQL5InnoDBDialect.class.getName());
		this.setProperty(Environment.DRIVER, Driver.class.getName());
		this.setProperty(Environment.DATASOURCE, "java:/SisGestorDB");
		this.setProperty(Environment.DEFAULT_SCHEMA, "sisgestor");
		this.setProperty(Environment.SHOW_SQL, Boolean.toString(this.isMostrarSQL));
		if (this.criarBancoDeDados) {
			this.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
		} else {
			this.setProperty(Environment.HBM2DDL_AUTO, "validate");
		}
		this.setProperty(Environment.POOL_SIZE, "10");
		this.setProperty(Environment.ISOLATION, "1");
		this.setProperty(Environment.USE_IDENTIFIER_ROLLBACK, Boolean.toString(true));
		this.setProperty(Environment.ORDER_UPDATES, Boolean.toString(true));
		this.setProperty(Environment.TRANSACTION_STRATEGY, JTATransactionFactory.class.getName());
		this.setProperty(Environment.TRANSACTION_MANAGER_STRATEGY, JBossTransactionManagerLookup.class
				.getName());
		this.setProperty(Environment.USE_REFLECTION_OPTIMIZER, Boolean.toString(false));

		for (Class<?> classe : ClassesAnotadas.getClassesAnotadas()) {
			this.addAnnotatedClass(classe);
		}
	}

	/**
	 * Recupera a configuração do hibernate.
	 * 
	 * @return configuração do hibernate
	 */
	public static ConfiguracaoHibernate getConfiguracao() {
		return configuracao;
	}

	/**
	 * Cria o banco de dados.
	 */
	public void recriarBancoDeDados() {
		if (this.criarBancoDeDados) {
			SchemaExport schemaExport = new SchemaExport(this);
			if (this.isGerarScript) {
				schemaExport.setOutputFile(".\\scriptDB-sisgestor.txt");
			}
			schemaExport.drop(false, true);
			schemaExport.create(false, true);
		}
	}
}
