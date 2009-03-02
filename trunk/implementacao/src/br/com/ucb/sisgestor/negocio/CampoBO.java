/*
 * Projeto: SisGestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Campo;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public interface CampoBO extends BaseBO<Campo, Integer> {

	/**
	 * Retorna um {@link List} de {@link Campo} a partir dos parâmetros informados.
	 * 
	 * @param nome parte do nome do campo
	 * @param idTipo indentificação do tipo do campo
	 * @param idWorkflow identificador do workflow
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna os campos
	 */
	List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer idWorkflow, Integer paginaAtual);

}
