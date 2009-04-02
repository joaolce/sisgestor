/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaManterProcessoDTO;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de manter processo do projeto.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoDWR extends BaseDWR {

	private ProcessoBO	processoBO;

	/**
	 * Pesquisa o {@link Processo} pelo id.
	 * 
	 * @param id identificador do processo
	 * @return processo encontrado
	 */
	public Processo getById(Integer id) {
		return this.processoBO.obter(id);
	}

	/**
	 * Recupera todos os processos referenciados pelo workflow
	 * 
	 * @param idWorkflow Código identificador do workflow
	 * @return {@link List} de {@link Processo}
	 */
	public ListaResultadoDTO<Processo> getByWorkflow(Integer idWorkflow) {
		ListaResultadoDTO<Processo> resultado = new ListaResultadoDTO<Processo>();

		List<Processo> listaProcessos = this.processoBO.getByWorkflow(idWorkflow);
		for (Processo processo : listaProcessos) {
			Hibernate.initialize(processo.getTransacoesAnteriores());
			Hibernate.initialize(processo.getTransacoesPosteriores());
		}

		resultado.setColecaoParcial(listaProcessos);

		return resultado;
	}

	/**
	 * Pesquisa os processos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Processo}
	 */
	public ListaResultadoDTO<Processo> pesquisar(PesquisaManterProcessoDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer idWorkflow = parametros.getIdWorkflow();
		Integer paginaAtual = parametros.getPaginaAtual();


		List<Processo> lista = this.processoBO.getByNomeDescricao(nome, descricao, idWorkflow, paginaAtual);

		ListaResultadoDTO<Processo> resultado = new ListaResultadoDTO<Processo>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, this.processoBO);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Processo}.
	 * 
	 * @param processoBO BO de {@link Processo}
	 */
	@Autowired
	public void setProcessoBO(ProcessoBO processoBO) {
		this.processoBO = processoBO;
	}
}
