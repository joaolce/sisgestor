/*
 * Projeto: SisGestor
 * Cria��o: 05/12/2008 por Jo�o L�cio
 */
package br.com.sisgestor.util;

import org.apache.taglibs.standard.tag.common.core.ParamSupport;

/**
 * Classe para adicionar par�metros a uma url especificada, os par�metros ir�o atrav�s da url.
 * 
 * @author Jo�o L�cio
 * @since 05/12/2008
 */
public final class ParametrosURL {

	private ParamSupport.ParamManager	parametros;

	/**
	 * Cria uma nova inst�ncia do tipo {@link ParametrosURL}
	 */
	public ParametrosURL() {
		this.parametros = new ParamSupport.ParamManager();
	}

	/**
	 * Adiciona um par�metro a url passada
	 * 
	 * @param nome nome do par�metro
	 * @param valor valor do par�metro
	 */
	public void addParametro(String nome, Object valor) {
		if (valor == null) {
			this.parametros.addParameter(nome, "");
		} else {
			this.parametros.addParameter(nome, valor.toString());
		}
	}

	/**
	 * Agrega os par�metros adicionados a url passada, dever� ser chamado sempre por �ltimo
	 * 
	 * @param url url original
	 * @return url formada com os par�metros
	 */
	public String aggregateParams(String url) {
		return this.parametros.aggregateParams(url);
	}
}
