/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisa de campos.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class PesquisaCampoDTO extends PesquisaPaginadaDTO {
	
	private String nome;
	private Integer tipo;
	
	/**
	 * Recupera parte do nome para pesquisa.
	 *
	 * @return parte do nome para pesquisa.
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Recupera o c�digo identificador do tipo para pesquisa.
	 *
	 * @return c�digo identificador do tipo para pesquisa
	 */
	public Integer getTipo() {
		return tipo;
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
	 * Atribui o c�odigo identificador do tipo para pesquisa.
	 * 
	 * @param tipo c�digo identificador do tipo para pesquisa
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	
}
