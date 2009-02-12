/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterProcessoAction;

/**
 * Form para a action {@link ManterProcessoAction}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoActionForm extends BaseForm {

	private Integer	idWorkflow;


	/**
	 * Recupera o valor de idWorkflow
	 * 
	 * @return idWorkflow
	 */
	public Integer getIdWorkflow() {
		return this.idWorkflow;
	}


	/**
	 * Atribui idWorkflow
	 * 
	 * @param idWorkflow o valor a ajustar em idWorkflow
	 */
	public void setIdWorkflow(Integer idWorkflow) {
		this.idWorkflow = idWorkflow;
	}
}
