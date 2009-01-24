/**
 * Oreaculous JS
 * 
 * API Para cria��o de janelas div din�micas
 */
var Janela = Class.create();
Janela.prototype = {
   /**
	 * altura da janela
	 * 
	 * @type Integer
	 */
   height :null,

   /**
	 * largura da janela
	 * 
	 * @type Integer
	 */
   width :null,

   /**
	 * posi��o Y em rela��o ao elemento que cont�m a janelaDiv
	 * 
	 * @type Integer
	 */
   top :0,

   /**
	 * posi��o X em rela��o ao elemento que cont�m a janelaDiv
	 * 
	 * @type Integer
	 */
   left :0,

   /**
	 * Indicar se a janela est� modal ou n�o, ao chamar undoModal se ele for chamado novamente n�o
	 * poder� fazer nada se o valor do modal for false
	 * 
	 * @type Boolean
	 */
   modal :true,

   /**
	 * T�tulo da janela
	 * 
	 * @type String
	 */
   titulo :null,

   /**
	 * ID do div de dentro da janela que ir� conter o conte�do, tamb�m usado para identificar a
	 * janela. N�o ser� poss�vel ter duas janelas com o mesmo ID
	 */
   idConteudo :null,

   /**
	 * Objeto de arraste da janela, coisa do scriptaculous
	 * 
	 * @type Draggable
	 */
   draggable :null,

   /**
	 * Elemento div que representa a janela toda
	 * 
	 * @type HTMLDivElement
	 */
   janelaDiv :null,

   /**
	 * Janela anterior que est� por tr�s da atual
	 * 
	 * @type Janela
	 */
   janelaAnterior :null,

   /**
	 * Bot�o fechar da janela, quando o bot�o � removido ele vem pra c� para depois poder ser
	 * recolado
	 * 
	 * @type HTMLDivElement
	 */
   botaoFechar :null,

   /**
	 * Manipulador de evento que � acionado ao fechar a janela. Para que a janela n�o seja fechada
	 * basta retornar false no manipulador do evento
	 * 
	 * @type Function
	 */
   onClose :null,

   /**
	 * Manipulador de evento que dever� ser acionado quando o conte�do de uma URL tiver sido
	 * carregado dentro da div de conte�do
	 * 
	 * @type Function
	 */
   onCompletePosFunction :null,

   /**
	 * Endere�o URL que dever� fornecer um conte�do HTML para a DIV
	 * 
	 * @type String
	 */
   url :null,

   /**
	 * M�todo HTTP utilizado para realizar a requisi��o
	 * 
	 * @type String
	 */
   httpMethod :"get",

   /**
	 * Se o m�todo de requisi��o � POST, aqui conter� a querystring que dever� ir no corpo da
	 * requisi��o POST
	 * 
	 * @type String
	 */
   postBody :null,

   /**
	 * Avaliar scripts dentro do HTML recebido da resposta
	 * 
	 * @type Boolean
	 */
   evalScripts :true,

   /**
	 * id do elemento que cobre a tela anterior
	 * 
	 * @type String
	 */
   idElementoCobertor :"elementoCobertor",

   /**
	 * �ndice de visualiza��o da janela (mesma propriedade do estilo)
	 * 
	 * @type Integer
	 */
   zIndex :null,

   /**
	 * objeto utilizado na requisi��o
	 * 
	 * @type XMLHttpRequest
	 */
   transportRequest :null,

   /**
	 * ajaxOptions
	 */
   ajaxOptions :null,
   /**
	 * @constructor
	 * 
	 * @param {Integer} height altura da janela
	 * @param {Integer} width largura da janela
	 * @param {Integer} left quantidade de pixels a partir da esquerda do elemento contedor
	 *        (coordenada X da tela)
	 * @param {Integer} top quantidade de pixels a partir do top do elemento contedor {coordenada Y
	 *        da tela}
	 * @param {String} titulo texto de t�tulo da janela
	 * @param {String} idConteudo identificador do div que representar� o conte�do da janela
	 */
   initialize : function(height, width, left, top, titulo, idConteudo) {
	   this.top = top;
	   this.left = left;
	   this.height = height;
	   this.width = width;
	   this.titulo = titulo;
	   this.idConteudo = idConteudo;
	   this.criarJanela();
	   this.efeitoAbrir();
   },
   /**
	 * Cria a janela
	 */
   criarJanela : function() {
	   var janelaDiv = Builder.node("div", {
	      UNSELECTABLE :"on",
	      style :this.getEstiloJanela()
	   });
	   var divBarra = Builder.node("div", {
		   style :this.getEstiloBarra()
	   }, [ Builder.node("div", {
	      style :this.getEstiloBarraTitulo(),
	      UNSELECTABLE :"on"
	   }, [ document.createTextNode(this.titulo) ]), Builder.node("div", {
		   style :this.getEstiloDivFechar()
	   }, [ Builder.node("a", {
	      href :Prototype.Browser.IE ? "#" : "javascript: void(0);",
	      style :this.getEstiloBotaoFechar()
	   }, [ document.createTextNode("X ") ]) ]) ]);
	   Event.observe(divBarra.lastChild.firstChild, "click", ( function(event) {
		   this.fecharJanela();
	   }).bind(this));

	   $(janelaDiv).setStyle( {
	      width :(this.width) + "px",
	      height :(this.height) + "px",
	      top :(this.top) + "px",
	      left :(this.left) + "px"
	   });
	   Event.observe(divBarra.firstChild, "mousedown", ( function(event) {
		   if (this.draggable == null) {
			   var divElement = Event.element(event);
			   this.draggable = new Draggable(divElement.parentNode.parentNode);
			   this.draggable.initDrag(event);
		   }
	   }).bind(this));
	   Event.observe(divBarra.firstChild, "mouseup", ( function(event) {
		   if (this.draggable != null) {
			   this.draggable.finishDrag(event);
			   this.draggable.destroy();
			   /*
				 * acontece um BUG com o IE se o usu�rio clica com o bot�o do meio do mouse em cima da
				 * barra de t�tulo: a janela fica por tr�s de tudo
				 */
			   if (Prototype.Browser.IE) {
				   this.janelaDiv.setStyle( {
					   zIndex :this.zIndex
				   });
			   }
			   this.draggable = null;
		   }
	   }).bind(this));

	   janelaDiv.appendChild(divBarra);
	   janelaDiv.appendChild(Builder.node("div", {
	      id :this.idConteudo,
	      style :this.getEstiloConteudo()
	   }));
	   Element.hide(janelaDiv);
	   this.getPrincipal().appendChild(janelaDiv);
	   this.getPrincipal().makePositioned();
	   this.janelaDiv = janelaDiv;
	   this.centralizarX();
	   this.centralizarY();
   },

   /**
	 * Efeito usado ao abrir a janela
	 */
   efeitoAbrir : function() {
	   if (Configuracao.efeitoAbrir != null) {
		   Configuracao.efeitoAbrir(this.janelaDiv);
	   }
	   this.focusJanela();
   },
   /**
	 * Colocar o foco em uma janela
	 */
   focusJanela : function() {
	   this.janelaDiv.focus();
   },
   /**
	 * Efeito usado ao fechar a janela
	 */
   efeitoFechar : function() {
	   if (Configuracao.efeitoFechar != null) {
		   Configuracao.efeitoFechar(this.janelaDiv);
	   }
   },

   /**
	 * retorna o div contendo o bot�o fechar da janela
	 * 
	 * @return {HTMLDivElement}
	 */
   getBotaoFechar : function() {
	   return $(this.janelaDiv.firstChild.lastChild.firstChild);
   },

   /**
	 * remove o bot�o fechar da janela
	 * 
	 * @return {HTMLAnchorElement}
	 */
   removerBotaoFechar : function() {
	   this.botaoFechar = this.getBotaoFechar().remove();
	   return this.botaoFechar;
   },

   /**
	 * adiciona um bot�o fechar a janela
	 */
   adicionarBotaoFechar : function() {
	   if (this.botaoFechar != null) {
		   this.janelaDiv.firstChild.lastChild.appendChild(this.botaoFechar);
	   }
   },

   /**
	 * retorna o elemento onde as janelas ser�o acrescentadas (appendadas)
	 * 
	 * @return{Element}
	 */
   getPrincipal : function() {
	   if (Configuracao.DivPrincipal != null) {
		   return $(Configuracao.DivPrincipal);
	   }
	   return $(document.body);
   },

   /**
	 * retorna o estilo personalizado pelo usu�rio com o estilo de configura��o padr�o da barra da
	 * janela
	 * 
	 * @return {String}
	 */
   getEstiloBarra : function() {
	   return Configuracao.EstiloBarra
	      .concat("cursor:move; white-space: nowrap;padding-left: 5px; height: 16px;");
   },

   /**
	 * retorna o estilo personalizado pelo usu�rio com o estilo de configura��o padr�o da barra de
	 * janela
	 * 
	 * @return {String}
	 */
   getEstiloBarraTitulo : function() {
	   return Configuracao.EstiloBarraTitulo.concat("width:90%; float: left;");
   },

   /**
	 * retorna o estilo da janela
	 * 
	 * @return {String}
	 */
   getEstiloJanela : function() {
	   return Configuracao.EstiloJanela.concat("position: absolute; ");
   },

   /**
	 * estilo do conte�do modific�vel da janela
	 * 
	 * @return {String}
	 */
   getEstiloConteudo : function() {
	   return Configuracao.EstiloConteudo;
   },

   /**
	 * adiciona elementos ao div de conte�do da janela
	 */
   adicionarConteudo : function(element) {
	   this.getConteudo().appendChild(element);
   },
   /**
	 * retorna o div de conte�do da janela
	 * 
	 * @return {HTMLDivElement}
	 */
   getConteudo : function() {
	   var conteudo = this.janelaDiv.lastChild;
	   while (conteudo.id != this.idConteudo) {
		   conteudo = conteudo.previousSibling;
	   }
	   return $(conteudo);
   },
   /**
	 * estilo da div que cont�m o bot�o fechar com o estilo personalizado pelo usu�rio
	 * 
	 * @return {String}
	 */
   getEstiloDivFechar : function() {
	   return Configuracao.EstiloDivFechar.concat("text-align: right; width: 5%; float: right;");
   },
   /**
	 * estilo do bot�o fechar com o estilo personalizado pelo usu�rio
	 */
   getEstiloBotaoFechar : function() {
	   return Configuracao.EstiloBotaoFechar.concat("padding-right: 3px;");
   },
   /**
	 * remover o conte�do da div de conte�do
	 */
   removerConteudo : function() {
	   this.getConteudo().innerHTML = "";
   },
   /**
	 * Centraliza a posi��o x da janela
	 */
   centralizarX : function() {
	   var largura = this.getPrincipal().clientWidth;
	   var inicioAreaUtil = window.pageXOffset;
	   this.janelaDiv.setStyle( {
		   left :(((largura / 2) - (this.janelaDiv.getWidth() / 2)) + inicioAreaUtil / 2) + "px"
	   });
   },
   /**
	 * Centraliza a posi��o y da janela.
	 */
   centralizarY : function() {
	   var altura = this.getPrincipal().clientHeight;
	   var inicioAreaUtil = window.pageYOffset;
	   this.janelaDiv.setStyle( {
		   top :(((altura / 2) - (this.janelaDiv.getHeight() / 2)) + inicioAreaUtil / 2) + "px"
	   });
   },
   /**
	 * coordenada X da janela
	 * 
	 * @param {Number} position
	 */
   setX : function(position) {
	   this.janelaDiv.setStyle( {
		   left :position + "px"
	   });
   },
   /**
	 * coordenada Y da janela
	 * 
	 * @param {Number} position
	 */
   setY : function(position) {
	   this.janelaDiv.setStyle( {
		   top :position + "px"
	   });
   },
   setHeight : function(height) {
	   if (height == null) {
		   Element.setStyle(this.janelaDiv, {
			   height :""
		   });
	   } else {
		   this.height = height;
		   Element.setStyle(this.janelaDiv, {
			   height :this.height
		   });
	   }
   },
   setWidth : function(width) {
	   if (width == null) {
		   Element.setStyle(this.janelaDiv, {
			   width :""
		   });
	   } else {
		   this.width = width;
		   Element.setStyle(this.janelaDiv, {
			   width :this.width
		   });
	   }
   },
   /**
	 * adiciona um manipulador de evento ao evento fechar janela, se a fun��o passada retornar false
	 * a janela n�o � fechada
	 * 
	 * @param {Function} oncloseFunction
	 */
   setOnClose : function(oncloseFunction) {
	   this.onClose = oncloseFunction;
   },
   /**
	 * retorna o manipulador de evento ao fechar janela
	 * 
	 * @return{Function}
	 */
   getOnClose : function() {
	   return this.onClose;
   },
   /**
	 * fecha e remove a janela
	 */
   fecharJanela : function() {
	   if ((this.getOnClose() != null) && (typeof this.getOnClose() == "function")) {
		   var isFechar = this.onClose();
		   if (!isFechar && (isFechar != undefined)) {
			   return;
		   }
	   }
	   var remover = ( function() {
		   try {
			   Element.remove(this.janelaDiv);
		   } catch (e) {}
		   if (this.transportRequest != null) {
			   this.transportRequest.abort();
			   if ((Configuracao.onCloseWindow != undefined) && (Configuracao.onCloseWindow != null)) {
				   Configuracao.onCloseWindow(this.transportRequest, this);
			   }
		   }
		   this.undoModal();
		   var indice = null;
		   for ( var index = 0; index < JanelaFactory.janelasAbertas.length; index++) {
			   var janela = JanelaFactory.janelasAbertas[index];
			   if (janela.idConteudo == this.idConteudo) {
				   if (index != 0) {
					   JanelaFactory.janelasAbertas[index - 1].focusJanela();
				   }
				   indice = index;
				   break;
			   }
		   }

		   JanelaFactory.janelasAbertas[indice] = null;
		   JanelaFactory.janelasAbertas = JanelaFactory.janelasAbertas.compact();
	   }).bind(this);

	   if (Configuracao.efeitoFechar != null) {
		   this.efeitoFechar();
		   window.setTimeout(remover, Configuracao.duracaoEfeitoFechar);
	   } else {
		   remover();
	   }
   },
   /**
	 * bloqueia os elementos que est�o por tr�z da janela que foi aberta, � sempre bloqueado o resto
	 * da p�gina se for a primeira janela a ser aberta ou a janela anterior a que acabou de ser
	 * aberta
	 */
   makeModal : function() {
	   var cobertor = null
	   if (Prototype.Browser.IE && (window.navigator.appVersion.indexOf("MSIE 6.0") != -1)) {
		   cobertor = $(Builder.node("iframe", {
		      frameBorder :"no",
		      scrolling :"no",
		      id :this.idElementoCobertor
		   }));
	   } else {
		   cobertor = $(Builder.node("div", {
			   id :this.idElementoCobertor
		   }));
	   }
	   cobertor.setOpacity(Configuracao.opacidadeFundo);
	   cobertor.setStyle( {
	      position :"absolute",
	      backgroundColor :"white",
	      top :"0px",
	      left :"0px",
	      height :"100%",
	      width :"100%"
	   });
	   cobertor.setStyle( {
		   zIndex :JanelaFactory.janelasAbertas.length + 4
	   });
	   this.zIndex = JanelaFactory.janelasAbertas.length + 5;
	   this.janelaDiv.setStyle( {
		   zIndex :this.zIndex
	   });
	   if (this.janelaAnterior != null) {
		   if (this.janelaAnterior.janelaDiv.lastChild.id == cobertor.id) {
			   return;
		   }
		   this.janelaAnterior.janelaDiv.appendChild(cobertor);
	   } else {
		   if (this.getPrincipal().lastChild.id == cobertor.id) {
			   return;
		   }
		   this.getPrincipal().appendChild(cobertor);
	   }
	   cobertor.setStyle( {
	      height :cobertor.parentNode.clientHeight + "px",
	      width :cobertor.parentNode.clientWidth + "px"
	   });
   },
   /**
	 * descobre a janela anterior ou o fundo da p�gina toda
	 */
   undoModal : function() {
	   if (!this.modal) {
		   return;
	   }
	   this.modal = false;
	   if (JanelaFactory.janelasAbertas.length == 1) {
		   this.janelaAnterior = null;
	   }
	   if (this.janelaAnterior != null) {
		   Element.remove(this.janelaAnterior.janelaDiv.lastChild);
	   } else {
		   if (JanelaFactory.janelasAbertas.length == 1) {
			   var elementoCobertor = this.getPrincipal().lastChild;
			   while (elementoCobertor.id != this.idElementoCobertor) {
				   elementoCobertor = elementoCobertor.previousSibling;
			   }
			   Element.remove(elementoCobertor);
		   }
	   }
   },

   /**
	 * set a janela anterior a que foi acabou de ser aberta, se for a primeira a propriedade � nula
	 */
   setJanelaAnterior : function(janela) {
	   this.janelaAnterior = janela;
   },

   /**
	 * retorna o postBody da requisi��o POST
	 */
   getPostBody : function() {
	   if (this.postBody == null) {
		   return undefined;
	   }
	   return this.postBody;
   },

   /**
	 * seta o postBody da requisi��o POST
	 * 
	 * @param {String} postBody
	 */
   setPostBody : function(postBody) {
	   this.setMethod("post");
	   this.postBody = postBody;
   },

   /**
	 * retorna a url que dever� fornecer o conte�do HTML da div
	 */
   getUrl : function() {
	   if (this.url == null) {
		   return this.url;
	   }
	   return this.url;
   },

   /**
	 * set o endere�o que dever� fornecer o conte�do HTML da div
	 */
   setUrl : function(url) {
	   this.url = url;
   },

   /**
	 * m�todo a ser utilizado na requisi��o
	 */
   getMethod : function() {
	   return this.httpMethod;
   },

   /**
	 * seta o m�todo a ser utilizado na requisi��o POST ou GET (padr�o: GET)
	 */
   setMethod : function(method) {
	   this.httpMethod = method;
   },

   /**
	 * avaliar scripts
	 */
   getEvalscripts : function() {
	   return this.evalScripts;
   },

   /**
	 * seta avaliar scripts
	 */
   setEvalscripts : function(state) {
	   this.evalScripts = state;
   },

   /**
	 * carrega o conte�do html da url passada dentro do conte�do da div
	 * 
	 * @param {String} url
	 */
   carregarUrl : function(url) {
	   if ((url != null) && (url != undefined)) {
		   this.setUrl(url);
	   }
	   if (this.getUrl() == null) {
		   return;
	   }
	   var updater = new Ajax.Updater(this.getConteudo(), this.getUrl(), {
	      method :this.getMethod(),
	      postBody :this.getPostBody(),
	      evalScripts :this.getEvalscripts(),
	      onSuccess :this.onSuccess,
	      onComplete :this.onComplete,
	      onFailure :this.onFailure
	   });
	   this.transportRequest = updater.transport;
	   this.ajaxOptions = updater.options;
   },
   /**
	 * recarrega o conte�do da DIV com o html retornado pela URL
	 */
   recarregarConteudo : function() {
	   this.carregarUrl();
   },
   /**
	 * manipulador de evento
	 * 
	 * @param {Ajax.Request} ajaxRequest
	 * @param {Object} json
	 */
   onSuccess : function(ajaxRequest, json) {
	   if ((Configuracao.onSuccess != undefined) && (Configuracao.onSuccess != null)) {
		   var janela = JanelaFactory.getJanelaByXHR(ajaxRequest.transport);
		   Configuracao.onSuccess(ajaxRequest.transport, janela);
	   }
   },

   /**
	 * manipulador de eventos
	 * 
	 * @param {Ajax.Request} ajaxRequest
	 * @param {Object} json
	 */
   onComplete : function(ajaxRequest, json) {
	   var janela = JanelaFactory.getJanelaByXHR(ajaxRequest.transport);
	   Configuracao.onComplete(ajaxRequest.transport, janela);
   },

   /**
	 * manipulador de eventos
	 * 
	 * @param {Ajax.Request} ajaxRequest
	 * @param {Object} json
	 */
   onFailure : function(ajaxRequest, json) {
	   if ((Configuracao.onSuccess != undefined) && (Configuracao.onSuccess != null)) {
		   var janela = JanelaFactory.getJanelaByXHR(ajaxRequest.transport);
		   Configuracao.onFailure(ajaxRequest.transport, janela);
	   }
   },
   /**
	 * manipulador de eventos
	 * 
	 * @param {Ajax.Request} ajaxRequest
	 */
   onException : function(request) {
	   Configuracao.onFailure(request, this);
   },
   /**
	 * set posfunction que dever� ser executada quando o conte�do HTML estiver preenchido dentro da
	 * div
	 * 
	 * @param {Function} posFunction
	 */
   addOnComplete : function(posFunction) {
	   this.onCompletePosFunction = posFunction;
   },

   /**
	 * corrige posi��o da div se a p�gina tiver barras de rolagem verticais, deixando sempre a janela
	 * no centro da tela verticalmente
	 */
   corrigirPosicaoDiv : function() {
	   var div = this.janelaDiv;
	   if (((document.body.clientHeight > document.documentElement.clientHeight) && (document.documentElement.scrollTop != 0))
	      || (document.body.clientHeight < (div.offsetTop + div.offsetHeight))) {
		   if ((document.body.clientHeight > document.documentElement.clientHeight)
		      && (document.documentElement.scrollTop != 0)) {
			   if (document.documentElement.clientHeight > div.offsetHeight) {
				   var tamanhoDivModificado = ((document.documentElement.scrollTop + (document.documentElement.clientHeight / 2)) - (div.offsetHeight / 2))
				      + "px";
				   Element.setStyle(div, {
					   top :tamanhoDivModificado
				   });
			   } else {
				   var tamanhoDivModificado = document.documentElement.scrollTop + "px";
				   Element.setStyle(div, {
					   top :tamanhoDivModificado
				   });
			   }
		   } else {
			   if (document.body.clientHeight > div.offsetHeight) {
				   var tamanhoDivModificado = ((document.body.clientHeight / 2) - (div.offsetHeight / 2))
				      + "px";
				   Element.setStyle(div, {
					   top :tamanhoDivModificado
				   });
			   } else {
				   Element.setStyle(div, {
					   top :"0px"
				   });
			   }
		   }
	   }
   }
};
/**
 * Factory de janelas
 * 
 * toda janela dever� ser fabricada a partir do getInstance desta classe
 */
