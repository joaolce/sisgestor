/*
 * Projeto: sisgestor
 * Criação: 12/01/2009 por João Lúcio
 */
package br.com.sisgestor.apresentacao.validator;

import br.com.sisgestor.util.constantes.ConstantesDB;
import br.com.sisgestor.apresentacao.actions.ManterDepartamentoAction;

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
		if (this.validaRequerido("label.sigla", "sigla")) {
			this.validaTamanhoMinimo("label.sigla", "sigla", 3);
			this.validaTamanhoMaximo("label.sigla", "sigla", ConstantesDB.SIGLA);
		}
		if (this.validaRequerido("label.nome", "nome")) {
			this.validaTamanhoMaximo("label.nome", "nome", ConstantesDB.NOME);
		}
		this.validaEmail("label.email", "email");
	}
}
