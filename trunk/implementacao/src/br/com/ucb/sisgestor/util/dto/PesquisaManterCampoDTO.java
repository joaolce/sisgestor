/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisa de manter campo.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class PesquisaManterCampoDTO extends PesquisaPaginadaDTO {

	private String		nome;
	private Integer	tipo;
	private Integer	idWorkflow;

	/**
	 * Recupera o c�digo identificador do workflow.
	 * 
	 * @return c�digo identificador do workflow
	 */
	public Integer getIdWorkflow() {
		return this.idWorkflow;
	}

	/**
	 * Recupera parte do nome para pesquisa.
	 * 
	 * @return parte do nome para pesquisa.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera o c�digo identificador do tipo para pesquisa.
	 * 
	 * @return c�digo identificador do tipo para pesquisa
	 */
	public Integer getTipo() {
		return this.tipo;
	}

	/**
	 * Atribui o c�digo identificador do workflow.
	 * 
	 * @param idWorkflow c�digo identificador do workflow
	 */
	public void setIdWorkflow(Integer idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	/**
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui o c�digo identificador do tipo para pesquisa.
	 * 
	 * @param tipo c�digo identificador do tipo para pesquisa
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}
