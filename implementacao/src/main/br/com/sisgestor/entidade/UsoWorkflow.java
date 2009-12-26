/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.DataUtil;
import br.com.sisgestor.util.Utils;
import br.com.sisgestor.util.constantes.ConstantesDB;
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
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa um uso do workflow.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UWR_USO_WORKFLOW")
@org.hibernate.annotations.Table(appliesTo = "UWR_USO_WORKFLOW")
@AttributeOverride(name = "id", column = @Column(name = "UWR_ID", nullable = false))
public class UsoWorkflow extends ObjetoPersistente {

	private Integer numero;
	private String anotacao;
	private Workflow workflow;
	private Timestamp dataHoraInicio;
	private List<HistoricoUsoWorkflow> historico;
	private Tarefa tarefa;
	private List<Anexo> anexos;
	private List<CampoUsoWorkflow> camposUsados;
	private Boolean usoFinalizado;

	private DataUtil dataUtil = DataUtil.get();

	/**
	 * Recupera os anexos da utilização do {@link Workflow}.
	 * 
	 * @return anexos da utilização do {@link Workflow}
	 */
	@OneToMany(targetEntity = Anexo.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	public List<Anexo> getAnexos() {
		return this.anexos;
	}

	/**
	 * Recupera a anotação do uso
	 * 
	 * @return anotação do uso
	 */
	@Column(name = "UWR_ANOTACAO", nullable = true, length = ConstantesDB.ANOTACAO)
	public String getAnotacao() {
		return this.anotacao;
	}

	/**
	 * Recupera o valor de usos
	 * 
	 * @return usos
	 */
	@OneToMany(targetEntity = CampoUsoWorkflow.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	@ForeignKey(name = "IR_UWR_UCA")
	@Cascade(CascadeType.ALL)
	public List<CampoUsoWorkflow> getCamposUsados() {
		return this.camposUsados;
	}

	/**
	 * Recupera a data/hora de inicio de uso da {@link Tarefa}.
	 * 
	 * @return data/hora de inicio de uso da {@link Tarefa}
	 */
	@Column(name = "UWR_DATA_HORA_INICIO", nullable = true)
	public Timestamp getDataHoraInicio() {
		return this.dataHoraInicio;
	}

	/**
	 * Recupera o histórico do uso do {@link Workflow}.
	 * 
	 * @return histórico do uso do {@link Workflow}
	 */
	@OneToMany(targetEntity = HistoricoUsoWorkflow.class, mappedBy = "usoWorkflow", fetch = FetchType.LAZY)
	@OrderBy("dataHora DESC")
	public List<HistoricoUsoWorkflow> getHistorico() {
		return this.historico;
	}

	/**
	 * Recupera o número sequencial de uso no ano.
	 * 
	 * @return número sequencial de uso no ano
	 */
	@Column(name = "UWR_NUMERO", nullable = false)
	public Integer getNumero() {
		return this.numero;
	}

	/**
	 * Recupera o número do registro formatado
	 * 
	 * @return número do registro no formato &lt;ano&gt;/&lt;numero&gt;
	 */
	@Transient
	public String getNumeroRegistro() {
		if (CollectionUtils.isNotEmpty(this.historico)) {
			//Recupera o primeiro registo do histórico pois é inicialização do workflow
			HistoricoUsoWorkflow usoWorkflow = this.historico.get(this.historico.size() - 1);
			Integer ano = dataUtil.getAno(usoWorkflow.getDataHora());
			String numero = Utils.get().completaComZero(String.valueOf(this.numero), 6);
			return ano + "/" + numero;
		}
		return "";
	}

	/**
	 * Recupera a {@link Tarefa} que está sendo executada.
	 * 
	 * @return {@link Tarefa} que está sendo executada
	 */
	@ManyToOne
	@JoinColumn(name = "TAR_ID", nullable = false)
	@ForeignKey(name = "IR_TAR_UWR")
	public Tarefa getTarefa() {
		return this.tarefa;
	}

	/**
	 * Recupera se o uso foi finalizado.
	 * 
	 * @return se o uso foi finalizado
	 */
	@Column(name = "UWR_FINALIZADO", nullable = false)
	public Boolean getUsoFinalizado() {
		return this.usoFinalizado;
	}

	/**
	 * Recupera o {@link Workflow} que está sendo usado.
	 * 
	 * @return {@link Workflow} que está sendo usado
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_UWR")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui os anexos da utilização do {@link Workflow}.
	 * 
	 * @param anexos anexos da utilização do {@link Workflow}
	 */
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Atribui a anotação do uso
	 * 
	 * @param anotacao anotação do uso
	 */
	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	/**
	 * Atribui os usos do uso workflow
	 * 
	 * @param camposUsados usos do uso workflow
	 */
	public void setCamposUsados(List<CampoUsoWorkflow> camposUsados) {
		this.camposUsados = camposUsados;
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
	 * Atribui o histórico do uso do {@link Workflow}.
	 * 
	 * @param historico histórico do uso do {@link Workflow}
	 */
	public void setHistorico(List<HistoricoUsoWorkflow> historico) {
		this.historico = historico;
	}

	/**
	 * Atribui o número sequencial de uso no ano.
	 * 
	 * @param numero número sequencial de uso no ano
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * Atribui a {@link Tarefa} que está sendo executada.
	 * 
	 * @param tarefa {@link Tarefa} que está sendo executada
	 */
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	/**
	 * Atribui se o uso foi finalizado.
	 * 
	 * @param usoFinalizado se o uso foi finalizado
	 */
	public void setUsoFinalizado(Boolean usoFinalizado) {
		this.usoFinalizado = usoFinalizado;
	}

	/**
	 * Atribui o {@link Workflow} que está sendo usado.
	 * 
	 * @param workflow {@link Workflow} que está sendo usado
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
