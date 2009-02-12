/*
 * Projeto: SisGestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Processo;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public interface ProcessoBO extends BaseBO<Processo, Integer> {

	/**
	 * Retorna um {@link List} de {@link Processo} a partir dos par�metros informados.
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descri��o do processo
	 * @param idWorkflow indentifica��o do workflow
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return Retorna os workflows
	 */
	List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow, Integer paginaAtual);
}
