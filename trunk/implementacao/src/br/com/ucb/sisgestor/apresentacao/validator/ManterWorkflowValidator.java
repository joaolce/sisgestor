/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterWorkflowAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Validações da action {@link ManterWorkflowAction}.
 * 
 * @author João Lúcio
 * @since 11/02/2009
 */
public class ManterWorkflowValidator extends BaseValidator {

	/**
	 * Faz as validações do método atualizar.
	 */
	public void atualizar() {
		this.salvar();
	}

	/**
	 * Faz as validações do método salvar.
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
