/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;

/**
 * Interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public interface BaseBO<T extends ObjetoPersistente> {

	/**
	 * Atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a atualizar
	 * @throws NegocioException caso uma regra de negócio seja violada
	 */
	void atualizar(T obj) throws NegocioException;

	/**
	 * Apaga um objeto da base de dados.
	 * 
	 * @param obj objeto persistente a apagar
	 * @throws NegocioException caso uma regra de negócio seja violada
	 */
	void excluir(T obj) throws NegocioException;

	/**
	 * Retorna o total de registros retornados pela consulta paginada.
	 * 
	 * @param parametros dto de parâmetros
	 * @return total de registros encontrados
	 */
	Integer getTotalPesquisa(PesquisaPaginadaDTO parametros);

	/**
	 * Recupera um objeto a partir da sua chave primária.
	 * 
	 * @param pk chave primária do objeto persistente
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
	 * Salva (incluí) um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a ser salvo
	 * @return {@link Integer} identificador do objeto criado
	 * @throws NegocioException caso uma regra de negócio seja violada
	 */
	Integer salvar(T obj) throws NegocioException;
}
