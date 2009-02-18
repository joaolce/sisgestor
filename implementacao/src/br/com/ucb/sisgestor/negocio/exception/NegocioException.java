/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.exception;

/**
 * Exceção de negócio
 * 
 * @author João Lúcio
 * @since 28/12/2008
 */
public class NegocioException extends Exception {

	private String[]	args;

	/**
	 * Construtor recebe uma mensagem que está no properties.
	 * 
	 * @param message mensagem
	 * @param args argumentos da mensagem
	 */
	public NegocioException(String message, String... args) {
		super(message);
		this.args = args;
	}

	/**
	 * Cria uma nova instância do tipo {@link NegocioException}.
	 * 
	 * @param causa causa da exceção
	 */
	public NegocioException(Throwable causa) {
		super(causa);
	}

	/**
	 * Recupera os argumentos da mensagem.
	 * 
	 * @return argumentos da mensagem
	 */
	public String[] getArgs() {
		return this.args.clone();
	}

	/**
	 * Atribuí os argumentos da mensagem.
	 * 
	 * @param args argumentos da mensagem
	 */
	public void setArgs(String[] args) {
		this.args = args.clone();
	}
}
