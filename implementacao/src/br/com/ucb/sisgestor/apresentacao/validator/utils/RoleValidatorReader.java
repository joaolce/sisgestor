/*
 * Projeto: sisgestor
 * Criação: 27/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator.utils;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
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
 * Classe que mapeia as roles permitidas nos métodos das actions informadas.
 * 
 * @author João Lúcio
 * @since 27/12/2008
 */
public class RoleValidatorReader {

	private static final Map<String, String>	roleValidatorMap	= new TreeMap<String, String>();
	private static final Log						LOG					= LogFactory.getLog(RoleValidatorReader.class);

	static {
		try {
			InputStream input =
					RoleValidatorReader.class
							.getResourceAsStream("/br/com/ucb/sisgestor/apresentacao/validator/utils/roleValidator.xml");

			SAXReader reader = new SAXReader();
			Document xml = reader.read(input);
			Element root = xml.getRootElement();

			List<Element> children = GenericsUtil.checkedList(root.elements(), Element.class);

			String actionMethod;
			String roles;

			for (Element child : children) {
				actionMethod = child.attributeValue("actionMethod");
				roles = child.attributeValue("roles");

				roleValidatorMap.put(actionMethod, roles);
			}
		} catch (DocumentException e) {
			LOG.error(e); //NOPMD by João Lúcio - apenas para logar
		}
	}

	/**
	 * Verifica se o usuário contém alguma das roles necessárias para executar o método da action informada.
	 * 
	 * @param usuario usuário a verificar se tem permissão
	 * @param action action a ser executada
	 * @param method método da action a ser executado
	 * @return <code>true</code> caso tenha permissão, <code>false</code> caso contrário
	 */
	public boolean isUserInAnyRoles(Usuario usuario, String action, String method) {
		String regras = roleValidatorMap.get(action + "#" + method);

		//neste caso, não existe nenhuma regra, então, pode passar
		if (StringUtils.isBlank(regras)) {
			return true;
		}

		String[] roles = regras.split(",");

		for (Permissao permissao : usuario.getPermissoes()) {
			for (String role : roles) {
				if (permissao.getId().equals(Integer.parseInt(role))) {
					return true;
				}
			}
		}
		return false;
	}
}
