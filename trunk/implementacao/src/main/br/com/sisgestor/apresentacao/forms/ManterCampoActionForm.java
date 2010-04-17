/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.sisgestor.apresentacao.forms;

import br.com.sisgestor.apresentacao.actions.ManterCampoAction;
import br.com.sisgestor.entidade.TipoCampoEnum;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 * Form para a action {@link ManterCampoAction}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoActionForm extends BaseForm {

	private String nome;
	private String descricao;
	private Integer tipo;
	private Boolean obrigatorio;
	private Integer workflow;
	private String[] opcoes;
	private List<TipoCampoEnum> tipos;

	/**
	 * Recupera a descri��o do campo.
	 * 
	 * @return descri��o do campo
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o nome do campo.
	 * 
	 * @return nome do campo
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera se o campo � obrigat�rio.
	 * 
	 * @return se o campo � obrigat�rio
	 */
	public Boolean getObrigatorio() {
		return this.obrigatorio;
	}

	/**
	 * Recupera as op��es pr�-definidas.
	 * 
	 * @return op��es pr�-definidas
	 */
	public String[] getOpcoes() {
		return (String[]) ArrayUtils.clone(this.opcoes);
	}

	/**
	 * Recupera o tipo do campo.
	 * 
	 * @return tipo do campo
	 */
	public Integer getTipo() {
		return this.tipo;
	}

	/**
	 * Recupera os tipos de campos dispon�veis.
	 * 
	 * @return tipos de campos dispon�veis
	 */
	public List<TipoCampoEnum> getTipos() {
		return this.tipos;
	}

	/**
	 * Recupera o identificador do workflow.
	 * 
	 * @return identificador do workflow
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui a descri��o do campo.
	 * 
	 * @param descricao descri��o do campo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o nome do campo.
	 * 
	 * @param nome nome do campo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui se o campo � obrigat�rio.
	 * 
	 * @param obrigatorio se o campo � obrigat�rio
	 */
	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	/**
	 * Atribui as op��es pr�-definidas.
	 * 
	 * @param opcoes op��es pr�-definidas
	 */
	public void setOpcoes(String[] opcoes) {
		this.opcoes = (String[]) ArrayUtils.clone(opcoes);
	}

	/**
	 * Atribui o tipo do campo.
	 * 
	 * @param tipo tipo do campo
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * Atribui os tipos de campos dispon�veis.
	 * 
	 * @param tipos tipos de campos dispon�veis
	 */
	public void setTipos(List<TipoCampoEnum> tipos) {
		this.tipos = tipos;
	}

	/**
	 * Atribui o identificador do workflow.
	 * 
	 * @param workflow identificador do workflow
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
