var JanelasComuns = Class.create();

JanelasComuns = {
   imagemFalha :"imagens/falha.png",
   imagemInformacao :"imagens/informacao.png",
   imagemSucesso :"imagens/check.png",
   imagemConfirmacao :"imagens/confirmacao.png",

   showInformation : function(mensagem) {
	   if ($("idInformacao") != null) {
		   return;
	   }
	   var janela = createWindow(110, 600, 200, 100, "Informa��o", "idInformacao", null);
	   $("idInformacao").parentNode.style.height = "";
	   Element.setStyle("idInformacao", {
		   paddingBottom :"10px"
	   });
	   var img = Builder.node("img", {
	      src :this.imagemInformacao,
	      width :16,
	      height :16
	   });
	   var divImg = Builder.node("div", {
		   style :"float: left; margin-right: 10px; margin-left: 3px;"
	   }, [ img ]);
	   var divMsg = Builder.node("div", {
		   style :"padding-top: 4px; margin-left: 30px;"
	   });
	   divMsg.innerHTML = mensagem;
	   Element.setStyle(divMsg, {
	      fontWeight :"bold",
	      color :"#007AE2"
	   });
	   var primeiro = Builder.node("div", [ divImg, divMsg ]);
	   var segundo = Builder.node("div", {
	      align :"center",
	      style :"clear: both; padding-top: 10px;"
	   }, [ Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"OK",
	      id :"confirmarBotao"
	   }) ]);
	   $("idInformacao").appendChild(primeiro);
	   $("idInformacao").appendChild(segundo);

	   var funcConfirmar = function() {
		   janela.fecharJanela();
	   };
	   Event.observe($("confirmarBotao"), "click", funcConfirmar);
	   Form.Element.focus("confirmarBotao");
   },
   /**
	 * mostrar mensagem de confirma��o
	 * 
	 * @param {String} mensagem
	 * @param {Function} funcTrue fun��o a ser executada caso o usu�rio clique ok
	 * @param {Function} funcFalse fun��o a ser executada caso o usu�rio clique cancelar (opcional)
	 */
   showConfirmDialog : function(mensagem, funcTrue, funcFalse) {
	   if ($("idConfirmacao") != null) {
		   return;
	   }
	   var janela = createWindow(110, 600, 200, 100, "Confirma��o", "idConfirmacao", null);
	   janela.setHeight(null);
	   Element.setStyle("idConfirmacao", {
		   paddingBottom :"10px"
	   });
	   var img = Builder.node("img", {
	      src :this.imagemConfirmacao,
	      width :22,
	      height :22
	   });
	   var divImg = Builder.node("div", {
		   style :"float: left; margin-right: 10px; margin-left: 3px;"
	   }, [ img ]);
	   var divMsg = Builder.node("div", {
		   style :"padding-top: 4px; margin-left: 30px;"
	   }, [ document.createTextNode(mensagem) ]);
	   Element.setStyle(divMsg, {
	      fontWeight :"bold",
	      color :"#007AE2"
	   });
	   var primeiro = Builder.node("div", [ divImg, divMsg ]);
	   var segundo = Builder.node("div", {
	      align :"center",
	      style :"clear: both; padding-top: 10px;"
	   }, [ Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"OK",
	      id :"confirmarBotao"
	   }), Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"Cancelar",
	      id :"cancelarBotao"
	   }) ]);
	   $("idConfirmacao").appendChild(primeiro);
	   $("idConfirmacao").appendChild(segundo);
	   var funcConfirmar = function() {
		   JanelaFactory.fecharJanela("idConfirmacao");
		   funcTrue();
	   };
	   Event.observe($("confirmarBotao"), "click", funcConfirmar);
	   var funcCancelar = function() {
		   if (funcFalse != undefined) {
			   funcFalse();
		   }
		   JanelaFactory.fecharJanela("idConfirmacao");
	   };
	   Event.observe("cancelarBotao", "click", funcCancelar);
	   Form.Element.focus("cancelarBotao");
   },

   /**
	 * Mostra uma mensagem de confirma��o com op��o de cancelar
	 * 
	 * @param {String} mensagem mensagem a ser exibida
	 * @param {Function} funcTrue fun��o a ser executada caso o usu�rio clique 'Sim'
	 * @param {Function} funcFalse fun��o a ser executada caso o usu�rio clique 'N�o'
	 */
   showConfirmCancelDialog : function(mensagem, funcTrue, funcFalse) {
	   if ($("idConfirmacao") != null) {
		   return;
	   }
	   var janela = createWindow(110, 600, 200, 100, "Confirma��o", "idConfirmacao", null);
	   janela.setHeight(null);
	   Element.setStyle("idConfirmacao", {
		   paddingBottom :"10px"
	   });
	   var img = Builder.node("img", {
	      src :this.imagemConfirmacao,
	      width :22,
	      height :22
	   });
	   var divImg = Builder.node("div", {
		   style :"float: left; margin-right: 10px; margin-left: 3px;"
	   }, [ img ]);
	   var divMsg = Builder.node("div", {
		   style :"padding-top: 4px; margin-left: 30px;"
	   }, [ document.createTextNode(mensagem) ]);
	   Element.setStyle(divMsg, {
	      fontWeight :"bold",
	      color :"#007AE2"
	   });
	   var primeiro = Builder.node("div", [ divImg, divMsg ]);
	   var segundo = Builder.node("div", {
	      align :"center",
	      style :"clear: both; padding-top: 10px;"
	   }, [ Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"Sim",
	      id :"botaoSim"
	   }), Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"N�o",
	      id :"botaoNao"
	   }), Builder.node("input", {
	      type :"button",
	      CLASS :"botaoOkCancelar",
	      value :"Cancelar",
	      id :"botaoCancelar"
	   }) ]);
	   $("idConfirmacao").appendChild(primeiro);
	   $("idConfirmacao").appendChild(segundo);
	   var funcSim = function() {
		   janela.fecharJanela();
		   funcTrue();
	   };
	   Event.observe($("botaoSim"), "click", funcSim);
	   var funcNao = function() {
		   funcFalse();
		   janela.fecharJanela();
	   };
	   Event.observe("botaoNao", "click", funcNao);
	   var funcCancelar = function() {
		   janela.fecharJanela();
	   };
	   Event.observe("botaoCancelar", "click", funcCancelar);
	   Form.Element.focus("botaoCancelar");
   },

   carregando :false,
   /**
	 * mostrar tela de aguarde enquanto uma p�gina carrega
	 */
   carregarNovaPagina : function() {
	   if (this.carregando) {
		   return;
	   }
	   this.carregando = true;
	   var janela = createWindow(80, 400, 300, 100, "Carregando...", "janelaRedirecionando");
	   janela.removerBotaoFechar();
	   janela.adicionarConteudo(Builder.node("img", {
	      height :16,
	      width :16,
	      src :requestUtils.imagemInformacao,
	      style :"margin-bottom:-3px; margin-right: 5px;"
	   }));

	   janela.adicionarConteudo(document
	      .createTextNode("Aguarde enquanto uma nova p�gina � carregada..."));
	   Element.setStyle("janelaRedirecionando", {
	      color :"#007AE2",
	      fontWeight :"bold"
	   });
   },
   /**
	 * @param {String} mensagem
	 */
   showLoadingStatus : function(mensagem) {
	   var janela = createWindow(50, 350, 300, 100, "Aguarde", "telaAguarde");
	   janela.undoModal();
	   janela.removerBotaoFechar();
	   dwr.util.setValue("telaAguarde", mensagem);
	   Element.setStyle("telaAguarde", {
	      fontWeight :"bold",
	      color :"#007AE2"
	   });
	   return janela;
   },
   /**
	 * Exibir uma mensagem em vermelho ou verde de acordo com o status passado
	 * 
	 * @param {String} message
	 * @param {Boolean} status
	 * @param {Function} posFunction fun��o que ser� executada quando o usu�rio clicar ok na tela
	 */
   showMessage : function(message, status, posFunction) {
	   try {
		   requestUtils.mensagens = new Array();
		   requestUtils.mensagens.push(message);
		   requestUtils.status = status == undefined ? false : status;
		   requestUtils.posClickFunction = typeof posFunction == "function" ? posFunction : null;
		   requestUtils.showMessages();
	   } catch (e) {}
   },

   showMessages : function(status) {
	   if (status != undefined) {
		   requestUtils.status = status;
	   }
	   requestUtils.showMessages();
   },
   /**
	 * dever� ser perguntado ao usu�rio quando ele solicitar alguma funcionalidade ass�ncrona
	 */
   sessaoFinalizada : function() {
	   JanelasComuns.showConfirmDialog(
	      "A sess�o atual foi finalizada, deseja ir para p�gina de login?", function() {
		      window.location.reload();
	      });
   },
   /**
	 * 
	 * @param {String} mens
	 */
   setMensagens : function(mens) {
	   requestUtils.setMensagens(mens);
   }
};
/**
 * cria uma janela dentro da tela atual de acordo com as especifica��es passadas e carrega a url
 * passada dentro dela
 * 
 * @param {Number} heightP altura em pixels
 * @param {Number} widthP largura em pixels
 * @param {Number} leftP posi��o x em pixels
 * @param {Number} topP posi��o y em pixels
 * @param {String} titulo titulo da janela que ser� criada
 * @param {String} id id a ser atribu�do ao div que ser� criado
 * @param {String} url necess�rio carregar nenhuma url passar esse par�metro como null
 * @param {Function} posFunction fun��o a ser executada ap�s o conte�do ser carregado
 * 
 * @return inst�ncia do objeto janela criado
 * @type Janela
 */
function createWindow(heightP, widthP, leftP, topP, titulo, id, url, posFunction) {
	var janela = JanelaFactory.getInstance(heightP, widthP, leftP, topP, titulo, id);
	if (janela != null) {
		janela.carregarUrl(url);
		if (posFunction != undefined) {
			janela.addOnComplete(posFunction);
		}
	}
	return janela;
}
