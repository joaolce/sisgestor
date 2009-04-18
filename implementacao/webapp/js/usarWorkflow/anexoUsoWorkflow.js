/**
 * Comportamentos para anexos do UC Usar Workflow.
 * 
 * @author João Lúcio
 * @since 09/04/2009
 */
var AnexoUsoWorkflow = Class.create();
AnexoUsoWorkflow.prototype = {

   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * Exclui o(s) anexo(s) selecionado(s)
	 */
   excluirAnexos : function() {
	   var idAnexos = dwr.util.getValue("anexosSelecionados");
	   var tamanhoAnexos = idAnexos.length;
	   var parametros = "";
	   for ( var i = 0; i < tamanhoAnexos; i++) {
		   parametros += "anexosSelecionados=" + idAnexos[i];
		   if (i != (tamanhoAnexos - 1)) {
			   parametros += "&";
		   }
	   }
	   requestUtils.simpleRequest("anexoUsoWorkflow.do?method=excluirAnexo&" + parametros,
	      ( function() {
		      if (requestUtils.status) {
			      JanelaFactory.getJanelaById("divVisualizarAnexos").recarregarConteudo();
			      usarWorkflow.popularHistorico();
		      }
	      }).bind(this));
   },

   /**
	 * Abre o popup para inserir um novo anexo ao uso.
	 */
   telaInserirAnexo : function() {
	   var url = "anexoUsoWorkflow.do?method=popupInserirAnexo&usoWorkflow="
	      + $F("idUsoWorkflowAnexo");
	   createWindow(120, 400, 250, 100, "Novo arquivo anexo", "divInserirArquivoAnexo", url);
   },

   /**
	 * Executado após inserir um novo anexo.
	 */
   anexoConcluido : function() {
	   window.parent.JanelasComuns.showMessage("O arquivo foi gravado com sucesso!", true,
	      function() {
		      window.parent.JanelaFactory.fecharJanela("divInserirArquivoAnexo");
	      });
	   window.parent.usarWorkflow.popularHistorico();
	   window.parent.JanelaFactory.getJanelaById("divVisualizarAnexos").recarregarConteudo();
   }
};

var anexoUsoWorkflow = new AnexoUsoWorkflow();
