/*
 * Projeto: sisgestor
 * Criação: 02/04/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de usar workflow.
 * 
 * @author João Lúcio
 * @since 02/04/2009
 */
public class PesquisaUsarWorkflowDTO extends PesquisaPaginadaDTO {

	private Integer	idUsoWorkflow;
	private Integer	workflow;
	private String		numeroRegistro;

	/**
	 * Recupera o código identificador do uso workflow
	 * 
	 * @return código identificador do uso workflow
	 */
	public Integer getIdUsoWorkflow() {
		return this.idUsoWorkflow;
	}

	/**
	 * Recupera o número de registro do workflow.
	 * 
	 * @return número de registro do workflow
	 */
	public String getNumeroRegistro() {
		return this.numeroRegistro;
	}

	/**
	 * Recupera o código do workflow.
	 * 
	 * @return código do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui o código identificador do uso workflow
	 * 
	 * @param idUsoWorkflow código identificador do uso workflow
	 */
	public void setIdUsoWorkflow(Integer idUsoWorkflow) {
		this.idUsoWorkflow = idUsoWorkflow;
	}

	/**
	 * Atribui o número de registro do workflow.
	 * 
	 * @param numeroRegistro número de registro do workflow
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Atribui o código do workflow.
	 * 
	 * @param workflow código do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
