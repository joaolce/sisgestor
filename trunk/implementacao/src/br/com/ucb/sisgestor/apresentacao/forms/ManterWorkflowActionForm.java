/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterWorkflowAction;

/**
 * Form para a action {@link ManterWorkflowAction}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class ManterWorkflowActionForm extends BaseForm {

	private Integer	id;


	/**
	 * Recupera o valor de id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return this.id;
	}


	/**
	 * Atribui id
	 * 
	 * @param id o valor a ajustar em id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
