/*
 * Projeto: sisgestor
 * Cria��o: 05/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Departamento}.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
public interface DepartamentoDAO extends BaseDAO<Departamento> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da sigla e nome.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String sigla, String nome);

	/**
	 * Recupera um {@link List} de {@link Departamento} ativos
	 * 
	 * @return lista de departamntos ativos
	 */
	List<Departamento> obterTodosAtivos();
}
