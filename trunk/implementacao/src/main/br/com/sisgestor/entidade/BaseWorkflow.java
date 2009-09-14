/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.sisgestor.entidade;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Index;

/**
 * Objeto base para os dados de workflow.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseWorkflow extends ObjetoPersistente {

	private String	descricao;
	private String	nome;

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	@Index(name = "IX_DESCRICAO")
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de nome
	 * 
	 * @return nome
	 */
	@Index(name = "IX_NOME")
	public String getNome() {
		return this.nome;
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
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
