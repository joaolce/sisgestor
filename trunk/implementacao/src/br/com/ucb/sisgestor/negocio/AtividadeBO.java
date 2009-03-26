/*
 * Projeto: SisGestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public interface AtividadeBO extends BaseBO<Atividade, Integer> {

	/**
	 * Atualiza as {@link TransacaoAtividade} informadas.
	 * 
	 * @param idProcesso identificador do {@link Processo}
	 * @param transacoes transações a armazenar
	 * @throws NegocioException caso exceção de negócio seja violada
	 */
	void atualizarTransacoes(Integer idProcesso, List<TransacaoAtividade> transacoes) throws NegocioException;

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

	/**
	 * Recupera todas as atividades referenciadas pelo processo
	 * 
	 * @param processo Id do processo
	 * @return Lista de atividades
	 */
	List<Atividade> getByProcesso(Integer processo);

	/**
	 * Verifica se há fluxo definido para as atividades do {@link Processo} informado.
	 * 
	 * @param idProcesso código identificador do {@link Processo}
	 * @return <code>true</code>, se houver fluxo definido;<br>
	 *         <code>false</code>, se não houver.
	 */
	boolean temFluxoDefinido(Integer idProcesso);
}
