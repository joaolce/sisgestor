/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
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

	/** Quantidade de registros por paginação da tela de Processos */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(6);

	/**
	 * Retorna um {@link List} de {@link Processo} a partir do nome e/ou descrição
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descrição do processo
	 * @param idWorkflow identificação do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Workflow}
	 */
	List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descrição do processo
	 * @param idWorkflow identificador do workflow
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Integer idWorkflow);
}
