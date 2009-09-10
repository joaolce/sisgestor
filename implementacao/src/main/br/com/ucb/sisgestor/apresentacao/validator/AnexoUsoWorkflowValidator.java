/*
 * Projeto: sisgestor
 * Cria��o: 09/04/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.AnexoUsoWorkflowAction;
import br.com.ucb.sisgestor.apresentacao.forms.AnexoUsoWorkflowActionForm;
import br.com.ucb.sisgestor.util.constantes.ConstantesContexto;

/**
 * Valida��es para a action {@link AnexoUsoWorkflowAction}.
 * 
 * @author Jo�o L�cio
 * @since 09/04/2009
 */
public class AnexoUsoWorkflowValidator extends BaseValidator {

	/**
	 * Faz as valida��es do m�todo download.
	 */
	public void download() {
		this.setForwardErroValidacao("erroValidacao");
	}

	/**
	 * Faz as valida��es do m�todo excluirAnexo.
	 */
	public void excluirAnexo() {
		Object anexos = this.getFormValue("anexosSelecionados");
		if (anexos == null) {
			this.addError("erro.arquivo.necessarioSelecao");
		}
	}

	/**
	 * Faz as valida��es do m�todo incluirAnexo.
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
