/*
 * Projeto: SisGestor
 * Criação: 05/12/2008 por João Lúcio
 */
package br.com.sisgestor.util;

import org.apache.taglibs.standard.tag.common.core.ParamSupport;

/**
 * Classe para adicionar parâmetros a uma url especificada, os parâmetros irão através da url.
 * 
 * @author João Lúcio
 * @since 05/12/2008
 */
public final class ParametrosURL {

	private ParamSupport.ParamManager	parametros;

	/**
	 * Cria uma nova instância do tipo {@link ParametrosURL}
	 */
	public ParametrosURL() {
		this.parametros = new ParamSupport.ParamManager();
	}

	/**
	 * Adiciona um parâmetro a url passada
	 * 
	 * @param nome nome do parâmetro
	 * @param valor valor do parâmetro
	 */
	public void addParametro(String nome, Object valor) {
		if (valor == null) {
			this.parametros.addParameter(nome, "");
		} else {
			this.parametros.addParameter(nome, valor.toString());
		}
	}

	/**
	 * Agrega os parâmetros adicionados a url passada, deverá ser chamado sempre por último
	 * 
	 * @param url url original
	 * @return url formada com os parâmetros
	 */
	public String aggregateParams(String url) {
		return this.parametros.aggregateParams(url);
	}
}
