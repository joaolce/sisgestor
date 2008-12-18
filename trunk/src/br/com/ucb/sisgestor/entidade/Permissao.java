/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;


/**
 * Classe que representa uma permissão no sistema
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PRM_PERMISSAO")
@org.hibernate.annotations.Table(appliesTo = "PRM_PERMISSAO")
@SequenceGenerator(name = "SEQ_PRM", sequenceName = "SEQ_PRM", allocationSize = 10)
@AttributeOverride(name = "id", column = @Column(name = "PRM_ID", nullable = false))
public class Permissao extends ObjetoPersistente {

	private String				descricao;
	private List<Usuario>	usuarios;

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	@Column(name = "PRM_DS", nullable = false, length = 30)
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Recupera o valor de usuarios
	 * 
	 * @return usuarios
	 */
	@ManyToMany(targetEntity = Usuario.class, mappedBy = "permissoes")
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Atribui descricao
	 * 
	 * @param descricao o valor a ajustar em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui usuarios
	 * 
	 * @param usuarios o valor a ajustar em usuarios
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
