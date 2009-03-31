/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
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
 * Hist�rico de uso do {@link Workflow}.
 * 
 * @author Jo�o L�cio
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
	 * Recupera a data/hora que houve a atualiza��o.
	 * 
	 * @return data/hora que houve a atualiza��o
	 */
	@Id
	@Column(name = "HUWR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utiliza��o do {@link Workflow} do hist�rico.
	 * 
	 * @return utiliza��o do {@link Workflow} do hist�rico
	 */
	@Id
	@ManyToOne
	@JoinColumn(name = "UWR_ID", nullable = false)
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Atribui a data/hora que houve a atualiza��o.
	 * 
	 * @param dataHora data/hora que houve a atualiza��o
	 */
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Atribui utiliza��o do {@link Workflow} do hist�rico.
	 * 
	 * @param usoWorkflow utiliza��o do {@link Workflow} do hist�rico
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}
}
