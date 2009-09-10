/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;

/**
 * Interface que representa um objeto de neg�cio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public interface BaseBO<T extends ObjetoPersistente> {

	/**
	 * Atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a atualizar
	 * @throws NegocioException caso uma regra de neg�cio seja violada
	 */
	void atualizar(T obj) throws NegocioException;

	/**
	 * Apaga um objeto da base de dados.
	 * 
	 * @param obj objeto persistente a apagar
	 * @throws NegocioException caso uma regra de neg�cio seja violada
	 */
	void excluir(T obj) throws NegocioException;

	/**
	 * Retorna o total de registros retornados pela consulta paginada.
	 * 
	 * @param parametros dto de par�metros
	 * @return total de registros encontrados
	 */
	Integer getTotalPesquisa(PesquisaPaginadaDTO parametros);

	/**
	 * Recupera um objeto a partir da sua chave prim�ria.
	 * 
	 * @param pk chave prim�ria do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	T obter(Integer pk);

	/**
	 * Recupera todos os objetos.
	 * 
	 * @return um {@link List} de {@link ObjetoPersistente}
	 */
	List<T> obterTodos();

	/**
	 * Salva (inclu�) um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a ser salvo
	 * @return {@link Integer} identificador do objeto criado
	 * @throws NegocioException caso uma regra de neg�cio seja violada
	 */
	Integer salvar(T obj) throws NegocioException;
}
