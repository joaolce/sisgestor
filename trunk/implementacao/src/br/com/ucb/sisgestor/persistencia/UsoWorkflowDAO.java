/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
public interface UsoWorkflowDAO extends BaseDAO<UsoWorkflow, Integer> {

	/** Quantidade de registros por pagina��o */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(17);

	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * 
	 * @param usuario usu�rio com tarefas pendentes
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(Usuario usuario);

	/**
	 * Recupera uma lista de workflows pendentes de serem inicializados pelo usu�rio logado.
	 * 
	 * @param usuario Usu�rio respons�vel por iniciar workflow
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<Workflow> recuperarPendentesIniciar(Usuario usuario);

	/**
	 * Recupera todos os workflows que est�o sendo usados, dependendo de tarefa do usu�rio.
	 * 
	 * @param usuario usu�rio com tarefas pendentes
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual);
}
