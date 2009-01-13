/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Form para a action {@link ManterDepartamentoAction}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterDepartamentoActionForm extends BaseForm {

	private Integer				id;
	private String					sigla;
	private String					nome;
	private String					email;
	private Integer				departamentoSuperior;
	private List<Departamento>	listaDepartamentos;

	/**
	 * Recupera o valor de departamentoSuperior
	 * 
	 * @return departamentoSuperior
	 */
	public Integer getDepartamentoSuperior() {
		return this.departamentoSuperior;
	}

	/**
	 * Recupera o valor de email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return this.email;
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
	 * Recupera o valor de sigla
	 * 
	 * @return sigla
	 */
	public String getSigla() {
		return this.sigla;
	}

	/**
	 * Atribui departamentoSuperior
	 * 
	 * @param departamentoSuperior o valor a ajustar em departamentoSuperior
	 */
	public void setDepartamentoSuperior(Integer departamentoSuperior) {
		this.departamentoSuperior = departamentoSuperior;
	}

	/**
	 * Atribui email
	 * 
	 * @param email o valor a ajustar em email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Atribui sigla
	 * 
	 * @param sigla o valor a ajustar em sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
