/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa um uso do workflow.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UWR_USO_WORKFLOW")
@org.hibernate.annotations.Table(appliesTo = "UWR_USO_WORKFLOW")
@AttributeOverride(name = "id", column = @Column(name = "UWR_ID", nullable = false))
public class UsoWorkflow extends ObjetoPersistente {

	private Workflow							workflow;
	private Timestamp							dataHoraInicio;
	private List<HistoricoUsoWorkflow>	historico;
	private List<Anexo>						anexos;

	/**
	 * Recupera os anexos da utiliza��o do {@link Workflow}.
	 * 
	 * @return anexos da utiliza��o do {@link Workflow}
	 */
	@OneToMany(targetEntity = Anexo.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	public List<Anexo> getAnexos() {
		return this.anexos;
	}

	/**
	 * Recupera a data/hora de inicio de uso do {@link Workflow}.
	 * 
	 * @return data/hora de inicio de uso do {@link Workflow}
	 */
	@Column(name = "UWR_DATA_HORA_INICIO", nullable = false)
	public Timestamp getDataHoraInicio() {
		return this.dataHoraInicio;
	}

	/**
	 * Recupera o hist�rico do uso do {@link Workflow}.
	 * 
	 * @return hist�rico do uso do {@link Workflow}
	 */
	@OneToMany(targetEntity = HistoricoUsoWorkflow.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	public List<HistoricoUsoWorkflow> getHistorico() {
		return this.historico;
	}

	/**
	 * Recupera o {@link Workflow} que est� sendo usado.
	 * 
	 * @return {@link Workflow} que est� sendo usado
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_UWR")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui os anexos da utiliza��o do {@link Workflow}.
	 * 
	 * @param anexos anexos da utiliza��o do {@link Workflow}
	 */
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Atribui a data/hora de inicio de uso do {@link Workflow}.
	 * 
	 * @param dataHoraInicio data/hora de inicio de uso do {@link Workflow}
	 */
	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	/**
	 * Atribui o hist�rico do uso do {@link Workflow}.
	 * 
	 * @param historico hist�rico do uso do {@link Workflow}
	 */
	public void setHistorico(List<HistoricoUsoWorkflow> historico) {
		this.historico = historico;
	}

	/**
	 * Atribui o {@link Workflow} que est� sendo usado.
	 * 
	 * @param workflow {@link Workflow} que est� sendo usado
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
