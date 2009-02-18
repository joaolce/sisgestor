/*
 * Projeto: SisGestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Tarefa;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface TarefaBO extends BaseBO<Tarefa, Integer> {

	/**
	 * Retorna um {@link List} de {@link Tarefa} a partir dos parâmetros informados.
	 * 
	 * @param nome parte do nome da tarefa
	 * @param descricao parte da descrição da tarefa
	 * @param usuario Usuário responsável pela tarefa
	 * @param idAtividade indentificação da atividade
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna as tarefas
	 */
	List<Tarefa> getByNomeDescricaoUsuario(String nome, String descricao, Integer usuario,
			Integer idAtividade, Integer paginaAtual);
}
