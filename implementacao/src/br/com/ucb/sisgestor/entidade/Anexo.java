/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Anexo de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "ANX_ANEXO")
@org.hibernate.annotations.Table(appliesTo = "ANX_ANEXO")
@AttributeOverride(name = "id", column = @Column(name = "ANX_ID", nullable = false))
public class Anexo extends ObjetoPersistente {

	private String			nome;
	private String			contentType;
	private byte[]			dados;
	private UsoWorkflow	usoWorkflow;

	/**
	 * Recupera o contentType do arquivo.
	 * 
	 * @return contentType do arquivo
	 */
	@Column(name = "ANX_CONTENT_TYPE", nullable = false, length = ConstantesDB.CONTENT_TYPE)
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Recupera o dado do arquivo.
	 * 
	 * @return dado do arquivo
	 */
	@Lob
	@Column(name = "ANX_DADOS", nullable = false)
	public byte[] getDados() {
		return this.dados;
	}

	/**
	 * Recupera o nome do arquivo.
	 * 
	 * @return nome do arquivo
	 */
	@Column(name = "ANX_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera a utilização do {@link Workflow} do anexo.
	 * 
	 * @return utilização do {@link Workflow} do anexo
	 */
	@ManyToOne
	@JoinColumn(name = "UWR_ID", nullable = false)
	@ForeignKey(name = "IR_UWR_ANX")
	public UsoWorkflow getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Atribui o contentType do arquivo.
	 * 
	 * @param contentType contentType do arquivo
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Atribui o dado do arquivo.
	 * 
	 * @param dados dado do arquivo
	 */
	public void setDados(byte[] dados) {
		this.dados = dados;
	}

	/**
	 * Atribui o nome do arquivo.
	 * 
	 * @param nome nome do arquivo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui a utilização do {@link Workflow} do anexo.
	 * 
	 * @param usoWorkflow utilização do {@link Workflow} do anexo
	 */
	public void setUsoWorkflow(UsoWorkflow usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}
}
