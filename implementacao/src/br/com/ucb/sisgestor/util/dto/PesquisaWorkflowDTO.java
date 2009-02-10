/*
 * Projeto: sisgestor
 * Criação: 28/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de workflow.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
public class PesquisaWorkflowDTO extends PesquisaPaginadaDTO {

	private String		nome;
	private String		descricao;
	private Boolean	ativo;

	/**
	 * Recupera o valor de ativo
	 * 
	 * @return ativo
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de nome
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui ativo
	 * 
	 * @param ativo o valor a ajustar em ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui descricao
	 * 
	 * @param descricao o valor a ajustar em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


}
