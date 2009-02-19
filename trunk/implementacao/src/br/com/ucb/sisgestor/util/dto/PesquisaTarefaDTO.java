/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago Pires
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de workflow.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class PesquisaTarefaDTO extends PesquisaPaginadaDTO {

	private String		descricao;
	private Integer	idAtividade;
	private String		nome;
	private Integer	usuario;

	/**
	 * Recupera parte da descrição para pesquisa.
	 * 
	 * @return parte da descrição para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o código identificador da atividade
	 * 
	 * @return código identificador da atividade
	 */
	public Integer getIdAtividade() {
		return this.idAtividade;
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
	 * Recupera o identificador do usuário para pesquisa.
	 * 
	 * @return identificador do usuário para pesquisa
	 */
	public Integer getUsuario() {
		return this.usuario;
	}

	/**
	 * Atribui parte da descrição para pesquisa.
	 * 
	 * @param descricao parte da descrição para pesquisa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o código identificador da atividade
	 * 
	 * @param idAtividade o código identificador da atividade
	 */
	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}

	/**
	 * Atribui parte do nome para pesquisa.
	 * 
	 * @param nome parte do nome para pesquisa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui o identificador do usuário para pesquisa.
	 * 
	 * @param usuario identificador do usuário para pesquisa
	 */
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
}
