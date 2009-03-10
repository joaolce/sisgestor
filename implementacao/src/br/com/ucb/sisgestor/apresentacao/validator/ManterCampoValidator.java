/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterCampoAction;
import br.com.ucb.sisgestor.entidade.TipoCampoEnum;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Validações da action {@link ManterCampoAction}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoValidator extends BaseValidator {

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
		if (this.validaRequerido("label.tipo", "tipo")) {
			int tipo = (Integer) this.getFormValue("tipo");
			if ((tipo == TipoCampoEnum.LISTA_DE_OPCOES.getId())
					|| (tipo == TipoCampoEnum.MULTIPLA_ESCOLHA.getId())) {
				//tipo deve possuir valores pré-definidos
				this.validaRequerido("label.opcoes", "opcoes");
			}
		}
		this.validaRequerido("label.obrigatorio", "obrigatorio");
	}
}
