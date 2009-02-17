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

	private Integer				id;
	private String					descricao;
	private String					nome;
	private Integer				processo;
	private Integer				departamentoResponsavel;
	private List<Departamento>	listaDepartamentos;

	/**
	 * Recupera o valor de departamentoResponsavel
	 * 
	 * @return departamentoResponsavel
	 */
	public Integer getDepartamentoResponsavel() {
		return this.departamentoResponsavel;
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
	 * Atribui departamentoResponsavel
	 * 
	 * @param departamentoResponsavel o valor a ajustar em departamentoResponsavel
	 */
	public void setDepartamentoResponsavel(Integer departamentoResponsavel) {
		this.departamentoResponsavel = departamentoResponsavel;
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
