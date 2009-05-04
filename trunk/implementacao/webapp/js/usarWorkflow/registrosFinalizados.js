/**
 * Ação a ser realizada ao iniciar a página.
 */
Event.observe(window, "load", function() {
	registrosFinalizados.pesquisar();
});

/**
 * Comportamentos para fazer a pesquisa de registros finalizados.
 * 
 * @author João Lúcio
 * @since 04/05/2009
 */
var RegistrosFinalizados = Class.create();
RegistrosFinalizados.prototype = {

   /**
	 * @constructor
	 */
   initialize : function() {},

   pesquisar : function() {

   }
};

var registrosFinalizados = new RegistrosFinalizados();
