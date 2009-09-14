/*
 * Projeto: sisgestor
 * Criação: 28/01/2009 por João Lúcio
 */
package br.com.sisgestor.util.dto;

/**
 * DTO para pesquisas paginadas.
 * 
 * @author João Lúcio
 * @since 28/01/2009
 */
public class PesquisaPaginadaDTO extends BaseDTO {

	private Integer	paginaAtual;

	/**
	 * Recupera a página atual da pesquisa.
	 * 
	 * @return página atual da pesquisa
	 */
	public Integer getPaginaAtual() {
		return this.paginaAtual;
	}

	/**
	 * Atribui a página atual da pesquisa.
	 * 
	 * @param paginaAtual página atual da pesquisa
	 */
	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
}
