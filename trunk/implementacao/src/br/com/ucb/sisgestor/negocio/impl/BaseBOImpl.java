/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.io.Serializable;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementação da interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {

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
		return null;
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
	 * Dá um refresh no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}
}
