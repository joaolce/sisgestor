/*
 * Projeto: sisgestor
 * Criação: 22/04/2009 por Thiago Pires
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
import org.hibernate.annotations.ForeignKey;

/**
 * Chave primária da classe {@link HistoricoUsuario}.
 * 
 * @author Thiago Pires
 * @since 22/04/2009
 */
@Embeddable
public class HistoricoUsuarioPK implements Serializable {

	private Usuario	usuario;
	private Timestamp	dataHora;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		HistoricoUsuarioPK pk = (HistoricoUsuarioPK) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.usuario, pk.usuario);
		equalsBuilder.append(this.dataHora, pk.dataHora);
		return equalsBuilder.isEquals();
	}

	/**
	 * Recupera a data/hora que houve a atualização.
	 * 
	 * @return data/hora que houve a atualização
	 */
	@Column(name = "HUUR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utilização do {@link Usuario} do histórico.
	 * 
	 * @return utilização do {@link Usuario} do histórico
	 */
	@ManyToOne
	@JoinColumn(name = "UUR_ID", nullable = false)
	@ForeignKey(name = "IR_UUR_HUUR")
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(this.usuario);
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
	 * Atribui utilização do {@link Usuario} do histórico.
	 * 
	 * @param usuario utilização do {@link Usuario} do histórico
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
