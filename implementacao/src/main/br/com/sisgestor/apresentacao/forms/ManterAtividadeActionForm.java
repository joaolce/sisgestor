/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.sisgestor.apresentacao.forms;

import br.com.sisgestor.apresentacao.actions.ManterAtividadeAction;
import br.com.sisgestor.entidade.Departamento;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 * Form para a action {@link ManterAtividadeAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeActionForm extends BaseForm {

	private String descricao;
	private String nome;
	private Integer processo;
	private Integer departamento;
	private List<Departamento> listaDepartamentos;
	private String[] fluxos;
	private String[] posicoes;

	/**
	 * Recupera o código identificador do departamento
	 * 
	 * @return código identificadór do departamento
	 */
	public Integer getDepartamento() {
		return this.departamento;
	}

	/**
	 * Recupera a descrição da atividade
	 * 
	 * @return descrição da atividade
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
		return (String[]) ArrayUtils.clone(this.fluxos);
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
	 * Recupera as posições das atividades
	 * 
	 * @return posicoes das atividades
	 */
	public String[] getPosicoes() {
		return (String[]) ArrayUtils.clone(this.posicoes);
	}

	/**
	 * Recupera o código identificador do processo.
	 * 
	 * @return código identificador do processo
	 */
	public Integer getProcesso() {
		return this.processo;
	}

	/**
	 * Atribui o departamento responsável pela atividade
	 * 
	 * @param departamento departamento responsável pela atividade
	 */
	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	/**
	 * Atribui a descrição da atividade
	 * 
	 * @param descricao descrição da atividade
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
		this.fluxos = (String[]) ArrayUtils.clone(fluxos);
	}

	/**
	 * Atribui a lista de departamentos disponíveis para a atividade
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
	 * Atribui as posições das atividades
	 * 
	 * @param posicoes posições das atividades
	 */
	public void setPosicoes(String[] posicoes) {
		this.posicoes = (String[]) ArrayUtils.clone(posicoes);
	}

	/**
	 * Atribui o código identificador do processo.
	 * 
	 * @param processo código identificador do processo
	 */
	public void setProcesso(Integer processo) {
		this.processo = processo;
	}

}
