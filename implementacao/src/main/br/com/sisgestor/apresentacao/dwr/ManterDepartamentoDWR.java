/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.sisgestor.apresentacao.dwr;

import br.com.sisgestor.util.dto.ListaResultadoDTO;
import br.com.sisgestor.util.dto.PesquisaManterDepartamentoDTO;
import br.com.sisgestor.negocio.DepartamentoBO;
import br.com.sisgestor.entidade.Departamento;
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
	 * Recupera todos os departamentos ativos cadastrados.
	 * 
	 * @return um {@link List} de {@link Departamento}
	 */
	public List<Departamento> obterTodosAtivos() {
		return this.departamentoBO.obterTodosAtivos();
	}

	/**
	 * Pesquisa os departamentos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	public ListaResultadoDTO<Departamento> pesquisar(PesquisaManterDepartamentoDTO parametros) {
		String sigla = parametros.getSigla();
		String nome = parametros.getNome();
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<Departamento> resultado = new ListaResultadoDTO<Departamento>();
		Integer totalRegistros = this.getTotalRegistros(parametros, this.departamentoBO);
		if (totalRegistros > 0) {
			List<Departamento> lista = this.departamentoBO.getBySiglaNome(sigla, nome, paginaAtual);
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
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
