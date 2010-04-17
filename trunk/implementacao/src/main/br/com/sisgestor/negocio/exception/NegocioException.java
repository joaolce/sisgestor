/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.sisgestor.negocio.exception;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;

/**
 * Exceção de negócio
 * 
 * @author João Lúcio
 * @since 28/12/2008
 */
public class NegocioException extends Exception {

	private String[] args;
	private Map<String, Object> valoresDevolvidos;

	/**
	 * Construtor recebe uma mensagem que está no properties.
	 * 
	 * @param message mensagem
	 * @param args argumentos da mensagem
	 */
	public NegocioException(String message, String... args) {
		super(message);
		this.args = (String[]) ArrayUtils.clone(args);
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
		return (String[]) ArrayUtils.clone(this.args);
	}

	/**
	 * Recupera os valores devolvidos da mensagem.
	 * 
	 * @return valores devolvidos da mensagem
	 */
	public Map<String, Object> getValoresDevolvidos() {
		return this.valoresDevolvidos;
	}

	/**
	 * Adiciona o valor devolvido da mensagem.
	 * 
	 * @param chave chave do valor
	 * @param valor valor devolvido
	 */
	public void putValorDevolvido(String chave, Object valor) {
		if (this.valoresDevolvidos == null) {
			this.valoresDevolvidos = new HashMap<String, Object>();
		}
		this.valoresDevolvidos.put(chave, valor);
	}

	/**
	 * Atribuí os argumentos da mensagem.
	 * 
	 * @param args argumentos da mensagem
	 */
	public void setArgs(String[] args) {
		this.args = (String[]) ArrayUtils.clone(args);
	}
}
