/*
 * Projeto: sisgestor
 * Cria��o: 31/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

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
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException;

	/**
	 * Recupera uma lista de anexos do {@link UsoWorkflow}
	 * 
	 * @param idUsoWorkflow C�digo identificador do uso workflow
	 * @return lista de anexos
	 */
	List<Anexo> getAnexos(Integer idUsoWorkflow);

	/**
	 * Inclui um anexo associado ao uso do {@link Workflow} informado.
	 * 
	 * @param anexo arquivo a ser anexado
	 * @throws NegocioException NegocioException caso exce��o de neg�cio seja violada
	 */
	void incluirAnexo(Anexo anexo) throws NegocioException;

	/**
	 * Recupera a lista de workflows em uso com {@link Tarefa} pendente do {@link Usuario} atual.
	 * 
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} com {@link UsoWorkflow}
	 */
	List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual);
}
