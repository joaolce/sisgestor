/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import java.util.List;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;
import br.com.ucb.sisgestor.entidade.Departamento;

/**
 * Form para a action {@link ManterDepartamentoAction}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterDepartamentoActionForm extends BaseForm {

	private Integer 				id;
	private String					email;
	private String					sigla;
	private String 			   nome;
	private List<Departamento> listaDepartamentos;
	
	private Departamento 		departamento;
	
	private Integer		 		departamentoSuperior;
	
	/**
	 * Recupera o valor de id
	 *
	 * @return id
	 */
	public Integer getId() {
		return id;
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
	 * Recupera o valor de email
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
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
	 * Recupera o valor de sigla
	 *
	 * @return sigla
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * Atribui sigla
	 *
	 * @param sigla o valor a ajustar em sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * Recupera o valor de listaDepartamentos
	 *
	 * @return listaDepartamentos
	 */
	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
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
	 * Recupera o valor de departamento
	 *
	 * @return departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}
	/**
	 * Atribui departamento
	 *
	 * @param departamento o valor a ajustar em departamento
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	/**
	 * Recupera o valor de departamentoSuperior
	 *
	 * @return departamentoSuperior
	 */
	public Integer getDepartamentoSuperior() {
		return departamentoSuperior;
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
	 * Recupera o valor de nome
	 *
	 * @return nome
	 */
	public String getNome() {
		return nome;
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
	