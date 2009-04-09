/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.UsarWorkflowAction;
import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.util.List;
import org.apache.struts.upload.FormFile;

/**
 * Form para a action {@link UsarWorkflowAction}.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowActionForm extends BaseForm {

	private Integer[]			anexosSelecionados;
	private List<Workflow>	listaWorkflows;
	private List<Anexo>		listaAnexos;
	private FormFile			arquivo;
	private Integer			workflow;
	private Integer			usoWorkflow;

	/**
	 * Recupera os anexos selecionados
	 * 
	 * @return anexos selecionados
	 */
	public Integer[] getAnexosSelecionados() {
		return this.anexosSelecionados;
	}

	/**
	 * Recupera o arquivo
	 * 
	 * @return arquivo
	 */
	public FormFile getArquivo() {
		return this.arquivo;
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
	 * Recupera o identificador do usoWorkflow. <br />
	 * Feito para utilizar nos anexos
	 * 
	 * @return identificador do usoWorkflow
	 */
	public Integer getUsoWorkflow() {
		return this.usoWorkflow;
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
	 * Atribui o arquivo
	 * 
	 * @param arquivo o arquivo a ser carregado
	 */
	public void setArquivo(FormFile arquivo) {
		this.arquivo = arquivo;
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
	 * Atribui o identificador do usoWorkflow. <br />
	 * Feito para utilizar nos anexos
	 * 
	 * @param usoWorkflow identificador do usoWorkflow
	 */
	public void setUsoWorkflow(Integer usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
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
