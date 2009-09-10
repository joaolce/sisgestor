/*
 * Projeto: sisgestor
 * Cria��o: 12/04/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.hibernate.CodigoDescricao;

/**
 * Enumera��o que representa uma a��o ocorrida no {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 12/04/2009
 */
public enum TipoAcaoEnum implements CodigoDescricao {

	/** In�cio de um {@link UsoWorkflow}. */
	INICIO_USO(1, "In�cio do registro"),
	/** Inclus�o de {@link Anexo}. */
	INCLUSAO_DE_ANEXO(2, "Inclus�o de anexo"),
	/** Exclus�o de {@link Anexo}. */
	EXCLUSAO_DE_ANEXO(3, "Exclus�o de anexo(s)"),
	/** Altera��o nos {@link CampoUsoWorkflow}. */
	ALTERACAO_CAMPOS(4, "Altera��o nos campos"),
	/** In�cio da {@link Tarefa}. */
	INICIO_TAREFA(5, "In�cio da tarefa"),
	/** Finaliza��o da {@link Tarefa}. */
	FINALIZAR_TAREFA(6, "Finaliza��o da tarefa"),
	/** Finaliza��o do registro do {@link UsoWorkflow}. */
	FINALIZAR_USO(7, "Finaliza��o do registro"),
	/** Altera��o na anota��es do {@link UsoWorkflow}. */
	ALTERACAO_ANOTACAO(8, "Altera��o nas anota��es");

	private Integer	chave;
	private String		descricao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link TipoAcaoEnum}.
	 * 
	 * @param chave chave do tipo
	 * @param descricao descri��o do tipo
	 */
	private TipoAcaoEnum(Integer chave, String descricao) {
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
