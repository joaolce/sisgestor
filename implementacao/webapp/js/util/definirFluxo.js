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
		this.esquerda = 0;
		this.topo = 0;
		this.varTopo = -80;
	},
	
	/** Flag para saber a div de origem para ligação */
	origem : null,
   
    /** Armazena os fluxos definidos na página. */
	listaFluxos : null,
	
	/** Posição horizontal da div */
	esquerda: 0,
	
	/** Posição vertical da div */
	topo: 0,
	
	/** Variável para ajustar posição vertical(topo) */
	varTopo : -80,
   
    /**
     * Cria um circulo(div) para fazer representação na página 
     * 
     * @param id Código identificador
     * @param nome Nome
     * @param descricao Descrição
     */
	gerarRepresentacao : function(id, nome, descricao){
		var div = $(Builder.node("div", {
			   id: id, 
			   ondblclick:"fluxo.ligar(\"" + id + "\");",
			   title: descricao
		}));
		   
		var divInterna = $(Builder.node("div",[document.createTextNode(nome)]));
		   
		Element.setStyle(divInterna, {
			top: "30px",
			position: "relative"
		});
		  
		Element.setStyle(div, {
			left: this.esquerda + "px",
			top: this.topo + "px",
			height: "80px",
			width: "80px",
			cursor: "move"
		});
		div.appendChild(divInterna);
		$("divFluxos").appendChild(div);
		
		new Draggable(div.id,{ 
			scroll: window 
		});
		
		grafico.criarCirculo(div.id);
		
		this.esquerda = this.esquerda + 100;
		this.topo = this.topo + this.varTopo;
		if(this.esquerda >= 800){
			this.topo = this.topo - this.varTopo + 20;
			this.esquerda = 0;
		}
    }, 

   /**
    * Limpa todos os fluxos criados na tela
    * */
   limparFluxo: function(){
    	this.initialize();
    	//TODO Implementar função para limpar as linhas
	   JanelasComuns.showMessage("Implemente-me");
   },
   
   /**
    * Liga uma div a outra.
    * 
    * @param id Identificador da div que recebeu duplo click.
    * */
   ligar : function(id){ 
		if(this.origem == null){
			this.origem = document.getElementById(id);
			this.destacarDiv(this.origem);
		} else {
			if(this.origem.id != id){
				if(!this.existeFluxoDefinido(this.origem.id, id)){
					var destino = document.getElementById(id);
					this.unirDivs(this.origem, destino);

					var fluxo = new Array(this.origem.id, id);
					this.listaFluxos.push(fluxo);

					this.destacarDiv(this.origem);
					this.origem = null;
				} else {
					JanelasComuns.showMessage("Fluxo já foi definido.");
					this.destacarDiv(this.origem);
					this.origem = null;
				}
			}
		}
	},
	
	/**
	 * Verifica se o fluxo já foi definido.
	 * 
	 * 
	 * @param origem Identificador da div de origem
	 * @param destino Identificador da div de destino
	 * @return  <code>true</code>, se o fluxo já foi definido;<br>
	 * 			<code>false</code>, se ainda não existe o fluxo definido.
	 */
	existeFluxoDefinido : function(origem, destino) {
		var fluxo = null;
		
		for ( var i = 0; i < this.listaFluxos.length; ++i) {
			fluxo = this.listaFluxos[i];

			if(((origem == fluxo[0]) && (destino == fluxo[1]))
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
	 * Coloca em destaque(remove destaque) a div selecionada como origem do fluxo
	 * 
	 * @param div Div a ser destacada ou removida o destaque
	 * */
	destacarDiv : function(div){
		//TODO Desenvolvendo...
//		var divs = div.firstChild.childNodes;
//		divs.each(function(element){
//			var i = element;
//		});
	}
};

var fluxo = new DefinirFluxo();