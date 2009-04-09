/*
 * Projeto: sisgestor
 * Criação: 09/04/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.AnexoUsoWorkflowAction;

/**
 * Validações para a action {@link AnexoUsoWorkflowAction}.
 * 
 * @author João Lúcio
 * @since 09/04/2009
 */
public class AnexoUsoWorkflowValidator extends BaseValidator {

	/**
	 * Faz as validações do método excluirAnexo.
	 */
	public void excluirAnexo() {
		Object anexos = this.getFormValue("anexosSelecionados");
		if (anexos == null) {
			this.addError("erro.arquivo.necessarioSelecao");
		}
	}

	/**
	 * Faz as validações do método incluirAnexo.
	 */
	public void incluirAnexo() {
		this.validaRequerido("label.enderecoArquivo", "arquivo");
		this.setForwardErroValidacao("iframeAnexos");
	}
}
