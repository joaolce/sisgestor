/*
 * Projeto: sisgestor
 * Cria��o: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.persistencia.BaseDAO;
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
	 * @return usuario encontrado
	 */
	public Usuario getById(Integer id) {
		return usuarioBO.obter(id);
	}

	/**
	 * Recupera todas permiss�es.
	 * 
	 * @return {@link List} de {@link Permissao}
	 */
	public List<Permissao> getTodasPermissoes() {
		return usuarioBO.getTodasPermissoes();
	}


	/**
	 * Pesquisa os usu�rios com os par�metros preenchidos.
	 * 
	 * @param login parte do login do usu�rio
	 * @param nome parte do nome do usu�rio
	 * @param departamento departamento do usu�rio
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Usuario}
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
