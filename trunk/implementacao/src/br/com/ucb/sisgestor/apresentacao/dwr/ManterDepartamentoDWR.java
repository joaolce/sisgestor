/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaDepartamentoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de manter departamento do projeto.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterDepartamentoDWR extends BaseDWR {

	private DepartamentoBO	departamentoBO;

	/**
	 * Pesquisa o {@link Departamento} pelo id.
	 * 
	 * @param id identificador do departamento
	 * @return Departamento
	 */
	public Departamento getById(Integer id) {
		return this.departamentoBO.obter(id);
	}

	/**
	 * Recupera todos os departamentos cadastrados.
	 * 
	 * @return um {@link List} de {@link Departamento}
	 */
	public List<Departamento> obterTodos() {
		return this.departamentoBO.obterTodos();
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

		List<Departamento> lista = this.departamentoBO.getBySiglaNome(sigla, nome, paginaAtual);

		ListaResultadoDTO<Departamento> resultado = new ListaResultadoDTO<Departamento>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, this.departamentoBO);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Departamento}.
	 * 
	 * @param departamentoBO BO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoBO(DepartamentoBO departamentoBO) {
		this.departamentoBO = departamentoBO;
	}
}
