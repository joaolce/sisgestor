/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterAtividadeAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Form para a action {@link ManterAtividadeAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeActionForm extends BaseForm {

	private String					descricao;
	private String					nome;
	private Integer				processo;
	private Integer				departamento;
	private List<Departamento>	listaDepartamentos;
	private String[]				fluxos;

	/**
	 * Recupera o c�digo identificador do departamento
	 * 
	 * @return c�digo identificad�r do departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera a descri��o da atividade
	 * 
	 * @return descri��o da atividade
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera os fluxos das atividades.
	 * 
	 * @return fluxos das atividades
	 */
	public String[] getFluxos() {
		return this.fluxos;
	}

	/**
	 * Recupera um {@link List} de departamentos cadastrados.
	 * 
	 * @return lista de departamentos cadastrados.
	 */
	public List<Departamento> getListaDepartamentos() {
		return this.listaDepartamentos;
	}

	/**
	 * Recupera o nome da atividade
	 * 
	 * @return nome da atividade
	 */
	public String getNome() {
		return this.nome;
	}


	/**
	 * Recupera o c�digo identificador do processo.
	 * 
	 * @return c�digo identificador do processo
	 */
	public Integer getProcesso() {
		return this.processo;
	}


	/**
	 * Atribui o departamento respons�vel pela atividade
	 * 
	 * @param departamento departamento respons�vel pela atividade
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}


	/**
	 * Atribui a descri��o da atividade
	 * 
	 * @param descricao descri��o da atividade
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui os fluxos das atividades.
	 * 
	 * @param fluxos fluxos das atividades
	 */
	public void setFluxos(String[] fluxos) {
		this.fluxos = fluxos;
	}


	/**
	 * Atribui a lista de departamentos dispon�veis para a atividade
	 * 
	 * @param listaDepartamentos lista de departamentos
	 */
	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}


	/**
	 * Atribui o nome da atividade
	 * 
	 * @param nome nome da atividade
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui o c�digo identificador do processo.
	 * 
	 * @param processo c�digo identificador do processo
	 */
	public void setProcesso(Integer processo) {
		this.processo = processo;
	}


}
