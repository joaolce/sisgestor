/*
 * Projeto: SisGestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Atividade;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface AtividadeBO extends BaseBO<Atividade, Integer> {

	/**
	 * Retorna um {@link List} de {@link Atividade} a partir dos parâmetros informados.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param departamento Departamento responsável pela atividade
	 * @param idProcesso indentificação do processo
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna as atividades
	 */
	List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual);
}
