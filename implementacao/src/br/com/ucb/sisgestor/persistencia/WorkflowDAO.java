/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface WorkflowDAO extends BaseDAO<Workflow, Integer> {

	/**
	 * Retorna um {@link List} de {@link Workflow} a partir do login, nome e departamento.
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Workflow}
	 */
	List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @return número do total de registros
	 */
	Integer getTotalRegistros(String nome, String descricao, Boolean ativo);
}
