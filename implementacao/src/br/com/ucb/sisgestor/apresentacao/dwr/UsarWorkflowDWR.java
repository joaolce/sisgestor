/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaUsarWorkflowDTO;
import java.util.List;
import org.hibernate.Hibernate;
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
	 * Recupera um uso de workflow a partir do identificador.
	 * 
	 * @param id identificador do uso
	 * @return {@link UsoWorkflow}
	 */
	public UsoWorkflow getById(Integer id) {
		return this.usoWorkflowBO.obter(id);
	}

	/**
	 * Recupera uma lista de campos do workflow pelo código identificador do uso workflow
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return lista de campos
	 */
	public List<Campo> getCamposByIdUsoWorkflow(Integer idUsoWorkflow) {
		List<Campo> campos = this.usoWorkflowBO.getCamposByIdUsoWorkflow(idUsoWorkflow);
		for (Campo campo : campos) {
			Hibernate.initialize(campo.getOpcoes());
		}
		return campos;
	}

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
