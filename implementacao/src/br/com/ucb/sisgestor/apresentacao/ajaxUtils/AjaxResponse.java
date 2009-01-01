/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
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
 * @author João Lúcio
 * @since 24/10/2008
 */
public class AjaxResponse {

	private DOMElement				ajaxResponse;
	private Document					document;
	private Map<String, Object>	valoresDevolvidos;

	/**
	 * Cria uma nova instância do tipo AjaxResponse
	 */
	public AjaxResponse() {
		this.document = DocumentFactory.getInstance().createDocument();
		this.document.setXMLEncoding("UTF-8");
		this.ajaxResponse = new DOMElement("ajaxResponse");
		this.document.add(this.ajaxResponse);
	}

	/**
	 * Adiciona uma mensagem no response
	 * 
	 * @param message mensagem a adicionar
	 */
	public void addMessage(String message) {
		DOMElement element = new DOMElement("message");
		element.setText(message);
		this.ajaxResponse.add(element);
	}

	/**
	 * Adiciona um elemento de retorno para o Javascript
	 * 
	 * @param chave chave do elemento
	 * @param valor valor do elemento
	 */
	public void putValorRetorno(String chave, Object valor) {
		if (this.valoresDevolvidos == null) {
			this.valoresDevolvidos = new HashMap<String, Object>();
		}
		this.valoresDevolvidos.put(chave, valor);
	}

	/**
	 * Nome do campo para onde o foco deverá ser movido quando a resposta retornar ao Javascript
	 * 
	 * @param focusControl nome do campo
	 */
	public void setFocusControl(String focusControl) {
		this.setNode("focusControl", focusControl);
	}

	/**
	 * id gerado do objeto, utilizado geralmente para retornar ao Javascript o id do objeto que foi inserido,
	 * excluído ou alterado
	 * 
	 * @param id id do objeto
	 */
	public void setGeneratedID(Object id) {
		if (id != null) {
			this.setNode("generatedId", id.toString());
		}
	}

	/**
	 * Status da requisição para indicar se a requisição foi bem sucedida ou mal sucedida (Ex. se um registro
	 * foi inserido com sucesso ou se não foi devido alguma validação faltando ou qualquer erro que tenha
	 * ocorrido)
	 * 
	 * @param estado status da requisição: <code>true</code> - verde, <code>false</code> - vermelho
	 */
	public void setStatus(boolean estado) {
		this.setNode("status", new Boolean(estado).toString());
	}

	/**
	 * URL que o Javascript utilizará para redirecionar o usuário para outra página
	 * 
	 * @param url url de redirecionamento
	 */
	public void setUrlForward(String url) {
		this.setNode("urlForward", url);
	}

	/**
	 * Já imprime o Ajax Response no formato XML
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DOMElement jsonObject = this.getValoresRetorno();
		if (jsonObject != null) {
			this.ajaxResponse.add(jsonObject);
		}
		return this.document.asXML();
	}

	/**
	 * Retorna o nó contendo os valores de retorno
	 * 
	 * @return o {@link DOMElement} com os valores de retorno
	 */
	private DOMElement getValoresRetorno() {
		if (this.valoresDevolvidos == null) {
			return null;
		}
		DOMElement parentNodeJson = new DOMElement("valoresDevolvidos");
		for (String chave : this.valoresDevolvidos.keySet()) {
			String valor = this.valoresDevolvidos.get(chave).toString();

			DOMElement atributo = new DOMElement(chave);
			atributo.addCDATA(valor);
			parentNodeJson.add(atributo);
		}
		return parentNodeJson;
	}

	/**
	 * Adiciona um nó ao ajaxResponse
	 * 
	 * @param node nome do nó
	 * @param value valor do nó
	 */
	private void setNode(String node, String value) {
		List<Node> selectNodes = GenericsUtil.checkedList(this.ajaxResponse.elements(node), Node.class);
		DOMElement element = new DOMElement(node);
		element.setText(value);
		if (selectNodes.size() == 0) {
			this.ajaxResponse.add(element);
		} else {
			this.ajaxResponse.replaceChild(element, selectNodes.get(0));
		}
	}
}
