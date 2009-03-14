/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Thiago
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
	 * Recupera a descri��o do processo
	 * 
	 * @return descri��o do processo
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
	 * Recupera o c�digo identificador do workflow.
	 * 
	 * @return c�digo identificador do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui a descri��o do processo
	 * 
	 * @param descricao descri��o do processo
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
	 * Atribui o c�digo identificador do workflow.
	 * 
	 * @param workflow c�digo identificador do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
