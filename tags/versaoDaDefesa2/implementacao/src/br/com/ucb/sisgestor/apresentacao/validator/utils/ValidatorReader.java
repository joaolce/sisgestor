/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator.utils;

import br.com.ucb.sisgestor.apresentacao.validator.BaseValidator;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Classe que mapeia as actions com as suas respectivas classes de validação.
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
public class ValidatorReader {

	private static final Log						LOG				= LogFactory.getLog(ValidatorReader.class);
	private static final Map<String, String>	validatorMap	= new TreeMap<String, String>();

	static {
		try {
			InputStream input =
					ValidatorReader.class
							.getResourceAsStream("/br/com/ucb/sisgestor/apresentacao/validator/utils/validator.xml");

			SAXReader reader = new SAXReader();
			Document xml = reader.read(input);
			Element root = xml.getRootElement();

			List<Element> children = GenericsUtil.checkedList(root.elements(), Element.class);

			String action;
			String validator;

			for (Element child : children) {
				action = child.attributeValue("action");
				validator = child.attributeValue("validator");

				validatorMap.put(action, validator);
			}
		} catch (DocumentException e) {
			LOG.error(e); //NOPMD by João Lúcio - apenas para logar
		}
	}

	/**
	 * Retorna um objeto do tipo {@link BaseValidator} de acordo com a action informada
	 * 
	 * @param action action a ser executada
	 * 
	 * @return classe de validação da action, ou <code>null</code> caso não houver
	 * @throws Exception caso exceção seja lançada
	 */
	public static BaseValidator getValidator(String action) throws Exception {
		BaseValidator validator = null;

		String nome = validatorMap.get(action);
		if (StringUtils.isNotBlank(nome)) {
			Class<?> classe = Class.forName(nome);
			validator = (BaseValidator) classe.newInstance();
		}
		return validator;
	}
}
