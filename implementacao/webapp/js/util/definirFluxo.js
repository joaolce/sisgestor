/**
 * Comportamentos para definição dos fluxos e comportamentos das divs
 * 
 * @author Thiago
 * @since 04/03/2009
 */
var DefinirFluxo = Class.create();
DefinirFluxo.prototype = {
   /**
	 * @constructor
	 */
   initialize : function() {
	   this.listaFluxos = new Array();
	   this.dives = new Hash();
	   this.esquerda = 0;
	   this.topo = 0;
	   this.varTopo = -80;
   },

   /** Flag para saber a div de origem para ligação */
   origem :null,

   /** Armazena os fluxos definidos na página. */
   listaFluxos :null,

   /** Mapa com as divs. */
   dives :null,

   /** Posição horizontal da div. */
   esquerda :0,

   /** Posição vertical da div */
   topo :0,

   /** Variável para ajustar posição vertical(topo) */
   varTopo :-80,

   /**
	 * Cria um circulo(div) para fazer representação na página
	 * 
	 * @param id Código identificador
	 * @param nome Nome
	 * @param descricao Descrição
	 */
   gerarRepresentacao : function(id, nome, descricao) {
	   var div = $(Builder.node("div", {
	      id :id,
	      ondblclick :"fluxo.ligar(\"" + id + "\");",
	      title :descricao
	   }));

	   var divInterna = $(Builder.node("div", [ document.createTextNode(nome) ]));

	   Element.setStyle(divInterna, {
	      top :"30px",
	      position :"relative"
	   });

	   Element.setStyle(div, {
	      left :this.esquerda + "px",
	      top :this.topo + "px",
	      height :"80px",
	      width :"80px",
	      cursor :"move"
	   });
	   div.appendChild(divInterna);
	   $("divFluxos").appendChild(div);
	   this.dives.set(id, this.getDraggable(div.id));

	   grafico.criarCirculo(div.id);

	   this.esquerda = this.esquerda + 100;
	   this.topo = this.topo + this.varTopo;
	   if (this.esquerda >= 800) {
		   this.topo = this.topo - this.varTopo + 20;
		   this.esquerda = 0;
	   }
   },

   /**
	 * Limpa todos os fluxos criados na tela, inclusive as setas.
	 */
   limparFluxo : function() {
	   //Obs.: Os elementos draggables são criados novamente nesta função.
	   var divCirculos = $("divFluxos").childNodes;
	   var identificador;
	   var drag;
	   var draggables = this.dives;
	   var totalDrags = this.dives.size();
	   if (totalDrags > 0){
		   this.initialize();
		   /* Inicia-se a partir da referência 1 pois a referência 0(zero) diz
		    * respeito ao texto "Definição do fluxo" presente no cabeçalho da div
		    */ 
		   for (var i = 0; i < totalDrags; i++) {
			   identificador = divCirculos[i+1].id;
			   drag = draggables.get(identificador);
			   if (drag != null) {
				   drag.destroy();
			   }
			   this.dives.set(identificador, this.getDraggable(identificador));
		   }
	   }
	   grafico.limpar();
   },

   /**
	 * Liga uma div a outra.
	 * 
	 * @param id Identificador da div que recebeu duplo click.
	 */
   ligar : function(id) {
	   if (this.origem == null) {
		   this.origem = $(id);
		   grafico.destacarDiv(this.origem);
	   } else {
		   if (this.origem.id != id) {
			   if (!this.existeFluxoDefinido(this.origem.id, id)) {
				   var destino = $(id);
				   this.unirDivs(this.origem, destino);
				   var fluxo = new Array(this.origem.id, id);
				   this.listaFluxos.push(fluxo);
				   grafico.removerDestaqueDiv(this.origem);
				   this.tiraDraggable(this.origem.id, id);
				   this.origem = null;
			   } else {
				   JanelasComuns.showMessage("Fluxo já foi definido.");
				   grafico.removerDestaqueDiv(this.origem);
				   this.origem = null;
			   }
		   } else {
			   grafico.removerDestaqueDiv(this.origem);
			   this.origem = null;
		   }
	   }
   },

   /**
	 * Verifica se o fluxo já foi definido.
	 * 
	 * 
	 * @param origem Identificador da div de origem
	 * @param destino Identificador da div de destino
	 * @return <code>true</code>, se o fluxo já foi definido;<br>
	 *         <code>false</code>, se ainda não existe o fluxo definido.
	 */
   existeFluxoDefinido : function(origem, destino) {
	   var fluxo = null;

	   for ( var i = 0; i < this.listaFluxos.length; ++i) {
		   fluxo = this.listaFluxos[i];

		   if (((origem == fluxo[0]) && (destino == fluxo[1]))
		      || ((origem == fluxo[1]) && (destino == fluxo[0]))) {
			   return true;
		   }
	   }

	   return false;
   },

   /**
	 * Liga duas divs através de uma seta.
	 * 
	 * @param origem Div de origem
	 * @param destino Div de destino
	 */
   unirDivs : function(origem, destino) {
	   var origemX = parseInt((origem.offsetWidth) / 2) + origem.offsetLeft;
	   var origemY = (origem.offsetTop) + ((origem.offsetHeight) / 2);

	   var destinoX = parseInt((destino.offsetWidth) / 2) + destino.offsetLeft;
	   var destinoY = (destino.offsetTop) + ((destino.offsetHeight) / 2);

	   grafico.criarSeta(origemX, origemY, destinoX, destinoY);
   },

   /**
	 * Tira o draggable das divs.
	 * 
	 * @param id1 id da primeira div
	 * @param id2 id da segunda div
	 */
   tiraDraggable : function(id1, id2) {
	   this.dives.get(id1).destroy();
	   this.dives.get(id2).destroy();
   },
   
   /**
     * Retorna um novo elemento draggable.
     * 
     * @param id Id da div
     * @return Elemento draggable
     */
   getDraggable : function(id){
	   return new Draggable(id, { scroll :window });
   }
};

var fluxo = new DefinirFluxo();
