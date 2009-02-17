/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterTarefaAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Valida��es da action {@link ManterTarefaAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaValidator extends BaseValidator {

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
		if (this.validaRequerido("label.descricao", "descricao")) {
			this.validaTamanhoMaximo("label.descricao", "descricao", ConstantesDB.DESCRICAO);
		}
	}
}
