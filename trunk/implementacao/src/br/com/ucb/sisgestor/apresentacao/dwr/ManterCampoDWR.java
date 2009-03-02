/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.TipoCampoEnum;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaCampoDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de manter campo do projeto.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoDWR extends BaseDWR {

	private CampoBO	campoBO;

	/**
	 * Pesquisa o {@link Campo} pelo id.
	 * 
	 * @param id identificador do campo
	 * @return campo encontrado
	 */
	public Campo getById(Integer id) {
		return this.campoBO.obter(id);
	}

	/**
	 * Recupera a lista de tipos de campo.
	 * 
	 * @return {@link List} com array de {@link Object}, onde: [0] - id do tipo, [1] - name do tipo, [2] -
	 *         descrição do tipo
	 */
	public List<Object[]> getTipos() {
		//método gambiarra feito, pois o DWR não converte de forma que conseguimos pegar os dados de uma enum
		List<Object[]> tipos = new ArrayList<Object[]>();
		for (TipoCampoEnum tipo : TipoCampoEnum.values()) {
			tipos.add(new Object[] {tipo.getId(), tipo.name(), tipo.getDescricao()});
		}
		return tipos;
	}

	/**
	 * Pesquisa os campos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Campo}
	 */
	public ListaResultadoDTO<Campo> pesquisar(PesquisaCampoDTO parametros) {
		String nome = parametros.getNome();
		Integer idTipo = parametros.getTipo();
		Integer paginaAtual = parametros.getPaginaAtual();
		Integer idWorkflow = parametros.getIdWorkflow();

		List<Campo> lista = this.campoBO.getByNomeTipo(nome, idTipo, idWorkflow, paginaAtual);

		ListaResultadoDTO<Campo> resultado = new ListaResultadoDTO<Campo>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, this.campoBO);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Campo}.
	 * 
	 * @param campoBO BO de {@link Campo}
	 */
	@Autowired
	public void setCampoBO(CampoBO campoBO) {
		this.campoBO = campoBO;
	}
}
