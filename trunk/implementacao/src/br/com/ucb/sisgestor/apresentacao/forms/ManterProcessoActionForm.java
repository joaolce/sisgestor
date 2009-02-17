/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterProcessoAction;

/**
 * Form para a action {@link ManterProcessoAction}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoActionForm extends BaseForm {

	private Integer	workflow;
	private Integer	id;
	private String		descricao;
	private String		nome;

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return this.id;
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
	 * Recupera o identificador do workflow.
	 * 
	 * @return identificador do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
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
	 * Atribui id
	 * 
	 * @param id o valor a ajustar em id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui o identificador do workflow.
	 * 
	 * @param workflow identificador do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
