/*
 * Projeto: sisgestor
 * Cria��o: 03/03/2009 por Jo�o L�cio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Entidade que representa um valor de campo pr�-definido do workflow.
 * 
 * @author Jo�o L�cio
 * @since 03/03/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "OPC_OPCAO_CAMPO")
@org.hibernate.annotations.Table(appliesTo = "OPC_OPCAO_CAMPO")
@AttributeOverride(name = "id", column = @Column(name = "OPC_ID", nullable = false))
public class OpcaoCampo extends ObjetoPersistente {

	private Campo		campo;
	private Integer	valor;
	private String		descricao;

	/**
	 * Recupera o campo da op��o.
	 * 
	 * @return campo da op��o
	 */
	@ManyToOne
	@JoinColumn(name = "CAM_ID", nullable = false)
	@ForeignKey(name = "IR_CAM_OPC")
	public Campo getCampo() {
		return this.campo;
	}

	/**
	 * Recupera a descri��o do campo.
	 * 
	 * @return descri��o do campo
	 */
	@Column(name = "OPC_DESCRICAO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_DESCRICAO_OPCAO)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera valor da op��o.
	 * 
	 * @return valor da op��o
	 */
	@Column(name = "OPC_VALOR", nullable = false)
	public Integer getValor() {
		return this.valor;
	}

	/**
	 * Atribui o campo da op��o.
	 * 
	 * @param campo campo da op��o
	 */
	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	/**
	 * Atribui a descri��o do campo.
	 * 
	 * @param descricao descri��o do campo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o valor da op��o.
	 * 
	 * @param valor valor da op��o
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
