/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.PermissaoBOImpl;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaUsuarioDTO;
import java.util.List;
import org.hibernate.Hibernate;

/**
 * Objeto DWR de manter usuário do projeto.
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
	 * @param id identificador do usuário
	 * @return usuario encontrado
	 */
	public Usuario getById(Integer id) {
		Usuario usuario = usuarioBO.obter(id);
		Hibernate.initialize(usuario.getPermissoes());
		return usuario;
	}

	/**
	 * Recupera todas permissões.
	 * 
	 * @return {@link List} de {@link Permissao}
	 */
	public List<Permissao> getTodasPermissoes() {
		return PermissaoBOImpl.getInstancia().obterTodos();
	}

	/**
	 * Pesquisa os usuários com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Usuario}
	 */
	public ListaResultadoDTO<Usuario> pesquisar(PesquisaUsuarioDTO parametros) {
		String login = parametros.getLogin();
		String nome = parametros.getNome();
		Integer departamento = parametros.getDepartamento();
		Integer paginaAtual = parametros.getPaginaAtual();

		List<Usuario> lista = usuarioBO.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);

		ListaResultadoDTO<Usuario> resultado = new ListaResultadoDTO<Usuario>();
		resultado.setColecaoParcial(lista);

		this.setTotalPesquisa(parametros, resultado, usuarioBO);
		return resultado;
	}
}
