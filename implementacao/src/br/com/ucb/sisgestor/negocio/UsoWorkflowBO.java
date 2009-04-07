/*
 * Projeto: sisgestor
 * Cria��o: 31/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;
import org.apache.struts.upload.FormFile;

/**
 * Interface para um objeto de neg�cio de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 31/03/2009
 */
public interface UsoWorkflowBO extends BaseBO<UsoWorkflow, Integer> {

	/**
	 * Exclui os anexos selecionados
	 * 
	 * @param anexosSelecionados Sele��o dos anexos
	 * @param id C�digo identificador do uso workflow
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	void excluirAnexos(Integer[] anexosSelecionados, Integer id) throws NegocioException;

	/**
	 * Recupera uma lista de anexos do {@link UsoWorkflow}
	 * 
	 * @param idUsoWorkflow C�digo identificador do uso workflow
	 * @return lista de anexos
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	List<Anexo> getAnexos(Integer idUsoWorkflow) throws NegocioException;

	/**
	 * Inclui um anexo associado ao uso workflow informado
	 * 
	 * @param arquivo Arquivo a ser anexado
	 * @param idUsoWorkflow C�digo identificador do uso workflow
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	void incluirAnexo(FormFile arquivo, Integer idUsoWorkflow) throws NegocioException;

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);
}
