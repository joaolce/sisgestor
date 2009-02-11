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
	 * Recupera indicador de ativo para a pesquisa.
	 * 
	 * @return indicador de ativo para a pesquisa
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera parte da descrição para pesquisa.
	 * 
	 * @return parte da descrição para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
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
	 * Atribui indicador de ativo para a pesquisa.
	 * 
	 * @param ativo indicador de ativo para a pesquisa
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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
	 * Atribui parte do nome para pesquisa.
	 * 
	 * @param nome parte do nome para pesquisa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


}
