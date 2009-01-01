/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * Interface para acesso aos dados de {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 01/01/2009
 */
public interface UsuarioDAO extends BaseDAO<Usuario, Integer> {

	/**
	 * Recupera um {@link Usuario} a partir do seu login.
	 * 
	 * @param login login do usu�rio
	 * @return usu�rio encontrado
	 */
	public Usuario recuperarPorLogin(String login);
}
