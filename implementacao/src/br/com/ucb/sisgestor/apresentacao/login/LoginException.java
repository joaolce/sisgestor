/*
 * Projeto: SisGestor
 * Cria��o: 09/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.login;

/**
 * TODO DOCUMENT ME!
 * 
 * @author Jo�o L�cio
 * @since 09/12/2008
 */
public class LoginException extends Exception {

	private int	errorCode;

	/**
	 * Cria uma nova inst�ncia do tipo LoginException
	 * 
	 * @param mensagem
	 */
	public LoginException(String mensagem) {
		this(mensagem, 1);
	}

	/**
	 * Cria uma nova inst�ncia do tipo LoginException
	 * 
	 * @param mensagem
	 * @param errorCode
	 */
	public LoginException(String mensagem, int errorCode) {
		super(mensagem);
		this.errorCode = errorCode;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
}
