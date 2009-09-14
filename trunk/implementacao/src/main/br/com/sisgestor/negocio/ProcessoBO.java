/*
 * Projeto: SisGestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.TransacaoProcesso;
import br.com.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public interface ProcessoBO extends BaseBO<Processo> {

	/**
	 * Atualiza as {@link TransacaoProcesso} informadas e as posi��es dos processos na p�gina.
	 * 
	 * @param idWorkflow identificador do {@link Workflow}
	 * @param fluxos Fluxos definidos pelo usu�rio
	 * @param posicoes Posi��es dos processos na p�gina
	 * @throws NegocioException caso exce��o de neg�cio seja violada
	 */
	void atualizarTransacoes(Integer idWorkflow, String[] fluxos, String[] posicoes) throws NegocioException;

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
	 * Verifica se h� fluxo definido para os processos do workflow informado.
	 * 
	 * @param idWorkflow C�digo identificador do workflow
	 * @return <code>true</code>, se houver fluxo definido;<br>
	 *         <code>false</code>, se n�o houver.
	 */
	boolean temFluxoDefinido(Integer idWorkflow);
}
