/*
 * Projeto: sisgestor
 * Criação: 09/04/2009 por João Lúcio
 */
package br.com.sisgestor.apresentacao.forms;

import br.com.sisgestor.apresentacao.actions.AnexoUsoWorkflowAction;
import br.com.sisgestor.entidade.Anexo;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.upload.FormFile;

/**
 * Form para a action {@link AnexoUsoWorkflowAction}.
 * 
 * @author João Lúcio
 * @since 09/04/2009
 */
public class AnexoUsoWorkflowActionForm extends BaseForm {

	private Integer usoWorkflow;
	private FormFile arquivo;
	private Integer[] anexosSelecionados;
	private List<Anexo> anexos;

	/**
	 * Recupera os anexos do uso.
	 * 
	 * @return anexos do uso
	 */
	public List<Anexo> getAnexos() {
		return this.anexos;
	}

	/**
	 * Recupera os anexos selecionados
	 * 
	 * @return anexos selecionados
	 */
	public Integer[] getAnexosSelecionados() {
		return (Integer[]) ArrayUtils.clone(this.anexosSelecionados);
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
	 * Recupera o identificador do usoWorkflow.
	 * 
	 * @return identificador do usoWorkflow
	 */
	public Integer getUsoWorkflow() {
		return this.usoWorkflow;
	}

	/**
	 * Atribui os anexos do uso.
	 * 
	 * @param anexos anexos do uso
	 */
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Atribui os anexos selecionados
	 * 
	 * @param anexosSelecionados Anexos selecionados na página
	 */
	public void setAnexosSelecionados(Integer[] anexosSelecionados) {
		this.anexosSelecionados = (Integer[]) ArrayUtils.clone(anexosSelecionados);
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
	 * Atribui o identificador do usoWorkflow.
	 * 
	 * @param usoWorkflow identificador do usoWorkflow
	 */
	public void setUsoWorkflow(Integer usoWorkflow) {
		this.usoWorkflow = usoWorkflow;
	}
}
