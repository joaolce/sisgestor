/*
 * Projeto: SisGestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Processo;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public interface ProcessoBO extends BaseBO<Processo, Integer> {

	/**
	 * Retorna um {@link List} de {@link Processo} a partir dos parâmetros informados.
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descrição do processo
	 * @param idWorkflow indentificação do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna os workflows
	 */
	List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow, Integer paginaAtual);
}
