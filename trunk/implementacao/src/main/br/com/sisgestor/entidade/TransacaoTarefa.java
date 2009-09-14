/*
 * Projeto: sisgestor
 * Cria��o: 03/03/2009 por Jo�o L�cio
 */
package br.com.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa uma transa��o de uma {@link Tarefa}.
 * 
 * @author Jo�o L�cio
 * @since 03/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TRT_TRANSACAO_TAREFA")
@org.hibernate.annotations.Table(appliesTo = "TRT_TRANSACAO_TAREFA")
@AttributeOverride(name = "id", column = @Column(name = "TRT_ID", nullable = false))
public class TransacaoTarefa extends ObjetoPersistente {

	private Tarefa	anterior;
	private Tarefa	posterior;

	/**
	 * Recupera a {@link Tarefa} anterior a transa��o.
	 * 
	 * @return {@link Tarefa} anterior a transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "TAR_ID_ANTERIOR", nullable = false)
	@ForeignKey(name = "IR_TAR_TRT1")
	public Tarefa getAnterior() {
		return this.anterior;
	}

	/**
	 * Recupera a {@link Tarefa} posterior a transa��o.
	 * 
	 * @return {@link Tarefa} posterior a transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "TAR_ID_POSTERIOR", nullable = false)
	@ForeignKey(name = "IR_TAR_TRT2")
	public Tarefa getPosterior() {
		return this.posterior;
	}

	/**
	 * Atribui a {@link Tarefa} anterior a transa��o.
	 * 
	 * @param anterior {@link Tarefa} anterior a transa��o
	 */
	public void setAnterior(Tarefa anterior) {
		this.anterior = anterior;
	}

	/**
	 * Atribui a {@link Tarefa} posterior a transa��o.
	 * 
	 * @param posterior {@link Tarefa} posterior a transa��o
	 */
	public void setPosterior(Tarefa posterior) {
		this.posterior = posterior;
	}
}
