/**
 * Request Utils
 *
 * Funcionalidades de requisi��es de form e de url simples com tratamento do XML gerado
 *
 * <b>(1) para submeter um form ass�ncronamente para o Struts:</b> <code>
 * 		<form name="exemplo" action="teste.do" onsubmit="requestUtils.submitForm(this); return false;">...</form>
 * </code>
 *
 * <b>(2) para fazer uma requisi��o GET a uma url e receber o resultado dela:</b> <code>
 * 		var url = "paginaTeste.do";
 * 		var posFunction = function(){
 * 			alert("resposta da requisi��o recebida");
 * 		};
 * 		requestUtils.simpleRequest(url, posFunction);
 * 		//o par�metro posFunction � opcional
 * </code>
 *
 */

var RequestUtils = Class.create();

RequestUtils.prototype = {
   /**
	 * @constructor
	 */
   initialize : function() {

   },

   /**
	 * status da requisi��o se true as mensagens aparecer�o em verde, false as mensagens aparecer�o
	 * em vermelho
	 *
	 * @type Boolean
	 */
   status :false,

   /**
	 * de mensagens recebidas e processadas pelo Javascript
	 *
	 * @type Array
	 */
   mensagens :null,

   /**
	 * @type String url para onde usu�rio ser� mandado
	 */
   redirecionar :null,

   /**
	 * @type String nome do campo que ser� focalizado quando o usu�rio clicar ok na janela de
	 *       mensagens
	 */
   focusControl :null,
   /**
	 * @type String id gerado pela inser��o, atualiza��o ou exclus�o
	 */
   generatedId :null,
   /**
	 * hashmap de valores {chave e valor} devolvidos pelo servidor
	 *
	 * @type Hash
	 */
   valoresDevolvidos :null,
   /**
	 * @type String nome do n� do xml que vai retornar o status da opera��o
	 */
   nodeStatus :"status",

   /**
	 * @type String nome do n� do xml que ir� conter as mensagens
	 */
   nodeMessage :"message",

   /**
	 * @type String nome do n� do xml que ir� conter a url para onde a p�gina ser� redirecionada
	 */
   nodeUrlForward :"urlForward",
   /**
	 * @type String id gerado do objeto, utilizado geralmente para retornar ao Javascript o id do
	 *       objeto que foi inserido, exclu�do ou alterado
	 */
   nodeGeneratedId :"generatedId",
   /**
	 * @type String nome do n� do xml que ir� conter o campo que dever� ser focalizado
	 */
   focusControlNode :"focusControl",
   /**
	 * @see valoresDevolvidos
	 * @type String nome do n� do xml que ir� conter os valores devolvidos pelo servidor
	 */
   valoresDevolvidosNode :"valoresDevolvidos",
   janelaAtual :null,
   /**
	 * fun��o que ser� executada quando o usu�rio clicar ok na janela de mensagens
	 *
	 * @type Function
	 */
   posClickFunction :null,
   imagemFalha :"imagens/falha.png",
   imagemInformacao :"imagens/informacao.png",
   imagemSucesso :"imagens/check.png",
   imagemConfirmacao :"imagens/confirmacao.png",
   /**
	 * mostrar uma mensagem de erro quando o servidor cair ou ficar sem conex�o de internet ou alguma
	 * resposta diferente de 200 for recebida
	 *
	 * @param {XMLHttpRequest} request
	 */
   showErrorDetail : function(request) {
	   var codigoResposta = request.status;
	   if ((codigoResposta == 403) || (codigoResposta == 405)) {
		   JanelasComuns.showMessage("Acesso n�o autorizado!", false);
	   } else {
		   var statusText = request.statusText;
		   alert("N�o foi poss�vel conectar ao servidor\n" + codigoResposta + " " + statusText);
	   }
   },
   /**
	 * @type HTMLFormElement
	 */
   formSubmetido :null,
   /**
	 * fazer uma requisi��o ass�ncrona simples utilizando o m�todo GET a uma url que dever� responder
	 * um XML para o javascript processar
	 *
	 * @param {String} url
	 * @param {Function} posFunction fun��o a ser executada quando a resposta for recebida (opcional)
	 * @param {Function} posClickFunction fun��o a ser executada quando o usu�rio clicar ok na janela
	 *        de mensagens
	 */
   simpleRequest : function(url, posFunction, posClickFunction) {
	   var esse = this;
	   var aj = new Ajax.Request(url, {
	      method :"get",
	      onSuccess : function(request) {
		      if (typeof posClickFunction == "function") {
			      esse.posClickFunction = posClickFunction;
		      }

		      esse.processarResposta(request);
		      if (typeof posFunction == "function") {
			      posFunction();
		      }
	      },
	      onFailure : function(request) {
		      esse.showErrorDetail(request);
	      }
	   });

   },
   /**
	 * enviar um formul�rio ass�ncronamente, dever� ser chamado no evento onsubmit do form
	 *
	 * @param {HTMLFormElement} form
	 * @param {Function} posFunction fun��o a ser executada quando a resposta for recebida (opcional)
	 * @param {Function} posClickFunction fun��o a ser executada quando o usu�rio clicar ok na janela
	 *        de mensagens
	 */
   submitForm : function(form, posFunction, posClickFunction) {
	   if ((form == null) || (form == undefined) || (form.nodeName == undefined)
	      || (form.nodeName != "FORM")) {
		   throw new Error("N�o � um objeto FORM");
	   }
	   if (isBlankOrNull(form.action)) {
		   throw new Error("atributo action vazio");
	   }

	   var inputsControl = null;
	   var textareasControl = null;
	   var selectControl = null;
	   this.posClickFunction = posClickFunction;
	   var esse = this;
	   this.formSubmetido = form;
	   var aj = Form.request(form, {
	      onSuccess : function(request) {
		      janela.fecharJanela();

		      inputsControl.doEstado();
		      textareasControl.doEstado();
		      selectControl.doEstado();

		      esse.processarResposta(request);
		      if (typeof posFunction == "function") {
			      posFunction();
		      }
	      },
	      onFailure : function(request) {
		      janela.fecharJanela();
		      inputsControl.doEstado();
		      textareasControl.doEstado();
		      selectControl.doEstado();
		      esse.showErrorDetail(request);
	      }
	   });
	   var janela = createWindow(80, 290, 330, 100, "Processando requisi��o", "janelaCarregando");
	   // remover botao fechar da janela
	   janela.removerBotaoFechar();
	   dwr.util.setValue("janelaCarregando", "aguarde enquanto a solicita��o � processada...")
	   Element.setStyle("janelaCarregando", {
	      color :"red",
	      fontWeight :"bold"
	   });
	   textareasControl = new HabilitaDesabilitaElementos("textarea");
	   inputsControl = new HabilitaDesabilitaElementos("input")
	   selectControl = new HabilitaDesabilitaElementos("select");
   },
   /**
	 * processar a resposta do servidor
	 *
	 * @param {XMLHttpRequest} request
	 */
   processarResposta : function(request) {
	   if ((request.status == 0) || (request.status == 403)) {
		   this.showErrorDetail(request);
		   return;
	   }
	   if (isBlankOrNull(request.getResponseHeader("ajaxResponse"))) {
		   JanelasComuns.sessaoFinalizada();
		   return;
	   }
	   if ((request.responseXML == null) || (request.responseXML.childNodes.length == 0)) {
		   alert("Erro: a resposta esperada era um XML");
		   return;
	   }

	   this.mensagens = new Array();
	   this.redirecionar = null;
	   var items = null;
	   if (Prototype.Browser.IE || Prototype.Browser.Opera) {
		   items = request.responseXML.childNodes.item(1).childNodes;
	   }
	   if (Prototype.Browser.Gecko || Prototype.Browser.WebKit) {
		   items = request.responseXML.firstChild.childNodes;
	   }
	   for ( var i = 0; i < items.length; i++) {
		   var item = items.item(i);
		   if (item.nodeName == this.nodeMessage) {
			   this.mensagens.push(item.firstChild.nodeValue);
		   }
		   if (item.nodeName == this.nodeUrlForward) {
			   this.redirecionar = item.firstChild.nodeValue;
		   }
		   if (item.nodeName == this.nodeStatus) {
			   this.status = eval(item.firstChild.nodeValue);
		   }
		   if (item.nodeName == this.focusControlNode) {
			   this.focusControl = item.firstChild.nodeValue;
		   }
		   if (item.nodeName == this.nodeGeneratedId) {
			   this.generatedId = item.firstChild.nodeValue;
		   }
		   if (item.nodeName == this.valoresDevolvidosNode) {
			   this.valoresDevolvidos = new Hash();
			   var nodesJson = item.childNodes;
			   for ( var index = 0; index < nodesJson.length; index++) {
				   var nodeJson = nodesJson.item(index);
				   var chave = nodeJson.nodeName;
				   var valor = nodeJson.firstChild.nodeValue;
				   this.valoresDevolvidos.set(chave, valor);
			   }
		   }

	   }
	   if ((this.mensagens.length == 0) && (this.redirecionar == null)) {
		   return;
	   }
	   if (this.mensagens.length != 0) {
		   this.showMessages();
		   return;
	   }
	   if (this.redirecionar != null) {
		   window.location.href = this.redirecionar;
	   }
   },
   /**
	 * mostrar as mensagens recebidas do servidor em uma janela pr�pria para mensagens que at� 200px
	 * ela se ajustar� ao tamanho e a quantidade de mensagens e a partir desse tamanho ela ter� uma
	 * barra de rolagem
	 */
   showMessages : function() {
	   var alturaMensagens = this.mensagens.length * 16;
	   var altura = (alturaMensagens) + 80;
	   var janela = null;
	   if (alturaMensagens <= 200) {
		   janela = createWindow(altura, 600, 200, 100, "Resultado da opera��o", "idValidacoes", null);
	   } else {
		   janela = createWindow(270, 600, 200, 100, "Resultado da opera��o", "idValidacoes", null);
	   }
	   janela.removerBotaoFechar();
	   this.janelaAtual = janela;
	   var img;
	   if (this.status) {
		   img = Builder.node("img", {
		      src :this.imagemSucesso,
		      height :16,
		      width :16
		   });
	   } else {
		   img = Builder.node("img", {
		      src :this.imagemFalha,
		      height :22,
		      width :22
		   });
	   }
	   Element.setStyle(img, {
	      marginRight :"5px",
	      marginLeft :"3px"
	   });
	   $("idValidacoes").appendChild(Builder.node("div", {
		   style :"float: left;"
	   }, [ img ]));
	   var div = Builder.node("div", {
	      id :"validations",
	      style :"float: right; width: 550px;"
	   });
	   $("idValidacoes").appendChild(div);
	   this.mensagens.each( function(message) {
		   $("validations").appendChild(document.createTextNode(message));
		   $("validations").appendChild(Builder.node("br"));
	   });
	   Element.setStyle(div, {
	      color :this.status ? "green" : "red",
	      fontWeight :"bold"
	   });
	   if (alturaMensagens > 200) {
		   Element.setStyle($("validations"), {
		      overflow :"auto",
		      height :"200px",
		      border :"rgb(183, 176, 164) solid 1px"
		   });
	   }
	   var div2 = Builder.node("div", {
	      align :"center",
	      style :"clear: both;"
	   }, [ Builder.node("br"), Builder.node("input", {
	      type :"button",
	      value :"OK",
	      style :"width: 120px;",
	      id :"buttonValidations"
	   }) ]);
	   $("idValidacoes").appendChild(div2);
	   Event.observe("buttonValidations", "click", ( function() {
		   this.fecharJanela();
		   if (this.posClickFunction != null) {
			   this.posClickFunction();
			   this.posClickFunction = null;
		   }
	   }).bind(this));
	   // no IE se esse infeliz estiver invis�vel ele n�o consegue focalizar...
	   try {
		   $("buttonValidations").focus();
	   } catch (e) {
		   new PeriodicalExecuter( function(pe) {
			   try {
				   $("buttonValidations").focus();
			   } catch (ex) {}
			   pe.stop();
		   }, 0.4);
	   }
	   Element.setStyle($("idValidacoes").parentNode, {
	      height :"",
	      paddingBottom :"10px"
	   });
	   janela.setOnClose(this.eventoFecharJanela.bind(this));
   },
   eventoFecharJanela : function() {
	   if (this.redirecionar != null) {
		   window.location.href = this.redirecionar;
	   }
	   if (this.focusControl != null) {
		   this.processarFocusControl();
	   }
   },
   /**
	 * fechar janela de mensagens e redirecionar o usu�rio para a url recebida como resposta (se
	 * houver)
	 */
   fecharJanela : function() {
	   this.janelaAtual.fecharJanela();
   },
   getNomeElementoFocus : function() {
	   if (this.focusControl.endsWith("]")) {
		   return this.focusControl.substr(0, this.focusControl.indexOf("["));
	   }
	   return this.focusControl
   },
   /**
	 * quando o campo de focus retornado for um campo m�ltiplo ele vir� no formato
	 * nomeDoCampo[indice] e atrav�s do �ndice o componente saber� qual elemento focar
	 *
	 * @return {Integer}
	 */
   getIndiceElementoFocus : function() {
	   var foc = this.focusControl;
	   if (foc.endsWith("]")) {
		   return parseInt(foc.substring(foc.indexOf("[") + 1, foc.indexOf("]")));
	   }
	   return null;
   },
   /**
	 * retorna todos os elementos que dever�o ser focalizado
	 *
	 * @return {Array} contendo os elementos de focus
	 */
   getElementosFocus : function() {
	   var elementFocus = $(this.formSubmetido).select(
	      "[name=\"" + this.getNomeElementoFocus() + "\"]");
	   var focus = this.focusControl;
	   if (elementFocus.length == 0) {
		   elementFocus = new Array();
		   if (this.formSubmetido[focus] == undefined) {
			   elementFocus.push($(focus));
		   } else {
			   elementFocus.push(this.formSubmetido[focus]);
		   }
	   }
	   if (focus.endsWith("]")) {
		   var indice = parseInt(focus.substring(focus.indexOf("[") + 1, focus.indexOf("]")));
		   var element = elementFocus[indice];
		   elementFocus = new Array();
		   elementFocus.push(element);
	   }
	   return elementFocus;
   },
   /**
	 * procurar o elemento de focus na tela e ativar o destaque em cima dele
	 *
	 */
   processarFocusControl : function() {
	   var elementFocus = this.getElementosFocus();
	   FactoryAbas.ativaAbaPorElemento(elementFocus);
	   this.focusControl = null;

	   if ((elementFocus.type == undefined) || (elementFocus.type != "hidden")) {
		   var classAnterior;
		   for ( var index = 0; index < elementFocus.length; index++) {
			   var pulsateEl = elementFocus[index];
			   Effect.Pulsate(pulsateEl);
			   classAnterior = pulsateEl.className;
			   pulsateEl.className = "destaqueVermelho";
			   try {
				   Form.Element.focus(pulsateEl);
			   } catch (ex) {}
		   }
		   new PeriodicalExecuter( function(pe) {
			   for ( var index = 0; index < elementFocus.length; index++) {
				   var pulsateEl = elementFocus[index];
				   pulsateEl.className = classAnterior;
			   }
			   pe.stop();
		   }, 4);
	   } else {
		   // se o elemento focus � um hidden sob para o elemento pai e focaliza ele
	var inputsParent = elementFocus.parentNode.getElementsByTagName("input");
	for ( var i = 0; i < inputsParent.length; i++) {
		var input = inputsParent.item(i);
		if (input.type != "hidden") {
			Effect.Pulsate(input);
			input.className = "destaqueVermelho";
			try {
				Form.Element.focus(input);
			} catch (ex) {}
			new PeriodicalExecuter( function() {
				input.className = "";
			}, 4);
			break;
		}
	}
}
},
/**
 *
 * @param {String} mens
 */
setMensagens : function(mens) {
this.mensagens = mens;
},
/**
 * @return {String}
 */
getMensagens : function() {
return this.mensagens;
}
};
var requestUtils = new RequestUtils();
Event.observe(window, "load", function(event) {
	preloadImages(requestUtils.imagemFalha);
	preloadImages(requestUtils.imagemInformacao);
	preloadImages(requestUtils.imagemSucesso);
	preloadImages(requestUtils.imagemConfirmacao);

	if ($("divErros123") != null) {
		return;
	}
	if ($("errorMessagesStruts") != null) {
		Element.cleanWhitespace("errorMessagesStruts");
		var mensagensNodes = $("errorMessagesStruts").childNodes;

		var mensagens = new Array();
		for ( var i = 0; i < mensagensNodes.length; i++) {
			mensagens.push(mensagensNodes.item(i).innerHTML);
		}
		JanelasComuns.setMensagens(mensagens);
		if (Prototype.Browser.IE) {
			new PeriodicalExecuter( function(pe) {
				JanelasComuns.showMessages(eval($("messageStatus").innerHTML));
				pe.stop();
			}, 0.4);
		} else {
			JanelasComuns.showMessages(eval($("messageStatus").innerHTML));
		}
	}
});
