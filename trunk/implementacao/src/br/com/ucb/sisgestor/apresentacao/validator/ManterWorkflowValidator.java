/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterWorkflowAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Valida��es da action {@link ManterWorkflowAction}.
 * 
 * @author Jo�o L�cio
 * @since 11/02/2009
 */
public class ManterWorkflowValidator extends BaseValidator {

	/**
	 * Faz as valida��es do m�todo atualizar.
	 */
	public void atualizar() {
		this.salvar();
	}

	/**
	 * Faz as valida��es do m�todo salvar.
	 */
	public void salvar() {
		if (this.validaRequerido("label.nome", "nome")) {
			this.validaTamanhoMaximo("label.nome", "nome", ConstantesDB.NOME);
		}
		this.validaRequerido("label.ativo", "ativo");
		if (this.validaRequerido("label.descricao", "descricao")) {
			this.validaTamanhoMaximo("label.descricao", "descricao", ConstantesDB.DESCRICAO);
		}
	}
}
