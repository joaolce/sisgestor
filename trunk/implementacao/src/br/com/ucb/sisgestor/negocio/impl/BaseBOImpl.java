/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave prim�ria do objeto persistente utilizado
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {


	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseBOImpl}
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
	 * Inicia a transa��o da sess�o para a thread em execu��o.
	 * 
	 * @return transa��o da sess�o
	 */
	protected Transaction beginTransaction() {
		return this.getSession().beginTransaction();
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
	 * D� um refresh no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}

	/**
	 * Verifica a exce��o lan�ada, caso n�o seja {@link NegocioException}, lan�a uma nova
	 * {@link NegocioException} e a exce��o original como causa.
	 * 
	 * @param excecao exce��o lan�ada
	 * @throws NegocioException nova exce��o ou a exce��o lan�ada
	 */
	protected void verificaExcecao(Exception excecao) throws NegocioException {
		if (excecao instanceof NegocioException) {
			throw NegocioException.class.cast(excecao);
		} else {
			throw new NegocioException(excecao);
		}
	}

	/**
	 * Retorna a sess�o de hibernate associada a thread atual.
	 * 
	 * @return sess�o atual do hibernate
	 */
	private Session getSession() {
		return HibernateUtil.getSession();
	}
}