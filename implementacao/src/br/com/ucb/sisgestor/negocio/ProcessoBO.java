/*
 * Projeto: SisGestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
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
	 * @return Retorna os processos
	 */
	List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow, Integer paginaAtual);

	/**
	 * Recupera todos os processos referenciados pelo workflow
	 * 
	 * @param workflow Id do workflow
	 * @return Lista de processos
	 */
	List<Processo> getByWorkflow(Integer workflow);

	/**
	 * Salva as transa��es dos processos informadas.
	 * 
	 * @param transacoes transa��es a armazenar
	 * @throws NegocioException caso exce��o de neg�cio seja lan�ada
	 */
	void salvarTransacoes(List<TransacaoProcesso> transacoes) throws NegocioException;
}
