/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Form para a action {@link ManterDepartamentoAction}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterDepartamentoActionForm extends BaseForm {

	private Integer				departamentoSuperior;
	private String					email;
	private List<Departamento>	listaDepartamentos;
	private String					nome;
	private String					sigla;

	/**
	 * Recupera código do departamento superior.
	 * 
	 * @return código do departamento superior
	 */
	public Integer getDepartamentoSuperior() {
		return this.departamentoSuperior;
	}

	/**
	 * Recupera o email do departamento.
	 * 
	 * @return email do departamento
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Recupera todos os departamentos cadastrados.
	 * 
	 * @return todos os departamentos cadastrados
	 */
	public List<Departamento> getListaDepartamentos() {
		return this.listaDepartamentos;
	}

	/**
	 * Recupera o nome do departamento.
	 * 
	 * @return nome do departamento
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera a sigla do departamento.
	 * 
	 * @return sigla do departamento
	 */
	public String getSigla() {
		return this.sigla;
	}

	/**
	 * Atribui o código do departamento superior.
	 * 
	 * @param departamentoSuperior código do departamento superior
	 */
	public void setDepartamentoSuperior(Integer departamentoSuperior) {
		this.departamentoSuperior = departamentoSuperior;
	}

	/**
	 * Atribui o email do departamento
	 * 
	 * @param email email do departamento
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Atribui todos os departamentos cadastrados.
	 * 
	 * @param listaDepartamentos todos os departamentos cadastrados
	 */
	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	/**
	 * Atribui o nome do departamento.
	 * 
	 * @param nome nome do departamento
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui a sigla do departamento.
	 * 
	 * @param sigla sigla do departamento
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
