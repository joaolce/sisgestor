/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ForeignKey;


/**
 * Classe que representa um usuário no sistema
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UUR_USUARIO")
@org.hibernate.annotations.Table(appliesTo = "UUR_USUARIO")
@SequenceGenerator(name = "SEQ_UUR", sequenceName = "SEQ_UUR", allocationSize = 10)
@AttributeOverride(name = "id", column = @Column(name = "UUR_ID", nullable = false))
public class Usuario extends ObjetoPersistente {

	private Departamento		departamento;
	private String				login;
	private String				nome;
	private List<Permissao>	permissoes;
	private String				senha;

	/**
	 * Recupera o departamento do usuário
	 * 
	 * @return departamento do usuário
	 */
	@ManyToOne
	@JoinColumn(name = "DPR_ID", nullable = false)
	@ForeignKey(name = "IRDPRUUR")
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * Recupera o login do usuário
	 * 
	 * @return login do usuário
	 */
	@Column(name = "UUR_DS_LOGIN", nullable = false, columnDefinition = "CHAR(15)", length = 15)
	public String getLogin() {
		return login;
	}

	/**
	 * Recupera o nome do usuário
	 * 
	 * @return nome do usuário
	 */
	@Column(name = "UUR_NM", nullable = false, length = 150)
	public String getNome() {
		return nome;
	}

	/**
	 * Recupera as permissões do usuário no sistema
	 * 
	 * @return permissoes permissões do usuário no sistema
	 */
	@ManyToMany(targetEntity = Permissao.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "UPM_USUARIO_PERMISSAO", joinColumns = @JoinColumn(name = "UUR_ID", nullable = false, unique = true), inverseJoinColumns = @JoinColumn(name = "PRM_ID", nullable = false, unique = true), uniqueConstraints = @UniqueConstraint(columnNames = {
			"UUR_ID", "PRM_ID"}))
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * Recupera a senha do usuário
	 * 
	 * @return senha senha do usuário
	 */
	@Column(name = "UUR_DS_SENHA", nullable = false)
	public String getSenha() {
		return senha;
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
