/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
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

	/**
	 * Recupera o c�digo identificador da atividade.
	 * 
	 * @return c�digo identificador da atividade
	 */
	public Integer getAtividade() {
		return this.atividade;
	}

	/**
	 * Recupera a descri��o da tarefa
	 * 
	 * @return descri��o da tarefa
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera um {@link List} de usu�rios dispon�veis para esta tarefa
	 * 
	 * @return lista de usu�rios
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
	 * Recupera o usu�rio respons�vel pela tarefa
	 * 
	 * @return usu�rio responsavel
	 */
	public Integer getUsuario() {
		return this.usuario;
	}


	/**
	 * Atribui o c�digo identificador da atividade.
	 * 
	 * @param atividade c�digo identificador da atividade
	 */
	public void setAtividade(Integer atividade) {
		this.atividade = atividade;
	}


	/**
	 * Atribui descricao
	 * 
	 * @param descricao descri��o da tarefa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui uma lista de usu�rios � tarefa
	 * 
	 * @param listaUsuarios lista de usu�rios
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
	 * Atribui o usu�rio respons�vel � tarefa
	 * 
	 * @param usuario usu�rio respons�vel pela tarefa
	 */
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
}
