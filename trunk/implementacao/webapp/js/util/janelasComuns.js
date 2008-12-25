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
	   var janela = createWindow(110, 600, 200, 100, "Informação", "idInformacao", null);
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
	 * mostrar mensagem de confirmação
	 * 
	 * @param {String} mensagem
	 * @param {Function} funcTrue função a ser executada caso o usuário clique ok
	 * @param {Function} funcFalse função a ser executada caso o usuário clique cancelar (opcional)
	 */
   showConfirmDialog : function(mensagem, funcTrue, funcFalse) {
	   if ($("idConfirmacao") != null) {
		   return;
	   }
	   var janela = createWindow(110, 600, 200, 100, "Confirmação", "idConfirmacao", null);
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
   carregando :false,
   /**
	 * mostrar tela de aguarde enquanto uma página carrega
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
	      .createTextNode("Aguarde enquanto uma nova página é carregada..."));
	   Element.setStyle("janelaRedirecionando", {
	      color :"#007AE2",
	      fontWeight :"bold"
	   });
   },
   /**
	 * Exibir uma mensagem em vermelho ou verde de acordo com o status passado
	 * 
	 * @param {String} message
	 * @param {Boolean} status
	 * @param {Function} posFunction função que será executada quando o usuário clicar ok na tela
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
	 * deverá ser perguntado ao usuário quando ele solicitar alguma funcionalidade assíncrona
	 */
   sessaoFinalizada : function() {
	   JanelasComuns.showConfirmDialog(
	      "A sessão atual foi finalizada, deseja ir para página de login?", function() {
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
 * cria uma janela dentro da tela atual de acordo com as especificações passadas e carrega a url
 * passada dentro dela
 * 
 * @param {Number} heightP altura em pixels
 * @param {Number} widthP largura em pixels
 * @param {Number} leftP posição x em pixels
 * @param {Number} topP posição y em pixels
 * @param {String} titulo titulo da janela que será criada
 * @param {String} id id a ser atribuído ao div que será criado
 * @param {String} url necessário carregar nenhuma url passar esse parâmetro como null
 * @param {Function} posFunction função a ser executada após o conteúdo ser carregado
 * 
 * @return {Janela} instância do objeto janela criado
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
