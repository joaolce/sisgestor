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
	initialize : function(){},
	
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
	   requestUtils.simpleRequest("anexoUsoWorkflow.do?method=excluirAnexo&" + parametros, ( function() {
		   if (requestUtils.status) {
			   this.pesquisarAnexos();
		   }
	   }).bind(this));
   }
};

var anexoUsoWorkflow = new AnexoUsoWorkflow();