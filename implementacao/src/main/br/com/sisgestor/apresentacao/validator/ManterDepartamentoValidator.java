/*
 * Projeto: sisgestor
 * Cria��o: 12/01/2009 por Jo�o L�cio
 */
package br.com.sisgestor.apresentacao.validator;

import br.com.sisgestor.util.constantes.ConstantesDB;
import br.com.sisgestor.apresentacao.actions.ManterDepartamentoAction;

/**
 * Valida��es da action {@link ManterDepartamentoAction}.
 * 
 * @author Jo�o L�cio
 * @since 12/01/2009
 */
public class ManterDepartamentoValidator extends BaseValidator {

	/**
	 * Faz as valida��es no m�todo atualizar.
	 */
	public void atualizar() {
		this.salvar();
	}

	/**
	 * Faz as valida��es no m�todo salvar.
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
