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
