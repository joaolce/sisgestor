/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Permissao;
import java.util.List;

/**
 * Form para a action {@link ManterUsuarioAction}.
 * 
 * @author Jo�o L�cio
 * @since 03/01/2009
 */
public class ManterUsuarioActionForm extends BaseForm {

	private Integer id;
	private String login;
	private String nome;
	private String email;
	private Integer departamento;
	private List<Departamento> listaDepartamentos;
	private List<Permissao> roles;

	private List<Permissao> permissoesInformadas;
	private List<Permissao> permissoesSelecionadas;
	private Integer[] permissoesInform;
	private Integer[] permissoesSel;

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
	 * Recupera o valor de permissoesInform
	 * 
	 * @return permissoesInform
	 */
	public Integer[] getPermissoesInform() {
		return this.permissoesInform;
	}

	/**
	 * Recupera o valor de permissoesInformadas
	 * 
	 * @return permissoesInformadas
	 */
	public List<Permissao> getPermissoesInformadas() {
		return this.permissoesInformadas;
	}

	/**
	 * Recupera o valor de permissoesSel
	 * 
	 * @return permissoesSel
	 */
	public Integer[] getPermissoesSel() {
		return this.permissoesSel;
	}

	/**
	 * Recupera o valor de permissoesSelecionadas
	 * 
	 * @return permissoesSelecionadas
	 */
	public List<Permissao> getPermissoesSelecionadas() {
		return this.permissoesSelecionadas;
	}


	/**
	 * Recupera o valor de roles
	 * 
	 * @return roles
	 */
	public List<Permissao> getRoles() {
		return this.roles;
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
	 * Atribui permissoesInform
	 * 
	 * @param permissoesInform o valor a ajustar em permissoesInform
	 */
	public void setPermissoesInform(Integer[] permissoesInform) {
		this.permissoesInform = permissoesInform;
	}

	/**
	 * Atribui permissoesInformadas
	 * 
	 * @param permissoesInformadas o valor a ajustar em permissoesInformadas
	 */
	public void setPermissoesInformadas(List<Permissao> permissoesInformadas) {
		this.permissoesInformadas = permissoesInformadas;
	}

	/**
	 * Atribui permissoesSel
	 * 
	 * @param permissoesSel o valor a ajustar em permissoesSel
	 */
	public void setPermissoesSel(Integer[] permissoesSel) {
		this.permissoesSel = permissoesSel;
	}

	/**
	 * Atribui permissoesSelecionadas
	 * 
	 * @param permissoesSelecionadas o valor a ajustar em permissoesSelecionadas
	 */
	public void setPermissoesSelecionadas(List<Permissao> permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}

	/**
	 * Atribui roles
	 * 
	 * @param roles o valor a ajustar em roles
	 */
	public void setRoles(List<Permissao> roles) {
		this.roles = roles;
	}
}
