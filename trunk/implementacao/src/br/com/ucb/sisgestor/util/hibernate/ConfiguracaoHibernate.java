/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Classe de configuração do Hibernate.
 * 
 * Esta classe foi feita para substituir o arquivo hibernate.cfg.xml
 * 
 * @author João Lúcio
 * @since 23/10/2008
 */
public final class ConfiguracaoHibernate extends AnnotationConfiguration {

	private static ConfiguracaoHibernate	configuracao;
	private final boolean						criarBancoDeDados	= true;
	private final String							FALSE					= "false";
	private final boolean						isGerarScript		= false;
	private final boolean						isMostrarSQL		= true;
	private final boolean						isMySQL				= false;
	private final String							TRUE					= "true";

	private ConfiguracaoHibernate() {
		if (this.isMySQL) {
			this.setConfiguracoesMySQL();
		} else {
			this.setConfiguracoesHsqlDB();
		}
		if (this.isMostrarSQL) {
			this.setProperty("hibernate.show_sql", this.TRUE);
		} else {
			this.setProperty("hibernate.show_sql", this.FALSE);
		}
		this.setProperty("hibernate.connection.datasource", "java:/SisGestorDB");
		this.setProperty("hibernate.connection.pool_size", "10");
		this.setProperty("hibernate.connection.isolation", "1");
		this.setProperty("use_identifier_rollback", this.TRUE);
		this.setProperty("order_updates", this.TRUE);
		this.setProperty("hibernate.transaction.factory_class",
				"org.hibernate.transaction.JTATransactionFactory");
		this.setProperty("hibernate.transaction.manager_lookup_class",
				"org.hibernate.transaction.JBossTransactionManagerLookup");
		this.setProperty("hibernate.bytecode.use_reflection_optimizer", this.FALSE);

		for (Class<?> classe : ClassesAnotadas.getClassesAnotadas()) {
			this.addAnnotatedClass(classe);
		}
	}

	/**
	 * Recupera a configuração do hibernate
	 * 
	 * @return configuração do hibernate
	 */
	public static ConfiguracaoHibernate getConfiguracao() {
		if (configuracao == null) {
			configuracao = new ConfiguracaoHibernate();
		}
		return configuracao;
	}

	/**
	 * Cria Banco de dados
	 */
	public void recriarBancoDeDados() {
		if (this.criarBancoDeDados) {
			SchemaExport schemaExport = new SchemaExport(this);
			if (this.isGerarScript) {
				schemaExport.setOutputFile(".\\scriptDB.txt");
			}
			schemaExport.drop(this.isGerarScript, true);
			schemaExport.create(this.isGerarScript, true);
		}
	}

	/**
	 * Faz as configurações do HsqlDB
	 */
	private void setConfiguracoesHsqlDB() {
		this.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		this.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
	}

	/**
	 * Faz as configurações para o MySQL
	 */
	private void setConfiguracoesMySQL() {
		this.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		this.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	}
}
