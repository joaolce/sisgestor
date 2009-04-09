/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
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
 * Implementação da interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no BO
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente> implements BaseBO<T> {

	private SessionFactory	sessionFactory;

	/**
	 * Cria uma nova instância do tipo {@link BaseBOImpl}.
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
	 * Atribui a fábrica de sessões do hibernate.
	 * 
	 * @param sessionFactory fábrica de sessões do hibernate
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
	 * @param destinatarios destinatários (TO) do email
	 * @return <code>true</code> se email enviado, <code>false</code> caso contrário
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
	 * Tira a persistência do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Faz um flush na sessão salvando os objetos que não estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * Retorna a sessão de hibernate associada a thread atual.
	 * 
	 * @return sessão atual do hibernate
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * Dá um merge no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o merge
	 */
	protected void merge(T objeto) {
		this.getSession().merge(objeto);
	}

	/**
	 * Dá um refresh no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}
}
