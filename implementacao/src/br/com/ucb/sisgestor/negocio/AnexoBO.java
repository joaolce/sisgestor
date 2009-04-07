/*
 * Projeto: SisGestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Anexo;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
public interface AnexoBO extends BaseBO<Anexo, Integer> {

	/**
	 * Recupera uma lista de anexos do uso workflow informado.
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return lista de anexos
	 */
	List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow);

}
