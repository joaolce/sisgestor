/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
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
 * Hist�rico de uso do {@link Workflow}.
 * 
 * @author Jo�o L�cio
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
	 * Recupera a a��o ocorrida no {@link UsoWorkflow}.
	 * 
	 * @return a��o ocorrida no {@link UsoWorkflow}
	 */
	@Column(name = "HUWR_ACAO", nullable = false)
	@Type(type = HibernateUserTypeConstants.CODIGO_INTEGER, parameters = @Parameter(name = "className", value = HibernateUserTypeConstants.TIPO_ACAO_ENUM))
	public TipoAcaoEnum getAcao() {
		return this.acao;
	}

	/**
	 * Recupera a data/hora que houve a atualiza��o.
	 * 
	 * @return data/hora que houve a atualiza��o
	 */
	@Id
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utiliza��o do {@link Workflow} do hist�rico.
	 * 
	 * @return utiliza��o do {@link Workflow} do hist�rico
	 */
	@Id
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Recupera o usu�rio respons�vel por alterar o uso
	 * 
	 * @return usu�rio respons�vel por alterar o uso
	 */
	@ManyToOne
	@JoinColumn(name = "UUR_ID", nullable = false)
	@ForeignKey(name = "IR_UUR_HUWR")
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * Atribui a a��o ocorrida no {@link UsoWorkflow}.
	 * 
	 * @param acao a��o ocorrida no {@link UsoWorkflow}
	 */
	public void setAcao(TipoAcaoEnum acao) {
		this.acao = acao;
	}

	/**
	 * Atribui a data/hora que houve a atualiza��o.
	 * 
	 * @param dataHora data/hora que houve a atualiza��o
	 */
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Atribui utiliza��o do {@link Workflow} do hist�rico.
	 * 
	 * @param usoWorkflow utiliza��o do {@link Workflow} do hist�rico
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}

	/**
	 * Atribui o usu�rio respons�vel por alterar o uso
	 * 
	 * @param usuario usu�rio respons�vel por alterar o uso
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
