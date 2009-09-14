/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.sisgestor.util.dto;

/**
 * DTO para pesquisas de manter processo.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class PesquisaManterProcessoDTO extends PesquisaPaginadaDTO {

	private String		nome;
	private String		descricao;
	private Integer	idWorkflow;

	/**
	 * Recupera parte da descri��o para pesquisa.
	 * 
	 * @return parte da descri��o para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o c�digo identificador do workflow
	 * 
	 * @return c�digo identificador do workflow
	 */
	public Integer getIdWorkflow() {
		return this.idWorkflow;
	}

	/**
	 * Recupera parte do nome para pesquisa.
	 * 
	 * @return parte do nome para pesquisa
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui parte da descri��o para pesquisa.
	 * 
	 * @param descricao parte da descri��o para pesquisa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui o c�digo identificador do workflow
	 * 
	 * @param idWorkflow c�digo identificador do workflow
	 */
	public void setIdWorkflow(Integer idWorkflow) {
		this.idWorkflow = idWorkflow;
	}


	/**
	 * Atribui parte do nome para pesquisa.
	 * 
	 * @param nome parte do nome para pesquisa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


}
