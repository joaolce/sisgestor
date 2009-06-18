/*
 * Projeto: sisgestor
 * Cria��o: 28/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que mapeia uma transa��o do {@link Processo}.
 * 
 * @author Thiago
 * @since 28/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TRP_TRANSACAO_PROCESSO")
@org.hibernate.annotations.Table(appliesTo = "TRP_TRANSACAO_PROCESSO")
@AttributeOverride(name = "id", column = @Column(name = "TRP_ID", nullable = false))
public class TransacaoProcesso extends ObjetoPersistente {

	private Processo	anterior;
	private Processo	posterior;

	/**
	 * Recupera o processo anterior da transa��o.
	 * 
	 * @return processoAnterior processo anterior da transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "PRO_ID_ANTERIOR", nullable = false)
	@ForeignKey(name = "IR_PRO_TRP1")
	public Processo getAnterior() {
		return this.anterior;
	}

	/**
	 * Recupera o processo posterior da transa��o.
	 * 
	 * @return processoPosterior processo posterior da transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "PRO_ID_POSTERIOR", nullable = false)
	@ForeignKey(name = "IR_PRO_TRP2")
	public Processo getPosterior() {
		return this.posterior;
	}

	/**
	 * Atribui o processo anterior da transa��o.
	 * 
	 * @param anterior processo anterior da transa��o
	 */
	public void setAnterior(Processo anterior) {
		this.anterior = anterior;
	}

	/**
	 * Atribui o processo posterior da transa��o.
	 * 
	 * @param posterior processo posterior da transa��o
	 */
	public void setPosterior(Processo posterior) {
		this.posterior = posterior;
	}
}
