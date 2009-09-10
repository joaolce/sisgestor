/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.PermissaoBO;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaManterUsuarioDTO;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de manter usuário do projeto.
 * 
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterUsuarioDWR extends BaseDWR {

	private UsuarioBO		usuarioBO;
	private PermissaoBO	permissaoBO;

	/**
	 * Pesquisa o {@link Usuario} pelo id.
	 * 
	 * @param id identificador do usuário
	 * @return usuario encontrado
	 */
	public Usuario getById(Integer id) {
		Usuario usuario = this.usuarioBO.obter(id);
		Hibernate.initialize(usuario.getPermissoes());
		return usuario;
	}

	/**
	 * Recupera todas permissões.
	 * 
	 * @return {@link List} de {@link Permissao}
	 */
	public List<Permissao> getTodasPermissoes() {
		return this.permissaoBO.obterTodos();
	}

	/**
	 * Pesquisa os usuários com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link Usuario}
	 */
	public ListaResultadoDTO<Usuario> pesquisar(PesquisaManterUsuarioDTO parametros) {
		String login = parametros.getLogin();
		String nome = parametros.getNome();
		Integer departamento = parametros.getDepartamento();
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<Usuario> resultado = new ListaResultadoDTO<Usuario>();
		Integer totalRegistros = this.getTotalRegistros(parametros, this.usuarioBO);
		if (totalRegistros > 0) {
			List<Usuario> lista =
					this.usuarioBO.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
		return resultado;
	}

	/**
	 * Atribui o BO de {@link Permissao}.
	 * 
	 * @param permissaoBO BO de {@link Permissao}
	 */
	@Autowired
	public void setPermissaoBO(PermissaoBO permissaoBO) {
		this.permissaoBO = permissaoBO;
	}

	/**
	 * Atribui o BO de {@link Usuario}.
	 * 
	 * @param usuarioBO BO de {@link Usuario}
	 */
	@Autowired
	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}
}
