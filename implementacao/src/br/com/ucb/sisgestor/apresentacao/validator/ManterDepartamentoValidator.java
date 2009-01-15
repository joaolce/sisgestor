/*
 * Projeto: sisgestor
 * Criação: 12/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;

/**
 * Validações da action {@link ManterDepartamentoAction}.
 * 
 * @author João Lúcio
 * @since 12/01/2009
 */
public class ManterDepartamentoValidator extends BaseValidator {

	/**
	 * Faz as validações no método atualizar.
	 */
	public void atualizar() {
		this.salvar();
	}

	/**
	 * Faz as validações no método salvar.
	 */
	public void salvar() {
		this.validaRequerido("label.sigla", "sigla");
		this.validaRequerido("label.nome", "nome");
		this.validaTamanhoMaximo("label.sigla", "sigla", 10);
		this.validaTamanhoMaximo("label.nome", "nome", 50);
		this.validaEmail("label.email", "email");
	}
}
