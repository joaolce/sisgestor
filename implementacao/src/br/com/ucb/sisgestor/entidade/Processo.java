/*
 * Projeto: sisgestor
 * Cria��o: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe para representar um Processo
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PRO_PROCESSO")
@org.hibernate.annotations.Table(appliesTo = "PRO_PROCESSO")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "PRO_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "PRO_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "PRO_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO))})
public class Processo extends BaseWorkflow {

	private List<Atividade>				atividades;
	private Workflow						workflow;
	private Boolean						processoInicial;
	private Boolean						processoFinal;
	private List<TransacaoProcesso>	transacoesAnteriores;
	private List<TransacaoProcesso>	transacoesPosteriores;

	/**
	 * Recupera as atividades do workflow.
	 * 
	 * @return atividades do workflow
	 */
	@OneToMany(targetEntity = Atividade.class, mappedBy = "processo")
	@Cascade(CascadeType.DELETE)
	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	/**
	 * Recupera o indicador se o processo � final
	 * 
	 * @return processoFinal indicador se o processo � final
	 */
	@Column(name = "PRO_FINAL", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getProcessoFinal() {
		return this.processoFinal;
	}

	/**
	 * Recupera o indicador se o processo � inicial
	 * 
	 * @return processoInicial indicador se o processo � inicial
	 */
	@Column(name = "PRO_INICIAL", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getProcessoInicial() {
		return this.processoInicial;
	}

	/**
	 * Recupera as transa��es anteriores diretas deste processo.
	 * 
	 * @return transa��es anteriores diretas deste processo
	 */
	@OneToMany(targetEntity = TransacaoProcesso.class, mappedBy = "posterior")
	@Cascade(CascadeType.DELETE)
	public List<TransacaoProcesso> getTransacoesAnteriores() {
		return this.transacoesAnteriores;
	}

	/**
	 * Recupera as transa��es posterioes diretas deste processo.
	 * 
	 * @return transa��es posterioes diretas deste processo
	 */
	@OneToMany(targetEntity = TransacaoProcesso.class, mappedBy = "anterior")
	@Cascade(CascadeType.DELETE)
	public List<TransacaoProcesso> getTransacoesPosteriores() {
		return this.transacoesPosteriores;
	}

	/**
	 * Recupera o workflow do processo.
	 * 
	 * @return workflow do processo
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_PRO")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui as atividades do workflow.
	 * 
	 * @param atividades atividades do workflow
	 */
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	/**
	 * Indica se este processo � final
	 * 
	 * @param processoFinal Indicador para estabelecer se este processo � final
	 */
	public void setProcessoFinal(Boolean processoFinal) {
		this.processoFinal = processoFinal;
	}

	/**
	 * Indica se este processo � inicial
	 * 
	 * @param processoInicial Indicador para estabelecer se este processo � inicial
	 */
	public void setProcessoInicial(Boolean processoInicial) {
		this.processoInicial = processoInicial;
	}

	/**
	 * Atribui as transa��es anteriores diretas deste processo.
	 * 
	 * @param transacoesAnteriores transa��es anteriores diretas deste processo
	 */
	public void setTransacoesAnteriores(List<TransacaoProcesso> transacoesAnteriores) {
		this.transacoesAnteriores = transacoesAnteriores;
	}

	/**
	 * Atribui as transa��es posterioes diretas deste processo.
	 * 
	 * @param transacoesPosteriores transa��es posterioes diretas deste processo
	 */
	public void setTransacoesPosteriores(List<TransacaoProcesso> transacoesPosteriores) {
		this.transacoesPosteriores = transacoesPosteriores;
	}

	/**
	 * Atribui o workflow do processo.
	 * 
	 * @param workflow workflow do processo
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}