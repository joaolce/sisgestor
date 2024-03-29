/*
 * Projeto: SisGestor
 * Cria��o: 24/10/2008 por Jo�o L�cio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa uma permiss�o no sistema. <br />
 * 
 * obs: para adicionar uma permiss�o no sistema, dever� ser colocado no web.xml e inserir no banco de dados.
 * 
 * @author Jo�o L�cio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PRM_PERMISSAO")
@org.hibernate.annotations.Table(appliesTo = "PRM_PERMISSAO")
@AttributeOverride(name = "id", column = @Column(name = "PRM_ID", nullable = false))
public class Permissao extends ObjetoPersistente {

	private String				descricao;
	private List<Usuario>	usuarios;

	/**
	 * Recupera a descri��o da permiss�o
	 * 
	 * @return descri��o da permiss�o
	 */
	@Column(name = "PRM_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera os usu�rios pertencentes da permiss�o
	 * 
	 * @return usu�rios pertencentes da permiss�o
	 */
	@ManyToMany(targetEntity = Usuario.class, mappedBy = "permissoes")
	@ForeignKey(name = "IR_PRM_UPM")
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Atribui a descri��o da permiss�o
	 * 
	 * @param descricao descri��o da permiss�o
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui usu�rios pertencentes da permiss�o
	 * 
	 * @param usuarios usu�rios pertencentes da permiss�o
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
