/*
 * Projeto: sisgestor
 * Cria��o: 04/02/2009 por Thiago
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.FetchType;
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
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@org.hibernate.annotations.Table(appliesTo = "WOR_WORKFLOW")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "WOR_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "WOR_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "WOR_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO))})
public class Workflow extends BaseWorkflow {

	private Boolean ativo;
	private Timestamp dataHoraExclusao;
	private List<Processo> processos;
	private List<Campo> campos;
	private List<UsoWorkflow> usos;

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
	 * Recupera os campos do workflow.
	 * 
	 * @return campos do workflow
	 */
	@OneToMany(targetEntity = Campo.class, mappedBy = "workflow", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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
	@OneToMany(targetEntity = Processo.class, mappedBy = "workflow", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Processo> getProcessos() {
		return this.processos;
	}

	/**
	 * Recupera os usos do workflow.
	 * 
	 * @return usos do workflow
	 */
	@OneToMany(targetEntity = UsoWorkflow.class, mappedBy = "workflow")
	public List<UsoWorkflow> getUsos() {
		return this.usos;
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
	 * Atribui os campos do workflow.
	 * 
	 * @param campos campos do workflow
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
	 * Atribui os processos do workflow.
	 * 
	 * @param processos processos do workflow
	 */
	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	/**
	 * Atribui os usos do workflow.
	 * 
	 * @param usos usos do workflow
	 */
	public void setUsos(List<UsoWorkflow> usos) {
		this.usos = usos;
	}
}
