/*
 * Projeto: SisGestor
 * Cria��o: 24/10/2008 por Jo�o L�cio
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
 * Classe que representa um usu�rio no sistema
 * 
 * @author Jo�o L�cio
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
	 * Recupera o departamento do usu�rio
	 * 
	 * @return departamento do usu�rio
	 */
	@ManyToOne
	@JoinColumn(name = "DPR_ID", nullable = false)
	@ForeignKey(name = "IRDPRUUR")
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * Recupera o login do usu�rio
	 * 
	 * @return login do usu�rio
	 */
	@Column(name = "UUR_DS_LOGIN", nullable = false, columnDefinition = "CHAR(15)", length = 15)
	public String getLogin() {
		return login;
	}

	/**
	 * Recupera o nome do usu�rio
	 * 
	 * @return nome do usu�rio
	 */
	@Column(name = "UUR_NM", nullable = false, length = 150)
	public String getNome() {
		return nome;
	}

	/**
	 * Recupera as permiss�es do usu�rio no sistema
	 * 
	 * @return permissoes permiss�es do usu�rio no sistema
	 */
	@ManyToMany(targetEntity = Permissao.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "UPM_USUARIO_PERMISSAO", joinColumns = @JoinColumn(name = "UUR_ID", nullable = false, unique = true), inverseJoinColumns = @JoinColumn(name = "PRM_ID", nullable = false, unique = true), uniqueConstraints = @UniqueConstraint(columnNames = {
			"UUR_ID", "PRM_ID"}))
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * Recupera a senha do usu�rio
	 * 
	 * @return senha senha do usu�rio
	 */
	@Column(name = "UUR_DS_SENHA", nullable = false)
	public String getSenha() {
		return senha;
	}

	/**
	 * Atribui o departamento do usu�rio
	 * 
	 * @param departamento departamento do usu�rio
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui o login do usu�rio
	 * 
	 * @param login login do usu�rio
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Atribui o nome do usu�rio
	 * 
	 * @param nome nome do usu�rio
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui as permiss�es do usu�rio no sistema
	 * 
	 * @param permissoes permiss�es do usu�rio no sistema
	 */
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * Atribui a senha do usu�rio
	 * 
	 * @param senha senha do usu�rio
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
