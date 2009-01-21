/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.io.Serializable;
import java.util.List;

/**
 * Interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public interface BaseBO<T extends ObjetoPersistente, PK extends Serializable> {

	/**
	 * Atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a atualizar
	 * @throws NegocioException
	 */
	public void atualizar(T obj) throws NegocioException;

	/**
	 * Apaga um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a apagar
	 * @throws NegocioException
	 */
	public void excluir(T obj) throws NegocioException;

	/**
	 * Recupera um objeto a partir da sua chave primária.
	 * 
	 * @param pk chave primária do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	public T obter(PK pk);

	/**
	 * Recupera todos os objetos.
	 * 
	 * @return um {@link List} de objeto
	 */
	public List<T> obterTodos();

	/**
	 * Salva um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a salvar
	 * @throws NegocioException
	 */
	public void salvar(T obj) throws NegocioException;
}
