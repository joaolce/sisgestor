/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Classe para representar um workflow.
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
		@AttributeOverride(name = "descricao", column = @Column(name = "WOR_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO))})
public class Workflow extends BaseWorkflow {

	private Boolean			ativo;
	private Timestamp			dataHoraExclusao;
	private List<Processo>	processos;

	/**
	 * Recupera o indicador se o workflow está ativo.
	 * 
	 * @return indicador se o workflow está ativo
	 */
	@Column(name = "WOR_ATIVO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera a data/hora de exclusão do workflow.
	 * 
	 * @return data/hora de exclusão do workflow
	 */
	@Column(name = "WOR_DATA_HORA_EXCLUSAO", nullable = true)
	public Timestamp getDataHoraExclusao() {
		return this.dataHoraExclusao;
	}

	/**
	 * Recupera os processos do workflow.
	 * 
	 * @return processos do workflow
	 */
	@OneToMany(targetEntity = Processo.class, mappedBy = "workflow")
	@Cascade(CascadeType.DELETE)
	public List<Processo> getProcessos() {
		return this.processos;
	}

	/**
	 * Atribui o indicador se o workflow está ativo.
	 * 
	 * @param ativo indicador se o workflow está ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui a data/hora de exclusão do workflow.
	 * 
	 * @param dataHoraExclusao data/hora de exclusão do workflow
	 */
	public void setDataHoraExclusao(Timestamp dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
	}


	/**
	 * Atribui processos do workflow.
	 * 
	 * @param processos processos do workflow
	 */
	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}
}
