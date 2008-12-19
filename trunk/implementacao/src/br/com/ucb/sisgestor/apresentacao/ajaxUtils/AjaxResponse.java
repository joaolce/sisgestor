/*
 * Projeto: SisGestor
 * Cria��o: 24/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.ajaxUtils;

import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Node;

/**
 * Classe que gera o XML para o Javascript interpretar
 * 
 * @author Jo�o L�cio
 * @since 24/10/2008
 */
public class AjaxResponse {

	private DOMElement				ajaxResponse;
	private Document					document;
	private Map<String, Object>	valoresDevolvidos;

	/**
	 * Cria uma nova inst�ncia do tipo AjaxResponse
	 */
	public AjaxResponse() {
		document = DocumentFactory.getInstance().createDocument();
		document.setXMLEncoding("UTF-8");
		ajaxResponse = new DOMElement("ajaxResponse");
		document.add(ajaxResponse);
	}

	/**
	 * Adiciona uma mensagem no response
	 * 
	 * @param message mensagem a adicionar
	 */
	public void addMessage(String message) {
		DOMElement element = new DOMElement("message");
		element.setText(message);
		ajaxResponse.add(element);
	}

	/**
	 * Adiciona um elemento de retorno para o Javascript
	 * 
	 * @param chave chave do elemento
	 * @param valor valor do elemento
	 */
	public void putValorRetorno(String chave, Object valor) {
		if (valoresDevolvidos == null) {
			valoresDevolvidos = new HashMap<String, Object>();
		}
		valoresDevolvidos.put(chave, valor);
	}

	/**
	 * Nome do campo para onde o foco dever� ser movido quando a resposta retornar ao Javascript
	 * 
	 * @param focusControl nome do campo
	 */
	public void setFocusControl(String focusControl) {
		setNode("focusControl", focusControl);
	}

	/**
	 * id gerado do objeto, utilizado geralmente para retornar ao Javascript o id do objeto que foi inserido,
	 * exclu�do ou alterado
	 * 
	 * @param id id do objeto
	 */
	public void setGeneratedID(Object id) {
		if (id != null) {
			setNode("generatedId", id.toString());
		}
	}

	/**
	 * Status da requisi��o para indicar se a requisi��o foi bem sucedida ou mal sucedida (Ex. se um registro
	 * foi inserido com sucesso ou se n�o foi devido alguma valida��o faltando ou qualquer erro que tenha
	 * ocorrido)
	 * 
	 * @param estado status da requisi��o
	 */
	public void setStatus(boolean estado) {
		setNode("status", new Boolean(estado).toString());
	}

	/**
	 * URL que o Javascript utilizar� para redirecionar o usu�rio para outra p�gina
	 * 
	 * @param url url de redirecionamento
	 */
	public void setUrlForward(String url) {
		setNode("urlForward", url);
	}

	/**
	 * J� imprime o Ajax Response no formato XML
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DOMElement jsonObject = getValoresRetorno();
		if (jsonObject != null) {
			ajaxResponse.add(jsonObject);
		}
		return document.asXML();
	}

	/**
	 * Retorna o n� contendo os valores de retorno
	 * 
	 * @return o {@link DOMElement} com os valores de retorno
	 */
	private DOMElement getValoresRetorno() {
		if (valoresDevolvidos == null) {
			return null;
		}
		DOMElement parentNodeJson = new DOMElement("valoresDevolvidos");
		for (String chave : valoresDevolvidos.keySet()) {
			String valor = valoresDevolvidos.get(chave).toString();

			DOMElement atributo = new DOMElement(chave);
			atributo.addCDATA(valor);
			parentNodeJson.add(atributo);
		}
		return parentNodeJson;
	}

	/**
	 * Adiciona um n� ao ajaxResponse
	 * 
	 * @param node nome do n�
	 * @param value valor do n�
	 */
	private void setNode(String node, String value) {
		List<Node> selectNodes = GenericsUtil.checkedList(ajaxResponse.elements(node), Node.class);
		DOMElement element = new DOMElement(node);
		element.setText(value);
		if (selectNodes.size() == 0) {
			ajaxResponse.add(element);
		} else {
			ajaxResponse.replaceChild(element, selectNodes.get(0));
		}
	}
}
