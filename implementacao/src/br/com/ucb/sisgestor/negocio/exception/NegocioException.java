/*
 * Projeto: sisgestor
 * Cria��o: 28/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.exception;

/**
 * Exce��o de neg�cio
 * 
 * @author Jo�o L�cio
 * @since 28/12/2008
 */
public class NegocioException extends Exception {

	private String[]	args;

	/**
	 * Construtor recebe uma mensagem que est� no properties
	 * 
	 * @param message mensagem
	 * @param args argumentos da mensagem
	 */
	public NegocioException(String message, String... args) {
		super(message);
		this.args = args;
	}


	/**
	 * Cria uma nova inst�ncia do tipo {@link NegocioException}.
	 * 
	 * @param causa causa da exce��o
	 */
	public NegocioException(Throwable causa) {
		super(causa);
	}

	/**
	 * Recupera os argumentos da mensagem
	 * 
	 * @return argumentos da mensagem
	 */
	public String[] getArgs() {
		return this.args;
	}

	/**
	 * Atribu� os argumentos da mensagem
	 * 
	 * @param args argumentos da mensagem
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}
}
