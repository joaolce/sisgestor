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

	private Integer	id;

	/**
	 * Recupera o c�digo identificador do objeto.
	 * 
	 * @return c�digo identificador do objeto
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
		Utils.resetProperties(this);
	}

	/**
	 * Atribui o c�digo identificador do objeto.
	 * 
	 * @param id c�digo identificador do objeto
	 */
	public void setId(Integer id) {
		this.id = id;
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
