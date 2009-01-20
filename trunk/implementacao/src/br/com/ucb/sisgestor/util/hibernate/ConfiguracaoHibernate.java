/*
 * Projeto: SisGestor
 * Cria��o: 23/10/2008 por Jo�o L�cio
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
 * Classe de configura��o do Hibernate.
 * 
 * Esta classe foi feita para substituir o arquivo hibernate.cfg.xml
 * 
 * @author Jo�o L�cio
 * @since 23/10/2008
 */
public final class ConfiguracaoHibernate extends AnnotationConfiguration {

	private static ConfiguracaoHibernate	configuracao;
	private final boolean						criarBancoDeDados	= false;
	private final boolean						isGerarScript		= false;
	private final boolean						isMostrarSQL		= true;

	private ConfiguracaoHibernate() {
		this.setProperty(Environment.DIALECT, MySQL5InnoDBDialect.class.getName());
		this.setProperty(Environment.DRIVER, Driver.class.getName());
		this.setProperty(Environment.DATASOURCE, "java:/SisGestorDB");
		this.setProperty(Environment.DEFAULT_SCHEMA, "sisgestor");
		this.setProperty(Environment.SHOW_SQL, Boolean.toString(this.isMostrarSQL));
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
	 * Recupera a configura��o do hibernate.
	 * 
	 * @return configura��o do hibernate
	 */
	public static ConfiguracaoHibernate getConfiguracao() {
		if (configuracao == null) {
			configuracao = new ConfiguracaoHibernate();
		}
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
			schemaExport.drop(this.isGerarScript, true);
			schemaExport.create(this.isGerarScript, true);
		}
	}
}
