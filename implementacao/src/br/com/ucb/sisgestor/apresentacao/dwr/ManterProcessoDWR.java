/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.impl.ProcessoBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaProcessoDTO;
import java.util.List;

/**
 * Objeto DWR de manter processo do projeto.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoDWR extends BaseDWR {

	private static ProcessoBO	processoBO;

	static {
		processoBO = ProcessoBOImpl.getInstancia();
	}

	/**
	 * Pesquisa o {@link Processo} pelo id.
	 * 
	 * @param id identificador do processo
	 * @return processo encontrado
	 */
	public Processo getById(Integer id) {
		return processoBO.obter(id);
	}

	/**
	 * Pesquisa os processos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Processo}
	 */
	public ListaResultadoDTO<Processo> pesquisar(PesquisaProcessoDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer idWorkflow = parametros.getIdWorkflow();
		Integer paginaAtual = parametros.getPaginaAtual();


		List<Processo> lista = processoBO.getByNomeDescricao(nome, descricao, idWorkflow, paginaAtual);

		ListaResultadoDTO<Processo> resultado = new ListaResultadoDTO<Processo>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, processoBO);
		return resultado;
	}
}
