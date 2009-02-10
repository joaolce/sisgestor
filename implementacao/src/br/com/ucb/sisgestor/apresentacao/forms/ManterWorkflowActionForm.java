/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterWorkflowAction;

/**
 * Form para a action {@link ManterWorkflowAction}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class ManterWorkflowActionForm extends BaseForm {

	private Integer	id;
	private String		descricao;
	private String		nome;
	private Boolean	ativo;

	/**
	 * Recupera o valor de ativo
	 * 
	 * @return ativo
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}


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
	 * Atribui ativo
	 * 
	 * @param ativo o valor a ajustar em ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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
}
