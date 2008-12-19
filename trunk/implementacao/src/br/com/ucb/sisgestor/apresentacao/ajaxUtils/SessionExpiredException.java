/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.ajaxUtils;

/**
 * Exceção de sessão expirada
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public class SessionExpiredException extends SecurityException {

	/**
	 * Cria uma exceção do tipo SessionExpiredException
	 * 
	 * @param message menssagem da exceção
	 */
	public SessionExpiredException(String message) {
		super(message);
	}
}
