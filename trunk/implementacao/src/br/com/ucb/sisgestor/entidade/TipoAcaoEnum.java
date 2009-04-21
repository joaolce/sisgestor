/*
 * Projeto: sisgestor
 * Criação: 12/04/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.hibernate.CodigoDescricao;

/**
 * Enumeração que representa uma ação ocorrida no {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 12/04/2009
 */
public enum TipoAcaoEnum implements CodigoDescricao {

	/** Início de um {@link UsoWorkflow}. */
	INICIO_USO(1, "Início do uso"),
	/** Inclusão de {@link Anexo}. */
	INCLUSAO_DE_ANEXO(2, "Inclusão de anexo"),
	/** Exclusão de {@link Anexo}. */
	EXCLUSAO_DE_ANEXO(3, "Exclusão de anexo"),
	/** Inclusão de {@link Anexo}. */
	ALTERACAO_CAMPOS(4, "Alteração nos campos"),
	/** Início da tarefa. */
	INICIO_TAREFA(5, "Início da tarefa"),
	/** Finalização da tarefa. */
	FINALIZAR_TAREFA(6, "Finalização da tarefa");

	private Integer	chave;
	private String		descricao;

	/**
	 * Cria uma nova instância do tipo {@link TipoAcaoEnum}.
	 * 
	 * @param chave chave do tipo
	 * @param descricao descrição do tipo
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
