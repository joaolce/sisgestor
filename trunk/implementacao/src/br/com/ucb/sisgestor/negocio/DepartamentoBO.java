/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public interface DepartamentoBO extends BaseBO<Departamento, Integer> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da sigla e nome.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @return total de registros encontrados
	 */
	public Integer getTotalRegistros(String sigla, String nome);
}
