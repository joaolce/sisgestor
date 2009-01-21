/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NaturalId;

/**
 * Classe que representa um usuário no sistema
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UUR_USUARIO")
@org.hibernate.annotations.Table(appliesTo = "UUR_USUARIO")
@AttributeOverride(name = "id", column = @Column(name = "UUR_ID", nullable = false))
public class Usuario extends ObjetoPersistente {

	private String				nome;
	private String				login;
	private String				senha;
	private String				email;
	private Departamento		departamento;
	private List<Permissao>	permissoes;

	/**
	 * Recupera o departamento do usuário
	 * 
	 * @return departamento do usuário
	 */
	@ManyToOne
	@JoinColumn(name = "DPR_ID", nullable = false)
	@ForeignKey(name = "IR_DPR_UUR")
	public Departamento getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o email do usuário.
	 * 
	 * @return email do usuário
	 */
	@Column(name = "UUR_EMAIL", nullable = true, length = 40)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera o login do usuário
	 * 
	 * @return login do usuário
	 */
	@Column(name = "UUR_LOGIN", nullable = false, columnDefinition = "CHAR(15)", length = 15)
	@NaturalId(mutable = true)
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o nome do usuário
	 * 
	 * @return nome do usuário
	 */
	@Column(name = "UUR_NOME", nullable = false, length = 150)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera as permissões do usuário no sistema
	 * 
	 * @return permissoes permissões do usuário no sistema
	 */
	@ManyToMany(targetEntity = Permissao.class)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@JoinTable(name = "UPM_USUARIO_PERMISSAO", //
	joinColumns = @JoinColumn(name = "UUR_ID", referencedColumnName = "UUR_ID", nullable = false), //
	inverseJoinColumns = @JoinColumn(name = "PRM_ID", referencedColumnName = "PRM_ID", nullable = false))
	@ForeignKey(name = "IR_UUR_UPM", inverseName = "IR_PRM_UPM")
	public List<Permissao> getPermissoes() {
		return this.permissoes;
	}

	/**
	 * Recupera a senha do usuário
	 * 
	 * @return senha senha do usuário
	 */
	@Column(name = "UUR_SENHA", nullable = false, length = 20, columnDefinition = "CHAR(20)")
	public String getSenha() {
		return this.senha;
	}

	/**
	 * Atribui o departamento do usuário
	 * 
	 * @param departamento departamento do usuário
	 */
	public void setDepartamento(Departamento departamento) {
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
	 * Atribui o login do usuário
	 * 
	 * @param login login do usuário
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Atribui o nome do usuário
	 * 
	 * @param nome nome do usuário
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui as permissões do usuário no sistema
	 * 
	 * @param permissoes permissões do usuário no sistema
	 */
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * Atribui a senha do usuário
	 * 
	 * @param senha senha do usuário
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
