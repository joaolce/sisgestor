/*
 * Projeto: SisGestor
 * Criação: 21/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import java.io.Serializable;
import java.util.List;

/**
 * Interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 21/10/2008
 */
public interface BaseDAO<T extends ObjetoPersistente, PK extends Serializable> {

	/** Máximo de resultados paginados */
	public final Integer	MAXIMO_RESULTADOS	= new Integer(9);

	/**
	 * Atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a atualizar
	 */
	public void atualizar(T obj);

	/**
	 * Apaga um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a apagar
	 */
	public void excluir(T obj);

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
	 */
	public void salvar(T obj);

	/**
	 * Salva ou atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a salvar ou atualizar
	 */
	public void salvarOuAtualizar(T obj);
}
