/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
public interface UsoWorkflowDAO extends BaseDAO<UsoWorkflow, Integer> {


	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * 
	 * @param usuario usuário com tarefas pendentes
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(Usuario usuario);

	/**
	 * Recupera todos os workflows que estão sendo usados, dependendo de tarefa do usuário.
	 * 
	 * @param usuario usuário com tarefas pendentes
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual);
}
