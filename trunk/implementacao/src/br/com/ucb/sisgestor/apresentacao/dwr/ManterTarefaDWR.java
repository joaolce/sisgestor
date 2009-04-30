/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaManterTarefaDTO;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de manter tarefa do projeto.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaDWR extends BaseDWR {

	private TarefaBO	tarefaBO;

	/**
	 * Recupera todas as tarefas referenciadas pela atividade
	 * 
	 * @param idAtividade Código identificador da atividade
	 * @return {@link List} de {@link Tarefa}
	 */
	public ListaResultadoDTO<Tarefa> getByAtividade(Integer idAtividade) {
		ListaResultadoDTO<Tarefa> resultado = new ListaResultadoDTO<Tarefa>();

		List<Tarefa> listaTarefas = this.tarefaBO.getByAtividade(idAtividade);
		for (Tarefa tarefa : listaTarefas) {
			Hibernate.initialize(tarefa.getTransacoesAnteriores());
			Hibernate.initialize(tarefa.getTransacoesPosteriores());
		}

		resultado.setColecaoParcial(listaTarefas);

		return resultado;
	}

	/**
	 * Pesquisa a {@link Tarefa} pelo id.
	 * 
	 * @param id identificador da tarefa
	 * @return tarefa encontrada
	 */
	public Tarefa getById(Integer id) {
		return this.tarefaBO.obter(id);
	}

	/**
	 * Pesquisa as tarefas com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Tarefa}
	 */
	public ListaResultadoDTO<Tarefa> pesquisar(PesquisaManterTarefaDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer usuario = parametros.getUsuario();
		Integer idAtividade = parametros.getIdAtividade();
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<Tarefa> resultado = new ListaResultadoDTO<Tarefa>();
		Integer totalRegistros = this.getTotalRegistros(parametros, this.tarefaBO);
		if (totalRegistros > 0) {
			List<Tarefa> lista =
					this.tarefaBO.getByNomeDescricaoUsuario(nome, descricao, usuario, idAtividade, paginaAtual);
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Tarefa}.
	 * 
	 * @param tarefaBO BO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaBO(TarefaBO tarefaBO) {
		this.tarefaBO = tarefaBO;
	}
}
