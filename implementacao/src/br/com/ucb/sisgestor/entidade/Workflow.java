/*
 * Projeto: sisgestor
 * Cria��o: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

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
	private List<Campo>		campos;

	/**
	 * Recupera o indicador se o workflow est� ativo.
	 * 
	 * @return indicador se o workflow est� ativo
	 */
	@Column(name = "WOR_ATIVO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera o indicador se o workflow est� ativo.
	 * 
	 * @return indicador se o workflow est� ativo
	 */
	@ManyToMany(targetEntity = Campo.class)
	@JoinTable(name = "WCP_WORKFLOW_CAMPO", joinColumns = @JoinColumn(name = "WOR_ID", referencedColumnName = "WOR_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "CAM_ID", referencedColumnName = "CAM_ID", nullable = false))
	@ForeignKey(name = "IR_WOR_WCP")
	public List<Campo> getCampos() {
		return this.campos;
	}

	/**
	 * Recupera a data/hora de exclus�o do workflow.
	 * 
	 * @return data/hora de exclus�o do workflow
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
	 * Atribui o indicador se o workflow est� ativo.
	 * 
	 * @param ativo indicador se o workflow est� ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


	/**
	 * Atribui campos
	 * 
	 * @param campos o valor a ajustar em campos
	 */
	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}


	/**
	 * Atribui a data/hora de exclus�o do workflow.
	 * 
	 * @param dataHoraExclusao data/hora de exclus�o do workflow
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
