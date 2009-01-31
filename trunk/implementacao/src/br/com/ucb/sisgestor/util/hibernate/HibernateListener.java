/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Classe com o evento de carregar o Hibernate na inicialização do contexto web. <br />
 * <br />
 * obs: feito para carregar configurações do hibernate, caso haja problema, o contexto não é inicializado
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
public class HibernateListener implements ServletContextListener {

	/**
	 * Executado na destruição do contexto
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		//implementação desnecessária
	}

	/**
	 * Executado na inicialização do contexto
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.closeSession();
	}
}
