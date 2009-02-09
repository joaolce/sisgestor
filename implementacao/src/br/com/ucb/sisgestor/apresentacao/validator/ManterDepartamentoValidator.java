/*
 * Projeto: sisgestor
 * Cria��o: 12/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

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
		this.validaRequerido("label.sigla", "sigla");
		this.validaRequerido("label.nome", "nome");
		this.validaTamanhoMaximo("label.sigla", "sigla", ConstantesDB.SIGLA);
		this.validaTamanhoMaximo("label.nome", "nome", ConstantesDB.NOME);
		this.validaEmail("label.email", "email");
	}
}
