/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.sisgestor.entidade;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Classe que representa um objeto persistente
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
@MappedSuperclass
public class ObjetoPersistente implements Serializable {

	private Integer id;

	/**
	 * Recupera o id do objeto
	 * 
	 * @return id do objeto
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	/**
	 * Atribui o id do objeto
	 * 
	 * @param id id do objeto
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		ObjetoPersistente comparado = (ObjetoPersistente) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.id, comparado.id);
		return builder.isEquals();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
