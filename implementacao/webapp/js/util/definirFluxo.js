/**
 * Comportamentos para definição dos fluxos e comportamentos das divs.
 * 
 * @author Thiago
 * @since 04/03/2009
 */
var DefinirFluxo = Class.create();
DefinirFluxo.prototype = {

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
    * Variável que armazena o total de círculos desenhados
    * na tela para utilização ao gerar representação de um fluxo já definido.
    */
   totalCirculos : 0,

   /**
	 * @constructor
	 */
   initialize : function() {
	   this.listaFluxos = new Array();
	   this.dives = new Hash();
	   this.esquerda = 0;
	   this.topo = 0;
	   this.varTopo = -80;
	   this.totalCirculos = 0;
   },

   /**
	 * Cria um circulo(div) para fazer representação na página
	 * 
	 * @param id Código identificador da div
	 * @param nome Nome que aparecerá dentro do círculo
	 * @param esquerda Posicionamento do elemento à esquerda
	 * @param topo Posicionamento do elemento ao topo
	 */
   gerarRepresentacao : function(id, nome, esquerda, topo) {
	   
	   //TODO Ainda há falha na definição do topo
	   if (topo != null) {
	   	this.topo  = this.totalCirculos * this.varTopo + topo - 39;
	   }
	   
	   if (esquerda != null) {
		   this.esquerda = esquerda - 5;
	   }
	   
	   var div = $(Builder.node("div", {
	      id :id,
	      ondblclick :"fluxo.ligar(" + id + ");",
	      title :nome
	   }));

	   var divInterna = $(Builder.node("div", [ document.createTextNode(nome) ]));

	   Element.setStyle(divInterna, {
	      top :"25px",
	      height :"30px",
	      width :"80px",
	      position :"relative",
	      overflow :"hidden"
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

	   this.esquerda += 100;
	   this.topo += this.varTopo;
	   if (this.esquerda >= 800) {
		   this.topo = this.topo - this.varTopo + 20;
		   this.esquerda = 0;
	   }
	   this.totalCirculos++;
   },

   /**
	 * Limpa todos os fluxos criados na tela, inclusive as setas.
	 */
   	limparFluxo : function() {
	   // Obs.: Os elementos draggables são criados novamente nesta função.
	   var divCirculos = $("divFluxos").childNodes;
	   var identificador;
	   var drag;
	   var draggables = this.dives;
	   var totalDrags = this.dives.size();
	   if (totalDrags > 0) {
	   	this.initialize();
	   	/*
	   	 * Inicia-se a partir da referência 1 pois a referência 0(zero) diz respeito ao texto
	     * "Definição do fluxo" presente no cabeçalho da div
	   	 */
	   	for ( var i = 0; i < totalDrags; i++) {
	   		identificador = divCirculos[i + 1].id;
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
    * Obtém as posições absolutas dos elementos posicionados na tela. <br />
    * obs: cada linha contém a seguinte formatação: &lt;id&gt;,&lt;left&gt,&lt;top&gt
    * 
    * @return {Array} com as posições das divs
    */
   obterPosicoes : function(){
	   var posicoes = new Array();
	   var circulos = $("divFluxos").childNodes;
	   var topo;
	   var esquerda;
	   var id;
	   var pos;
	   
	   for(var i = 1; i < circulos.length; ++i) {
		   id = circulos[i].id;
		   if(!isBlankOrNull(id)){
			   esquerda = $(id).offsetLeft;
			   topo = $(id).offsetTop;
			   pos = new Array(id +","+ esquerda + "," + topo);
			   posicoes.push(pos);
		   }
	   }
	   return posicoes;
   },

   /**
	* Liga uma div a outra.
	* 
	* @param {Number} id1 Identificador da div para ligar
	* @param {Number} id2 Identificador da div de destino para ligar
	*/
   ligar : function(id1, id2) {
	   //Parse para string pois as posições recebidas são do tipo number
	   id1 = id1.toString();
	   if(id2 != undefined) { //caso já esteja passando a div de destino, não precisa dar destaque a de início.
	     	id2 = id2.toString();
	     	if (!this.existeFluxoDefinido(id1, id2)){
	     		this.adicionaLigacao(id1, id2);
	     		this.tiraDraggable(id1, id2);
	     	}
	   	return;
	   }
	   if (this.origem == null) {
		   this.origem = $(id1);
		   grafico.destacarDiv(this.origem);
	   } else {
		   if (this.origem.id != id1) {
			   if (!this.existeFluxoDefinido(this.origem.id, id1)) {
			   	this.adicionaLigacao(this.origem.id, id1);
				   this.tiraDraggable(this.origem.id, id1);
			   } else {
				   JanelasComuns.showMessage("Fluxo já foi definido.");
			   }
		   }
		   grafico.removerDestaqueDiv(this.origem);
		   this.origem = null;
	   }
   },
   
   /**
	 * Verifica se o fluxo já foi definido.
	 * 
	 * @param {String} origem Identificador da div de origem
	 * @param {String} destino Identificador da div de destino
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
   getDraggable : function(id) {
	   return new Draggable(id, {
		   scroll :window
	   });
   },
   
   /**
    * Adiciona a lista de fluxos a ligação informada.
    * 
    * @param {String} idInicio identificador da div de origem
    * @param {String} idFim identificador da div de destino
    */
   adicionaLigacao: function(idInicio, idFim){
   	this.unirDivs($(idInicio), $(idFim));
	   var fluxo = new Array(idInicio, idFim);
	   this.listaFluxos.push(fluxo);
   }
};

var fluxo = new DefinirFluxo();
