/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * Interface para acesso aos dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
public interface UsuarioDAO extends BaseDAO<Usuario, Integer> {

	/**
	 * Recupera um {@link Usuario} a partir do seu login.
	 * 
	 * @param login login do usuário
	 * @return usuário encontrado
	 */
	public Usuario recuperarPorLogin(String login);
}
