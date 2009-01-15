/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
public interface UsuarioDAO extends BaseDAO<Usuario, Integer> {

	/**
	 * Retorna um {@link List} de {@link Usuario} a partir do login, nome e departamento
	 * 
	 * @param login
	 * @param nome
	 * @param departamento
	 * @param paginaAtual
	 * @return {@link List} de {@link Usuario}
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param login
	 * @param nome
	 * @param departamento
	 * @return total de registros
	 */
	public Integer getTotalRegistros(String login, String nome, Integer departamento);

	/**
	 * Recupera um {@link Usuario} a partir do seu login.
	 * 
	 * @param login login do usuário
	 * @return usuário encontrado
	 */
	public Usuario recuperarPorLogin(String login);
}
