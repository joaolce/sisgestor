/**
 * A��o a ser realizada ao iniciar a p�gina
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
	 * transfere todos os �tens (Permiss�o) de um combo para o outro e vice-versa
	 *  
	 * @param {String} origem
	 * @param {String} destino
	 */
	transfereTodos: function(origem, destino){
		
		ComboFunctions.transfereTodos(origem, destino);
		
	},
	
	/**
	 * Transfere as permiss�es de um combo para o outro onde ficar�o os �tens selecionados
	 * 
	 * @param {String} origem
	 * @param {String} destino
	 */
	transferePermissao: function(origem, destino){
	
		ComboFunctions.transfereOptions(origem, destino);
	},

   tabelaTelaPrincipal :null,

   /**
    * Recupera todas permiss�es.
    * @return
    */
   getTodasPermissoes: function(){
	   ManterUsuarioDWR.getTodasPermissoes(function(permissoes){
		   dwr.util.removeAllOptions("permissoesInformadas");
		   dwr.util.addOptions("permissoesInformadas", permissoes, "id", "descricao");
	   });
   }
};

var permissao = new ComportamentosTela();
