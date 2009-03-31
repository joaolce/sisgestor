/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Histórico de uso do {@link Workflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@IdClass(HistoricoUsoWorkflowPK.class)
@javax.persistence.Entity
@javax.persistence.Table(name = "HUWR_USO_WORKFLOW")
@org.hibernate.annotations.Table(appliesTo = "HUWR_USO_WORKFLOW")
public class HistoricoUsoWorkflow implements Serializable {

	private UsoWorkflow	usoWorkflow;
	private Timestamp		dataHora;

	/**
	 * Recupera a data/hora que houve a atualização.
	 * 
	 * @return data/hora que houve a atualização
	 */
	@Id
	@Column(name = "HUWR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utilização do {@link Workflow} do histórico.
	 * 
	 * @return utilização do {@link Workflow} do histórico
	 */
	@Id
	@ManyToOne
	@JoinColumn(name = "UWR_ID", nullable = false)
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Atribui a data/hora que houve a atualização.
	 * 
	 * @param dataHora data/hora que houve a atualização
	 */
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Atribui utilização do {@link Workflow} do histórico.
	 * 
	 * @param usoWorkflow utilização do {@link Workflow} do histórico
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}
}
