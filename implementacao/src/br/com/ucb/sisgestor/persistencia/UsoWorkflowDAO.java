/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
public interface UsoWorkflowDAO extends BaseDAO<UsoWorkflow> {

	/** Quantidade de registros por pagina��o. */
	Integer	QTD_REGISTROS_PAGINA					= Integer.valueOf(17);
	/** Quantidade de registros por pagina��o para a p�gina de uso finalizado. */
	Integer	QTD_REGISTROS_PAGINA_FINALIZADOS	= Integer.valueOf(13);

	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * 
	 * @param usuario usu�rio com tarefas pendentes
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(Usuario usuario);

	/**
	 * Recupera a lista de {@link UsoWorkflow} finalizados a partir dos par�metros informados.
	 * 
	 * @param anoRegistro ano de in�cio do registro
	 * @param numeroSequencial n�mero sequencial do ano de �nicio
	 * @param idWorkflow c�digo identificador do workflow
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarFinalizados(Integer anoRegistro, Integer numeroSequencial, Integer idWorkflow,
			Integer paginaAtual);

	/**
	 * Recupera todos os workflows que est�o sendo usados, dependendo de tarefa do usu�rio.
	 * 
	 * @param usuario usu�rio com tarefas pendentes
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual);

	/**
	 * Recupera o n�mero total de registros retornado pela consulta
	 * {@link #recuperarFinalizados(Integer, Integer, Integer, Integer)}.
	 * 
	 * @param anoRegistro ano de in�cio do registro
	 * @param numeroSequencial n�mero sequencial do ano de �nicio
	 * @param idWorkflow c�digo identificador do workflow
	 * @return n�mero total de registros
	 */
	Integer recuperarTotalFinalizados(Integer anoRegistro, Integer numeroSequencial, Integer idWorkflow);

	/**
	 * Recupera o �ltimo n�mero de {@link UsoWorkflow} iniciado no ano da data informada.
	 * 
	 * @param ano ano da data informada
	 * @return n�mero mais recente do ano
	 */
	Integer recuperarUltimoNumeroDoAno(Integer ano);

	/**
	 * Salva um hist�rico do {@link UsoWorkflow}.
	 * 
	 * @param historicoUsoWorkflow hist�rico a salvar
	 */
	void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow);
}
