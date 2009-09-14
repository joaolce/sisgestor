/*
 * Projeto: SisGestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.sisgestor.negocio;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Atividade;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.TransacaoAtividade;
import java.util.List;

/**
 * Interface para um objeto de neg�cio de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface AtividadeBO extends BaseBO<Atividade> {

	/**
	 * Atualiza as {@link TransacaoAtividade} informadas e as posi��es das atividades na p�gina.
	 * 
	 * @param idProcesso identificador do {@link Processo}
	 * @param fluxos Fluxos definidos pelo usu�rio
	 * @param posicoes Posi��es das atividades na p�gina
	 * @throws NegocioException caso exce��o de neg�cio seja violada
	 */
	void atualizarTransacoes(Integer idProcesso, String[] fluxos, String[] posicoes) throws NegocioException;

	/**
	 * Retorna um {@link List} de {@link Atividade} a partir dos par�metros informados.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descri��o da atividade
	 * @param departamento Departamento respons�vel pela atividade
	 * @param idProcesso indentifica��o do processo
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return Retorna as atividades
	 */
	List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual);

	/**
	 * Recupera todas as atividades referenciadas pelo processo
	 * 
	 * @param processo Id do processo
	 * @return Lista de atividades
	 */
	List<Atividade> getByProcesso(Integer processo);

	/**
	 * Verifica se h� fluxo definido para as atividades do {@link Processo} informado.
	 * 
	 * @param idProcesso c�digo identificador do {@link Processo}
	 * @return <code>true</code>, se houver fluxo definido;<br>
	 *         <code>false</code>, se n�o houver.
	 */
	boolean temFluxoDefinido(Integer idProcesso);
}
