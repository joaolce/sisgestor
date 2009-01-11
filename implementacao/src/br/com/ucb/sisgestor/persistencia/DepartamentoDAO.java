/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import java.util.List;

import br.com.ucb.sisgestor.entidade.Departamento;

/**
 * Interface para acesso aos dados de {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public interface DepartamentoDAO extends BaseDAO<Departamento, Integer> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da descrição
	 *
	 * @param nome
	 * @param paginaAtual
	 * @return
	 */
	List<Departamento> getByNome(String nome, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 *
	 * @param nome
	 * @return
	 */
	Integer getTotalRegistros(String nome);
}
