/**
 * Utilitários JS da aplicação
 */

/**
 * desabilitar e voltar o estado anterior dos elementos especificado pela tag útil para bloquear a
 * interação do usuário com a tela enquanto algo estiver processando, ou simplesmente para travar
 * alguns tipos de elementos
 */
var HabilitaDesabilitaElementos = Class.create();
HabilitaDesabilitaElementos.prototype = {
   tag :null,
   elementoAlvo :null,
   elementsStore :null,
   /**
	 * @constructor
	 * 
	 * @param {String} tag elemento html que deverá ser desabilitado
	 * @param {String} idelemento elemento pai, opcional, se não passado desabilitará todas as tags
	 *        especificadas do documento
	 */
   initialize : function(tag, idelemento) {
	   if (! [ "input", "select", "textarea" ].include(tag)) {
		   throw new Error("somente input, select, textarea");
	   }
	   if ((idelemento != undefined) && (idelemento != null)) {
		   this.elementoAlvo = $(idelemento)
	   }
	   this.tag = tag;
	   this.elementsStore = new Array();
	   this.doEstado();
   },
   ativo :true,
   /**
	 * troca o estado dos elementos desabilitados, utilize essa função para voltar o estado normal
	 * dos objetos desativados
	 */
   doEstado : function() {
	   var selects = null;
	   if (this.elementoAlvo != null) {
		   selects = elementoAlvo.getElementsByTagName(this.tag);
	   } else {
		   selects = document.getElementsByTagName(this.tag);
	   }
	   if (this.ativo) {
		   for ( var i = 0; i < selects.length; i++) {
			   this.elementsStore[i] = new Array();
			   this.elementsStore[i][0] = selects[i];
			   this.elementsStore[i][1] = selects[i].disabled;
			   selects[i].disabled = true;
		   }
		   this.ativo = false;
	   } else {
		   for ( var i = 0; i < this.elementsStore.length; i++) {
			   this.elementsStore[i][0].disabled = this.elementsStore[i][1];
		   }
		   this.elementsStore = null;
		   this.elementsStore = new Array();
		   this.ativo = true;
	   }
   },
   /**
	 * 
	 * @param {Boolean} estado
	 */
   setEstado : function(estado) {
	   this.ativo = estado;
	   this.doEstado();
   }
};

/**
 * classe de manipulações com checkboxes
 */
