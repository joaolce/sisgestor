/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterCampoAction;
import br.com.ucb.sisgestor.entidade.TipoCampoEnum;
import java.util.List;

/**
 * Form para a action {@link ManterCampoAction}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoActionForm extends BaseForm {

	private String						nome;
	private Integer					tipo;
	private List<TipoCampoEnum>	tipos;

	/**
	 * Recupera o nome do campo
	 * 
	 * @return nome do campo
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera o tipo do campo
	 * 
	 * @return tipo do campo
	 */
	public Integer getTipo() {
		return this.tipo;
	}

	/**
	 * Recupera os tipos de campos disponíveis.
	 * 
	 * @return tipos de campos disponíveis
	 */
	public List<TipoCampoEnum> getTipos() {
		return this.tipos;
	}

	/**
	 * Atribui o nome do campo
	 * 
	 * @param nome nome do campo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui o tipo do campo
	 * 
	 * @param tipo tipo do campo
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * Atribui os tipos de campos disponíveis.
	 * 
	 * @param tipos tipos de campos disponíveis
	 */
	public void setTipos(List<TipoCampoEnum> tipos) {
		this.tipos = tipos;
	}
}
