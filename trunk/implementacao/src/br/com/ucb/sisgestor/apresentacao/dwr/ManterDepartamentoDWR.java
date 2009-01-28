/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaDepartamentoDTO;
import java.util.List;

/**
 * Objeto DWR de manter departamento do projeto.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterDepartamentoDWR extends BaseDWR {

	private static DepartamentoBO	departamentoBO;

	static {
		departamentoBO = DepartamentoBOImpl.getInstancia();
	}

	/**
	 * Pesquisa o {@link Departamento} pelo id.
	 * 
	 * @param id identificador do departamento
	 * @return Departamento
	 */
	public Departamento getById(Integer id) {
		return departamentoBO.obter(id);
	}

	/**
	 * Recupera todos os departamentos cadastrados.
	 * 
	 * @return um {@link List} de {@link Departamento}
	 */
	public List<Departamento> obterTodos() {
		return departamentoBO.obterTodos();
	}

	/**
	 * Pesquisa os departamentos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	public ListaResultadoDTO<Departamento> pesquisar(PesquisaDepartamentoDTO parametros) {
		String sigla = parametros.getSigla();
		String nome = parametros.getNome();
		Integer paginaAtual = parametros.getPaginaAtual();

		List<Departamento> lista = departamentoBO.getBySiglaNome(sigla, nome, paginaAtual);

		ListaResultadoDTO<Departamento> resultado = new ListaResultadoDTO<Departamento>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, departamentoBO);
		return resultado;
	}
}
