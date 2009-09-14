/*
 * Projeto: sisgestor
 * Cria��o: 28/01/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util.dto;

/**
 * DTO para pesquisas paginadas.
 * 
 * @author Jo�o L�cio
 * @since 28/01/2009
 */
public class PesquisaPaginadaDTO extends BaseDTO {

	private Integer	paginaAtual;

	/**
	 * Recupera a p�gina atual da pesquisa.
	 * 
	 * @return p�gina atual da pesquisa
	 */
	public Integer getPaginaAtual() {
		return this.paginaAtual;
	}

	/**
	 * Atribui a p�gina atual da pesquisa.
	 * 
	 * @param paginaAtual p�gina atual da pesquisa
	 */
	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
}
