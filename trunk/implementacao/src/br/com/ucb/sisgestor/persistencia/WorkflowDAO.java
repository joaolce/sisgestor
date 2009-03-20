/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

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
	 * Retorna um {@link List} de {@link Workflow} a partir do nome, descri��o e/ou ativo/inativo. <br />
	 * obs: recupera apenas os workflows n�o exclu�dos
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descri��o do workflow
	 * @param ativo indica se o workflow est� ativo ou n�o
	 * @param paginaAtual p�gina atual da pesquisa
	 * @param excluidos indica se deve apresentar os workflows excluidos
	 * @return {@link List} de {@link Workflow}
	 */
	List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo, Boolean excluidos,
			Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta. <br />
	 * obs: recupera apenas dos workflows n�o exclu�dos
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descri��o do workflow
	 * @param ativo indica se o workflow est� ativo ou n�o
	 * @param excluido indica se deve apresentar os workflows excluidos
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Boolean ativo, Boolean excluido);
}
