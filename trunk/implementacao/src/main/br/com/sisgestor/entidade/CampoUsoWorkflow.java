package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;


/**
 * Classe para representar o valor do campo utilizado no uso workflow.
 * 
 * @author Thiago
 * @since 15/04/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "UCA_USOWORKFLOW_CAMPO")
@org.hibernate.annotations.Table(appliesTo = "UCA_USOWORKFLOW_CAMPO")
@AttributeOverride(name = "id", column = @Column(name = "UCA_ID", nullable = false))
public class CampoUsoWorkflow extends ObjetoPersistente {

	private UsoWorkflow	usoWorkflow;
	private Campo			campo;
	private String			valor;

	/**
	 * Recupera o campo usado
	 * 
	 * @return campo usado
	 */
	@ManyToOne
	@JoinColumn(name = "CAM_ID", nullable = false)
	@ForeignKey(name = "IR_CAM_UCA")
	public Campo getCampo() {
		return this.campo;
	}

	/**
	 * Recupera o usoWorkflow
	 * 
	 * @return Uso Workflow
	 */
	@ManyToOne
	@JoinColumn(name = "UWR_ID", nullable = false)
	@ForeignKey(name = "IR_UWR_UCA")
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Recupera o valor do campo
	 * 
	 * @return valor do campo
	 */
	@Column(name = "UCA_VALOR", nullable = false, length = ConstantesDB.VALOR_CAMPO)
	public String getValor() {
		return this.valor;
	}

	/**
	 * Atribui campo usado
	 * 
	 * @param campo campo usado
	 */
	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	/**
	 * Atribui o usoWorkflow
	 * 
	 * @param usoWorkflow Uso Workflow
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}

	/**
	 * Atribui o valor do campo
	 * 
	 * @param valor o valor do campo
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
