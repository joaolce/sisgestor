/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import java.util.List;
import br.com.ucb.sisgestor.apresentacao.actions.ManterCampoAction;
import br.com.ucb.sisgestor.entidade.Tipo;

/**
 * Form para a action {@link ManterCampoAction}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoActionForm extends BaseForm {
	
	private String nome;
	private Integer tipo;
	private List<Tipo> listaTipos;
	
	/**
	 * Recupera o nome do campo
	 *
	 * @return nome do campo
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Recupera o tipo do campo
	 *
	 * @return tipo do campo
	 */
	public Integer getTipo() {
		return tipo;
	}
	
	/**
	 * Recupera um {@link List} de tipos  de campos disponíveis
	 *
	 * @return lista de tipos
	 */
	public List<Tipo> getListaTipos() {
		return listaTipos;
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
	 * Atribui a lista de tipos de campo
	 *
	 * @param listaTipos lista de tipos de campo
	 */
	public void setListaTipos(List<Tipo> listaTipos) {
		this.listaTipos = listaTipos;
	}
	
	
}
