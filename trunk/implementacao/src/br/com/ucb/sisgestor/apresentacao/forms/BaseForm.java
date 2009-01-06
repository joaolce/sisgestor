/*
 * Projeto: sisgestor
 * Cria��o: 29/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.util.Utils;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Base para os forms do struts.
 * 
 * @author Jo�o L�cio
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
	 * Busca uma mensagem no arquivo de propriedades
	 * 
	 * @param property chave da propriedade
	 * @return valor da propriedade
	 */
	protected String getMessage(String property) {
		return Utils.getMessageFromProperties(property);
	}
}
