/*
 * Projeto: sisgestor
 * Cria��o: 27/12/2008 por Jo�o L�cio
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
 * Classe que mapeia as roles permitidas nos m�todos das actions informadas.
 * 
 * @author Jo�o L�cio
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
			LOG.error(e); //NOPMD by Jo�o L�cio - apenas para logar
		}
	}

	/**
	 * Verifica se o usu�rio cont�m alguma das roles necess�rias para executar o m�todo da action informada.
	 * 
	 * @param usuario usu�rio a verificar se tem permiss�o
	 * @param action action a ser executada
	 * @param method m�todo da action a ser executado
	 * @return <code>true</code> caso tenha permiss�o, <code>false</code> caso contr�rio
	 */
	public boolean isUserInAnyRoles(Usuario usuario, String action, String method) {
		String regras = roleValidatorMap.get(action + "#" + method);

		//neste caso, n�o existe nenhuma regra, ent�o, pode passar
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