var CheckboxFunctions = Class.create();
CheckboxFunctions = {
   /**
	 * marca todos os checkbox onde o valor do mesmo seja igual a um dos valores do array de integer
	 * passado no argumento
	 * 
	 * @param {Array} arrayValores valores a serem marcados nos checks
	 * @param {String} checkboxName nome do checkbox a ser marcado os valores
	 * @param {String} propertyName nome da propriedade dentro dos objetos do array que será extraído
	 *        o valor a ser marcado (opcional)
	 */
   setCheckboxes : function(arrayValores, checkboxName, propertyName) {
	   var checkboxes;
	   if (typeof checkboxName == "string") {
		   checkboxes = $(document.body).select("input[name=\"" + checkboxName + "\"]");
	   } else {
		   if (checkboxName.nodeName == undefined) {
			   checkboxes = checkboxName;
		   } else {
			   checkboxes = new Array();
			   checkboxes.push(checkboxName);
		   }
	   }
	   var val = null;
	   if ((propertyName != undefined) && (propertyName != null)) {
		   var d = new Array();
		   arrayValores.each( function(dados) {
			   d.push(dwr.util._getValueFrom(dados, propertyName));
		   });
		   val = d;
	   } else {
		   val = arrayValores;
	   }
	   for ( var i = 0; i < checkboxes.length; i++) {
		   var checkbox = checkboxes[i];
		   checkbox.checked = false;
		   if (val.include(checkbox.value)) {
			   checkbox.checked = true;
		   }
	   }
   },
   /**
	 * retornar os valores dos checkboxes que está marcados em um array
	 * 
	 * @param {String} checkboxName name do checkbox que terá seus valores coletados
	 * @return {Array}
	 */
   getCheckboxesValues : function(checkboxName) {
	   var chk = document.getElementsByName(checkboxName);
	   var valores = new Array();
	   for ( var i = 0; i < chk.length; i++) {
		   if (chk.item(i).checked) {
			   valores.push(chk.item(i).value);
		   }
	   }
	   return valores;
   },
   getCheckboxesLabel : function(checkboxName) {
	   var chk = document.getElementsByName(checkboxName);
	   var valores = new Array();
	   for ( var i = 0; i < chk.length; i++) {
		   Element.cleanWhitespace(chk.item(i).parentNode);
		   if (chk.item(i).checked) {
			   var valor = new String(chk.item(i).nextSibling.nodeValue.replace(/\n/g, ""));
			   valores.push(valor.strip());
		   }
	   }
	   return valores;
   },
   /**
	 * recuperar o objecto HTMLInputElement Checkbox da coleção de checkboxes que contenha o valor
	 * passado
	 * 
	 * @param {Object} checkbox
	 * @param {Object} valor
	 */
   getCheckbox : function(checkbox, valor) {
	   var chks = document.getElementsByName(checkbox);
	   for ( var i = 0; i < chks.length; i++) {
		   if (chks.item(i).value == "" + valor) {
			   return chks.item(i);
		   }
	   }
	   return null;
   },
   /**
	 * 
	 * @param {String} checkboxName name do checkbox
	 * @return {Integer}
	 */
   getQtdsMarcados : function(checkboxName) {
	   var colecao = document.getElementsByName(checkboxName);
	   var total = 0;
	   for ( var i = 0; i < colecao.length; i++) {
		   if (colecao.item(i).checked) {
			   total++;
		   }
	   }
	   return total;
   },
   /**
	 * Marcar todos os checkbox da coleção
	 * 
	 * @param {String} checkboxName
	 */
   marcarTodos : function(checkboxName) {
	   this.marcar(true, checkboxName);
   },
   /**
	 * @param {String} checkboxName
	 */
   desmarcarTodos : function(checkboxName) {
	   this.marcar(false, checkboxName);
   },
   /**
	 * @param {HTMLButtonElement} botaoChamador
	 * @param {String} checkboxName
	 */
   marcarBotao : function(botaoChamador, checkboxName) {
	   if (botaoChamador.statusCheckBox == undefined) {
		   botaoChamador.statusCheckBox = true;
	   }
	   this.marcar(botaoChamador.statusCheckBox, checkboxName);
	   botaoChamador.statusCheckBox = !botaoChamador.statusCheckBox;
   },
   /**
	 * @param {Boolean} status
	 * @param {String} checkboxName
	 */
   marcar : function(status, checkboxName) {
	   var colecao = document.getElementsByName(checkboxName);
	   for ( var i = 0; i < colecao.length; i++) {
		   colecao.item(i).checked = status;
	   }
   },
   /**
	 * criar vários checkboxes dentro de um determinado elemento (div, de preferência)
	 * 
	 * @see dwr.util.addOptions
	 * @see dwr.util.addRows
	 * 
	 * @param {String} divId id do elemento conterá os checkboxes criados
	 * @param {Array} arraydata dados que preencherão os checkboxes
	 * @param {String} value propriedade contida nos elementos do arraydata que possui o valor dos
	 *        checks
	 * @param {String} text propriedade contida nos elementos do arraydata que possui o label dos
	 *        checks
	 * @param {String} radioname nome do conjunto de checkboxes que será criado
	 */
   addCheckboxes : function(divId, arraydata, value, text, checkname) {
	   var divcontainer = $(divId);
	   // limpa o div
	divcontainer.innerHTML = "";
	arraydata.each( function(dados) {
		var label = Builder.node("label");

		var check = Builder.node("input", {
		   type :"checkbox",
		   name :checkname,
		   value :dwr.util._getValueFrom(dados, value),
		   className :"checkboxInput"
		});

		if (Prototype.Browser.IE) {
			var id = "id" + Math.random();
			label.htmlFor = id;
			check.id = id;
		}

		label.appendChild(check);

		label.appendChild(document.createTextNode(dwr.util._getValueFrom(dados, text) + " "));
		divcontainer.appendChild(label);
	});
}

};

/**
 * classe de manipulação de radiobuttons
 * 
 */
var RadioFunctions = Class.create();
RadioFunctions = {
   /**
	 * criar vários radios dentro de um determinado elemento (div, de preferência)
	 * 
	 * @see dwr.util.addOptions
	 * @see dwr.util.addRows
	 * 
	 * @param {String} divId id do elemento conterá os radios criados
	 * @param {Array} arraydata dados que preencherão os radiobuttons
	 * @param {String} value propriedade contida nos elementos do arraydata que possui o valor dos
	 *        radios
	 * @param {String} text propriedade contida nos elementos do arraydata que possui o label dos
	 *        radios
	 * @param {String} radioname nome do conjunto de radio que será criado
	 */
   addRadios : function(divId, arraydata, value, text, radioname) {
	   var divcontainer = $(divId);
	   divcontainer.innerHTML = "";
	   arraydata.each( function(dados) {
		   var label = Builder.node("label");

		   var radio = Builder.node("input", {
		      type :"radio",
		      name :radioname,
		      value :dwr.util._getValueFrom(dados, value),
		      className :"radioInput"
		   });

		   if (Prototype.Browser.IE) {
			   var id = "id" + Math.random();
			   label.htmlFor = id;
			   radio.id = id;
		   }

		   label.appendChild(radio);

		   label.appendChild(document.createTextNode(dwr.util._getValueFrom(dados, text) + " "));
		   divcontainer.appendChild(label);
	   });
   },
   /**
	 * set um valor em um RADIO deve ser utilizado no lugar do dwr.util.getValue quando o objeto
	 * passado não é o id de um objeto ou seja o seguinte código não funcionaria com o dwr.util
	 * <code>
    * 		dwr.util.getValue(form.radios);
    * 		dwr.util.setValue(form.radios, "valor");
    * </code>
	 * 
	 * @param {NodeList} collection de radios
	 * 
	 * @return{String}
	 */
   getValue : function(radios) {
	   if (typeof radios == "string") {
		   return dwr.util.getValue(radios);
	   } else {
		   var colected = new Array();
		   for ( var index = 0; index < radios.length; index++) {
			   var radio = radios.item(index);
			   if (radio.checked) {
				   colected.push(radio.value);
			   }
		   }
		   if (colected.length == 0) {
			   return null;
		   }
		   if (colected.length == 1) {
			   return colected[0];
		   }
		   return colected;
	   }
   },
   /**
	 * set um valor em um RADIO deve ser utilizado no lugar do dwr.util.getValue quando o objeto
	 * passado não é o id de um objeto ou seja o seguinte código não funcionaria com o dwr.util
	 * <code>
    * 		dwr.util.getValue(form.radios);
    * 		dwr.util.setValue(form.radios, "valor");
    * </code>
	 * 
	 * @param {NodeList} collection de radios
	 * @param {String} valor
	 */
   setValue : function(radios, valor) {
	   if (typeof radios == "string") {
		   return dwr.util.getValue(radios);
	   } else {
		   for ( var index = 0; index < radios.length; index++) {
			   var radio = radios.item(index);
			   if (radio.value == valor) {
				   radio.checked = true;
				   break;
			   }
		   }
	   }
   },
   getLabel : function(radio) {
	   var radio = document.getElementsByName(radio);
	   var valores = new Array();
	   for ( var i = 0; i < radio.length; i++) {
		   Element.cleanWhitespace(radio.item(i).parentNode);
		   if (radio.item(i).checked) {
			   var valor = new String(radio.item(i).nextSibling.nodeValue.replace(/\n/g, ""));
			   valores.push(valor.strip());
		   }
	   }
	   return valores;
   }
};

