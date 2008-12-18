/*
 * Projeto: SisGestor
 * Criação: 05/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Classe para adicionar parâmetros a uma url especificada, os parâmetros irão através da url.
 * 
 * @author João Lúcio
 * @since 05/12/2008
 */
public final class ParametrosURL {

	private static String	encoding;
	private String				parametros;
	private String				url;

	static { //recuperando o encoding padrão do sistema
		encoding = System.getProperty("file.encoding", "ISO-8859-1");
	}

	/**
	 * Cria uma nova instância do tipo ParametrosURL
	 */
	public ParametrosURL() {
		this.parametros = "";
	}

	/**
	 * Adiciona um parâmetro a url passada
	 * 
	 * @param nome nome do parâmetro
	 * @param valor valor do parâmetro
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
	 * Armazena a url que será usada
	 * 
	 * @param url o valor a ajustar em url
	 */
	public void setURL(String url) {
		this.url = url;
	}

	/**
	 * Sobrescrito método para escrever a {@link String} da url completa
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
