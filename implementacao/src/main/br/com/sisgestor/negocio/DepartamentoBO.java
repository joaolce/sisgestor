/*
 * Projeto: sisgestor
 * Cria��o: 05/01/2009 por Jo�o L�cio
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Departamento}.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
public interface DepartamentoBO extends BaseBO<Departamento> {

	/**
	 * Retorna um {@link List} de {@link Departamento} a partir da sigla e nome.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return Retorna os departamentos
	 */
	List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual);

	/**
	 * Recupera um {@link List} de {@link Departamento} ativos
	 * 
	 * @return lista de departamntos ativos
	 */
	List<Departamento> obterTodosAtivos();
}
