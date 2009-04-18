/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Lista de resultados de consultas.
 * 
 * @param <T> {@link Serializable} utilizado
 * @author Thiago
 * @since 10/01/2009
 */
public class ListaResultadoDTO<T extends Serializable> extends BaseDTO {

	private Integer totalRegistros;
	private List<T> colecaoParcial;

	/**
	 * Recupera a coleção de registros da página atual.
	 * 
	 * @return colecaoParcial coleção de registros da página atual
	 */
	public List<T> getColecaoParcial() {
		return this.colecaoParcial;
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
	 * Atribui a coleção de registros da página atual.
	 * 
	 * @param colecaoParcial coleção de registros da página atual
	 */
	public void setColecaoParcial(List<T> colecaoParcial) {
		this.colecaoParcial = colecaoParcial;
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
