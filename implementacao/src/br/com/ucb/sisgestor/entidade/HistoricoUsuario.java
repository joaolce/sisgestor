/*
 * Projeto: sisgestor
 * Criação: 22/04/2009 por Thiago Pires
 */
package br.com.ucb.sisgestor.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Histórico de uso do {@link Usuario}.
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
	 * Recupera a data/hora que houve a atualização.
	 * 
	 * @return data/hora que houve a atualização
	 */
	@Id
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	/**
	 * Recupera a utilização do {@link Usuario} do histórico.
	 * 
	 * @return utilização do {@link Usuario} do histórico
	 */
	@Id
	public Usuario getUsuario() {
		return this.usuario;
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
	 * Atribui utilização do {@link Usuario} do histórico.
	 * 
	 * @param usuario utilização do {@link Usuario} do histórico
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
