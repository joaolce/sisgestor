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

	private String		descricao;
	private String		nome;
	private Integer	workflow;

	/**
	 * Recupera a descrição do processo
	 * 
	 * @return descrição do processo
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o nome do processo
	 * 
	 * @return nome do processo
	 */
	public String getNome() {
		return this.nome;
	}


	/**
	 * Recupera o código identificador do workflow.
	 * 
	 * @return código identificador do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui a descrição do processo
	 * 
	 * @param descricao descrição do processo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o nome do processo
	 * 
	 * @param nome nome do processo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui o código identificador do workflow.
	 * 
	 * @param workflow código identificador do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
