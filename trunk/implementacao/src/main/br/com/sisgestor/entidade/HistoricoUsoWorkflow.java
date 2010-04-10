/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.hibernate.HibernateUserTypeConstants;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * Histórico de uso do {@link Workflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@IdClass(HistoricoUsoWorkflowPK.class)
@javax.persistence.Entity
@javax.persistence.Table(name = "HUWR_USO_WORKFLOW")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@org.hibernate.annotations.Table(appliesTo = "HUWR_USO_WORKFLOW")
public class HistoricoUsoWorkflow implements Serializable {

	private UsoWorkflow usoWorkflow;
	private Timestamp dataHora;
	private Usuario usuario;
	private TipoAcaoEnum acao;

	/**
	 * Recupera a ação ocorrida no {@link UsoWorkflow}.
	 * 
	 * @return ação ocorrida no {@link UsoWorkflow}
	 */
	@Column(name = "HUWR_ACAO", nullable = false)
	@Type(type = HibernateUserTypeConstants.CODIGO_INTEGER, parameters = @Parameter(name = "className", value = HibernateUserTypeConstants.TIPO_ACAO_ENUM))
	public TipoAcaoEnum getAcao() {
		return this.acao;
	}

	/**
	 * Recupera a data/hora que houve a atualização.
	 * 
	 * @return data/hora que houve a atualização
	 */
	@Id
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utilização do {@link Workflow} do histórico.
	 * 
	 * @return utilização do {@link Workflow} do histórico
	 */
	@Id
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Recupera o usuário responsável por alterar o uso
	 * 
	 * @return usuário responsável por alterar o uso
	 */
	@ManyToOne
	@JoinColumn(name = "UUR_ID", nullable = false)
	@ForeignKey(name = "IR_UUR_HUWR")
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * Atribui a ação ocorrida no {@link UsoWorkflow}.
	 * 
	 * @param acao ação ocorrida no {@link UsoWorkflow}
	 */
	public void setAcao(TipoAcaoEnum acao) {
		this.acao = acao;
	}

	/**
	 * Atribui a data/hora que houve a atualização.
	 * 
	 * @param dataHora data/hora que houve a atualização
	 */
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Atribui utilização do {@link Workflow} do histórico.
	 * 
	 * @param usoWorkflow utilização do {@link Workflow} do histórico
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}

	/**
	 * Atribui o usuário responsável por alterar o uso
	 * 
	 * @param usuario usuário responsável por alterar o uso
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
