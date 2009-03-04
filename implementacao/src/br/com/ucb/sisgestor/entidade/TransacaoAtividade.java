/*
 * Projeto: sisgestor
 * Criação: 03/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que mapeia uma transação de {@link Atividade}.
 * 
 * @author João Lúcio
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
	 * Recupera a {@link Atividade} anterior a transação.
	 * 
	 * @return {@link Atividade} anterior a transação
	 */
	@ManyToOne
	@JoinColumn(name = "ATI_ID_ANTERIOR", nullable = false)
	@ForeignKey(name = "IR_ATI_TRA1")
	public Atividade getAnterior() {
		return this.anterior;
	}

	/**
	 * Recupera a {@link Atividade} posterior a transação.
	 * 
	 * @return {@link Atividade} posterior a transação
	 */
	@ManyToOne
	@JoinColumn(name = "ATI_ID_POSTERIOR", nullable = false)
	@ForeignKey(name = "IR_ATI_TRA2")
	public Atividade getPosterior() {
		return this.posterior;
	}

	/**
	 * Atribui a {@link Atividade} anterior a transação.
	 * 
	 * @param anterior {@link Atividade} anterior a transação
	 */
	public void setAnterior(Atividade anterior) {
		this.anterior = anterior;
	}

	/**
	 * Atribui a {@link Atividade} posterior a transação.
	 * 
	 * @param posterior {@link Atividade} posterior a transação
	 */
	public void setPosterior(Atividade posterior) {
		this.posterior = posterior;
	}
}
