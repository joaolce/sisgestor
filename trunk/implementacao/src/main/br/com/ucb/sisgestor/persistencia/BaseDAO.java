/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import java.util.List;

/**
 * Interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
public interface BaseDAO<T extends ObjetoPersistente> {

	/** Quantidade de registros por pagina��o */
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
	 * Recupera um objeto a partir da sua chave prim�ria.
	 * 
	 * @param pk chave prim�ria do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	T obter(Integer pk);

	/**
	 * Recupera um objeto a partir da sua chave prim�ria. <br />
	 * obs: o objeto recuperado ser� a partir de uma sess�o nova do hibernate, ignorando as altera��es j�
	 * feitas no objeto da sess�o atual
	 * 
	 * @param pk chave prim�ria do objeto persistente
	 * 
	 * @return objeto recuperado
	 */
	T obterAntigo(Integer pk);

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
	 * @return {@link Integer} indentificador do objeto criado
	 */
	Integer salvar(T obj);
}
