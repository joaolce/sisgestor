/*
 * Projeto: sisgestor
 * Cria��o: 28/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisa de departamentos.
 * 
 * @author Jo�o L�cio
 * @since 28/01/2009
 */
public class PesquisaDepartamentoDTO extends PesquisaPaginadaDTO {

	private String	sigla;
	private String	nome;

	/**
	 * Recupera o valor de nome.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera o valor de sigla.
	 * 
	 * @return sigla
	 */
	public String getSigla() {
		return this.sigla;
	}

	/**
	 * Atribui nome.
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui sigla.
	 * 
	 * @param sigla o valor a ajustar em sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
