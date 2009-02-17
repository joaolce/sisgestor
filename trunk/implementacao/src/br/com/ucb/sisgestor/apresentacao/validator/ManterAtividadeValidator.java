/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterAtividadeAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Validações da action {@link ManterAtividadeAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeValidator extends BaseValidator {

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
		this.validaRequerido("label.departamento.responsavel", "departamentoResponsavel");
	}
}
