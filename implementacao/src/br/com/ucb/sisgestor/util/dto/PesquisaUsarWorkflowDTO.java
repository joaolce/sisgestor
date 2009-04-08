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


	/**
	 * Recupera o código identificador do uso workflow
	 * 
	 * @return código identificador do uso workflow
	 */
	public Integer getIdUsoWorkflow() {
		return this.idUsoWorkflow;
	}


	/**
	 * Atribui o código identificador do uso workflow
	 * 
	 * @param idUsoWorkflow código identificador do uso workflow
	 */
	public void setIdUsoWorkflow(Integer idUsoWorkflow) {
		this.idUsoWorkflow = idUsoWorkflow;
	}

}
