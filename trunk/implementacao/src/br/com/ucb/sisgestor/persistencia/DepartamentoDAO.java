/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public interface DepartamentoDAO extends BaseDAO<Departamento> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da sigla e nome.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @param paginaAtual página atual da pesquisa
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
	 * Verifica se a sigla do departamento já está em uso
	 * 
	 * @param sigla Sigla do departamento a ser verificada
	 * @return <code>true</code>, se já está em uso;<code>false</code>, se ainda não.
	 */
	boolean isSiglaUtilizada(String sigla);

	/**
	 * Recupera um {@link List} de {@link Departamento} ativos
	 * 
	 * @return lista de departamntos ativos
	 */
	List<Departamento> obterTodosAtivos();
}