var JanelaFactory = Class.create();
JanelaFactory = {
   /**
	 * de Janela
	 * 
	 * @type Array
	 */
   janelasAbertas :new Array(),
   /**
	 * Instancia uma nova Janela, registra e retorna o objeto
	 * 
	 * @param {Integer} height
	 * @param {Integer} width
	 * @param {Integer} left
	 * @param {Integer} top
	 * @param {String} titulo
	 * @param {String} idConteudo
	 * 
	 * @return instancia da janela com os par�metros passados
	 * @type Janela
	 */
   getInstance : function(height, width, left, top, titulo, idConteudo) {
	   if ($(idConteudo) == null) {
		   var novaJanela = new Janela(height, width, left, top, titulo, idConteudo);
		   if (this.janelasAbertas.length != 0) {
			   var janelaAnterior = this.janelasAbertas[(this.janelasAbertas.length - 1)];
			   novaJanela.setJanelaAnterior(janelaAnterior);
		   }
		   this.janelasAbertas.push(novaJanela);
		   novaJanela.makeModal();
		   return novaJanela;
	   }
	   return null;
   },
   /**
	 * fechar janela ativa
	 */
   fecharJanelaAtiva : function() {
	   this.janelasAbertas[this.janelasAbertas.length - 1].fecharJanela();
	   if (this.janelasAbertas.length != 0) {
		   this.janelasAbertas[this.janelasAbertas.length - 1].focusJanela();
	   }
   },
   /**
	 * fecha a janela pelo id da mesma
	 * 
	 * @param {String} idJanela
	 */
   fecharJanela : function(idJanela) {
	   var janela = this.getJanelaById(idJanela);
	   if (janela != null) {
		   janela.fecharJanela();
	   }
   },
   /**
	 * retorna uma inst�ncia aberta da janela pelo id da mesma
	 * 
	 * @param {String} idJanela
	 * 
	 * @return janela criada
	 * @type Janela
	 */
   getJanelaById : function(idJanela) {
	   for ( var index = 0; index < this.janelasAbertas.length; index++) {
		   var janela = this.janelasAbertas[index];
		   if (janela.idConteudo == idJanela) {
			   return janela;
		   }
	   }
	   return null;
   },
   /**
	 * retorna uma janela aberta onde o XHR � igual ao xhr passado
	 * 
	 * @param {XMLHttpRequest} transportRequest
	 * 
	 * @return inst�ncia da janela
	 * @type Janela
	 */
   getJanelaByXHR : function(transportRequest) {
	   for ( var index = 0; index < this.janelasAbertas.length; index++) {
		   var janela = this.janelasAbertas[index];
		   if (janela.transportRequest == transportRequest) {
			   return janela;
		   }
	   }
	   return null;
   }
};
