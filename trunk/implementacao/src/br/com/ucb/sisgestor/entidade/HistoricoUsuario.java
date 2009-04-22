/*
 * Projeto: sisgestor
 * Cria��o: 22/04/2009 por Thiago Pires
 */
package br.com.ucb.sisgestor.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Hist�rico de uso do {@link Usuario}.
 * 
 * @author Thiago Pires
 * @since 22/04/2009
 */
@IdClass(HistoricoUsuarioPK.class)
@javax.persistence.Entity
@javax.persistence.Table(name = "HUUR_UUR_USUARIO")
@org.hibernate.annotations.Table(appliesTo = "HUUR_UUR_USUARIO")
public class HistoricoUsuario implements Serializable {

	private Usuario	usuario;
	private Timestamp	dataHora;

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
	 * Recupera a utiliza��o do {@link Usuario} do hist�rico.
	 * 
	 * @return utiliza��o do {@link Usuario} do hist�rico
	 */
	@Id
	public Usuario getUsuario() {
		return this.usuario;
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
	 * Atribui utiliza��o do {@link Usuario} do hist�rico.
	 * 
	 * @param usuario utiliza��o do {@link Usuario} do hist�rico
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
