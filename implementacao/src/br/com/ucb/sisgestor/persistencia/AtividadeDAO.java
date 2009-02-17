/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Atividade;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface AtividadeDAO extends BaseDAO<Atividade, Integer> {


	/**
	 * Retorna um {@link List} de {@link Atividade} a partir do nome e/ou descrição
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param idProcesso identificação do processo
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Atividade}
	 */
	List<Atividade> getByNomeDescricao(String nome, String descricao, Integer idProcesso, Integer paginaAtual);

}