/**
 * classe de funções para manipular selects e options (combobox)
 * 
 */
var ComboFunctions = Class.create();
ComboFunctions = {
   /**
	 * transfere um indice selecionado em um combo para outro combo o valor dos options a serem
	 * manipulados devem conter apenas números
	 * 
	 * @param {String} origem id do objeto combo que possui um índice selecionado
	 * @param {String} destino id do objeto combo destino onde o option representado pelo índice
	 *        selecionado será adicionado
	 * @param {Integer} inx indice a ser transferido da origem (opcional)
	 */
   transfereOptions : function(origem, destino, inx) {
	   var indice;
	   if (inx == undefined) {
		   indice = $(origem).selectedIndex;
	   } else {
		   indice = inx;
	   }
	   if (indice == -1) {
		   return;
	   }
	   if (this.containsValue(destino, $F(origem))) {
		   return;
	   }
	   var texto = $(origem).options[indice].text;
	   var idObj = $F(origem);

	   $(origem).options[indice] = null;

	   var optionsOrdenado = $(destino).options;
	   $(destino).options[$(destino).options.length] = new Option(texto, idObj);
	   this.ordenarOptions(destino);
   },
   /**
	 * Remove o indice selecionado em um combo
	 * 
	 * @param {String} origem id do objeto combo que possui um índice selecionado
	 * @param {Integer} index a ser apagado(opcional)
	 */
   removeOption : function(origem, index) {
	   var indice;
	   if (index == undefined) {
		   indice = $(origem).selectedIndex
	   } else {
		   indice = index;
	   }
	   if (indice == -1) {
		   return;
	   }
	   $(origem).options[indice] = null;
   },

   /**
	 * Remove a opção da combo a partir do seu valor.
	 * 
	 * @param {String} origem id do objeto combo que possui o valor selecionado
	 * @param {String} valor a ser apagado
	 */
   removeOptionValue : function(origem, valor) {
	   if (valor == undefined) {
		   return;
	   }
	   for ( var i = 0; i < $(origem).options.length; i++) {
		   if ($(origem).options[i].value == valor) {
			   $(origem).options[i] = null;
		   }
	   }
	   this.ordenarOptions(origem);
   },

   /**
	 * seleciona todos os options de um combo
	 * 
	 * @param {String} select objeto select a ser selecionado
	 */
   selecionaCombo : function(select) {
	   var oSelect = document.getElementById(select);
	   oSelect.multiple = true;
	   for ( var i = 0; i < oSelect.options.length; i++) {
		   oSelect.options.item(i).selected = true;
	   }
   },
   /**
	 * deseleciona um select que tenha um option selecionado (GAMBI I.E.)
	 * 
	 * @param {String} select id do select que será desselecionado
	 */
   deselecionarCombo : function(select) {
	   if (Prototype.Browser.IE) {
		   var options = $(select).options;
		   var option = new Option("", "");
		   options.add(option);
		   option.selected = true;
		   options.remove($(select).options.length - 1);
	   }
   },
   /**
	 * transferir todos ostions de um select para outro o valor dos options a serem manipulados devem
	 * ser sempre números pois não será transferido os que já estiverem no select destino (que
	 * possuirem o mesmo value)
	 * 
	 * @see dwr.util.addOptions
	 * @see dwr.util.removeAllOptions
	 * 
	 * @param {String} origem id do select de origem dos options
	 * @param {String} destino destino dos options coletados substituida no dia 13/03/2008 pois o
	 *        sistema deve selecinar e enviar so as selecionadas
	 */
   transfereTodosOld : function(origem, destino) {
	   var options = $(destino).options;
	   var valores = new Array();
	   var selecionadas = new Array();

	   for ( var j = 0; j < options.length; j++) {
		   var valor = parseInt(options.item(j).value);
		   if (!isNaN(valor)) {
			   valores.push(valor);
		   }
	   }
	   for ( var j = 0; j < $(origem).options.length; j++) {
		   var option = $(origem).options.item(j);
		   var valor = parseInt(option.value);
		   if (!valores.include(valor)) {
			   selecionadas.push(option);
		   }
	   }

	   if (Prototype.Browser.IE) {
		   dwr.util.addOptions(destino, selecionadas, "value", "innerHTML");
		   dwr.util.removeAllOptions(origem);
	   } else {
		   dwr.util.addOptions(destino, selecionadas, "value", "text");
		   // TODO: Existe um BUG no firefox versão 2.0.0.3 que as vezes o dwr.util.removeAllOptions
	// não funciona
	dwr.util.removeAllOptions(origem);
}
},

/**
 * transferir options de um select para outro o valor dos options a serem manipulados devem ser
 * sempre números pois não será transferido os que já estiverem no select destino (que possuirem o
 * mesmo value)
 * 
 * @see dwr.util.addOptions
 * 
 * 
 * @param {String} origem id do select de origem dos options
 * @param {String} destino destino dos options coletados
 */
transfereTodos : function(origem, destino) {
var options = $(destino).options;
var valores = new Array();
var selecionadas = new Array();
var movimentaParaDestino = new Array();
var movimentaParaOrigem = new Array();

for ( var j = 0; j < options.length; j++) {
	var valor = parseInt(options.item(j).value);
	if (!isNaN(valor)) {
		valores.push(valor);
	}
}
for ( var j = 0; j < $(origem).options.length; j++) {
	var option = $(origem).options.item(j);
	var valor = parseInt(option.value);
	if (!valores.include(valor)) {
		selecionadas.push(option);
	}
	if ((option.selected == true) && !this.containsValue(destino, option.value)) {
		movimentaParaDestino.push(option);
	} else {
		if (!this.containsValue(destino, option.value)) {
			movimentaParaOrigem.push(option);
		}
	}
}

if (Prototype.Browser.IE) {
	dwr.util.addOptions(destino, movimentaParaDestino, "value", "text");
	dwr.util.removeAllOptions(origem);
	dwr.util.addOptions(origem, movimentaParaOrigem, "value", "text");
} else {
	dwr.util.addOptions(destino, movimentaParaDestino, "value", "text");
	// TODO: Existe um BUG no firefox versão 2.0.0.3 que as vezes o dwr.util.removeAllOptions não
	// funciona
	dwr.util.removeAllOptions(origem);
	dwr.util.addOptions(origem, movimentaParaOrigem, "value", "text");
}
this.ordenarOptions(destino);
},
/**
 * pegar todos os valores selecionados em um select-multiple a função considera que o valor dos
 * options contenham apenas números interos
 * 
 * @param {String} id do select que terá seus valores coletados
 * @param {Boolean} selected somente os selecionados
 * @return {Array} de integers coletados
 */
getValues : function(id, selected) {
var options = $(id).options;
var valores = new Array();
for ( var i = 0; i < options.length; i++) {
	if (!selected) {
		if (options[i].selected) {
			valores.push(options[i].value);
		}
	} else {
		valores.push(options[i].value);
	}

}
return valores;
},
/**
 * verifica se no combo existe um options com o value selecionado
 * 
 * @param {String} select id
 * @param {Integer} v valor a ser verificado no select
 * @return {Boolean}
 */
containsValue : function(select, v) {
var options = $(select).options;
var valores = new Array();
for ( var i = 0; i < options.length; i++) {
	var val = parseInt(options.item(i).value);
	if (!isNaN(val)) {
		valores.push(val);
	}
}
if (valores.include(v)) {
	return true;
}
return false
},
/**
 * verifica se todos os elementos do array passado estão contidos no select
 * 
 * @param {String} select id
 * @param {Array} valores
 */
containsValues : function(select, valores) {
for ( var index = 0; index < valores.length; index++) {
	if (!this.containsValue(select, valores[index].toString())) {
		return false;
	}
}
return true;
},
/**
 * transfere todos os valores do array que estiverem no select origem para o select destino
 * 
 * @param {Array} valores que deverão ser removidos da origem e depositados no destino
 * @param {String} origem id do HTMLSelectElement de origem
 * @param {String} destino id do HTMLSelectElement de destino
 */
setValues : function(valores, origem, destino, property) {
if ((property != undefined) && (property != null)) {
	var d = new Array();
	valores.each( function(dados) {
		d.push(dwr.util._getValueFrom(dados, property));
	});
	valores = d;
}

var optionsOrigem = $(origem).options;
var aremover = new Array();
for ( var i = 0; i < optionsOrigem.length; i++) {
	var value = optionsOrigem[i].value;
	if (valores.include(value)) {
		aremover.push(optionsOrigem[i]);
	}
}
dwr.util.removeAllOptions(destino);
dwr.util.addOptions(destino, aremover, "value", "text");
aremover.each( function(option) {
	Element.remove(option);
});
},
/**
 * retornar o label (não o valor) do option selecionado
 * 
 * @param {String} elemento
 * @return {String} innerHTML do option selecionado
 */
getOptionInnerLabel : function(elemento) {
var select = $(elemento);
if (select.selectedIndex == -1) {
	return null;
}
return select.options[select.selectedIndex].innerHTML;
},
/**
 * retornar o label de todos os options selecionados e não selecionados (multiple)
 * 
 * @param {String} elemento
 * @return {Array}
 */
getAllOptionInnerLabel : function(elemento) {
var select = $(elemento);
var options = select.options;
var valores = new Array();
for ( var index = 0; index < options.length; index++) {
	valores.push(new String(options[index].innerHTML).strip());
}
return valores;
},
/**
 * fecha a janela de pesquisa, transfere somente as opções selecionadas que não estão no combo da
 * tela principal e remove o que foi selecionado das opções disponíveis da tela principal
 * 
 * @param {String} sel1 select das opções selecionadas da tela principal
 * @param {String} sel2 select das opções selecionadas da tela popup
 * @param {String} sel3 select que contém todas as opções na tela principal, onde será removido
 *        quando selecionado
 */
transfereOptionsJanela : function(sel1, sel2, sel3) {
var options = $(sel1).options;
var irrId = new Array();

for ( var i = 0; i < options.length; i++) {
	var option = options.item(i);
	irrId.push(parseInt(option.value));
}

var optionsSel = $(sel2).options;
var atransportar = new Array();
for ( var i = 0; i < optionsSel.length; i++) {
	var option = optionsSel.item(i);
	if (!irrId.include(parseInt(option.value))) {
		atransportar.push(option);
	}
}
for ( var i = 0; i < atransportar.length; i++) {
	var option = Element.remove(atransportar[i]);

	var optionsTela = $(sel3).options;
	for ( var j = 0; j < optionsTela.length; j++) {
		var optionTela = optionsTela.item(j);
		if (optionTela.value == option.value) {
			Element.remove(optionTela);
			break;
		}
	}

	$(sel1).appendChild(option);
}
JanelaFactory.fecharJanelaAtiva();
},
ordenarOptions : function(select) {
select = $(select);
var options = select.options;
var ordenado = new Array();
for ( var i = 0; i < options.length; i++) {
	ordenado.push(options[i]);
}
ordenado.sort( function(elemento, element2) {
	return elemento.text.localeCompare(element2.text);
});
for (i = 0; i < ordenado.length; i++) {
	select.appendChild(ordenado[i]);
}
}
};
/**
 * funções de máscaras para os campos, deverá ser chamada preferêncialmente de dentro de um .js como
 * segue o exemplo abaixo: <code>
 * 		Event.observe(window, "load", function(event){
 * 			MaskInput("cpf", "###.###.###-##");
 * 		})
 * </code>
 * ou então no final da página antes do campo (o que não é aconselhável, por questões de
 * centralização do código dentro do .js)
 * 
 * então po <b>#</b> - somente números <b>*</b> - números e letras (qualquer caracter) <b>x</b> -
 * somente letras
 * 
 * datas: ##/##/#### ou ##/##/#### ##:## cpf: ###.###.###-## cnpj: ##.###.###/####-##
 * 
 * @param {String} id do campo que deverá conter a máscara
 * @param {String} mask máscara que o campo deverá obedecer
 */
