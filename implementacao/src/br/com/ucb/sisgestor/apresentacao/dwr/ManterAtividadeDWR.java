/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.impl.AtividadeBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaAtividadeDTO;
import java.util.List;

/**
 * Objeto DWR de manter atividade do projeto.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeDWR extends BaseDWR {

	private static AtividadeBO	atividadeBO;

	static {
		atividadeBO = AtividadeBOImpl.getInstancia();
	}

	/**
	 * Pesquisa a {@link Atividade} pelo id.
	 * 
	 * @param id identificador da atividade
	 * @return atividade encontrada
	 */
	public Atividade getById(Integer id) {
		return atividadeBO.obter(id);
	}

	/**
	 * Pesquisa as atividades com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Atividade}
	 */
	public ListaResultadoDTO<Atividade> pesquisar(PesquisaAtividadeDTO parametros) {
		String nome = parametros.getNome();
		String descricao = parametros.getDescricao();
		Integer idProcesso = parametros.getIdProcesso();
		Integer paginaAtual = parametros.getPaginaAtual();


		List<Atividade> lista = atividadeBO.getByNomeDescricao(nome, descricao, idProcesso, paginaAtual);

		ListaResultadoDTO<Atividade> resultado = new ListaResultadoDTO<Atividade>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, atividadeBO);
		return resultado;
	}
}
