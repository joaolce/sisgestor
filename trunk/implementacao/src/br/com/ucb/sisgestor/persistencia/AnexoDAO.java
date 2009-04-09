/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Anexo;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
public interface AnexoDAO extends BaseDAO<Anexo> {

	/**
	 * Recupera uma lista de anexos do uso workflow informado.
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return lista de anexos
	 */
	List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow);

}
