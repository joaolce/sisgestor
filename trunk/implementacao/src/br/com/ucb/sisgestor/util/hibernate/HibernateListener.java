/*
 * Projeto: SisGestor
 * Cria��o: 24/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Classe com o evento de carregar o Hibernate na inicializa��o do contexto web. <br />
 * <br />
 * obs: feito para carregar configura��es do hibernate, caso haja problema, o contexto n�o � inicializado
 * 
 * @author Jo�o L�cio
 * @since 24/10/2008
 */
public class HibernateListener implements ServletContextListener {

	/**
	 * Executado na destrui��o do contexto
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		//implementa��o desnecess�ria
	}

	/**
	 * Executado na inicializa��o do contexto
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.closeSession();
	}
}
