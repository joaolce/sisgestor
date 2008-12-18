/*
 * Projeto: SisGestor
 * Cria��o: 05/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Classe para adicionar par�metros a uma url especificada, os par�metros ir�o atrav�s da url.
 * 
 * @author Jo�o L�cio
 * @since 05/12/2008
 */
public final class ParametrosURL {

	private static String	encoding;
	private String				parametros;
	private String				url;

	static { //recuperando o encoding padr�o do sistema
		encoding = System.getProperty("file.encoding", "ISO-8859-1");
	}

	/**
	 * Cria uma nova inst�ncia do tipo ParametrosURL
	 */
	public ParametrosURL() {
		this.parametros = "";
	}

	/**
	 * Adiciona um par�metro a url passada
	 * 
	 * @param nome nome do par�metro
	 * @param valor valor do par�metro
	 */
	public synchronized void addParametro(String nome, Object valor) {
		try {
			if (valor == null) {
				this.parametros += URLEncoder.encode(nome, encoding) + "=";
			} else {
				this.parametros +=
						URLEncoder.encode(nome, encoding) + "=" + URLEncoder.encode(valor.toString(), encoding);
			}
		} catch (UnsupportedEncodingException e) {}
	}

	/**
	 * Armazena a url que ser� usada
	 * 
	 * @param url o valor a ajustar em url
	 */
	public void setURL(String url) {
		this.url = url;
	}

	/**
	 * Sobrescrito m�todo para escrever a {@link String} da url completa
	 */
	@Override
	public String toString() {
		int idx = this.url.indexOf('?');
		if (idx == -1) {
			return this.url + "?" + this.parametros;
		} else {
			StringBuffer sb = new StringBuffer(this.url);
			sb.insert(idx + 1, this.parametros + "&");
			return sb.toString();
		}
	}
}
