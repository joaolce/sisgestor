/*
 * Projeto: sisgestor
 * Criação: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;

/**
 * Classe para representar um campo utilizado no workflow.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CAM_CAMPO")
@org.hibernate.annotations.Table(appliesTo = "CAM_CAMPO")
@AttributeOverride(name = "id", column = @Column(name = "CAM_ID", nullable = false))
public class Campo extends ObjetoPersistente {

	private String				nome;
	private Tipo				tipo;
	private List<Workflow>	workflows;

	/**
	 * Recupera o nome do campo.
	 * 
	 * @return nome do campo
	 */
	@Column(name = "CAM_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera o tipo do campo.
	 * 
	 * @return tipo do campo
	 */
	@ManyToOne
	@JoinColumn(name = "TIP_ID", nullable = false)
	@ForeignKey(name = "IR_TIP_CAM")
	public Tipo getTipo() {
		return this.tipo;
	}

	/**
	 * Recupera o valor de workflows
	 * 
	 * @return workflows
	 */
	@ManyToMany(targetEntity = Workflow.class, mappedBy = "campos")
	@ForeignKey(name = "IR_CAM_WCP")
	public List<Workflow> getWorkflows() {
		return this.workflows;
	}


	/**
	 * Atribui o nome do campo.
	 * 
	 * @param nome nome do campo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui o tipo do campo.
	 * 
	 * @param tipo tipo do campo
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	/**
	 * Atribui workflows pertencentes ao campo
	 * 
	 * @param workflows workflows pertencentes ao campo
	 */
	public void setWorkflows(List<Workflow> workflows) {
		this.workflows = workflows;
	}
}
