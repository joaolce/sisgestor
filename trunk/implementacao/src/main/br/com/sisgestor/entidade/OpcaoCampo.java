/*
 * Projeto: sisgestor
 * Criação: 03/03/2009 por João Lúcio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 * Entidade que representa um valor de campo pré-definido do workflow.
 * 
 * @author João Lúcio
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
	 * Recupera o campo da opção.
	 * 
	 * @return campo da opção
	 */
	@ManyToOne
	@JoinColumn(name = "CAM_ID", nullable = false)
	@ForeignKey(name = "IR_CAM_OPC")
	public Campo getCampo() {
		return this.campo;
	}

	/**
	 * Recupera a descrição do campo.
	 * 
	 * @return descrição do campo
	 */
	@Column(name = "OPC_DESCRICAO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_DESCRICAO_OPCAO)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera valor da opção.
	 * 
	 * @return valor da opção
	 */
	@Column(name = "OPC_VALOR", nullable = false)
	public Integer getValor() {
		return this.valor;
	}

	/**
	 * Atribui o campo da opção.
	 * 
	 * @param campo campo da opção
	 */
	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	/**
	 * Atribui a descrição do campo.
	 * 
	 * @param descricao descrição do campo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o valor da opção.
	 * 
	 * @param valor valor da opção
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
