/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface WorkflowDAO extends BaseDAO<Workflow, Integer> {

	/**
	 * Retorna um {@link List} de {@link Workflow} a partir do nome, descrição e/ou ativo/inativo. <br />
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @param paginaAtual página atual da pesquisa
	 * @param excluidos indica se deve apresentar os workflows excluidos
	 * @return {@link List} de {@link Workflow}
	 */
	List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo, Boolean excluidos,
			Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @param excluido indica se deve apresentar os workflows excluidos
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Boolean ativo, Boolean excluido);

	/**
	 * Recupera uma lista de workflows pendentes de serem inicializados pelo usuário logado.
	 * 
	 * @param usuario Usuário responsável por iniciar workflow
	 * @return {@link List} com {@link Workflow}
	 */
	List<Workflow> recuperarPendentesIniciar(Usuario usuario);
}
