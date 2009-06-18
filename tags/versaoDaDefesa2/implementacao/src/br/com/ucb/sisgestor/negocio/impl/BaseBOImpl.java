/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.mail.Email;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no BO
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente> implements BaseBO<T> {

	private SessionFactory	sessionFactory;

	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseBOImpl}.
	 */
	protected BaseBOImpl() {
		//apenas para proteger no pacote
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		return 0;
	}

	/**
	 * Atribui a f�brica de sess�es do hibernate.
	 * 
	 * @param sessionFactory f�brica de sess�es do hibernate
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Faz o envio de um email.
	 * 
	 * @param remetente remetente do email
	 * @param assunto assunto do email
	 * @param corpo corpo do email
	 * @param destinatarios destinat�rios (TO) do email
	 * @return <code>true</code> se email enviado, <code>false</code> caso contr�rio
	 */
	@Transactional(readOnly = true)
	protected boolean enviaEmail(String remetente, String assunto, String corpo, String... destinatarios) {
		try {
			Email email = new Email();
			email.setAssunto(assunto);
			email.setRemetente(remetente);
			email.setCorpo(corpo);

			for (String destinatario : destinatarios) {
				email.addDestinatariosTO(destinatario.trim());
			}

			email.send();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Tira a persist�ncia do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sess�o salvando os objetos que n�o estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * Retorna a sess�o de hibernate associada a thread atual.
	 * 
	 * @return sess�o atual do hibernate
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * D� um merge no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o merge
	 */
	protected void merge(T objeto) {
		this.getSession().merge(objeto);
	}

	/**
	 * D� um refresh no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}
}
