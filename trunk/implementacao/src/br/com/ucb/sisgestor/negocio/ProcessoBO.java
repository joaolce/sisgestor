/*
 * Projeto: SisGestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
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
	 * Salva as transações dos processos informadas.
	 * 
	 * @param transacoes transações a armazenar
	 * @throws NegocioException caso exceção de negócio seja lançada
	 */
	void salvarTransacoes(List<TransacaoProcesso> transacoes) throws NegocioException;
}
