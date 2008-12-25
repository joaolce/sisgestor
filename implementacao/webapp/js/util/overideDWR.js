/**
 * Sobrescrita de funcionalidade do DWR (Para compatibilidade com as versões antigas)
 * 
 * Sobrescreví o comportamento desse método para que ele continue se comportando como estava nas
 * versões 1.x.x .
 * 
 * Incrivelmente eles mudaram a implementação do setValue
 * 
 * Nas versões 1.x.x: <code>
 *      ...
 *		else if (ele.type == "checkbox") {
 *	    	ele.checked = val;
 *	    }
 *      ...
 * 	</code>
 * 
 * Com a implementação anterior era possível setar um valor boleano para indicar se um checkbox está
 * marcado ou não o que foi utilizado no sistema inteiro:
 * 
 * <code>
 * 	DWRUtil.setValue("checkboxQualquer", true) //deixa o checkbox marcado
 * 	DWRUtil.setValue("checkboxQualquer", false) //deixa o checkbox desmarcado
 * </code>
 * 
 * Agora na versão 2.x.x esse comportamento não acontece mais, o setValue só marca o checkbox se o
 * valor que foi passado é igual ao valor do checkbox
 * 
 * Por isso mudamos o comportamento do método atual para o comportamento antigo
 */

/**
 * Set the value an HTML element to the specified value.
 * 
 * @see http://getahead.org/dwr/browser/util/setvalue
 */
dwr.util.setValue = function(ele, val, options) {
	if (val == null) {
		val = "";
	}
	if (options == null) {
		options = {};
	}

	var orig = ele;
	if (typeof ele == "string") {
		ele = dwr.util.byId(ele);
		// We can work with names and need to sometimes for radio buttons, and IE has
		// an annoying bug where getElementById() returns an element based on name if
		// it doesn't find it by id. Here we don't want to do that, so:
		if (ele && (ele.id != orig)) {
			ele = null;
		}
	}
	var nodes = null;
	if (ele == null) {
		// Now it is time to look by name
		nodes = document.getElementsByName(orig);
		if (nodes.length >= 1) {
			ele = nodes.item(0);
		}
	}

	if (ele == null) {
		dwr.util._debug("setValue() can't find an element with id/name: " + orig + ".");
		return;
	}

	// All paths now lead to some update so we highlight a change
	dwr.util.highlight(ele, options);

	if (dwr.util._isHTMLElement(ele, "select")) {
		if ((ele.type == "select-multiple") && dwr.util._isArray(val)) {
			dwr.util._selectListItems(ele, val);
		} else {
			dwr.util._selectListItem(ele, val);
		}
		return;
	}

	if (dwr.util._isHTMLElement(ele, "input")) {
		if ((ele.type == "radio") || (ele.type == "checkbox")) {
			if (nodes && (nodes.length >= 1)) {
				for ( var i = 0; i < nodes.length; i++) {
					var node = nodes.item(i);
					if (node.type != ele.type) {
						continue;
					}
					if (dwr.util._isArray(val)) {
						node.checked = false;
						for ( var j = 0; j < val.length; j++) {
							if (val[j] == node.value) {
								node.checked = true;
							}
						}
					} else {
						/*
						 * aqui ocorreu a mudança, o struts gera checkbox (<html:checkbox />) com o value
						 * contendo "on" então se o valor for igual a esse marca o checkbox da forma antiga
						 */
						var valorComparacao = node.value == "on" ? true : node.value;
						node.checked = (valorComparacao == val);
					}
				}
			} else {
				ele.checked = (val == true);
			}
		} else {
			ele.value = val;
		}

		return;
	}

	if (dwr.util._isHTMLElement(ele, "textarea")) {
		ele.value = val;
		return;
	}

	// If the value to be set is a DOM object then we try importing the node
	// rather than serializing it out
	if (val.nodeType) {
		if (val.nodeType == 9 /* Node.DOCUMENT_NODE */) {
			val = val.documentElement;
		}
		val = dwr.util._importNode(ele.ownerDocument, val, true);
		ele.appendChild(val);
		return;
	}

	// Fall back to innerHTML and friends
	if (dwr.util._shouldEscapeHtml(options) && (typeof (val) == "string")) {
		if (ele.textContent) {
			ele.textContent = val;
		} else if (ele.innerText) {
			ele.innerText = val;
		} else {
			ele.innerHTML = dwr.util.escapeHtml(val);
		}
	} else {
		ele.innerHTML = val;
	}
};
