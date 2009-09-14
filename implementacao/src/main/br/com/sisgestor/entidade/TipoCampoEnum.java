/*
 * Projeto: sisgestor
 * Criação: 26/02/2009 por João Lúcio
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.hibernate.CodigoDescricao;

/**
 * Tipo de campo do workflow.
 * 
 * @author João Lúcio
 * @since 26/02/2009
 */
public enum TipoCampoEnum implements CodigoDescricao {

	/** Campo do tipo de data. */
	DATA(1, "Data"),
	/** Campo do tipo de hora. */
	HORA(2, "Hora"),
	/** Campo do tipo de lista de opções. */
	LISTA_DE_OPCOES(3, "Lista de opções"),
	/** Campo do tipo de múltipla escolha. */
	MULTIPLA_ESCOLHA(4, "Múltipla escolha"),
	/** Campo do tipo texto. */
	TEXTO(5, "Texto");

	private Integer	chave;
	private String		descricao;

	/**
	 * Cria uma nova instância do tipo {@link TipoCampoEnum}.
	 * 
	 * @param chave chave do tipo
	 * @param descricao descrição do tipo
	 */
	private TipoCampoEnum(Integer chave, String descricao) {
		this.chave = chave;
		this.descricao = descricao;
	}

	/**
	 * Recupera o tipo a partir do id.
	 * 
	 * @param id identificador do tipo
	 * @return {@link TipoCampoEnum} encontrado, ou <code>null</code>
	 */
	public static TipoCampoEnum getTipo(Integer id) {
		for (TipoCampoEnum tipo : values()) {
			if (tipo.getId().equals(id)) {
				return tipo;
			}
		}
		return null;
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
