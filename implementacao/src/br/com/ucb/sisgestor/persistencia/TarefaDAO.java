/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Tarefa;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface TarefaDAO extends BaseDAO<Tarefa, Integer> {

	/** Quantidade de registros por paginação da tela de Tarefas */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(4);

	/**
	 * Retorna um {@link List} de {@link Tarefa} a partir do nome e/ou descrição
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
	 * @return número do total de registros
	 */
	Integer getTotalRegistros(String nome, String descricao, Integer usuario, Integer idAtividade);

}
