/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.sisgestor.apresentacao.validator;

import br.com.sisgestor.util.constantes.ConstantesDB;
import br.com.sisgestor.apresentacao.actions.ManterProcessoAction;

/**
 * Validações da action {@link ManterProcessoAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterProcessoValidator extends BaseValidator {

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
		if (this.validaRequerido("label.descricao", "descricao")) {
			this.validaTamanhoMaximo("label.descricao", "descricao", ConstantesDB.DESCRICAO);
		}
	}
}
