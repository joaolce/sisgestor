/*
 * Projeto: sisgestor
 * Cria��o: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;

/**
 * Classe para representar tipo de campo.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TIP_TIPO")
@org.hibernate.annotations.Table(appliesTo = "TIP_TIPO")
@AttributeOverride(name = "id", column = @Column(name = "TIP_ID", nullable = false))
public class Tipo extends ObjetoPersistente {

	private String	descricao;

	/**
	 * Recupera a descri��o do tipo
	 * 
	 * @return descri��o do tipo
	 */
	/*
	 * O tamanho do campo desci��o est� com o mesmo tamanho do campo nome,
	 * pois n�o h� necessidade de um tamanho maior que o do mome para a descri��o do tipo.
	 * */
	@Column(name = "TIP_DESCRICAO", nullable = false, length = ConstantesDB.NOME)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Atribui a descri��o do tipo.
	 * 
	 * @param descricao descri��o do tipo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
