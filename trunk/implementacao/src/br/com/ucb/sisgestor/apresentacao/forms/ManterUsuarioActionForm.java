/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Form para a action {@link ManterUsuarioAction}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterUsuarioActionForm extends BaseForm {

	private Integer				id;
	private String					login;
	private String					nome;
	private String					email;
	private Integer				departamento;
	private List<Usuario>		usuarios;
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
	 * Recupera o valor de login
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
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
	 * Recupera o valor de usuarios
	 * 
	 * @return usuarios
	 */
	public List<Usuario> getUsuarios() {
		return this.usuarios;
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
	 * Atribui login
	 * 
	 * @param login o valor a ajustar em login
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * Atribui usuarios
	 * 
	 * @param usuarios o valor a ajustar em usuarios
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
