/*
 * Projeto: sisgestor
 * Criação: 28/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de manter usuário.
 * 
 * @author João Lúcio
 * @since 28/01/2009
 */
public class PesquisaManterUsuarioDTO extends PesquisaPaginadaDTO {

	private String		login;
	private String		nome;
	private Integer	departamento;

	/**
	 * Recupera o código identificador do departamento da pesquisa.
	 * 
	 * @return código identificador do departamento da pesquisa
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera parte do login para pesquisa.
	 * 
	 * @return parte do login para pesquisa
	 */
	public String getLogin() {
		return this.login;
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
	 * Atribui o código do departamento da pesquisa.
	 * 
	 * @param departamento código do departamento da pesquisa
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui parte do login para pesquisa.
	 * 
	 * @param login parte do login para pesquisa
	 */
	public void setLogin(String login) {
		this.login = login;
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
