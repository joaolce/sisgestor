/*
 * Projeto: SisGestor
 * Cria��o: 25/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.ajaxUtils;

/**
 * Exce��o de sess�o expirada
 * 
 * @author Jo�o L�cio
 * @since 25/10/2008
 */
public class SessionExpiredException extends SecurityException {

	/**
	 * Cria uma exce��o do tipo SessionExpiredException
	 * 
	 * @param message menssagem da exce��o
	 */
	public SessionExpiredException(String message) {
		super(message);
	}
}
