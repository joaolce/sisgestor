/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.impl.TarefaBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaTarefaDTO;
import java.util.List;

/**
 * Objeto DWR de manter tarefa do projeto.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaDWR extends BaseDWR {

	private static TarefaBO	tarefaBO;

	static {
		tarefaBO = TarefaBOImpl.getInstancia();
	}

	/**
	 * Pesquisa a {@link Tarefa} pelo id.
	 * 
	 * @param id identificador da tarefa
	 * @return tarefa encontrada
	 */
	public Tarefa getById(Integer id) {
		return tarefaBO.obter(id);
	}

	/**
	 * Pesquisa as tarefas com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Tarefa}
	 */
	public ListaResultadoDTO<Tarefa> pesquisar(PesquisaTarefaDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer usuario = parametros.getUsuario();
		Integer idAtividade = parametros.getIdAtividade();
		Integer paginaAtual = parametros.getPaginaAtual();


		List<Tarefa> lista =
				tarefaBO.getByNomeDescricaoUsuario(nome, descricao, usuario, idAtividade, paginaAtual);

		ListaResultadoDTO<Tarefa> resultado = new ListaResultadoDTO<Tarefa>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, tarefaBO);
		return resultado;
	}
}
