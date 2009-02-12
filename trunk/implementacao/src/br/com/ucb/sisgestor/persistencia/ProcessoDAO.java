/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public interface ProcessoDAO extends BaseDAO<Processo, Integer> {


	/**
	 * Retorna um {@link List} de {@link Processo} a partir do nome e/ou descri��o
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descri��o do processo
	 * @param idWorkflow identifica��o do workflow
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Workflow}
	 */
	List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow, Integer paginaAtual);

}
