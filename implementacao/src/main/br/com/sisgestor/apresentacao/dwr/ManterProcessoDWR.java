/*
 * Projeto: sisgestor
 * Cria��o: 10/01/2009 por Thiago
 */
package br.com.sisgestor.apresentacao.dwr;

import br.com.sisgestor.util.dto.ListaResultadoDTO;
import br.com.sisgestor.util.dto.PesquisaManterProcessoDTO;
import br.com.sisgestor.negocio.ProcessoBO;
import br.com.sisgestor.entidade.Processo;
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
	 * @param idWorkflow C�digo identificador do workflow
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
	 * Pesquisa os processos com os par�metros preenchidos.
	 * 
	 * @param parametros par�metros da pesquisa
	 * @return {@link List} de {@link Processo}
	 */
	public ListaResultadoDTO<Processo> pesquisar(PesquisaManterProcessoDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer idWorkflow = parametros.getIdWorkflow();
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<Processo> resultado = new ListaResultadoDTO<Processo>();
		Integer totalRegistros = this.getTotalRegistros(parametros, this.processoBO);
		if (totalRegistros > 0) {
			List<Processo> lista = this.processoBO.getByNomeDescricao(nome, descricao, idWorkflow, paginaAtual);
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
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
