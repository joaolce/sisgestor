/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface WorkflowBO extends BaseBO<Workflow, Integer> {

	/**
	 * Retorna um {@link List} de {@link Workflow} a partir dos par�metros informados.
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descri��o do workflow
	 * @param ativo indica se o workflow est� ativo ou n�o
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return Retorna os workflows
	 */
	List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo, Integer paginaAtual);
}
