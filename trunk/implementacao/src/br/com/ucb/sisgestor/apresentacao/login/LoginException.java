/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public class LoginException extends Exception {

	private int	errorCode;

	/**
	 * Cria uma nova instância do tipo LoginException
	 * 
	 * @param mensagem
	 */
	public LoginException(String mensagem) {
		this(mensagem, 1);
	}

	/**
	 * Cria uma nova instância do tipo LoginException
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
