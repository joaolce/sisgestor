/*
 * Projeto: sisgestor
 * Cria��o: 02/04/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util.dto;

/**
 * DTO para pesquisas de usar workflow.
 * 
 * @author Jo�o L�cio
 * @since 02/04/2009
 */
public class PesquisaUsarWorkflowDTO extends PesquisaPaginadaDTO {

	private String		numeroRegistro;
	private Integer	workflow;

	/**
	 * Recupera o n�mero de registro do workflow.
	 * 
	 * @return n�mero de registro do workflow
	 */
	public String getNumeroRegistro() {
		return this.numeroRegistro;
	}

	/**
	 * Recupera o c�digo do workflow.
	 * 
	 * @return c�digo do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui o n�mero de registro do workflow.
	 * 
	 * @param numeroRegistro n�mero de registro do workflow
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Atribui o c�digo do workflow.
	 * 
	 * @param workflow c�digo do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