function MaskInput(id, mask) {


	// validar se for máscara de data
	if (mask == "##/##/####") {
		Event.observe(id, "keyup", function(event) {
			formataData($(id));
			// Ao pressionar a tecla TAB, o valor do campo é selecionado
			if (event.keyCode == 9) {
				Form.Element.activate($(id));
			}
		});

		Event.observe(id, "keypress", function(event) {
			// Recupera o caracter informado
			var key = event.charCode;

			var elemento = Event.element(event);

			// Limpa o campo apenas se o caracter informado for número e se já estiver preenchido
			if ((key >= 48) && (key <= 57) && ($(elemento).value.length == 10)) {
				dwr.util.setValue($(elemento), "");
			}
		});

		Event.observe(id, "blur", function(event) {
			var elemento = Event.element(event);
			if (!isBlankOrNull(elemento.value) && !isDate(elemento.value, "dd/MM/yyyy")) {
				JanelasComuns.showMessage("A data informada não é válida!", false, function() {
					Form.Element.activate(elemento);
				});
			}
		});
	} else {
		var oStringMask = new Mask(mask);
		oStringMask.attach($(id));
		if (!isBlankOrNull($(id).value)) {
			$(id).value = oStringMask.format($(id).value);
		}
	}
}
/**
 * ativar máscara do elemento após colocar o valor no mesmo
 * 
 * @param {String} elemento
 */
