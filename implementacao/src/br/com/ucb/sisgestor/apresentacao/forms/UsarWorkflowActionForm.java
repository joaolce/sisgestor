/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Form para a action {@link UsarWorkflowAction}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowActionForm extends BaseForm {

	private Boolean			ativo;
	private String				descricao;
	private String				nome;
	private List<Workflow>	listaWorkflows;
	private Integer			workflow;

	/**
	 * Recupera o indicador de ativo do workflow.
	 * 
	 * @return indicador de ativo do workflow
	 */
	public Boolean getAtivo() {
		return this.ativo;
	}

	/**
	 * Recupera a descrição do workflow.
	 * 
	 * @return descrição do workflow
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera a lista de workflows disponíveis para serem iniciados
	 * 
	 * @return lista de workflows
	 */
	public List<Workflow> getListaWorkflows() {
		return this.listaWorkflows;
	}

	/**
	 * Recupera o nome do workflow.
	 * 
	 * @return nome do workflow
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera o workflow selecionado
	 * 
	 * @return workflow selecionado
	 */
	public Integer getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui o indicador de ativo do workflow.
	 * 
	 * @param ativo indicador de ativo do workflow
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Atribui a descrição do workflow.
	 * 
	 * @param descricao descrição do workflow
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * Atribui a lista de workflows
	 * 
	 * @param listaWorkflows {@link List} de {@link Workflow}
	 */
	public void setListaWorkflows(List<Workflow> listaWorkflows) {
		this.listaWorkflows = listaWorkflows;
	}


	/**
	 * Atribui o nome do workflow.
	 * 
	 * @param nome nome do workflow
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Atribui o workflow a ser iniciado
	 * 
	 * @param workflow workflow a ser iniciado
	 */
	public void setWorkflow(Integer workflow) {
		this.workflow = workflow;
	}
}
