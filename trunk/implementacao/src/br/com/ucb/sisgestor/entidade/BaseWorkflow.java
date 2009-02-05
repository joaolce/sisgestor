/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.MappedSuperclass;


/**
 * Objeto base para os dados de workflow
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@MappedSuperclass
public class BaseWorkflow extends ObjetoPersistente {

	private String	nome;
	private String	descricao;

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de nome
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui descricao
	 * 
	 * @param descricao o valor a ajustar em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