function ativarMascara(elemento) {
	try {
		$(elemento).focus();
		$(elemento).blur();
	} catch (e) {/* do nothing */}
}
/**
 * Selecionar uma tag
 * 
 * @param {HTMLElement} o
 */
function selecionarElemento(o) {
	if (document.body.createTextRange) {
		var tr = document.body.createTextRange();
		tr.moveToElementText(o);
		tr.select();
	} else if (document.createRange) {
		var tr = document.createRange();
		tr.selectNode(o);
		window.getSelection().removeAllRanges();
		window.getSelection().addRange(tr);
	}
}
/*
 * limpar todosos campos da tela
 * 
 * @param {HTMLFormElement} form se não especificado ele limpará todos os campos da tela
 */
function limparForm(form) {
	var inputs = null;
	var selects = null;
	var textareas = null;

	if (form != undefined) {
		inputs = form.getElementsByTagName("input");
		selects = form.getElementsByTagName("select");
		textareas = form.getElementsByTagName("textarea");
	} else {
		inputs = document.getElementsByTagName("input");
		selects = document.getElementsByTagName("select");
		textareas = document.getElementsByTagName("textarea");
	}
	var input;
	for ( var i = 0; i < inputs.length; i++) {
		input = inputs.item(i);
		if (input.type == "checkbox") {
			input.checked = false;
		}
		if (input.type == "text") {
			input.value = "";
		}
		if (input.type == "password") {
			input.value = "";
		}
		if (input.type == "hidden") {
			input.value = "";
		}
	}
	for (i = 0; i < selects.length; i++) {
		selects.item(i).selectedIndex = 0;
	}
	for (i = 0; i < textareas.length; i++) {
		input = textareas.item(i);
		input.value = "";
	}
}
/**
 * redireciona o navegador para outra url mantendo o histórico
 * 
 * @param {String} url
 */
