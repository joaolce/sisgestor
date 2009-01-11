/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.util;

import java.util.List;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;

/**
 * Lista de resultados de consultas
 *
 * @author Thiago
 * @since 10/01/2009
 */
public class ListaResultadoDTO extends BaseDTO{
	/**
	 * coleção parcial contendo os resultados
	 */
	private List<ObjetoPersistente> colecaoParcial;
	
	/**
	 * Total de registros retornado pela consulta sem paginação
	 */
	private Integer totalRegistros;
	

	/**
	 * Recupera o valor de colecaoParcial
	 *
	 * @return colecaoParcial
	 */
	public List<ObjetoPersistente> getColecaoParcial() {
		return colecaoParcial;
	}

	/**
	 * Atribui colecaoParcial
	 *
	 * @param colecaoParcial o valor a ajustar em colecaoParcial
	 */
	public void setColecaoParcial(List<ObjetoPersistente> colecaoParcial) {
		this.colecaoParcial = colecaoParcial;
	}

	/**
	 * Recupera o valor de totalRegistros
	 *
	 * @return totalRegistros
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * Atribui totalRegistros
	 *
	 * @param totalRegistros o valor a ajustar em totalRegistros
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}
