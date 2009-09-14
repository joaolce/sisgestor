/*
 * Projeto: sisgestor
 * Criação: 09/04/2009 por João Lúcio
 */
package br.com.sisgestor.apresentacao.validator;

import br.com.sisgestor.util.constantes.ConstantesContexto;
import br.com.sisgestor.apresentacao.forms.AnexoUsoWorkflowActionForm;
import br.com.sisgestor.apresentacao.actions.AnexoUsoWorkflowAction;

/**
 * Validações para a action {@link AnexoUsoWorkflowAction}.
 * 
 * @author João Lúcio
 * @since 09/04/2009
 */
public class AnexoUsoWorkflowValidator extends BaseValidator {

	/**
	 * Faz as validações do método download.
	 */
	public void download() {
		this.setForwardErroValidacao("erroValidacao");
	}

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
		this.setForwardErroValidacao("iframeAnexos");
		this.validaRequerido("label.enderecoArquivo", "arquivo");

		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) this.getForm();
		if ((form.getUsoWorkflow() == null) || (form.getUsoWorkflow().intValue() == 0)) {
			Integer idUsoWorkflow = (Integer) this.getSession().getAttribute(ConstantesContexto.ID_USO_WORKFLOW);
			form.setUsoWorkflow(idUsoWorkflow);
		}
	}
}
