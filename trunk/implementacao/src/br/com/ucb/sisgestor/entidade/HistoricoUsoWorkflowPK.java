/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Chave prim�ria da classe {@link HistoricoUsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
@Embeddable
public class HistoricoUsoWorkflowPK implements Serializable {

	private UsoWorkflow	usoWorkflow;
	private Timestamp		dataHora;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		HistoricoUsoWorkflowPK pk = (HistoricoUsoWorkflowPK) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.usoWorkflow, pk.usoWorkflow);
		equalsBuilder.append(this.dataHora, pk.dataHora);
		return equalsBuilder.isEquals();
	}

	/**
	 * Recupera a data/hora que houve a atualiza��o.
	 * 
	 * @return data/hora que houve a atualiza��o
	 */
	@Column(name = "HUWR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utiliza��o do {@link Workflow} do hist�rico.
	 * 
	 * @return utiliza��o do {@link Workflow} do hist�rico
	 */
	@ManyToOne
	@JoinColumn(name = "UWR_ID", nullable = false)
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(this.usoWorkflow);
		hashCodeBuilder.append(this.dataHora);
		return hashCodeBuilder.toHashCode();
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