function execute(url) {
	window.top.location.href = url;
}
/**
 * interpretar uma string como número e se a mesma não for retorna null
 * 
 * @see parseInt
 * 
 * @param {String} numero
 */
function parseIntNull(numero) {
	if (isNaN(parseInt(numero))) {
		return null;
	}
	return parseInt(numero);
}
/**
 * verifica se a string esta vazia ou nula
 * 
 * @param {String} value
 * @return {Boolean}
 */
function isBlankOrNull(value) {
	if ((value != null) && !new String(value).blank()) {
		return false;
	}
	return true;
}
/**
 * Verificar se uma coleção está vazia ou nula
 * 
 * @param {Array} collection
 */
function isEmpty(collection) {
	if ((collection != null) && (collection.length != 0)) {
		return false;
	}
	return true;
}
/**
 * verificar se uma coleção não está vazia ou nula
 * 
 * @param {Object} collection
 */
function isNotEmpty(collection) {
	return !isEmpty(collection);
}
/**
 * verifica se objeto passado é um inteiro válido ou não
 * 
 * @param {Object} value
 * @return {Boolean}
 */
function isInteger(value) {
	if ((value != null) && (typeof value != "undefined")) {
		return !isNaN(parseInt(value));
	}
	return false;
}
/**
 * verifica se objeto passado é um ponto flutuante válido ou não
 * 
 * @param {Object} value
 * @return {Boolean}
 */

function isFloat(value) {
	if ((value != null) && (typeof value != "undefined")) {
		return !isNaN(parseFloat(value));
	}
	return false;
}
/**
 * @return {String} data atual no formato dd/MM/yyyy
 */
function getDataAtual() {
	return getStringDate(new Date());
}
/**
 * retorna uma data no formato pt-BR
 * 
 * @param {Date} data
 * @return {String} data no formato dd/MM/yyyy
 */
function getStringDate(data) {
	if (!data instanceof Date || (data == undefined)) {
		return null;
	}
	return formatDate(data, "dd/MM/yyyy");
}
/**
 * 
 * @param {String} dado
 */
function nobreakSpace(dado) {
	if (isBlankOrNull(dado)) {
		return "&nbsp;";
	}
	return dado;
}
/**
 * retorna um timestamp (data/hora) no formato pt-BR
 * 
 * @param {Date} data
 * @return {String} data no formato dd/MM/yyyy HH:mm
 */

function getStringTimestamp(data) {
	if (!data instanceof Date || (data == undefined)) {
		return null;
	}
	/*
	 * bug miserável que acredito ser falha do sistema acontece em todos os navegadores uma vez ou
	 * outra aparece uma data com GMT-0200 ao invés do GMT brasileiro GMT-0300 (Hora Oficial do
	 * Brasil)
	 */
	/*
	 * if(data.toString().indexOf("GMT-0200") != -1){ data = new Date(data.getTime());
	 * data.setHours(data.getHours() -1); }
	 */
	return formatDate(data, "dd/MM/yyyy HH:mm");
}
/**
 * limpar todos os eventos de um objeto
 * 
 * @param {String} id
 */
function limparTodosEventos(id) {
	$(id).onblur = function() {};
	$(id).onchange = function() {};
	$(id).onfocus = function() {};
	$(id).onkeydown = function() {};
	$(id).onkeyup = function() {};
	$(id).onkeypress = function() {};
	$(id).onmousedown = function() {};
	$(id).onmouseout = function() {};
	$(id).onmouseover = function() {};
	$(id).onselect = function() {};
}
/**
 * Transformar o form em um array de elementos, utilizado para converter o form e enviar para o DWR
 * como objeto. Ele não converte select-multiple, para pegar os values de select-multiple use a
 * função ComboFunctions.getValues(id) e atribua o resultado a uma propriedade do form (i.e.
 * form.instituicoes = ComboFunctions.getValues(id)) Use-o ao invés de usar o
 * dwr.util.getValues(form), pois o mesmo apresenta vários bugs
 * 
 * @see ComboFunctions.getValues
 * 
 * @param {HTMLFormElement} form
 * @return {object}
 */
