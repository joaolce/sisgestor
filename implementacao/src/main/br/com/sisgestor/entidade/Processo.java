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
 * Classe para representar um Processo
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PRO_PROCESSO")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@org.hibernate.annotations.Table(appliesTo = "PRO_PROCESSO")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "PRO_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "PRO_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "PRO_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO)),
		@AttributeOverride(name = "top", column = @Column(name = "PRO_TOP", nullable = true)),
		@AttributeOverride(name = "left", column = @Column(name = "PRO_LEFT", nullable = true))})
public class Processo extends BaseWorkflowDesenhavel {

	private List<Atividade> atividades;
	private Workflow workflow;
	private List<TransacaoProcesso> transacoesAnteriores;
	private List<TransacaoProcesso> transacoesPosteriores;

	/**
	 * Recupera as atividades do workflow.
	 * 
	 * @return atividades do workflow
	 */
	@OneToMany(targetEntity = Atividade.class, mappedBy = "processo", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	/**
	 * Recupera as transações anteriores diretas deste processo.
	 * 
	 * @return transações anteriores diretas deste processo
	 */
	@OneToMany(targetEntity = TransacaoProcesso.class, mappedBy = "posterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoProcesso> getTransacoesAnteriores() {
		return this.transacoesAnteriores;
	}

	/**
	 * Recupera as transações posterioes diretas deste processo.
	 * 
	 * @return transações posterioes diretas deste processo
	 */
	@OneToMany(targetEntity = TransacaoProcesso.class, mappedBy = "anterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoProcesso> getTransacoesPosteriores() {
		return this.transacoesPosteriores;
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
	 * Atribui as transações anteriores diretas deste processo.
	 * 
	 * @param transacoesAnteriores transações anteriores diretas deste processo
	 */
	public void setTransacoesAnteriores(List<TransacaoProcesso> transacoesAnteriores) {
		this.transacoesAnteriores = transacoesAnteriores;
	}

	/**
	 * Atribui as transações posterioes diretas deste processo.
	 * 
	 * @param transacoesPosteriores transações posterioes diretas deste processo
	 */
	public void setTransacoesPosteriores(List<TransacaoProcesso> transacoesPosteriores) {
		this.transacoesPosteriores = transacoesPosteriores;
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
