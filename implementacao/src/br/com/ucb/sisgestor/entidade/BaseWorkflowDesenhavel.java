/*
 * Projeto: sisgestor
 * Criação: 12/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.MappedSuperclass;

/**
 * Objeto base para objetos de workflow que tem fluxo.
 * 
 * @author João Lúcio
 * @since 12/03/2009
 */
@MappedSuperclass
public class BaseWorkflowDesenhavel extends BaseWorkflow {

	private Integer	top;
	private Integer	left;

	/**
	 * Recupera o left da posição na div.
	 * 
	 * @return left da posição na div
	 */
	public Integer getLeft() {
		return this.left;
	}

	/**
	 * Recupera o top da posição na div.
	 * 
	 * @return top da posição na div
	 */
	public Integer getTop() {
		return this.top;
	}

	/**
	 * Atribui o left da posição na div.
	 * 
	 * @param left left da posição na div
	 */
	public void setLeft(Integer left) {
		this.left = left;
	}

	/**
	 * Atribui top o da posição na div.
	 * 
	 * @param top top da posição na div
	 */
	public void setTop(Integer top) {
		this.top = top;
	}
}
