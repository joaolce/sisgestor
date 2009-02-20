/*
 * Projeto: sisgestor
 * Cria��o: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaCampoDTO;
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
	 * Pesquisa os campos com os par�metros preenchidos.
	 * 
	 * @param parametros par�metros da pesquisa
	 * @return {@link List} de {@link Campo}
	 */
	public ListaResultadoDTO<Campo> pesquisar(PesquisaCampoDTO parametros) {
		String nome = parametros.getNome();
		Integer idTipo = parametros.getTipo();
		Integer paginaAtual = parametros.getPaginaAtual();

		List<Campo> lista = this.campoBO.getByNomeTipo(nome, idTipo, paginaAtual);

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
