/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import java.util.List;
import br.com.ucb.sisgestor.entidade.Campo;

/**
 * Interface para acesso aos dados de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public interface CampoDAO extends BaseDAO<Campo, Integer> {

	/**
	 * Retorna um {@link List} de {@link Campo} a partir do nome e/ou tipo.
	 * 
	 * @param nome parte do nome do campo
	 * @param idTipo identificação do tipo
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Campo}
	 */
	List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome do campo
	 * @param tipo identificador do tipo
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, Integer tipo);
}
