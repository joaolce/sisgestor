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
	 * Recupera o indicador de usu�rio chefe.
	 * 
	 * @return indicador de usu�rio chefe
	 */
	public Boolean getChefe() {
		return this.chefe;
	}

	/**
	 * Recupera a confirma��o de nova senha do usu�rio.
	 * 
	 * @return confirma��o de nova senha do usu�rio
	 */
	public String getConfirmarSenha() {
		return this.confirmarSenha;
	}

	/**
	 * Recupera o c�digo do departamento.
	 * 
	 * @return c�digo do departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o email do usu�rio.
	 * 
	 * @return email do usu�rio
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
	 * Recupera o login do usu�rio.
	 * 
	 * @return login do usu�rio
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o nome do usu�rio.
	 * 
	 * @return nome do usu�rio
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera a nova senha do usu�rio.
	 * 
	 * @return nova senha do usu�rio
	 */
	public String getNovaSenha() {
		return this.novaSenha;
	}

	/**
	 * Recupera os identificadores das permiss�es selecionadas.
	 * 
	 * @return identificadores das permiss�es selecionadas
	 */
	public Integer[] getPermissoes() {
		return this.permissoes;
	}

	/**
	 * Recupera todas as permiss�es do sistema.
	 * 
	 * @return todas as permiss�es do sistema
	 */
	public List<Permissao> getPermissoesDisponiveis() {
		return this.permissoesDisponiveis;
	}

	/**
	 * Recupera a senha atual do usu�rio.
	 * 
	 * @return senha atual do usu�rio
	 */
	public String getSenhaAtual() {
		return this.senhaAtual;
	}

	/**
	 * Atribui o indicador de usu�rio chefe.
	 * 
	 * @param chefe indicador de usu�rio chefe
	 */
	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	/**
	 * Atribui a confirma��o de nova senha do usu�rio.
	 * 
	 * @param confirmarSenha confirma��o de nova senha do usu�rio
	 */
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	/**
	 * Atribui o c�digo do departamento.
	 * 
	 * @param departamento c�digo do departamento
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui o email do usu�rio.
	 * 
	 * @param email email do usu�rio
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
	 * Atribui o login do usu�rio.
	 * 
	 * @param login login do usu�rio
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Atribui o nome do usu�rio.
	 * 
	 * @param nome nome do usu�rio
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui a nova senha do usu�rio.
	 * 
	 * @param novaSenha nova senha do usu�rio
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * Atribui os identificadores das permiss�es selecionadas.
	 * 
	 * @param permissoes identificadores das permiss�es selecionadas
	 */
	public void setPermissoes(Integer[] permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * Atribui todas as permiss�es do sistema.
	 * 
	 * @param permissoesDisponiveis todas as permiss�es do sistema
	 */
	public void setPermissoesDisponiveis(List<Permissao> permissoesDisponiveis) {
		this.permissoesDisponiveis = permissoesDisponiveis;
	}

	/**
	 * Atribui a senha atual do usu�rio.
	 * 
	 * @param senhaAtual senha atual do usu�rio
	 */
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}
