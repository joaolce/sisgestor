/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaUsarWorkflowDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de usar workflow do projeto.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowDWR extends BaseDWR {

	private UsoWorkflowBO	usoWorkflowBO;

	/**
	 * Pesquisa os workflows com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	public ListaResultadoDTO<UsoWorkflow> pesquisar(PesquisaUsarWorkflowDTO parametros) {
		Integer paginaAtual = parametros.getPaginaAtual();

		List<UsoWorkflow> lista = this.usoWorkflowBO.recuperarPendentesUsuarioAtual(paginaAtual);

		ListaResultadoDTO<UsoWorkflow> resultado = new ListaResultadoDTO<UsoWorkflow>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, this.usoWorkflowBO);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link UsoWorkflowBO}.
	 * 
	 * @param usoWorkflowBO BO de {@link UsoWorkflowBO}
	 */
	@Autowired
	public void setUsoWorkflowBO(UsoWorkflowBO usoWorkflowBO) {
		this.usoWorkflowBO = usoWorkflowBO;
	}
}
