/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago Pires
 */
package br.com.ucb.sisgestor.util.dto;

/**
 * DTO para pesquisas de atividade.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class PesquisaAtividadeDTO extends PesquisaPaginadaDTO {

	private Integer	idProcesso;
	private String		nome;
	private String		descricao;
	private Integer	departamento;

	/**
	 * Recupera o identificador do departamento para pesquisa.
	 * 
	 * @return identificador do departamento para pesquisa
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera parte da descrição para pesquisa.
	 * 
	 * @return parte da descrição para pesquisa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de idProcesso
	 * 
	 * @return idProcesso
	 */
	public Integer getIdProcesso() {
		return this.idProcesso;
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
	 * Atribui o identificador do departamento para pesquisa.
	 * 
	 * @param departamento identificador do departamento para pesquisa
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
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
	 * Atribui idProcesso
	 * 
	 * @param idProcesso o valor a ajustar em idProcesso
	 */
	public void setIdProcesso(Integer idProcesso) {
		this.idProcesso = idProcesso;
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
