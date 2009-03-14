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
	initialize : function() {},
	
	/** Flag para saber a div de origem para ligação */
	origem : null,
   
    /** Armazena os fluxos definidos na página. */
	listaFluxos : null,
   
    /**
     * Cria uma div para representar 
     * 
     * @param prefixo Prefixo para identificar a div. Exempo: 'pro' , 'ati' ou 'tar'
     * @param nome Nome
     * @param id Código identificador
     * @param descricao Descrição
     * */
     criarDiv : function(prefixo, id, nome, descricao){
		var pre = prefixo + "_" + id;
		var div = $(Builder.node("div", {
			   id: pre, 
			   ondblclick:"fluxo.ligar(\"" + pre + "\");",
			   title: descricao
		}));
		   
		var divInterna = $(Builder.node("div",[document.createTextNode(nome)]));
		   
		Element.setStyle(divInterna, {
			position: "relative",
			top: "30px"
		});
		   
		Element.setStyle(div, {
			height: "80px",
			width: "80px",
			position: "relative",
			cursor: "move"
		});
		div.appendChild(divInterna);
		$("divFluxos").appendChild(div);
		
		new Draggable(div.id,{ scroll: window });
		
		grafico.criarCirculo(div.id);
    }, 

   /**
    * Limpa todos os fluxos criados na tela
    * */
   limparFluxo: function(){
    	this.listaFluxos = new Array();
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
		} else {
			if(this.origem.id != id){
				if(!this.existeFluxoDefinido(this.origem.id, id)){
					var destino = document.getElementById(id);
					this.unirDivs(this.origem, destino);

					var fluxo = new Array(this.origem.id, id);
					this.listaFluxos.push(fluxo);

					this.origem = null;
				} else {
					//TODO Teste
					alert('Fluxo já definido!');
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
	 * */
	existeFluxoDefinido: function(origem, destino){
		var fluxo = null;
		
		  for (var i = 0; i < this.listaFluxos.length; ++i) {
			  fluxo = this.listaFluxos[i];
			  
			  if((origem == fluxo[0] && destino == fluxo[1]) 
			       || (origem == fluxo[1] && destino == fluxo[0])){
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
	 * */
	unirDivs: function(origem,destino){
	    var origemX = parseInt((origem.offsetWidth) / 2) + origem.offsetLeft;
	    var origemY = (origem.offsetTop) + ((origem.offsetHeight)/2);
	    
	    var destinoX = parseInt((destino.offsetWidth) / 2) + destino.offsetLeft;
	    var destinoY = (destino.offsetTop) + ((destino.offsetHeight)/2);
	    
	    grafico.criarSeta(origemX,origemY,destinoX,destinoY);
	}   
};

var fluxo = new DefinirFluxo();
