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


	/**
	 * Retorna um {@link List} de {@link Tarefa} a partir do nome e/ou descrição
	 * 
	 * @param nome parte do nome da tarefa
	 * @param descricao parte da descrição da tarefa
	 * @param idAtividade identificação da atividade
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Tarefa}
	 */
	List<Tarefa> getByNomeDescricao(String nome, String descricao, Integer idAtividade, Integer paginaAtual);

}
