/*
 * Projeto: sisgestor
 * Cria��o: 26/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.hibernate.CodigoDescricao;

/**
 * Tipo de campo do workflow.
 * 
 * @author Jo�o L�cio
 * @since 26/02/2009
 */
public enum TipoCampoEnum implements CodigoDescricao {

	/** Campo do tipo de data. */
	DATA(1, "Data"),
	/** Campo do tipo de hora. */
	HORA(2, "Hora"),
	/** Campo do tipo de lista de op��es. */
	LISTA_DE_OPCOES(3, "Lista de op��es"),
	/** Campo do tipo de m�ltipla escolha. */
	MULTIPLA_ESCOLHA(4, "M�ltipla escolha"),
	/** Campo do tipo texto. */
	TEXTO(5, "Texto");

	private Integer	chave;
	private String		descricao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link TipoCampoEnum}.
	 * 
	 * @param chave chave do tipo
	 * @param descricao descri��o do tipo
	 */
	private TipoCampoEnum(Integer chave, String descricao) {
		this.chave = chave;
		this.descricao = descricao;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getId() {
		return this.chave;
	}
}
