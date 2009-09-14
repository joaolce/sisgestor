/*
 * Projeto: SisGestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Anexo;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
public interface AnexoBO extends BaseBO<Anexo> {

	/**
	 * Exclui os anexos selecionados.
	 * 
	 * @param anexosSelecionados Seleção dos anexos
	 * @throws NegocioException NegocioException caso exceção de negócio seja violada
	 */
	void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException;

	/**
	 * Recupera uma lista de anexos do uso workflow informado.
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return lista de anexos
	 */
	List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow);
}
