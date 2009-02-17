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

	private String					login;
	private String					nome;
	private String					email;
	private Integer				departamento;
	private Boolean				chefe;
	private List<Departamento>	listaDepartamentos;
	private List<Permissao>		permissoesDisponiveis;
	private Integer[]				permissoes;
	private String					senhaAtual;
	private String					novaSenha;
	private String					confirmarSenha;

	/**
	 * Recupera o indicador de usuário chefe.
	 * 
	 * @return indicador de usuário chefe
	 */
	public Boolean getChefe() {
		return this.chefe;
	}

	/**
	 * Recupera a confirmação de nova senha do usuário.
	 * 
	 * @return confirmação de nova senha do usuário
	 */
	public String getConfirmarSenha() {
		return this.confirmarSenha;
	}

	/**
	 * Recupera o código do departamento.
	 * 
	 * @return código do departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o email do usuário.
	 * 
	 * @return email do usuário
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera todos os departamentos cadastrados.
	 * 
	 * @return todos os departamentos cadastrados
	 */
	public List<Departamento> getListaDepartamentos() {
		return this.listaDepartamentos;
	}

	/**
	 * Recupera o login do usuário.
	 * 
	 * @return login do usuário
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o nome do usuário.
	 * 
	 * @return nome do usuário
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera a nova senha do usuário.
	 * 
	 * @return nova senha do usuário
	 */
	public String getNovaSenha() {
		return this.novaSenha;
	}

	/**
	 * Recupera os identificadores das permissões selecionadas.
	 * 
	 * @return identificadores das permissões selecionadas
	 */
	public Integer[] getPermissoes() {
		return this.permissoes;
	}

	/**
	 * Recupera todas as permissões do sistema.
	 * 
	 * @return todas as permissões do sistema
	 */
	public List<Permissao> getPermissoesDisponiveis() {
		return this.permissoesDisponiveis;
	}

	/**
	 * Recupera a senha atual do usuário.
	 * 
	 * @return senha atual do usuário
	 */
	public String getSenhaAtual() {
		return this.senhaAtual;
	}

	/**
	 * Atribui o indicador de usuário chefe.
	 * 
	 * @param chefe indicador de usuário chefe
	 */
	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	/**
	 * Atribui a confirmação de nova senha do usuário.
	 * 
	 * @param confirmarSenha confirmação de nova senha do usuário
	 */
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	/**
	 * Atribui o código do departamento.
	 * 
	 * @param departamento código do departamento
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui o email do usuário.
	 * 
	 * @param email email do usuário
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Atribui todos os departamentos cadastrados.
	 * 
	 * @param listaDepartamentos todos os departamentos cadastrados
	 */
	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	/**
	 * Atribui o login do usuário.
	 * 
	 * @param login login do usuário
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Atribui o nome do usuário.
	 * 
	 * @param nome nome do usuário
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui a nova senha do usuário.
	 * 
	 * @param novaSenha nova senha do usuário
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * Atribui os identificadores das permissões selecionadas.
	 * 
	 * @param permissoes identificadores das permissões selecionadas
	 */
	public void setPermissoes(Integer[] permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * Atribui todas as permissões do sistema.
	 * 
	 * @param permissoesDisponiveis todas as permissões do sistema
	 */
	public void setPermissoesDisponiveis(List<Permissao> permissoesDisponiveis) {
		this.permissoesDisponiveis = permissoesDisponiveis;
	}

	/**
	 * Atribui a senha atual do usuário.
	 * 
	 * @param senhaAtual senha atual do usuário
	 */
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}
