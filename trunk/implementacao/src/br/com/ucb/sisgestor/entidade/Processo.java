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
		@AttributeOverride(name = "descricao", column = @Column(name = "PRO_DESCRICAO", nullable = true, length = ConstantesDB.DESCRICAO))})
public class Processo extends BaseWorkflow {

	private Workflow			workflow;
	private List<Atividade>	atividades;

	/**
	 * Recupera o valor de atividades
	 * 
	 * @return atividades
	 */
	@OneToMany(targetEntity = Atividade.class, mappedBy = "processo")
	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	/**
	 * Recupera o valor de workflow
	 * 
	 * @return workflow
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_PRO")
	public Workflow getWorkflow() {
		return this.workflow;
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
	 * Atribui workflow
	 * 
	 * @param workflow o valor a ajustar em workflow
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
