/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
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
	 * Modifica a {@link Tarefa} atual do {@link UsoWorkflow} para a especificada.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a atualizar a tarefa
	 * @param idTarefa identificador da nova tarefa
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	void modificarTarefa(UsoWorkflow usoWorkflow, Integer idTarefa) throws NegocioException;

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);

	/**
	 * Salva um registro de {@link HistoricoUsoWorkflow}.
	 * 
	 * @param historicoUsoWorkflow {@link HistoricoUsoWorkflow} a salvar
	 */
	void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow);

	/**
	 * Salva os valores informados dos campos.
	 * 
	 * @param valores Array de campos com seus valores
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @throws NegocioException caso seja violada um regra
	 */
	void salvarValoresCampos(String[] valores, Integer idUsoWorkflow) throws NegocioException;
}
