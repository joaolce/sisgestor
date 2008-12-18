/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator.utils;

import br.com.ucb.sisgestor.util.GenericsUtil;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
public class ValidateReader {

	private static final Map<String, String>	validateMap	= new HashMap<String, String>();

	static {
		try {
			//pega o arquivo como stream
			InputStream input =
					ValidateReader.class
							.getResourceAsStream("/br/com/ucb/sisgestor/apresentacao/validator/utils/validate.xml");

			SAXReader reader = new SAXReader();
			Document xml = reader.read(input);
			Element root = xml.getRootElement();

			List<Element> children = GenericsUtil.checkedList(root.elements(), Element.class);

			String action;
			String validate;
			//le todos os mapeamentos e joga na raiz
			for (Element child : children) {
				action = child.attributeValue("action");
				validate = child.attributeValue("validate");
				validateMap.put(action, validate);
			}
		} catch (DocumentException e) {
			LogFactory.getLog(ValidateReader.class).error(e);
		}
	}

	/**
	 * Retorna um objeto do tipo {@link BaseValidate} de acordo com a action informada
	 * 
	 * @param action action a ser executada
	 * 
	 * @return classe de validação da action
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static BaseValidate getValidate(String action) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		BaseValidate validate = null;

		String nome = validateMap.get(action);
		if (GenericValidator.isBlankOrNull(nome)) {
			return null;
		}
		Class<?> classe = Class.forName(nome);
		validate = (BaseValidate) classe.newInstance();

		return validate;
	}
}
