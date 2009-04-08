/*
 * Projeto: sisgestor
 * Cria��o: 02/04/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de usar workflow.
 * 
 * @author Jo�o L�cio
 * @since 02/04/2009
 */
public class PesquisaUsarWorkflowDTO extends PesquisaPaginadaDTO {

	private Integer	idUsoWorkflow;


	/**
	 * Recupera o c�digo identificador do uso workflow
	 * 
	 * @return c�digo identificador do uso workflow
	 */
	public Integer getIdUsoWorkflow() {
		return this.idUsoWorkflow;
	}


	/**
	 * Atribui o c�digo identificador do uso workflow
	 * 
	 * @param idUsoWorkflow c�digo identificador do uso workflow
	 */
	public void setIdUsoWorkflow(Integer idUsoWorkflow) {
		this.idUsoWorkflow = idUsoWorkflow;
	}

}
