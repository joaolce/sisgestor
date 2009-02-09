/*
 * Projeto: sisgestor
 * Criação: 29/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.util.Utils;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Base para os forms do struts.
 * 
 * @author João Lúcio
 * @since 29/12/2008
 */
public class BaseForm extends ActionForm {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
		Utils.resetProperties(this);
	}

	/**
	 * Busca uma mensagem no arquivo de propriedades.
	 * 
	 * @param key chave da propriedade
	 * @param args argumentos da mensagem a substituir
	 * @return valor da propriedade
	 */
	protected String getMessage(String key, String... args) {
		return Utils.getMessageFromProperties(key, args);
	}
}
