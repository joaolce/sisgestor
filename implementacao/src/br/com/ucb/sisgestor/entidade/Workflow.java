/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.OneToMany;


/**
 * Classe para representar um workflow
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "WOR_WORKFLOW")
@org.hibernate.annotations.Table(appliesTo = "WOR_WORKFLOW")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "WOR_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "WOR_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "WOR_DESCRICAO", nullable = true, length = ConstantesDB.DESCRICAO))})
public class Workflow extends BaseWorkflow {

	private Boolean			ativo;
	private List<Processo>	processos;

	/**
	 * Recupera o valor de ativo
	 * 
	 * @return ativo
	 */
	@Column(name = "WOR_ATIVO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera o valor de processos
	 * 
	 * @return processos
	 */
	@OneToMany(targetEntity = Processo.class, mappedBy = "workflow")
	public List<Processo> getProcessos() {
		return this.processos;
	}

	/**
	 * Atribui ativo
	 * 
	 * @param ativo o valor a ajustar em ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui processos
	 * 
	 * @param processos o valor a ajustar em processos
	 */
	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

}
