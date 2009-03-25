/*
 * Projeto: sisgestor
 * Cria��o: 12/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.MappedSuperclass;

/**
 * Objeto base para objetos de workflow que tem fluxo.
 * 
 * @author Jo�o L�cio
 * @since 12/03/2009
 */
@MappedSuperclass
public class BaseWorkflowDesenhavel extends BaseWorkflow {

	private Integer	top;
	private Integer	left;

	/**
	 * Recupera o left da posi��o na div.
	 * 
	 * @return left da posi��o na div
	 */
	public Integer getLeft() {
		return this.left;
	}

	/**
	 * Recupera o top da posi��o na div.
	 * 
	 * @return top da posi��o na div
	 */
	public Integer getTop() {
		return this.top;
	}

	/**
	 * Atribui o left da posi��o na div.
	 * 
	 * @param left left da posi��o na div
	 */
	public void setLeft(Integer left) {
		this.left = left;
	}

	/**
	 * Atribui top o da posi��o na div.
	 * 
	 * @param top top da posi��o na div
	 */
	public void setTop(Integer top) {
		this.top = top;
	}
}
