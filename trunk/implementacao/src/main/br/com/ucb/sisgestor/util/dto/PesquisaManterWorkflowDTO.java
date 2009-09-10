/*
 * Projeto: sisgestor
 * Cria��o: 28/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de manter workflow.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
public class PesquisaManterWorkflowDTO extends PesquisaPaginadaDTO {

	private String		nome;
	private String		descricao;
	private Boolean	ativo;
	private Boolean	excluidos;

	/**
	 * Recupera indicador de ativo para a pesquisa.
	 * 
	 * @return indicador de ativo para a pesquisa
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera parte da descri��o para pesquisa.
	 * 
	 * @return parte da descri��o para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera se deve mostrar workflows excluidos.
	 * 
	 * @return se deve mostrar workflows excluidos
	 */
	public Boolean getExcluidos() {
		return this.excluidos;
	}

	/**
	 * Recupera parte do nome para pesquisa.
	 * 
	 * @return parte do nome para pesquisa
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Atribui indicador de ativo para a pesquisa.
	 * 
	 * @param ativo indicador de ativo para a pesquisa
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui parte da descri��o para pesquisa.
	 * 
	 * @param descricao parte da descri��o para pesquisa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui se deve mostrar workflows excluidos.
	 * 
	 * @param excluidos se deve mostrar workflows excluidos
	 */
	public void setExcluidos(Boolean excluidos) {
		this.excluidos = excluidos;
	}

	/**
	 * Atribui parte do nome para pesquisa.
	 * 
	 * @param nome parte do nome para pesquisa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
