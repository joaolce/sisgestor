/*
 * Projeto: SisGestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Atividade;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface AtividadeBO extends BaseBO<Atividade, Integer> {

	/**
	 * Retorna um {@link List} de {@link Atividade} a partir dos par�metros informados.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descri��o da atividade
	 * @param idProcesso indentifica��o do processo
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return Retorna as atividades
	 */
	List<Atividade> getByNomeDescricao(String nome, String descricao, Integer idProcesso, Integer paginaAtual);
}
