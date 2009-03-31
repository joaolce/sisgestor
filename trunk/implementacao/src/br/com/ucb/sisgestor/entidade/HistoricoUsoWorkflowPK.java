/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
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
 * Chave primária da classe {@link HistoricoUsoWorkflow}.
 * 
 * @author João Lúcio
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
	 * Recupera a data/hora que houve a atualização.
	 * 
	 * @return data/hora que houve a atualização
	 */
	@Column(name = "HUWR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utilização do {@link Workflow} do histórico.
	 * 
	 * @return utilização do {@link Workflow} do histórico
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
