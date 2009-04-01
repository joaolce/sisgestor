/*
 * Projeto: sisgestor
 * Cria��o: 31/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 31/03/2009
 */
public interface UsoWorkflowBO extends BaseBO<UsoWorkflow, Integer> {

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @return {@link List} com {@link UsoWorkflow}
	 * @throws NegocioException caso regra de neg�cio seja violada
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual() throws NegocioException;
}
