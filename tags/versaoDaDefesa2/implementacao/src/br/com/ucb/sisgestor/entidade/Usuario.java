/*
 * Projeto: SisGestor
 * Cria��o: 24/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@AttributeOverride(name = "id", column = @Column(name = "UUR_ID", nullable = false))
public class Usuario extends ObjetoPersistente {

	private Boolean			chefe;
	private Timestamp			dataHoraExclusao;
	private Departamento		departamento;
	private String				email;
	private String				login;
	private String				nome;
	private List<Permissao>	permissoes;
	private String				senha;
	private List<Tarefa>		tarefas;

	/**
	 * Recupera se o usu�rio � chefe do departamento.
	 * 
	 * @return se o usu�rio � chefe do departamento
	 */
	@Column(name = "UUR_CHEFE", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getChefe() {
		return this.chefe;
	}

	/**
	 * Recupera a data/hora de exclus�o do usu�rio.
	 * 
	 * @return data/hora de exclus�o do usu�rio
	 */
	@Column(name = "UUR_DATA_HORA_EXCLUSAO", nullable = true)
	public Timestamp getDataHoraExclusao() {
		return this.dataHoraExclusao;
	}

	/**
	 * Recupera o departamento do usu�rio
	 * 
	 * @return departamento do usu�rio
	 */
	@ManyToOne
	@JoinColumn(name = "DPR_ID", nullable = false)
	@ForeignKey(name = "IR_DPR_UUR")
	public Departamento getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o email do usu�rio.
	 * 
	 * @return email do usu�rio
	 */
	@Column(name = "UUR_EMAIL", nullable = true, length = ConstantesDB.EMAIL)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera o login do usu�rio
	 * 
	 * @return login do usu�rio
	 */
	@Column(name = "UUR_LOGIN", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_LOGIN)
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o nome do usu�rio
	 * 
	 * @return nome do usu�rio
	 */
	@Column(name = "UUR_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera as permiss�es do usu�rio no sistema
	 * 
	 * @return permissoes permiss�es do usu�rio no sistema
	 */
	@ManyToMany(targetEntity = Permissao.class)
	@JoinTable(name = "UPM_USUARIO_PERMISSAO", //XXX: inserir primary key na DDL
	joinColumns = @JoinColumn(name = "UUR_ID", referencedColumnName = "UUR_ID", nullable = false), //
	inverseJoinColumns = @JoinColumn(name = "PRM_ID", referencedColumnName = "PRM_ID", nullable = false))
	@ForeignKey(name = "IR_UUR_UPM")
	public List<Permissao> getPermissoes() {
		return this.permissoes;
	}

	/**
	 * Recupera a senha do usu�rio
	 * 
	 * @return senha senha do usu�rio
	 */
	@Column(name = "UUR_SENHA", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_SENHA)
	public String getSenha() {
		return this.senha;
	}

	/**
	 * Recupera as tarefas que s�o do usu�rio.
	 * 
	 * @return tarefas que s�o do usu�rio
	 */
	@OneToMany(targetEntity = Tarefa.class, mappedBy = "usuario", fetch = FetchType.LAZY)
	public List<Tarefa> getTarefas() {
		return this.tarefas;
	}

	/**
	 * Atribui se o usu�rio � chefe do departamento.
	 * 
	 * @param chefe se o usu�rio � chefe do departamento
	 */
	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	/**
	 * Atribui a data/hora de exclus�o do usu�rio.
	 * 
	 * @param dataHoraExclusao data/hora de exclus�o do usu�rio
	 */
	public void setDataHoraExclusao(Timestamp dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
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
	 * Atribui o email do usu�rio.
	 * 
	 * @param email email do usu�rio
	 */
	public void setEmail(String email) {
		this.email = email;
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


	/**
	 * Atribui as tarefas que s�o do usu�rio.
	 * 
	 * @param tarefas tarefas que s�o do usu�rio
	 */
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
}
