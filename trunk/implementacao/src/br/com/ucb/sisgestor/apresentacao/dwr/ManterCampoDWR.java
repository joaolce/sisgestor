/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.negocio.impl.CampoBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaCampoDTO;

import java.util.List;

/**
 * Objeto DWR de manter campo do projeto.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoDWR extends BaseDWR {

	private static CampoBO	campoBO;

	static {
		campoBO = CampoBOImpl.getInstancia();
	}

	/**
	 * Pesquisa o {@link Campo} pelo id.
	 * 
	 * @param id identificador do campo
	 * @return campo encontrado
	 */
	public Campo getById(Integer id) {
		return campoBO.obter(id);
	}

	/**
	 * Pesquisa os campos com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Campo}
	 */
	public ListaResultadoDTO<Campo> pesquisar(PesquisaCampoDTO parametros) {
		//TODO Implemente-me
		return null;
	}
}
