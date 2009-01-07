/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Classe que representa uma permissão no sistema
 * 
 * @author João Lúcio
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
	 * Recupera a descrição da permissão
	 * 
	 * @return descrição da permissão
	 */
	@Column(name = "PRM_DS", nullable = false, length = 30)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera os usuários pertencentes da permissão
	 * 
	 * @return usuários pertencentes da permissão
	 */
	@ManyToMany(targetEntity = Usuario.class, mappedBy = "permissoes")
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Atribui a descrição da permissão
	 * 
	 * @param descricao descrição da permissão
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui usuários pertencentes da permissão
	 * 
	 * @param usuarios usuários pertencentes da permissão
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
