/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.Utils;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Classe que representa um objeto persistente
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
@MappedSuperclass
public abstract class ObjetoPersistente implements Serializable {

	private Integer	id;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ObjetoPersistente)) {
			return false;
		}
		ObjetoPersistente comparado = (ObjetoPersistente) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.id, comparado.id);
		return builder.isEquals();
	}

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

	/**
	 * Verifica se todas as propriedades do objeto s�o nulas.
	 * 
	 * @return <code>true</code> caso sejam, <code>false</code> caso contr�rio
	 */
	@Transient
	protected boolean isAllNull() {
		return Utils.isTodasPropriedadesVazias(this);
	}
}
