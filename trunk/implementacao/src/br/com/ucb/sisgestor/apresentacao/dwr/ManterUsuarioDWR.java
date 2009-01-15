/*
 * Projeto: sisgestor
 * Cria��o: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import java.util.List;

/**
 * Objeto DWR de manter usu�rio do projeto.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterUsuarioDWR extends BaseDWR {

	private static UsuarioBO	usuarioBO;

	static {
		usuarioBO = UsuarioBOImpl.getInstancia();
	}

	/**
	 * Pesquisa o {@link Usuario} pelo id.
	 * 
	 * @param id identificador do usu�rio
	 * @return Usuario
	 */
	public Usuario getById(Integer id) {
		return usuarioBO.obter(id);
	}

	/**
	 * 
	 * Pesquisa os usu�rios com os par�metros preenchidos.
	 * 
	 * @param login
	 * @param nome
	 * @param departamento
	 * @param paginaAtual
	 * @return Retorna uma lista de usu�rios
	 */
	public ListaResultadoDTO<Usuario> pesquisar(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		List<Usuario> listaUsuarios =
				usuarioBO.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);

		ListaResultadoDTO<Usuario> dto = new ListaResultadoDTO<Usuario>();

		dto.setColecaoParcial(listaUsuarios);

		//Busca o total de registros
		if ((paginaAtual == null) && !listaUsuarios.isEmpty()) {
			Integer total = usuarioBO.getTotalRegistros(login, nome, departamento);
			this.setSessionAttribute(DadosContexto.TOTAL_PESQUISA_SESSAO, total);
			dto.setTotalRegistros(total);
		} else {
			dto.setTotalRegistros((Integer) this.getSessionAttribute(DadosContexto.TOTAL_PESQUISA_SESSAO));
		}
		return dto;
	}
}
