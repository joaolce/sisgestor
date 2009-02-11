/**
 * ABAS
 * 
 * <b>Instru��es de uso</b>
 * 
 * <code>
 *	 	<a href="#" id="tabRegistroAncora">
 *			Aba 1
 *		</a>
 *		<a href="#" id="tabCidadaoAncora">
 *			Aba 2
 *		</a>
 *		<div id="tabCriteriosPesquisa">
 *			Conte�do da tab1
 *		</div>
 *		<div id="tabResultadoPesquisa">
 *			Conte�do da tab2
 *		</div>
 * 
 * 		<script type="text/javascript">
 * 			Event.observe(window, "load", function(){
 * 				//lembrando que a ordem importa, tem que ser primeir o id do bot�o que vai ativar a tab depois o conte�do dela
 *				FactoryAbas.getNewAba("tabRegistroAncora,tabRegistro;tabCidadaoAncora,tabCidadao"); 
 * 			});
 * 		</script>
 * 
 *	</code>
 */

var Aba = Class.create();

Aba.prototype = {
   /**
	 * estilo para o bot�o de aba ativa
	 * 
	 * @type String
	 */
   styleClassAtivo :"mtabi",

   /**
	 * estilo para o bot�o de aba inativa
	 * 
	 * @type String
	 */
   styleClassInativo :"mtaba",

   /**
	 * pares de abas (bot�o e div de conte�do da aba)
	 * 
	 * @type Hash
	 */
   pares :null,

   /**
	 * ao inicializar a primeira aba passada � a ativa
	 */
   parAtivo :null,

   /**
	 * @constructor
	 * 
	 * dever� ser passado os pares de bot�o/conte�do como segue o exemplo
	 * botao1,conteudo1;botao2,conteudo2;botao3,conteudo3
	 * @param {String} conjuntos
	 */
   initialize : function(conjuntos) {
	   this.pares = new Hash();
	   var pares = conjuntos.split(";");
	   this.parAtivo = null;
	   for ( var index = 0; index < pares.length; index++) {
		   var items = pares[index];
		   items = items.split(",");
		   this.addPar(items[0], items[1]);
	   }
   },
   /**
	 * adicionar um par de bot�o/conteudo
	 */
   addPar : function(elemento, conteudo) {
	   this.pares.set(elemento, conteudo);
	   if (this.parAtivo == null) {
		   $(elemento).className = this.styleClassAtivo;
		   $(conteudo).show();
		   this.parAtivo = elemento;
	   } else {
		   $(elemento).className = this.styleClassInativo;
		   $(conteudo).hide();
	   }
	   Event.observe(elemento, "click", this.setAtivo.bindAsEventListener(this, elemento));
   },
   /**
	 * define quem est� ativo a partir do clique e inativa as outras abas
	 */
   setAtivo : function(e) {
	   var elemento = $A(arguments)[1];
	   this.pares.each(( function(pair) {
	   	var divArea = $(pair.value);
		   if (pair.key != elemento) {
			   $(pair.key).className = this.styleClassInativo;
			   divArea.hide();
		   } else {
			   $(pair.key).className = this.styleClassAtivo;
			   divArea.show();
			   var firstDescendant = divArea.firstDescendant();
			   if((firstDescendant != null) && (firstDescendant.nodeName.toLowerCase() == "textarea") && (divArea.descendans().length == 1)) {
			   	firstDescendant.focus();
			   }
		   }
	   }).bind(this));
   },
   /**
	 * mostra a aba que cont�m o elemento passado
	 * 
	 * @type HTMLElement
	 */
   ativaTabPorElemento : function(elemento) {
	   if ($(elemento) == null) {
		   return false;
	   }
	   if (Object.isArray(elemento)) {
		   elemento = elemento[0];
	   }
	   var resultado = false;
	   this.pares.each(( function(iterator) {
		   if ($(elemento).descendantOf(iterator.value)) {
			   resultado = true;
			   this.setAtivo(null, iterator.key);
			   throw $break;
		   }
	   }).bind(this));
	   return resultado;
   },
   /**
	 * ativa a tab pelo id do elemento bot�o
	 * 
	 * @param {String} idElemento
	 */
   ativaTabPorId : function(idElemento) {
	   if (this.pares.get(idElemento) != null) {
		   this.setAtivo(null, idElemento);
		   return true;
	   }
	   return false;
   }
};
/**
 * Fachada para cria��o de abas
 */
var FactoryAbas = Class.create();
FactoryAbas = {
   /**
	 * todas as abas ativas
	 * 
	 * @type Array
	 */
   abasAtivas :new Array(),
   /**
	 * @param {String} conjuntos
	 * @return{Aba}
	 */
   getNewAba : function(conjuntos) {
	   var aba = new Aba(conjuntos);
	   this.abasAtivas.push(aba);
	   return aba;
   },
   /**
	 * procura o elemento passado em todos os conjuntos de abas e ativa a aba onde ele foi encontrado
	 * 
	 * @param {Element}elemento
	 */
   ativaAbaPorElemento : function(elemento) {
	   if (typeof elemento == "string") {
		   var el = $(elemento);
		   if (el == null) {
			   elemento = document.getElementsByName(elemento).item(0);
		   }
	   }
	   if (elemento == null) {
		   return;
	   }
	   this.abasAtivas.each( function(aba) {
		   if (aba.ativaTabPorElemento(elemento)) {
			   throw $break;
		   }
	   });
   },
   /**
	 * Ativa a aba pelo id do bot�o da mesma
	 * 
	 * @param {String} idBotaoAba
	 */
   ativaAbaPorId : function(idBotaoAba) {
	   this.abasAtivas.each( function(aba) {
		   if (aba.ativaTabPorId(idBotaoAba)) {
			   throw $break;
		   }
	   });
   }
};
