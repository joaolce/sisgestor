/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.persistencia.BaseDAO;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
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
	 * Pesquisa os departamentos com os parâmetros preenchidos.
	 * 
	 * @param sigla parte da sigla do departamento
	 * @param nome parte do nome do departamento
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Departamento}
	 */
	public ListaResultadoDTO<Departamento> pesquisar(String sigla, String nome, Integer paginaAtual) {
		List<Departamento> listaDepartamentos = departamentoBO.getBySiglaNome(sigla, nome, paginaAtual);

		ListaResultadoDTO<Departamento> dto = new ListaResultadoDTO<Departamento>();

		dto.setColecaoParcial(listaDepartamentos);

		//Busca o total de registros
		if ((paginaAtual == null) && !listaDepartamentos.isEmpty()) {
			Integer total = departamentoBO.getTotalRegistros(sigla, nome);
			this.setSessionAttribute(DadosContexto.TOTAL_PESQUISA, total);
			this.setSessionAttribute(DadosContexto.TAMANHO_PAGINA, BaseDAO.MAXIMO_RESULTADOS);
			dto.setTotalRegistros(total);
			dto.setQuantidadeRegistrosPagina(BaseDAO.MAXIMO_RESULTADOS);
		} else {
			dto.setTotalRegistros((Integer) this.getSessionAttribute(DadosContexto.TOTAL_PESQUISA));
			dto.setQuantidadeRegistrosPagina((Integer) this.getSessionAttribute(DadosContexto.TAMANHO_PAGINA));
		}
		return dto;
	}
}
