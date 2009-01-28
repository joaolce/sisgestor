/*
 * Projeto: sisgestor
 * Criação: 28/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de usuários.
 * 
 * @author João Lúcio
 * @since 28/01/2009
 */
public class PesquisaUsuarioDTO extends PesquisaPaginadaDTO {

	private String		login;
	private String		nome;
	private Integer	departamento;

	/**
	 * Recupera o valor de departamento.
	 * 
	 * @return departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o valor de login.
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o valor de nome.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui departamento.
	 * 
	 * @param departamento o valor a ajustar em departamento
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui login.
	 * 
	 * @param login o valor a ajustar em login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Atribui nome.
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
