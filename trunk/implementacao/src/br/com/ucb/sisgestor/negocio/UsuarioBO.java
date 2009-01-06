/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;

/**
 * Interface para um objeto de neg�cio de {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public interface UsuarioBO extends BaseBO<Usuario, Integer> {

	/**
	 * Recupera um usu�rio a partir do seu login.
	 * 
	 * @param login login do usu�rio
	 * @return usu�rio encontrado
	 * @throws NegocioException
	 */
	public Usuario recuperarPorLogin(String login) throws NegocioException;
}
