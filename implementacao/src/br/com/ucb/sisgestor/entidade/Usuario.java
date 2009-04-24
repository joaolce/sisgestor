/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
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
	 * Recupera se o usuário é chefe do departamento.
	 * 
	 * @return se o usuário é chefe do departamento
	 */
	@Column(name = "UUR_CHEFE", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getChefe() {
		return this.chefe;
	}

	/**
	 * Recupera a data/hora de exclusão do usuário.
	 * 
	 * @return data/hora de exclusão do usuário
	 */
	@Column(name = "UUR_DATA_HORA_EXCLUSAO", nullable = true)
	public Timestamp getDataHoraExclusao() {
		return this.dataHoraExclusao;
	}

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
	@Column(name = "UUR_EMAIL", nullable = true, length = ConstantesDB.EMAIL)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera o login do usuário
	 * 
	 * @return login do usuário
	 */
	@Column(name = "UUR_LOGIN", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_LOGIN)
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupera o nome do usuário
	 * 
	 * @return nome do usuário
	 */
	@Column(name = "UUR_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera as permissões do usuário no sistema
	 * 
	 * @return permissoes permissões do usuário no sistema
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
	 * Recupera a senha do usuário
	 * 
	 * @return senha senha do usuário
	 */
	@Column(name = "UUR_SENHA", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_SENHA)
	public String getSenha() {
		return this.senha;
	}

	/**
	 * Recupera as tarefas que são do usuário.
	 * 
	 * @return tarefas que são do usuário
	 */
	@OneToMany(targetEntity = Tarefa.class, mappedBy = "usuario", fetch = FetchType.LAZY)
	public List<Tarefa> getTarefas() {
		return this.tarefas;
	}

	/**
	 * Atribui se o usuário é chefe do departamento.
	 * 
	 * @param chefe se o usuário é chefe do departamento
	 */
	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	/**
	 * Atribui a data/hora de exclusão do usuário.
	 * 
	 * @param dataHoraExclusao data/hora de exclusão do usuário
	 */
	public void setDataHoraExclusao(Timestamp dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
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


	/**
	 * Atribui as tarefas que são do usuário.
	 * 
	 * @param tarefas tarefas que são do usuário
	 */
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
}
