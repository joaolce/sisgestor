/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterAtividadeAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Form para a action {@link ManterAtividadeAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeActionForm extends BaseForm {

	private String					descricao;
	private String					nome;
	private Integer				processo;
	private Integer				departamento;
	private List<Departamento>	listaDepartamentos;

	/**
	 * Recupera o valor de departamento
	 * 
	 * @return departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
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
	 * Recupera o valor de listaDepartamentos
	 * 
	 * @return listaDepartamentos
	 */
	public List<Departamento> getListaDepartamentos() {
		return this.listaDepartamentos;
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
	 * Recupera o identificador do processo.
	 * 
	 * @return identificador do processo
	 */
	public Integer getProcesso() {
		return this.processo;
	}


	/**
	 * Atribui departamento
	 * 
	 * @param departamento o valor a ajustar em departamento
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
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
	 * Atribui listaDepartamentos
	 * 
	 * @param listaDepartamentos o valor a ajustar em listaDepartamentos
	 */
	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
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
	 * Atribui o identificador do processo.
	 * 
	 * @param processo identificador do processo
	 */
	public void setProcesso(Integer processo) {
		this.processo = processo;
	}


}
