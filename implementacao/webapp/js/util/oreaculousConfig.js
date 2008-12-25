var Configuracao = Class.create();
Configuracao = {
   DivPrincipal :"principal",
   EstiloBarraTitulo :"font-weight: bolder;",
   EstiloBarra :"border: 1px solid activeborder; color: rgb(0, 0, 0); background-color: rgb(229, 233, 237);",
   EstiloJanela :"border: 1px solid rgb(183, 176, 164);  background-color: rgb(250, 250, 250);",
   EstiloDivFechar :"font-weight:bolder;",
   EstiloBotaoFechar :"text-decoration: none; color: #3D5E11; ",
   EstiloConteudo :"padding: 4px;",
   onSuccess : function(request, janela) {
	   if ((request.status == 0) || (request.status == 403)) {
		   requestUtils.showErrorDetail(request);
		   return;
	   }
	   // verificar se não é a página de login
	   if (isBlankOrNull(request.getResponseHeader("ajaxResponse"))) {
		   janela.setOnClose(null);
		   janela.fecharJanela();
		   janela.ajaxOptions.evalScripts = false;
		   JanelasComuns.sessaoFinalizada();
		   hideLoading();
		   return false;
	   }
   },
   onComplete : function(request, janela) {
	   if ((request.status == 0) || (request.status == 403)) {
		   requestUtils.showErrorDetail(request);
		   return;
	   }
	   var ajaxResponse = request.getResponseHeader("ajaxResponse");
	   if ((ajaxResponse == null) || new String(ajaxResponse).blank()) {
		   return false;
	   }
	   hideLoading();
	   if ((janela.onCompletePosFunction != null)
	      && (typeof janela.onCompletePosFunction == "function")) {
		   janela.onCompletePosFunction();
	   }
	   configuraPagina(janela.idConteudo);
	   Form.focusFirstElement(janela.getConteudo().getElementsByTagName("form").item(0));
   },
   onFailure : function(request, janela) {
	   if ((request.status == 0) || (request.status == 403)) {
		   requestUtils.showErrorDetail(request);
	   }
	   janela.ajaxOptions.evalScripts = false;
	   janela.setOnClose(null);
	   janela.fecharJanela();
	   requestUtils.showErrorDetail(request);
   },
   onException : function(request, janela) {

   },
   onCloseWindow : function(request, janela) {
	   loadingFactory.hideLoading();
   },
   efeitoAbrir : function(janelaDiv) {
	   Element.show(janelaDiv);
   },
   duracaoEfeitoFechar :500,
   efeitoFechar :null,
   opacidadeFundo :0.5
};
