/*
 * Projeto: sisgestor
 * Cria��o: 28/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas paginadas.
 * 
 * @author Jo�o L�cio
 * @since 28/01/2009
 */
public class PesquisaPaginadaDTO extends BaseDTO {

	private Integer	paginaAtual;

	/**
	 * Recupera o valor de paginaAtual.
	 * 
	 * @return paginaAtual
	 */
	public Integer getPaginaAtual() {
		return this.paginaAtual;
	}

	/**
	 * Atribui paginaAtual.
	 * 
	 * @param paginaAtual o valor a ajustar em paginaAtual
	 */
	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
}
