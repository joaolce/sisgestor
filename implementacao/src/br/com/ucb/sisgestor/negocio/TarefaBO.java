/*
 * Projeto: SisGestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface TarefaBO extends BaseBO<Tarefa> {

	/**
	 * Atualiza as {@link TransacaoTarefa} informadas e as posições das tarefas na página.
	 * 
	 * @param idAtividade identificador da {@link Atividade}
	 * @param fluxos Fluxos definidos pelo usuário
	 * @param posicoes Posições das tarefas na página
	 * @throws NegocioException caso exceção de negócio seja violada
	 */
	void atualizarTransacoes(Integer idAtividade, String[] fluxos, String[] posicoes) throws NegocioException;

	/**
	 * Recupera todas as tarefas referenciadas pela atividade
	 * 
	 * @param atividade Id da atividade
	 * @return Lista de tarefas
	 */
	List<Tarefa> getByAtividade(Integer atividade);

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

	/**
	 * Verifica se há fluxo definido para as tarefas da {@link Atividade} informada.
	 * 
	 * @param idAtividade código identificador da {@link Atividade}
	 * @return <code>true</code>, se houver fluxo definido;<br>
	 *         <code>false</code>, se não houver.
	 */
	boolean temFluxoDefinido(Integer idAtividade);
}
