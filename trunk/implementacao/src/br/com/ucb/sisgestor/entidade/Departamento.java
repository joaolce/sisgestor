/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NaturalId;

/**
 * Classe que representa um departamento no sistema.
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "DPR_DEPARTAMENTO")
@org.hibernate.annotations.Table(appliesTo = "DPR_DEPARTAMENTO")
@AttributeOverride(name = "id", column = @Column(name = "DPR_ID", nullable = false))
public class Departamento extends ObjetoPersistente {

	private List<Atividade>		atividades;
	private List<Departamento>	departamentosFilhos;
	private Departamento			departamentoSuperior;
	private String					email;
	private String					nome;
	private String					sigla;
	private List<Usuario>		usuarios;

	/**
	 * Recupera o valor de atividades
	 * 
	 * @return atividades
	 */
	@OneToMany(targetEntity = Atividade.class, mappedBy = "departamento")
	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	/**
	 * Recupera os departamentos filhos diretos do departamento.
	 * 
	 * @return departamentos filhos diretos do departamento
	 */
	@OneToMany(targetEntity = Departamento.class, mappedBy = "departamentoSuperior")
	public List<Departamento> getDepartamentosFilhos() {
		return this.departamentosFilhos;
	}

	/**
	 * Recupera o departamento superior do departamento.
	 * 
	 * @return departamento superior do departamento
	 */
	@ManyToOne
	@JoinColumn(name = "DPR_ID_SUPERIOR")
	@ForeignKey(name = "IR_DPR_DPR")
	public Departamento getDepartamentoSuperior() {
		return this.departamentoSuperior;
	}

	/**
	 * Recupera o email do departamento.
	 * 
	 * @return email do departamento
	 */
	@Column(name = "DPR_EMAIL", nullable = true, length = ConstantesDB.EMAIL)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera o nome do departamento
	 * 
	 * @return nome do departamento
	 */
	@Column(name = "DPR_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Retorna a sigla do departamento.
	 * 
	 * @return sigla do departamento
	 */
	@Column(name = "DPR_SIGLA", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_SIGLA)
	@NaturalId(mutable = true)
	public String getSigla() {
		return this.sigla;
	}

	/**
	 * Recupera os usuários do departamento.
	 * 
	 * @return usuários do departamento
	 */
	@OneToMany(targetEntity = Usuario.class, mappedBy = "departamento")
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Atribui atividades
	 * 
	 * @param atividades o valor a ajustar em atividades
	 */
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	/**
	 * Atribui os departamentos filhos diretos do departamento.
	 * 
	 * @param departamentosFilhos departamentos filhos diretos do departamento
	 */
	public void setDepartamentosFilhos(List<Departamento> departamentosFilhos) {
		this.departamentosFilhos = departamentosFilhos;
	}

	/**
	 * Atribui o departamento superior do departamento.
	 * 
	 * @param departamentoSuperior departamento superior do departamento
	 */
	public void setDepartamentoSuperior(Departamento departamentoSuperior) {
		this.departamentoSuperior = departamentoSuperior;
	}


	/**
	 * Atribui o email do departamento.
	 * 
	 * @param email email do departamento
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Atribui o nome do departamento
	 * 
	 * @param nome nome do departamento
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui a sigla do departamento.
	 * 
	 * @param sigla sigla do departamento
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}


	/**
	 * Atribui os usuários do departamento.
	 * 
	 * @param usuarios usuários do departamento
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
