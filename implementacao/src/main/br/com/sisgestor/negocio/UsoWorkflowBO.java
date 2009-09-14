/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.UsoWorkflow;
import br.com.sisgestor.entidade.Usuario;
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
	 * Recupera se o {@link UsoWorkflow} em questão pode mudar de tarefa.
	 * 
	 * @param idUso identificador do uso
	 * @return <code>true</code> caso possa, <code>false</code> caso contrário
	 */
	Boolean podeMudarDeTarefa(Integer idUso);

	/**
	 * Recupera a lista de {@link UsoWorkflow} finalizados a partir dos parâmetros informados.
	 * 
	 * @param numeroRegistro número do registro do uso
	 * @param idWorkflow código identificador do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarFinalizados(String numeroRegistro, Integer idWorkflow, Integer paginaAtual);

	/**
	 * Recupera a lista de {@link UsoWorkflow} em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);

	/**
	 * Recupera o número total de registros retornado pela consulta
	 * {@link #recuperarFinalizados(String, Integer, Integer)}.
	 * 
	 * @param numeroRegistro número do registro do uso
	 * @param idWorkflow código identificador do workflow
	 * @return número total de registros
	 */
	Integer recuperarTotalFinalizados(String numeroRegistro, Integer idWorkflow);

	/**
	 * Salva a anotação do uso
	 * 
	 * @param idUsoWorkflow Código indentificador do {@link UsoWorkflow}
	 * @param anotacao Anotação a ser salva
	 * @throws NegocioException caso seja violada um regra
	 */
	void salvarAnotacao(Integer idUsoWorkflow, String anotacao) throws NegocioException;

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
