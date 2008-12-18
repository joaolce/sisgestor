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

	private static final long	serialVersionUID	= 0xa998328a3dd61e5aL;

	private int						errorCode;

	private String					operador;

	private String					unidade;

	private String					dependencia;

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
		this.errorCode = 0;
		this.operador = null;
		this.unidade = null;
		this.dependencia = null;
		this.errorCode = errorCode;
	}

	/**
	 * Cria uma nova instância do tipo LoginException
	 * 
	 * @param mensagem
	 * @param errorCode
	 * @param unidade
	 * @param dependencia
	 * @param operador
	 * @param cause
	 */
	public LoginException(String mensagem, int errorCode, String unidade, String dependencia, String operador,
			Throwable cause) {
		super(mensagem, cause);
		this.errorCode = 0;
		this.operador = null;
		this.unidade = null;
		this.dependencia = null;
		this.errorCode = errorCode;
		this.operador = operador;
		this.dependencia = dependencia;
		this.unidade = unidade;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getDependencia() {
		return this.dependencia;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getOperador() {
		return this.operador;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public String getUnidade() {
		return this.unidade;
	}
}
