/*
 * Projeto: sisgestor
 * Cria��o: 22/04/2009 por Thiago Pires
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
 * Chave prim�ria da classe {@link HistoricoUsuario}.
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
	 * Recupera a data/hora que houve a atualiza��o.
	 * 
	 * @return data/hora que houve a atualiza��o
	 */
	@Column(name = "HUUR_DATA_HORA", nullable = false)
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utiliza��o do {@link Usuario} do hist�rico.
	 * 
	 * @return utiliza��o do {@link Usuario} do hist�rico
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
	 * Atribui a data/hora que houve a atualiza��o.
	 * 
	 * @param dataHora data/hora que houve a atualiza��o
	 */
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Atribui utiliza��o do {@link Usuario} do hist�rico.
	 * 
	 * @param usuario utiliza��o do {@link Usuario} do hist�rico
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
