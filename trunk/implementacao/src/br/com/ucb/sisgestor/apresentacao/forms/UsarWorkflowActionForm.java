/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Form para a action {@link UsarWorkflowAction}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowActionForm extends BaseForm {

	private Integer			workflow;
	private List<Workflow>	listaWorkflows;
	private List<Tarefa>		proximasTarefas;
	private Integer			tarefa;

	/**
	 * Recupera a lista de workflows disponíveis para serem iniciados
	 * 
	 * @return lista de workflows
	 */
	public List<Workflow> getListaWorkflows() {
		return this.listaWorkflows;
	}

	/**
	 * Recupera as próximas tarefas disponíveis.
	 * 
	 * @return próximas tarefas disponíveis
	 */
	public List<Tarefa> getProximasTarefas() {
		return this.proximasTarefas;
	}

	/**
	 * Recupera o identificador da tarefa atual.
	 * 
	 * @return identificador da tarefa atual
	 */
	public Integer getTarefa() {
		return this.tarefa;
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
	 * Atribui a lista de workflows
	 * 
	 * @param listaWorkflows {@link List} de {@link Workflow}
	 */
	public void setListaWorkflows(List<Workflow> listaWorkflows) {
		this.listaWorkflows = listaWorkflows;
	}

	/**
	 * Atribui as próximas tarefas disponíveis.
	 * 
	 * @param proximasTarefas próximas tarefas disponíveis
	 */
	public void setProximasTarefas(List<Tarefa> proximasTarefas) {
		this.proximasTarefas = proximasTarefas;
	}

	/**
	 * Atribui o identificador da tarefa atual.
	 * 
	 * @param tarefa identificador da tarefa atual
	 */
	public void setTarefa(Integer tarefa) {
		this.tarefa = tarefa;
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
