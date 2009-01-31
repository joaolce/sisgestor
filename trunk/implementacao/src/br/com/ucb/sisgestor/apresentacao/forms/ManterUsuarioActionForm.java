/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Permissao;
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
	private Boolean				chefe;
	private List<Departamento>	listaDepartamentos;
	private List<Permissao>		roles;
	private List<Permissao>		permissoesDisponiveis;
	private Integer[]				permissoesInform;
	private Integer[]				permissoes;

	private String					senhaAtual;
	private String					novaSenha;
	private String					confirmarSenha;

	/**
	 * Recupera o valor de chefe.
	 * 
	 * @return chefe
	 */
	public Boolean getChefe() {
		return this.chefe;
	}

	/**
	 * Recupera o valor de confirmarSenha
	 * 
	 * @return confirmarSenha
	 */
	public String getConfirmarSenha() {
		return this.confirmarSenha;
	}

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
	 * Recupera o valor de novaSenha
	 * 
	 * @return novaSenha
	 */
	public String getNovaSenha() {
		return this.novaSenha;
	}

	/**
	 * Recupera o valor de permissoes
	 * 
	 * @return permissoes
	 */
	public Integer[] getPermissoes() {
		return this.permissoes;
	}

	/**
	 * Recupera o valor de permissoesDisponiveis
	 * 
	 * @return permissoesDisponiveis
	 */
	public List<Permissao> getPermissoesDisponiveis() {
		return this.permissoesDisponiveis;
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
	 * Recupera o valor de roles
	 * 
	 * @return roles
	 */
	public List<Permissao> getRoles() {
		return this.roles;
	}

	/**
	 * Recupera o valor de senhaAtual
	 * 
	 * @return senhaAtual
	 */
	public String getSenhaAtual() {
		return this.senhaAtual;
	}

	/**
	 * Atribui chefe.
	 * 
	 * @param chefe o valor a ajustar em chefe
	 */
	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	/**
	 * Atribui confirmarSenha
	 * 
	 * @param confirmarSenha o valor a ajustar em confirmarSenha
	 */
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
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
	 * Atribui novaSenha
	 * 
	 * @param novaSenha o valor a ajustar em novaSenha
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * Atribui permissoes
	 * 
	 * @param permissoes o valor a ajustar em permissoes
	 */
	public void setPermissoes(Integer[] permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * Atribui permissoesDisponiveis
	 * 
	 * @param permissoesDisponiveis o valor a ajustar em permissoesDisponiveis
	 */
	public void setPermissoesDisponiveis(List<Permissao> permissoesDisponiveis) {
		this.permissoesDisponiveis = permissoesDisponiveis;
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
	 * Atribui roles
	 * 
	 * @param roles o valor a ajustar em roles
	 */
	public void setRoles(List<Permissao> roles) {
		this.roles = roles;
	}


	/**
	 * Atribui senhaAtual
	 * 
	 * @param senhaAtual o valor a ajustar em senhaAtual
	 */
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}
