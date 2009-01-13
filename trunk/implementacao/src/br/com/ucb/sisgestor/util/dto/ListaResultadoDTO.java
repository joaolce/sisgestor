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

	/**
	 * cole��o parcial contendo os resultados
	 */
	private List<T>	colecaoParcial;

	/**
	 * Total de registros retornado pela consulta sem pagina��o
	 */
	private Integer	totalRegistros;


	/**
	 * Recupera a cole��o de registros da p�gina atual.
	 * 
	 * @return colecaoParcial cole��o de registros da p�gina atual
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
	 * Atribui a cole��o de registros da p�gina atual.
	 * 
	 * @param colecaoParcial cole��o de registros da p�gina atual
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