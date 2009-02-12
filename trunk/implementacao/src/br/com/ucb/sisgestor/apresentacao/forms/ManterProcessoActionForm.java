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

	private Integer	workflow;

	/**
	 * Recupera o identificador do workflow.
	 * 
	 * @return identificador do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui o identificador do workflow.
	 * 
	 * @param workflow identificador do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
