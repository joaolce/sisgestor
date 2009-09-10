/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;

/**
 * Validações da action {@link UsarWorkflowAction}.
 * 
 * @author Thiago Pires
 * @since 08/04/2009
 */
public class UsarWorkflowValidator extends BaseValidator {

	/**
	 * Faz as validações do método iniciarUso.
	 */
	public void iniciarUso() {
		this.validaRequerido("label.workflow", "workflow");
	}

	/**
	 * Faz as validações do método proximaTarefa.
	 */
	public void proximaTarefa() {
		this.validaRequerido("label.proximaTarefa", "tarefa");
	}
}
