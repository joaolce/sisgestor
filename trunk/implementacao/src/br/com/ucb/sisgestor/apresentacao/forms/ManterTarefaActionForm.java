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

	private Integer			atividade;
	private String				descricao;
	private List<Usuario>	listaUsuarios;
	private String				nome;
	private Integer			usuario;
	private String[]			fluxos;
	private String[]			posicoes;

	/**
	 * Recupera o código identificador da atividade.
	 * 
	 * @return código identificador da atividade
	 */
	public Integer getAtividade() {
		return this.atividade;
	}

	/**
	 * Recupera a descrição da tarefa
	 * 
	 * @return descrição da tarefa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera os fluxos das tarefas.
	 * 
	 * @return fluxos das tarefas
	 */
	public String[] getFluxos() {
		return this.fluxos;
	}

	/**
	 * Recupera um {@link List} de usuários disponíveis para esta tarefa
	 * 
	 * @return lista de usuários
	 */
	public List<Usuario> getListaUsuarios() {
		return this.listaUsuarios;
	}

	/**
	 * Recupera o nome da tarefa
	 * 
	 * @return nome da tarefa
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera as posições das tarefas
	 * 
	 * @return posicoes das tarefas
	 */
	public String[] getPosicoes() {
		return this.posicoes;
	}

	/**
	 * Recupera o usuário responsável pela tarefa
	 * 
	 * @return usuário responsavel
	 */
	public Integer getUsuario() {
		return this.usuario;
	}

	/**
	 * Atribui o código identificador da atividade.
	 * 
	 * @param atividade código identificador da atividade
	 */
	public void setAtividade(Integer atividade) {
		this.atividade = atividade;
	}


	/**
	 * Atribui descricao
	 * 
	 * @param descricao descrição da tarefa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui os fluxos das tarefas.
	 * 
	 * @param fluxos fluxos das tarefas
	 */
	public void setFluxos(String[] fluxos) {
		this.fluxos = fluxos;
	}


	/**
	 * Atribui uma lista de usuários à tarefa
	 * 
	 * @param listaUsuarios lista de usuários
	 */
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	/**
	 * Atribui o nome da tarefa
	 * 
	 * @param nome nome da tarefa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui as posições das tarefas
	 * 
	 * @param posicoes posições das tarefas
	 */
	public void setPosicoes(String[] posicoes) {
		this.posicoes = posicoes;
	}


	/**
	 * Atribui o usuário responsável à tarefa
	 * 
	 * @param usuario usuário responsável pela tarefa
	 */
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
}
