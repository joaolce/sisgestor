/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa um uso do workflow.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UWR_USO_WORKFLOW")
@org.hibernate.annotations.Table(appliesTo = "UWR_USO_WORKFLOW")
@AttributeOverride(name = "id", column = @Column(name = "UWR_ID", nullable = false))
public class UsoWorkflow extends ObjetoPersistente {

	private Workflow							workflow;
	private Timestamp							dataHoraInicio;
	private Timestamp							dataHoraFim;
	private List<HistoricoUsoWorkflow>	historico;
	private Tarefa								tarefa;
	private List<Anexo>						anexos;

	/**
	 * Recupera os anexos da utiliza��o do {@link Workflow}.
	 * 
	 * @return anexos da utiliza��o do {@link Workflow}
	 */
	@OneToMany(targetEntity = Anexo.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	public List<Anexo> getAnexos() {
		return this.anexos;
	}

	/**
	 * Recupera a data/hora de fim de uso da {@link Tarefa}.
	 * 
	 * @return data/hora de fim de uso da {@link Tarefa}
	 */
	@Column(name = "UWR_DATA_HORA_FIM", nullable = true)
	public Timestamp getDataHoraFim() {
		return this.dataHoraFim;
	}

	/**
	 * Recupera a data/hora de inicio de uso da {@link Tarefa}.
	 * 
	 * @return data/hora de inicio de uso da {@link Tarefa}
	 */
	@Column(name = "UWR_DATA_HORA_INICIO", nullable = false)
	public Timestamp getDataHoraInicio() {
		return this.dataHoraInicio;
	}

	/**
	 * Recupera o hist�rico do uso do {@link Workflow}.
	 * 
	 * @return hist�rico do uso do {@link Workflow}
	 */
	@OneToMany(targetEntity = HistoricoUsoWorkflow.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	@OrderBy("dataHora DESC")
	public List<HistoricoUsoWorkflow> getHistorico() {
		return this.historico;
	}

	/**
	 * Recupera o n�mero do registro formatado
	 * 
	 * @return n�mero do registro no formato &lt;ano&gt;/&lt;registro&gt;
	 */
	@Transient
	public String getNumeroRegistro() {
		if (this.historico != null) {
			//Recupera o primeiro registo do hist�rico pois � inicializa��o do workflow
			HistoricoUsoWorkflow usoWorkflow = this.historico.get(this.historico.size() - 1);
			Integer ano = DataUtil.getAno(usoWorkflow.getDataHora());
			String registro = Utils.completaComZero(String.valueOf(this.getId()), 6);
			return ano + "/" + registro;
		}
		return "";
	}

	/**
	 * Recupera a {@link Tarefa} que est� sendo executada.
	 * 
	 * @return {@link Tarefa} que est� sendo executada
	 */
	@ManyToOne
	@JoinColumn(name = "TAR_ID", nullable = false)
	@ForeignKey(name = "IR_TAR_UWR")
	public Tarefa getTarefa() {
		return this.tarefa;
	}

	/**
	 * Recupera o {@link Workflow} que est� sendo usado.
	 * 
	 * @return {@link Workflow} que est� sendo usado
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_UWR")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui os anexos da utiliza��o do {@link Workflow}.
	 * 
	 * @param anexos anexos da utiliza��o do {@link Workflow}
	 */
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Atribui a data/hora de fim de uso da {@link Tarefa}.
	 * 
	 * @param dataHoraFim data/hora de fim de uso da {@link Tarefa}
	 */
	public void setDataHoraFim(Timestamp dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	/**
	 * Atribui a data/hora de inicio de uso da {@link Tarefa}.
	 * 
	 * @param dataHoraInicio data/hora de inicio de uso da {@link Tarefa}
	 */
	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	/**
	 * Atribui o hist�rico do uso do {@link Workflow}.
	 * 
	 * @param historico hist�rico do uso do {@link Workflow}
	 */
	public void setHistorico(List<HistoricoUsoWorkflow> historico) {
		this.historico = historico;
	}

	/**
	 * Atribui a {@link Tarefa} que est� sendo executada.
	 * 
	 * @param tarefa {@link Tarefa} que est� sendo executada
	 */
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	/**
	 * Atribui o {@link Workflow} que est� sendo usado.
	 * 
	 * @param workflow {@link Workflow} que est� sendo usado
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
