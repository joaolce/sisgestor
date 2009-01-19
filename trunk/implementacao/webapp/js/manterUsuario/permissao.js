/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	permissao.getTodasPermissoes();
});

/**
 * Comportamentos para o UC Manter Usuario.
 */
var ComportamentosTela = Class.create();
ComportamentosTela.prototype = {
   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * transfere todos os ítens (Permissão) de um combo para o outro e vice-versa
	 * 
	 * @param {String} origem
	 * @param {String} destino
	 */
   transfereTodos : function(origem, destino) {

	   ComboFunctions.transfereTodos(origem, destino);
	   console.debug("transfere 2");

   },

   /**
	 * Transfere as permissões de um combo para o outro onde ficarão os ítens selecionados
	 * 
	 * @param {String} origem
	 * @param {String} destino
	 */
   transferePermissao : function(origem, destino) {

	   ComboFunctions.transfereOptions(origem, destino);
	   console.debug("transfere");
   },

   tabelaTelaPrincipal :null,

   /**
	 * Recupera todas permissões.
	 * 
	 * @return
	 */
   getTodasPermissoes : function() {
	   ManterUsuarioDWR.getTodasPermissoes( function(permissoes) {
		   dwr.util.removeAllOptions("permissoesInform");
		   dwr.util.addOptions("permissoesInform", permissoes, "id", "descricao");
	   });
   }
};

var permissao = new ComportamentosTela();
