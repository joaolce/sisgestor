/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaWorkflowDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de usar workflow do projeto.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowDWR extends BaseDWR {

	private WorkflowBO workflowBO;

	/**
	 * Pesquisa o {@link Workflow} pelo id.
	 * 
	 * @param id identificador do workflow
	 * @return workflow encontrado
	 */
	public Workflow getById(Integer id) {
		return this.workflowBO.obter(id);
	}

	/**
	 * Pesquisa os workflows com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Workflow}
	 */
	public ListaResultadoDTO<Workflow> pesquisar(PesquisaWorkflowDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Boolean ativo = parametros.getAtivo();
		Boolean excluidos = parametros.getExcluidos();
		Integer paginaAtual = parametros.getPaginaAtual();

		List<Workflow> lista =
				this.workflowBO.getByNomeDescricaoAtivo(nome, descricao, ativo, excluidos, paginaAtual);

		ListaResultadoDTO<Workflow> resultado = new ListaResultadoDTO<Workflow>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, this.workflowBO);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Workflow}.
	 * 
	 * @param workflowBO BO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowBO(WorkflowBO workflowBO) {
		this.workflowBO = workflowBO;
	}
}
