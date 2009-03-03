/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Campo;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public interface CampoDAO extends BaseDAO<Campo, Integer> {

	/** Quantidade de registros por paginação da tela de campos */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(6);

	/**
	 * Retorna um {@link List} de {@link Campo} a partir do nome e/ou tipo.
	 * 
	 * @param nome parte do nome do campo
	 * @param idTipo identificação do tipo
	 * @param idWorkflow identificador do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Campo}
	 */
	List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer idWorkflow, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome do campo
	 * @param tipo identificador do tipo
	 * @param idWorkflow identificador do workflow
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, Integer tipo, Integer idWorkflow);
}
