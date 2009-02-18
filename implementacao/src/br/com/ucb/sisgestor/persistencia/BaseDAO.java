/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import java.io.Serializable;
import java.util.List;

/**
 * Interface que representa um DAO (Data Access Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave prim�ria do objeto persistente utilizado
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
public interface BaseDAO<T extends ObjetoPersistente, PK extends Serializable> {

	/** Quantidade de registros por pagina��o */
	Integer	QTD_REGISTROS_PAGINA	= Integer.valueOf(9);
	
	/** Quantidade de registros por pagina��o da tela de Processos*/
	Integer QTD_REGISTROS_PAGINA_PROCESSO = Integer.valueOf(6);
	
	/** Quantidade de registros por pagina��o da tela de Atividades*/
	Integer QTD_REGISTROS_PAGINA_ATIVIDADE = Integer.valueOf(4);
	
	/** Quantidade de registros por pagina��o da tela de Tarefas*/
	Integer QTD_REGISTROS_PAGINA_TAREFA = Integer.valueOf(4);

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
	T obter(PK pk);

	/**
	 * Recupera todos os objetos.
	 * 
	 * @return um {@link List} de objeto
	 */
	List<T> obterTodos();

	/**
	 * Salva um objeto na base de dados.
	 * 
	 * @param obj objeto persistente a salvar
	 */
	void salvar(T obj);
}