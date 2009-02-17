/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago Pires
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de workflow.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class PesquisaTarefaDTO extends PesquisaPaginadaDTO {

	private Integer	idAtividade;
	private String		nome;
	private String		descricao;
	private Integer usuario;


	/**
	 * Recupera parte da descrição para pesquisa.
	 * 
	 * @return parte da descrição para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de idAtividade
	 * 
	 * @return idAtividade
	 */
	public Integer getIdAtividade() {
		return this.idAtividade;
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
	 * Atribui idAtividade
	 * 
	 * @param idAtividade o valor a ajustar em idAtividade
	 */
	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}


	/**
	 * Atribui parte do nome para pesquisa.
	 * 
	 * @param nome parte do nome para pesquisa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Recupera o identificador do usuário para pesquisa.
	 * 
	 * @return identificador do usuário para pesquisa
	 */
	public Integer getUsuario() {
		return usuario;
	}

	/**
	 * Atribui o identificador do usuário para pesquisa.
	 * 
	 * @param usuario identificador do usuário para pesquisa
	 */
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}



}
