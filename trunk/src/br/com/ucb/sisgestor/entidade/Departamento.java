/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;


/**
 * Classe que representa um departamento no sistema
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "DPR_DEPARTAMENTO")
@org.hibernate.annotations.Table(appliesTo = "DPR_DEPARTAMENTO")
@SequenceGenerator(name = "SEQ_DPR", sequenceName = "SEQ_DPR", allocationSize = 10)
@AttributeOverride(name = "id", column = @Column(name = "DPR_ID", nullable = false))
public class Departamento extends ObjetoPersistente {

	private String	email;
	private String	sigla;

	/**
	 * Recupera o email do departamento
	 * 
	 * @return email do departamento
	 */
	@Column(name = "DPR_DS_EMAIL", nullable = true, length = 30)
	public String getEmail() {
		return email;
	}

	/**
	 * Retorna a sigla do departamento
	 * 
	 * @return sigla do departamento
	 */
	@Column(name = "DPR_SL", nullable = false, columnDefinition = "CHAR(10)")
	public String getSigla() {
		return sigla;
	}

	/**
	 * Atribui o email do departamento
	 * 
	 * @param email email do departamento
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Atribui a sigla do departamento
	 * 
	 * @param sigla sigla do departamento
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
