/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface TarefaDAO extends BaseDAO<Tarefa> {

	/** Quantidade de registros por paginação da tela de Tarefas */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(5);

	/**
	 * Exclui uma {@link TransacaoTarefa} informada.
	 * 
	 * @param transacao transação a excluir
	 */
	void excluirTransacao(TransacaoTarefa transacao);

	/**
	 * Recupera todas as tarefas referenciadas pela atividade
	 * 
	 * @param atividade Id da atividade
	 * @return Lista de tarefas
	 */
	List<Tarefa> getByAtividade(Integer atividade);

	/**
	 * Retorna um {@link List} de {@link Tarefa} a partir do nome, descrição e usuário
	 * 
	 * @param nome parte do nome da tarefa
	 * @param descricao parte da descrição da tarefa
	 * @param usuario Usuário responsável pela tarefa
	 * @param idAtividade identificação da atividade
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Tarefa}
	 */
	List<Tarefa> getByNomeDescricaoUsuario(String nome, String descricao, Integer usuario,
			Integer idAtividade, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param usuario Usuário responsável pela tarefa
	 * @param idAtividade identificador da atividade
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Integer usuario, Integer idAtividade);

	/**
	 * Recupera as transações de tarefa do {@link Atividade}.
	 * 
	 * @param idAtividade identificador do {@link Atividade}
	 * @return {@link List} de {@link TransacaoAtividade}
	 */
	List<TransacaoTarefa> recuperarTransacoesDaAtividade(Integer idAtividade);

	/**
	 * Salva uma {@link TransacaoTarefa} informada.
	 * 
	 * @param transacao transação a armazenar
	 */
	void salvarTransacao(TransacaoTarefa transacao);

}
