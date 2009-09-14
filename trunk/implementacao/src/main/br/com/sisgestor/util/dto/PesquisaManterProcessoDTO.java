/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
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
	 * Recupera parte da descrição para pesquisa.
	 * 
	 * @return parte da descrição para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o código identificador do workflow
	 * 
	 * @return código identificador do workflow
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
	 * Atribui parte da descrição para pesquisa.
	 * 
	 * @param descricao parte da descrição para pesquisa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui o código identificador do workflow
	 * 
	 * @param idWorkflow código identificador do workflow
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
