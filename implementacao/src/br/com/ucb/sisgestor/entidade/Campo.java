/*
 * Projeto: sisgestor
 * Criação: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import br.com.ucb.sisgestor.util.hibernate.HibernateUserTypeConstants;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

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
	private TipoCampoEnum	tipo;
	private Workflow			workflow;

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
	@Column(name = "CAM_TIPO", nullable = false)
	@Type(type = HibernateUserTypeConstants.CODIGO_INTEGER, parameters = @Parameter(name = "className", value = HibernateUserTypeConstants.TIPO_CAMPO_ENUM))
	public TipoCampoEnum getTipo() {
		return this.tipo;
	}

	/**
	 * Recupera o workflow do campo.
	 * 
	 * @return workflow do campo
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_CAM")
	public Workflow getWorkflow() {
		return this.workflow;
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
	public void setTipo(TipoCampoEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * Atribui o workflow do campo.
	 * 
	 * @param workflow workflow do campo
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
