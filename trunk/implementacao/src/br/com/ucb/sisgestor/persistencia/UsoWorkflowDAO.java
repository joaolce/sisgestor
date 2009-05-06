/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
public interface UsoWorkflowDAO extends BaseDAO<UsoWorkflow> {

	/** Quantidade de registros por paginação. */
	Integer	QTD_REGISTROS_PAGINA					= Integer.valueOf(17);
	/** Quantidade de registros por paginação para a página de uso finalizado. */
	Integer	QTD_REGISTROS_PAGINA_FINALIZADOS	= Integer.valueOf(13);

	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * 
	 * @param usuario usuário com tarefas pendentes
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(Usuario usuario);

	/**
	 * Recupera a lista de {@link UsoWorkflow} finalizados a partir dos parâmetros informados.
	 * 
	 * @param anoRegistro ano de início do registro
	 * @param numeroSequencial número sequencial do ano de ínicio
	 * @param idWorkflow código identificador do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarFinalizados(Integer anoRegistro, Integer numeroSequencial, Integer idWorkflow,
			Integer paginaAtual);

	/**
	 * Recupera todos os workflows que estão sendo usados, dependendo de tarefa do usuário.
	 * 
	 * @param usuario usuário com tarefas pendentes
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual);

	/**
	 * Recupera o número total de registros retornado pela consulta
	 * {@link #recuperarFinalizados(Integer, Integer, Integer, Integer)}.
	 * 
	 * @param anoRegistro ano de início do registro
	 * @param numeroSequencial número sequencial do ano de ínicio
	 * @param idWorkflow código identificador do workflow
	 * @return número total de registros
	 */
	Integer recuperarTotalFinalizados(Integer anoRegistro, Integer numeroSequencial, Integer idWorkflow);

	/**
	 * Recupera o último número de {@link UsoWorkflow} iniciado no ano da data informada.
	 * 
	 * @param ano ano da data informada
	 * @return número mais recente do ano
	 */
	Integer recuperarUltimoNumeroDoAno(Integer ano);

	/**
	 * Salva um histórico do {@link UsoWorkflow}.
	 * 
	 * @param historicoUsoWorkflow histórico a salvar
	 */
	void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow);
}
