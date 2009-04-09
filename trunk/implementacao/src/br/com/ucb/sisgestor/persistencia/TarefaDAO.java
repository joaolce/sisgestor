/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
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

	/** Quantidade de registros por pagina��o da tela de Tarefas */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(5);

	/**
	 * Exclui uma {@link TransacaoTarefa} informada.
	 * 
	 * @param transacao transa��o a excluir
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
	 * Retorna um {@link List} de {@link Tarefa} a partir do nome, descri��o e usu�rio
	 * 
	 * @param nome parte do nome da tarefa
	 * @param descricao parte da descri��o da tarefa
	 * @param usuario Usu�rio respons�vel pela tarefa
	 * @param idAtividade identifica��o da atividade
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Tarefa}
	 */
	List<Tarefa> getByNomeDescricaoUsuario(String nome, String descricao, Integer usuario,
			Integer idAtividade, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descri��o da atividade
	 * @param usuario Usu�rio respons�vel pela tarefa
	 * @param idAtividade identificador da atividade
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Integer usuario, Integer idAtividade);

	/**
	 * Recupera as transa��es de tarefa do {@link Atividade}.
	 * 
	 * @param idAtividade identificador do {@link Atividade}
	 * @return {@link List} de {@link TransacaoAtividade}
	 */
	List<TransacaoTarefa> recuperarTransacoesDaAtividade(Integer idAtividade);

	/**
	 * Salva uma {@link TransacaoTarefa} informada.
	 * 
	 * @param transacao transa��o a armazenar
	 */
	void salvarTransacao(TransacaoTarefa transacao);

}
