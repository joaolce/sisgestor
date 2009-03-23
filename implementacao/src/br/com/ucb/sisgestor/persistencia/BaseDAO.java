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

	/** Quantidade de registros por paginação */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(9);

	/**
	 * Atualiza um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a atualizar
	 */
	void atualizar(T obj);

	/**
	 * Apaga um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a apagar
	 */
	void excluir(T obj);

	/**
	 * Recupera um objeto a partir da sua chave primária.
	 * 
	 * @param pk chave primária do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	T obter(PK pk);

	/**
	 * Recupera um objeto a partir da sua chave primária. <br />
	 * obs: o objeto recuperado será a partir de uma sessão nova do hibernate, ignorando as alterações já
	 * feitas no objeto da sessão atual
	 * 
	 * @param pk chave primária do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	T obterAntigo(PK pk);

	/**
	 * Recupera todos os objetos.
	 * 
	 * @return um {@link List} de {@link ObjetoPersistente}
	 */
	List<T> obterTodos();

	/**
	 * Salva um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a salvar
	 */
	void salvar(T obj);
}