function getForm(form) {
	return Form.serializeElements(Form.getElements(form), true);
}
/**
 * 
 * @param {String} idImagem1
 * @param {String} idImagem2
 * @param {Boolean} estado
 */
function selecionarImagem(idImagem1, idImagem2, estado) {
	var valor = eval(estado);
	if (!valor) {
		$(idImagem1).className = "imagemDestaque";
		$(idImagem2).className = "imagemLadoLabel";
	} else {
		$(idImagem2).className = "imagemDestaque";
		$(idImagem1).className = "imagemLadoLabel";
	}
}
/**
 * Clonar o runtimestyle do ie pois ao chamar o cloneNode eles não são clonados
 * 
 * @param {Element} origem
 * @param {Element} destino
 */
function clonarRuntimeStyleIE(origem, destino) {
	if (Prototype.Browser.IE) {
		var runTimeFiltersIE = new Array();
		$(origem).select("img").each( function(image) {
			runTimeFiltersIE.push(image.runtimeStyle.filter);
		});
		$(destino).select("img").each( function(image, index) {
			image.runtimeStyle.filter = runTimeFiltersIE[index];
		});
	}
}
/**
 * Conta a quantidade de caracteres passada pelo txtArea, e mostra um mensagem dentro de um alert
 * para informar ao usuário que a quantidade de caracteres foi extrapolada.
 * 
 * @param {HTMLTextAreaElement} txtArea
 * @param {Integer} tamanho
 */
function contaChar(txtArea, tamanho) {
	if (txtArea.value.length > tamanho) {
		dwr.util.setValue(txtArea, txtArea.value.substring(0, tamanho));
		JanelasComuns.showMessage("É permitido somente " + tamanho + " caracteres.");
	}
	dwr.util.setValue("contagem", tamanho - txtArea.value.length);
}
/**
 * pré carregar várias imagens
 * 
 * @param {Array} contendo os src das imagens a serem pré-carregadas
 */
function preloadImages() {
	var d = document;
	if (d.images) {
		if (!d.MM_p) {
			d.MM_p = new Array();
		}
		var i, j = d.MM_p.length, a = preloadImages.arguments;
		for (i = 0; i < a.length; i++) {
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image();
				d.MM_p[j++].src = a[i];
			}
		}
	}
}
function copiarParaAreaDeTransferencia(meintext) {
	if (window.clipboardData) {
		// the IE-manier
		window.clipboardData.setData("Text", meintext);
		// waarschijnlijk niet de beste manier om Moz/NS te detecteren;
		// het is mij echter onbekend vanaf welke versie dit precies werkt:
	} else if (window.netscape) {
		// dit is belangrijk maar staat nergens duidelijk vermeld:
		netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		// maak een interface naar het clipboard
		var clip = Components.classes['@mozilla.org/widget/clipboard;1']
		   .createInstance(Components.interfaces.nsIClipboard);
		if (!clip) {
			return;
		}
		// maak een transferable
		var trans = Components.classes['@mozilla.org/widget/transferable;1']
		   .createInstance(Components.interfaces.nsITransferable);
		if (!trans) {
			return;
		}
		// specificeer wat voor soort data we op willen halen; text in dit geval
		trans.addDataFlavor('text/unicode');

		// om de data uit de transferable te halen hebben we 2 nieuwe objecten nodig om het in op te
		// slaan
		var str = new Object();
		var len = new Object();

		var str = Components.classes["@mozilla.org/supports-string;1"]
		   .createInstance(Components.interfaces.nsISupportsString);

		var copytext = meintext;

		str.data = copytext;

		trans.setTransferData("text/unicode", str, copytext.length * 2);

		var clipid = Components.interfaces.nsIClipboard;

		if (!clip) {
			return false;
		}

		clip.setData(trans, null, clipid.kGlobalClipboard);
	}
	return false;
}

/**
 * executar o callback se qualquer evento de alteração for causado pelo usuário
 * 
 * @param {String} elemento name ou id
 * @param {Function} callback
 */
function observarAlteracao(elemento, callback) {
	var elementos = new Array();
	var objReal = $(elemento);
	if (objReal != null) {
		elementos.push(objReal);
	}
	var collection = document.getElementsByName(elemento);
	for ( var i = 0; i < collection.length; i++) {
		elementos.push(collection.item(i));
	}
	if (elementos.length == 0) {
		return;
	}
	elementos.each( function(obj) {
		if (obj.nodeName.toLowerCase() == "input") {
			switch (obj.type.toLowerCase()) {
				case "text":
					Event.observe(obj, "keypress", callback);
					Event.observe(obj, "keyup", callback);
					break;
				case "radio":
				case "button":
				case "submit":
				case "checkbox":
					Event.observe(obj, "click", callback);
					break;
			}
		}
		if (obj.nodeName.toLowerCase() == "textarea") {
			Event.observe(obj, "keypress", callback);
			Event.observe(obj, "keyup", callback);
		}
		if (obj.nodeName.toLowerCase() == "select") {
			Event.observe(obj, "change", callback);
		}
	});
}

/**
 * retornar o título da janela
 */
function getTituloPagina() {
	return document.getElementsByTagName("head").item(0).getElementsByTagName("title").item(0).innerHTML;
}
/**
 * limparFormatacao deixa somente os números
 * 
 * @param {String} dado
 */
