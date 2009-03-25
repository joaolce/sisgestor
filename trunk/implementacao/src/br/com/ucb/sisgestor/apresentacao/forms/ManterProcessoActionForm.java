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
	private String[]	fluxos;
	private String[]	posicoes;

	/**
	 * Recupera a descrição do processo
	 * 
	 * @return descrição do processo
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera os fluxos dos processos.
	 * 
	 * @return fluxos dos processos
	 */
	public String[] getFluxos() {
		return this.fluxos;
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
	 * Recupera as posições dos processos
	 * 
	 * @return posicoes dos processos
	 */
	public String[] getPosicoes() {
		return this.posicoes;
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
	 * Atribui os fluxos dos processos.
	 * 
	 * @param fluxos fluxos dos processos
	 */
	public void setFluxos(String[] fluxos) {
		this.fluxos = fluxos;
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
	 * Atribui as posições dos processos
	 * 
	 * @param posicoes posições dos processos
	 */
	public void setPosicoes(String[] posicoes) {
		this.posicoes = posicoes;
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
