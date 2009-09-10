/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;

/**
 * Valida��es da action {@link UsarWorkflowAction}.
 * 
 * @author Thiago Pires
 * @since 08/04/2009
 */
public class UsarWorkflowValidator extends BaseValidator {

	/**
	 * Faz as valida��es do m�todo iniciarUso.
	 */
	public void iniciarUso() {
		this.validaRequerido("label.workflow", "workflow");
	}

	/**
	 * Faz as valida��es do m�todo proximaTarefa.
	 */
	public void proximaTarefa() {
		this.validaRequerido("label.proximaTarefa", "tarefa");
	}
}
