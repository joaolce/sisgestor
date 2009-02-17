/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterTarefaAction;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Form para a action {@link ManterTarefaAction}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaActionForm extends BaseForm {

	private String				descricao;
	private String				nome;
	private Integer			atividade;
	private Integer			usuarioResponsavel;
	private List<Usuario>	listaUsuarios;

	/**
	 * Recupera o identificador da atividade.
	 * 
	 * @return identificador da atividade
	 */
	public Integer getAtividade() {
		return this.atividade;
	}

	/**
	 * Recupera o valor de descricao
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o valor de listaUsuarios
	 * 
	 * @return listaUsuarios
	 */
	public List<Usuario> getListaUsuarios() {
		return this.listaUsuarios;
	}

	/**
	 * Recupera o valor de nome
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}


	/**
	 * Recupera o valor de usuarioResponsavel
	 * 
	 * @return usuarioResponsavel
	 */
	public Integer getUsuarioResponsavel() {
		return this.usuarioResponsavel;
	}


	/**
	 * Atribui o identificador da atividade.
	 * 
	 * @param atividade identificador da atividade
	 */
	public void setAtividade(Integer atividade) {
		this.atividade = atividade;
	}


	/**
	 * Atribui descricao
	 * 
	 * @param descricao o valor a ajustar em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui listaUsuarios
	 * 
	 * @param listaUsuarios o valor a ajustar em listaUsuarios
	 */
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	/**
	 * Atribui nome
	 * 
	 * @param nome o valor a ajustar em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui usuarioResponsavel
	 * 
	 * @param usuarioResponsavel o valor a ajustar em usuarioResponsavel
	 */
	public void setUsuarioResponsavel(Integer usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}
}
