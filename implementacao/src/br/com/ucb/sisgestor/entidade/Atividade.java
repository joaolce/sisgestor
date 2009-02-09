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
 * Classe que representa uma Atividade.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "ATI_ATIVIDADE")
@org.hibernate.annotations.Table(appliesTo = "ATI_ATIVIDADE")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "ATI_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "ATI_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "ATI_DESCRICAO", nullable = true, length = ConstantesDB.DESCRICAO))})
public class Atividade extends BaseWorkflow {

	private Processo		processo;
	private List<Tarefa>	tarefas;
	private Departamento	departamento;

	/**
	 * Recupera o valor de departamento
	 * 
	 * @return departamento
	 */
	@ManyToOne
	@JoinColumn(name = "DRP_ID", nullable = false)
	@ForeignKey(name = "IR_DPR_ATI")
	public Departamento getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o valor de processo
	 * 
	 * @return processo
	 */
	@ManyToOne
	@JoinColumn(name = "PRO_ID", nullable = false)
	@ForeignKey(name = "IR_PRO_ATI")
	public Processo getProcesso() {
		return this.processo;
	}

	/**
	 * Recupera o valor de tarefas
	 * 
	 * @return tarefas
	 */
	@OneToMany(targetEntity = Tarefa.class, mappedBy = "atividade")
	public List<Tarefa> getTarefas() {
		return this.tarefas;
	}

	/**
	 * Atribui departamento
	 * 
	 * @param departamento o valor a ajustar em departamento
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui processo
	 * 
	 * @param processo o valor a ajustar em processo
	 */
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	/**
	 * Atribui tarefas
	 * 
	 * @param tarefas o valor a ajustar em tarefas
	 */
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
}
