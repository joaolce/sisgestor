/*
 * Projeto: SisGestor
 * Cria��o: 21/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Classe que representa um objeto persistente
 * 
 * @author Jo�o L�cio
 * @since 21/10/2008
 */
@MappedSuperclass
public abstract class ObjetoPersistente extends ConstantesDB implements Serializable {

	private Integer	id;

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
	 * Verifica se todas as propriedades do objeto s�o nulas
	 * 
	 * @return <code>true</code> caso verdadeiro, <code>false</code> caso contr�rio
	 */
	@Transient
	protected boolean isAllNull() {
		return Utils.isTodasPropriedadesVazias(this);
	}
}
