/*
 * Projeto: sisgestor
 * Cria��o: 28/01/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util.dto;

/**
 * DTO para pesquisa de manter departamento.
 * 
 * @author Jo�o L�cio
 * @since 28/01/2009
 */
public class PesquisaManterDepartamentoDTO extends PesquisaPaginadaDTO {

	private String	sigla;
	private String	nome;

	/**
	 * Recupera parte do nome para pesquisa.
	 * 
	 * @return parte do nome para pesquisa
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera parte da sigla para pesquisa.
	 * 
	 * @return parte da sigla para pesquisa
	 */
	public String getSigla() {
		return this.sigla;
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
	 * Atribui parte da sigla para pesquisa.
	 * 
	 * @param sigla parte da sigla para pesquisa
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
