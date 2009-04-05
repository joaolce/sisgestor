/*
 * Projeto: sisgestor
 * Cria��o: 31/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 31/03/2009
 */
public interface UsoWorkflowBO extends BaseBO<UsoWorkflow, Integer> {

	/**
	 * Recupera a lista de workflows pendentes de serem inicializados.
	 * 
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<Workflow> recuperarPendentesIniciar();

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);
}
