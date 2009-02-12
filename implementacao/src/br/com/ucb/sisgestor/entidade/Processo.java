/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe para representar um Processo
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PRO_PROCESSO")
@org.hibernate.annotations.Table(appliesTo = "PRO_PROCESSO")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "PRO_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "PRO_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "PRO_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO))})
public class Processo extends BaseWorkflow {

	private Workflow			workflow;
	private List<Atividade>	atividades;

	/**
	 * Recupera as atividades do workflow.
	 * 
	 * @return atividades do workflow
	 */
	@OneToMany(targetEntity = Atividade.class, mappedBy = "processo")
	@Cascade(CascadeType.DELETE)
	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	/**
	 * Recupera o workflow do processo.
	 * 
	 * @return workflow do processo
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_PRO")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui as atividades do workflow.
	 * 
	 * @param atividades atividades do workflow
	 */
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	/**
	 * Atribui o workflow do processo.
	 * 
	 * @param workflow workflow do processo
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
