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

	/** Quantidade de registros por paginação da tela de Atividades */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(6);

	/**
	 * Retorna um {@link List} de {@link Atividade} a partir do nome, descrição e/ou departamento
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param departamento Departamento responsável pela atividade
	 * @param idProcesso identificação do processo
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Atividade}
	 */
	List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param departamento Departamento responsável pela atividade
	 * @param idProcesso identificador do processo
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String nome, String descricao, Integer departamento, Integer idProcesso);

}
