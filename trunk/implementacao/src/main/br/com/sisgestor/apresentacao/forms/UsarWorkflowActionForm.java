/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.sisgestor.apresentacao.forms;

import br.com.sisgestor.apresentacao.actions.UsarWorkflowAction;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.Workflow;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 * Form para a action {@link UsarWorkflowAction}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowActionForm extends BaseForm {

	private String[] valor;
	private Integer workflow;
	private List<Workflow> listaWorkflows;
	private List<Tarefa> proximasTarefas;
	private Integer tarefa;
	private String anotacao;

	/**
	 * Recupera a anotação do uso
	 * 
	 * @return anotação do uso
	 */
	public String getAnotacao() {
		return this.anotacao;
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
	 * Recupera os valores dos campos da página
	 * 
	 * @return valores dos campos
	 */
	public String[] getValor() {
		return (String[]) ArrayUtils.clone(this.valor);
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
	 * Atribui a anotação do uso
	 * 
	 * @param anotacao anotação do uso
	 */
	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
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
	 * Atribui os valores dos campos
	 * 
	 * @param valor valores dos campos
	 */
	public void setValor(String[] valor) {
		this.valor = (String[]) ArrayUtils.clone(valor);
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