function limparFormatacao(dado) {
	return dado.replace(/\D/g, "");
}
/**
 * formata documento para CPF/CNPJ
 */
function formatarDocumento(documento) {
	if ((documento.length != 11) && (documento.length != 14)) {
		return "";
	}
	if (documento.length == 11) {
		return documento.substr(0, 3) + '.' + documento.substr(3, 3) + '.' + documento.substr(6, 3)
		   + '-' + documento.substr(9, 2);
	} else {
		return documento.substr(0, 2) + '.' + documento.substr(2, 3) + '.' + documento.substr(5, 3)
		   + '/' + documento.substr(8, 4) + '-' + documento.substr(12, 2);
	}
}
/**
 * formata data fornece a mascara conforme o valor for digitado em evento onkeyup ou press e invoca
 * uma outra funcao filtra campo para a remocao caso os caracteres especiais tenha sido digitados
 */
function formataData(campo) {
	filtraCampo(campo);
	vr = campo.value;
	tam = vr.length;
	if ((tam > 2) && (tam < 5)) {
		campo.value = vr.substr(0, tam - 2) + '/' + vr.substr(tam - 2, tam);
	}
	if ((tam >= 5) && (tam <= 10)) {
		campo.value = vr.substr(0, 2) + '/' + vr.substr(2, 2) + '/' + vr.substr(4, 4);
	}
	if (tam > 10) {
		campo.value = vr.substr(0, 2) + '/' + vr.substr(2, 2) + '/' + vr.substr(4, 4);
	}
}

/**
 * Aplicar bloquei de tela com mensagem de confirmação de saída no caso do usuário ter alterado
 * algum campo da tela
 * 
 * @param {HTMLFormElement} form
 */
function confirmacaoSair(form) {
	form = $(form);
	var campoAlterado = function(event) {
		shouldCoverDisplay = false;
		window.onbeforeunload = function() {
			return "Deseja sair sem salvar as alterações?";
		};
	};
	form.getElements().each( function(elemento) {
		switch (elemento.nodeName.toLowerCase()) {
			case "input":
				switch (elemento.type.toLowerCase()) {
					case "text":
						Event.observe(elemento, "keypress", campoAlterado);
						Event.observe(elemento, "keyup", campoAlterado);
						break;
					case "radio":
					case "button":
					case "checkbox":
						Event.observe(elemento, "click", campoAlterado);
						break;
				}
				break;
			case "textarea":
				Event.observe(elemento, "keypress", campoAlterado);
				Event.observe(elemento, "keyup", campoAlterado);
				break;
			case "select":
				Event.observe(elemento, "change", campoAlterado);
				break;
			default:
				break;
		}
	});
}

function filtraCampo(campo) {
	var s = "";
	var cp = "";
	vr = campo.value;
	tam = vr.length;
	for (i = 0; i < tam; i++) {
		if ((vr.substring(i, i + 1) != "/") && (vr.substring(i, i + 1) != "-")
		   && (vr.substring(i, i + 1) != ".") && (vr.substring(i, i + 1) != ",")) {
			s = s + vr.substring(i, i + 1);
		}
	}
	campo.value = s;
}

/**
 * as funções setPreHook e setPostHook serão chamadas automaticamente pelo DWR, quando começar e
 * terminar as requisições feitas por ele, assim é possível controlar o loading do sistema,
 * mostrando ou escondendo. O mesmo é feito para as requisições feitas com a API do prototype
 */
Event.observe(window, "load", function() {
	var preHook = function() {
		loadingFactory.showLoading();
	};
	var postHook = function() {
		loadingFactory.hideLoading();
	};
	dwr.util.setEscapeHtml(false);
	dwr.engine.setPreHook(preHook);
	dwr.engine.setPostHook(postHook);


	dwr.engine.setTextHtmlHandler( function() {
		JanelasComuns.sessaoFinalizada();
	});
	Ajax.Responders.register( {
	   onCreate :preHook,
	   onComplete :postHook
	});
	dwr.engine.setErrorHandler( function(message, ex) {
		if (ex.name == "org.directwebremoting.extend.AccessDeniedException") {
			JanelasComuns.showMessage("Acesso não autorizado!", false);
		} else if (ex.name == "br.com.ucb.sisgestor.apresentacao.dwr.utils.SessionExpiredException") {
			JanelasComuns.sessaoFinalizada();
		} else {
			dwr.engine.defaultErrorHandler(message, ex);
		}
	});
});

/**
 * Exibir as mensagens de erro geradas pelo struts (ActionErrors) de uma requisição síncrona
 */
Event.observe(window, "load", function() {
	Element.show("principal");
	dwr.util.setValue("tituloTela", getTituloPagina());
});
var shouldCoverDisplay = true;

var aoDescarregarPagina = function(event) {
	if (shouldCoverDisplay) {
		JanelasComuns.carregarNovaPagina();
	}
};
Event.observe(window, Prototype.Browser.Opera ? "unload" : "beforeunload", aoDescarregarPagina);

/**
 * Cancelar o aviso de deseja sair
 */
function cancelarConfirmacaoAoSair() {
	window.onbeforeunload = aoDescarregarPagina;
}
