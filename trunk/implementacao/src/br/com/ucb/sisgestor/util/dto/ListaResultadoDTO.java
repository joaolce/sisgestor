/*
 * Projeto: sisgestor
 * Cria��o: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.dto;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import java.util.List;

/**
 * Lista de resultados de consultas.
 * 
 * @param <T> {@link ObjetoPersistente} utilizado
 * @author Thiago
 * @since 10/01/2009
 */
public class ListaResultadoDTO<T extends ObjetoPersistente> extends BaseDTO {

	private Integer	totalRegistros;
	private List<T>	colecaoParcial;
	private Integer	quantidadeRegistrosPagina;

	/**
	 * Recupera a cole��o de registros da p�gina atual.
	 * 
	 * @return colecaoParcial cole��o de registros da p�gina atual
	 */
	public List<T> getColecaoParcial() {
		return this.colecaoParcial;
	}

	/**
	 * Recupera a quantidade de registros por p�gina.
	 * 
	 * @return quantidade de registros por p�gina
	 */
	public Integer getQuantidadeRegistrosPagina() {
		return this.quantidadeRegistrosPagina;
	}

	/**
	 * Recupera o total de registros da consulta.
	 * 
	 * @return total de registros da consulta
	 */
	public Integer getTotalRegistros() {
		return this.totalRegistros;
	}

	/**
	 * Atribui a cole��o de registros da p�gina atual.
	 * 
	 * @param colecaoParcial cole��o de registros da p�gina atual
	 */
	public void setColecaoParcial(List<T> colecaoParcial) {
		this.colecaoParcial = colecaoParcial;
	}


	/**
	 * Atribui a quantidade de registros por p�gina.
	 * 
	 * @param quantidadeRegistrosPagina quantidade de registros por p�gina
	 */
	public void setQuantidadeRegistrosPagina(Integer quantidadeRegistrosPagina) {
		this.quantidadeRegistrosPagina = quantidadeRegistrosPagina;
	}


	/**
	 * Atribui o total de registros da consulta
	 * 
	 * @param totalRegistros total de registros da consulta
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}
