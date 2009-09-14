/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.sisgestor.apresentacao.forms;

import br.com.sisgestor.apresentacao.actions.ManterWorkflowAction;

/**
 * Form para a action {@link ManterWorkflowAction}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class ManterWorkflowActionForm extends BaseForm {

	private Boolean	ativo;
	private String		descricao;
	private String		nome;

	/**
	 * Recupera o indicador de ativo do workflow.
	 * 
	 * @return indicador de ativo do workflow
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera a descri��o do workflow.
	 * 
	 * @return descri��o do workflow
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o nome do workflow.
	 * 
	 * @return nome do workflow
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui o indicador de ativo do workflow.
	 * 
	 * @param ativo indicador de ativo do workflow
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui a descri��o do workflow.
	 * 
	 * @param descricao descri��o do workflow
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o nome do workflow.
	 * 
	 * @param nome nome do workflow
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
