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
public interface DepartamentoDAO extends BaseDAO<Departamento, Integer> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da descri��o
	 * 
	 * @param nome parte do nome do departamento
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	public List<Departamento> getByNome(String nome, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome do departamento
	 * @return totalRegistros total de registros encontrados
	 */
	public Integer getTotalRegistros(String nome);
}
