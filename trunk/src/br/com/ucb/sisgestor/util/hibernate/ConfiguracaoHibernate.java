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
		if (isMySQL) {
			setConfiguracoesMySQL();
		} else {
			setConfiguracoesHsqlDB();
		}
		if (isMostrarSQL) {
			setProperty("hibernate.show_sql", TRUE);
		} else {
			setProperty("hibernate.show_sql", FALSE);
		}
		setProperty("hibernate.connection.datasource", "java:/SisGestorDB");
		setProperty("hibernate.connection.pool_size", "10");
		setProperty("hibernate.connection.isolation", "1");
		setProperty("use_identifier_rollback", TRUE);
		setProperty("order_updates", TRUE);
		setProperty("hibernate.bytecode.use_reflection_optimizer", FALSE);

		for (Class<?> classe : ClassesAnotadas.getClassesAnotadas()) {
			addAnnotatedClass(classe);
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
		if (criarBancoDeDados) {
			SchemaExport schemaExport = new SchemaExport(this);
			if (isGerarScript) {
				schemaExport.setOutputFile(".\\scriptDB.txt");
			}
			schemaExport.drop(isGerarScript, true);
			schemaExport.create(isGerarScript, true);
		}
	}

	/**
	 * Faz as configurações do HsqlDB
	 */
	private void setConfiguracoesHsqlDB() {
		setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
	}

	/**
	 * Faz as configurações para o MySQL
	 */
	private void setConfiguracoesMySQL() {
		setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	}
}
