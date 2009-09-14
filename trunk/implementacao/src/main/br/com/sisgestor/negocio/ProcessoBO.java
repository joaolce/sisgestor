/*
 * Projeto: SisGestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.TransacaoProcesso;
import br.com.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public interface ProcessoBO extends BaseBO<Processo> {

	/**
	 * Atualiza as {@link TransacaoProcesso} informadas e as posições dos processos na página.
	 * 
	 * @param idWorkflow identificador do {@link Workflow}
	 * @param fluxos Fluxos definidos pelo usuário
	 * @param posicoes Posições dos processos na página
	 * @throws NegocioException caso exceção de negócio seja violada
	 */
	void atualizarTransacoes(Integer idWorkflow, String[] fluxos, String[] posicoes) throws NegocioException;

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
	 * Verifica se há fluxo definido para os processos do workflow informado.
	 * 
	 * @param idWorkflow Código identificador do workflow
	 * @return <code>true</code>, se houver fluxo definido;<br>
	 *         <code>false</code>, se não houver.
	 */
	boolean temFluxoDefinido(Integer idWorkflow);
}
