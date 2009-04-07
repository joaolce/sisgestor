/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;
import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;

/**
 * Form para a action {@link UsarWorkflowAction}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowActionForm extends BaseForm {

	private Integer[]			anexosSelecionados;
	private Boolean			ativo;
	private String				descricao;
	private String				nome;
	private List<Workflow>	listaWorkflows;
	private List<Anexo>		listaAnexos;
	private String				nomeAnexo;
	private Integer			idAnexo;
	private Integer			workflow;

	/**
	 * Recupera os anexos selecionados
	 * 
	 * @return anexos selecionados
	 */
	public Integer[] getAnexosSelecionados() {
		return this.anexosSelecionados;
	}

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
	 * Recupera o id do anexo
	 * 
	 * @return id do anexo
	 */
	public Integer getIdAnexo() {
		return this.idAnexo;
	}

	/**
	 * Recupera uma lista de anexos
	 * 
	 * @return lista de anexos
	 */
	public List<Anexo> getListaAnexos() {
		return this.listaAnexos;
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
	 * Recupera o nome do anexo
	 * 
	 * @return nome do anexo
	 */
	public String getNomeAnexo() {
		return this.nomeAnexo;
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
	 * Atribui os anexos selecionados
	 * 
	 * @param anexosSelecionados Anexos selecionados na página
	 */
	public void setAnexosSelecionados(Integer[] anexosSelecionados) {
		this.anexosSelecionados = anexosSelecionados;
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
	 * Atribui o id do anexo
	 * 
	 * @param idAnexo id do anexo
	 */
	public void setIdAnexo(Integer idAnexo) {
		this.idAnexo = idAnexo;
	}


	/**
	 * Atribui a lista de anexos
	 * 
	 * @param listaAnexos Lista de anexos
	 */
	public void setListaAnexos(List<Anexo> listaAnexos) {
		this.listaAnexos = listaAnexos;
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
	 * Atribui o nome do anexo
	 * 
	 * @param nomeAnexo O nome do anexo
	 */
	public void setNomeAnexo(String nomeAnexo) {
		this.nomeAnexo = nomeAnexo;
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
