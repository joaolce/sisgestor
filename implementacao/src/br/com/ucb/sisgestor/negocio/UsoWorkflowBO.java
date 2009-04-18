/*
 * Projeto: sisgestor
 * Cria��o: 31/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
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
public interface UsoWorkflowBO extends BaseBO<UsoWorkflow> {

	/**
	 * Inicia a tarefa atual do {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflow uso a iniciar a tarefa
	 * @throws NegocioException caso seja violada um regra
	 */
	void iniciarTarefa(UsoWorkflow usoWorkflow) throws NegocioException;

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);

	/**
	 * Salva um registro de {@link HistoricoUsoWorkflow}.
	 * 
	 * @param historicoUsoWorkflow {@link HistoricoUsoWorkflow} a salvar
	 */
	void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow);
}
