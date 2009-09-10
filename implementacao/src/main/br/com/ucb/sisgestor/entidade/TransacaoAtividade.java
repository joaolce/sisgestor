/*
 * Projeto: sisgestor
 * Cria��o: 03/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que mapeia uma transa��o de {@link Atividade}.
 * 
 * @author Jo�o L�cio
 * @since 03/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TRA_TRANSACAO_ATIVIDADE")
@org.hibernate.annotations.Table(appliesTo = "TRA_TRANSACAO_ATIVIDADE")
@AttributeOverride(name = "id", column = @Column(name = "TRA_ID", nullable = false))
public class TransacaoAtividade extends ObjetoPersistente {

	private Atividade	anterior;
	private Atividade	posterior;

	/**
	 * Recupera a {@link Atividade} anterior a transa��o.
	 * 
	 * @return {@link Atividade} anterior a transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "ATI_ID_ANTERIOR", nullable = false)
	@ForeignKey(name = "IR_ATI_TRA1")
	public Atividade getAnterior() {
		return this.anterior;
	}

	/**
	 * Recupera a {@link Atividade} posterior a transa��o.
	 * 
	 * @return {@link Atividade} posterior a transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "ATI_ID_POSTERIOR", nullable = false)
	@ForeignKey(name = "IR_ATI_TRA2")
	public Atividade getPosterior() {
		return this.posterior;
	}

	/**
	 * Atribui a {@link Atividade} anterior a transa��o.
	 * 
	 * @param anterior {@link Atividade} anterior a transa��o
	 */
	public void setAnterior(Atividade anterior) {
		this.anterior = anterior;
	}

	/**
	 * Atribui a {@link Atividade} posterior a transa��o.
	 * 
	 * @param posterior {@link Atividade} posterior a transa��o
	 */
	public void setPosterior(Atividade posterior) {
		this.posterior = posterior;
	}
}
