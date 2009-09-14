/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
		@AttributeOverride(name = "descricao", column = @Column(name = "ATI_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO)),
		@AttributeOverride(name = "top", column = @Column(name = "ATI_TOP", nullable = true)),
		@AttributeOverride(name = "left", column = @Column(name = "ATI_LEFT", nullable = true))})
public class Atividade extends BaseWorkflowDesenhavel {

	private Departamento					departamento;
	private Processo						processo;
	private List<Tarefa>					tarefas;
	private List<TransacaoAtividade>	transacoesAnteriores;
	private List<TransacaoAtividade>	transacoesPosteriores;

	/**
	 * Recupera o departamento da atividade.
	 * 
	 * @return departamento da atividade
	 */
	@ManyToOne
	@JoinColumn(name = "DRP_ID", nullable = false)
	@ForeignKey(name = "IR_DPR_ATI")
	public Departamento getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera o processo da atividade.
	 * 
	 * @return processo da atividade
	 */
	@ManyToOne
	@JoinColumn(name = "PRO_ID", nullable = false)
	@ForeignKey(name = "IR_PRO_ATI")
	public Processo getProcesso() {
		return this.processo;
	}

	/**
	 * Recupera as tarefas da atividade.
	 * 
	 * @return tarefas da atividade
	 */
	@OneToMany(targetEntity = Tarefa.class, mappedBy = "atividade", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Tarefa> getTarefas() {
		return this.tarefas;
	}

	/**
	 * Recupera as transações anteriores diretas da atividade.
	 * 
	 * @return transações anteriores diretas da atividade
	 */
	@OneToMany(targetEntity = TransacaoAtividade.class, mappedBy = "posterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoAtividade> getTransacoesAnteriores() {
		return this.transacoesAnteriores;
	}

	/**
	 * Recupera as transações posterioes diretas da atividade.
	 * 
	 * @return transações posterioes diretas da atividade
	 */
	@OneToMany(targetEntity = TransacaoAtividade.class, mappedBy = "anterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoAtividade> getTransacoesPosteriores() {
		return this.transacoesPosteriores;
	}

	/**
	 * Atribui o departamento da atividade.
	 * 
	 * @param departamento departamento da atividade
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui o processo da atividade.
	 * 
	 * @param processo processo da atividade
	 */
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	/**
	 * Atribui as tarefas da atividade.
	 * 
	 * @param tarefas tarefas da atividade
	 */
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	/**
	 * Atribui as transações anteriores diretas da atividade.
	 * 
	 * @param transacoesAnteriores transações anteriores diretas da atividade
	 */
	public void setTransacoesAnteriores(List<TransacaoAtividade> transacoesAnteriores) {
		this.transacoesAnteriores = transacoesAnteriores;
	}

	/**
	 * Atribui as transações posterioes diretas da atividade.
	 * 
	 * @param transacoesPosteriores transações posterioes diretas da atividade
	 */
	public void setTransacoesPosteriores(List<TransacaoAtividade> transacoesPosteriores) {
		this.transacoesPosteriores = transacoesPosteriores;
	}
}
