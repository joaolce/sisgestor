/*
 * Projeto: SisGestor
 * Cria��o: 07/04/2009 por Thiago
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Anexo;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
public interface AnexoBO extends BaseBO<Anexo> {

	/**
	 * Exclui os anexos selecionados.
	 * 
	 * @param anexosSelecionados Sele��o dos anexos
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException;

	/**
	 * Recupera uma lista de anexos do uso workflow informado.
	 * 
	 * @param idUsoWorkflow C�digo identificador do uso workflow
	 * @return lista de anexos
	 */
	List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow);
}
