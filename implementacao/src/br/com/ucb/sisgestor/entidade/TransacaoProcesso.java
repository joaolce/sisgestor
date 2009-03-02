/*
 * Projeto: sisgestor
 * Cria��o: 28/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.ForeignKey;


/**
 * Classe para mapeamento das transa��es do processo
 * 
 * @author Thiago
 * @since 28/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TRA_PROCESSO")
@org.hibernate.annotations.Table(appliesTo = "TRA_PROCESSO")
@AttributeOverride(name = "id", column = @Column(name = "TRA_ID", nullable = false))
public class TransacaoProcesso extends ObjetoPersistente {

	private Processo	processo;
	private Processo	processoAnterior;
	private Processo	processoPosterior;

	/**
	 * Recupera o processo desta transa��o
	 * 
	 * @return processo desta transa��o
	 */
	@ManyToOne
	@JoinColumn(name = "PRO_ID", nullable = false)
	@ForeignKey(name = "IR_PRO_TRA")
	public Processo getProcesso() {
		return this.processo;
	}

	/**
	 * Recupera o processo anterior da transa��o
	 * 
	 * @return processoAnterior processo anterior da transa��o
	 */
	@OneToOne
	@JoinColumn(name = "PRO_ANT_ID", nullable = true)
	@ForeignKey(name = "FK_PRO_ANT")
	public Processo getProcessoAnterior() {
		return this.processoAnterior;
	}

	/**
	 * Recupera o processo posterior da transa��o
	 * 
	 * @return processoPosterior processo posterior da transa��o
	 */
	@OneToOne
	@JoinColumn(name = "PRO_POS_ID", nullable = true)
	@ForeignKey(name = "FK_PRO_POS")
	public Processo getProcessoPosterior() {
		return this.processoPosterior;
	}

	/**
	 * Atribui processo referente a esta transa��o
	 * 
	 * @param processo o processo referente a esta transa��o
	 */
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}


	/**
	 * Atribui processoAnterior
	 * 
	 * @param processoAnterior o valor a ajustar em processoAnterior
	 */
	public void setProcessoAnterior(Processo processoAnterior) {
		this.processoAnterior = processoAnterior;
	}


	/**
	 * Atribui processoPosterior
	 * 
	 * @param processoPosterior o valor a ajustar em processoPosterior
	 */
	public void setProcessoPosterior(Processo processoPosterior) {
		this.processoPosterior = processoPosterior;
	}
}
