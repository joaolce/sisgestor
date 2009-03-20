/**
 * @author Thiago
 * @since 13/03/2009
 */
var Grafico = Class.create();
Grafico.prototype = {

	/** Cor da seta que une os ciculos */
	corLinha: "green",
	
	/** Cor da div circular default */
	corDefault: "blue",
	
	/** Cor da div circular quando destacada */
	corDestaque: "red",
	
	/** 
	 * Altura do circulo
	 * 
	 * Obs.: Deverá ser de mesma altura da div que contém o circulo.
	  */
	alturaCirculo: 80,
	
	/** 
	 * Largura do circulo 
	 * 
	 * Obs.: Deverá ser de mesma largura da div que contém o circulo.
	  */
	larguraCirculo: 80,
	
	/** Armazena as linhas dos fluxos */
	linhas: null,
	
	/** Armazena o total de linhas */
	totalLinhas: 0,
		
	/**
	 * @constructor
	 */
	initialize : function() {
		this.linhas = new Hash();
		this.totalLinhas = 0;
	},
	
	/**
	 * Cria um circulo para representação da div
	 *  
	 * @param id Identificador da div que conterá o circulo
	 */
	criarCirculo: function(id){
		var circle = new jsGraphics(id);

		circle.setColor(this.corDefault); 
		circle.drawEllipse(0, 0, this.larguraCirculo, this.alturaCirculo);
		circle.paint();
	},
	
	/**
	 * Cria uma seta da origem para o destino.
	  */
	criarSeta: function(origemX, origemY, destinoX, destinoY){
		var dive = $("divFluxos");
		var jg = new jsGraphics(dive);
		jg.setColor(this.corLinha);
		
		//TODO Em testes...
//		var pontos = this.formatarPosicaoPontos(origemX, origemY, destinoX, destinoY);
		
//		jg.drawLine(pontos[0], pontos[1], pontos[2], pontos[3]);
		jg.drawLine(origemX, origemY, destinoX, destinoY);
		jg.paint();
		
		this.linhas.set(this.totalLinhas,jg);
		this.totalLinhas++;
	},
	
	/**
	 * Remove as linhas da tela.
	 * */
	limpar: function(){
		for(var i=0; i < this.totalLinhas; i++){
			this.linhas.get(i).clear();
		}
		this.initialize();
	},
	
	
	// Em testes...
	formatarPosicaoPontos: function(origemX, origemY, destinoX, destinoY){
		if(origemX < destinoX ){
			origemX += 20;//this.larguraCirculo/2;
			destinoX -= 20;//this.larguraCirculo/2;
		} else {
			origemX -= 20;//this.larguraCirculo/2;
			destinoX += 20;//this.larguraCirculo/2;
		}
		
		if(origemY < destinoY){
			origemY += 20;//this.alturaCirculo/2;
			destinoY -= 20;//this.alturaCirculo/2;
		} else{
			origemY -= 20;//this.alturaCirculo/2;
			destinoY += 20;//this.alturaCirculo/2;
		}
		
		return new Array(origemX, origemY, destinoX, destinoY);
	},
	
	/** 
	 * Coloca em destaque a div selecionada como origem do fluxo
	 * 
	 * @param div Div a ser destacada
	 * */
	destacarDiv : function(div){
		var divInterna = div.childNodes[1].childNodes;
		for(var i=0; i < divInterna.length; i++){
			Element.setStyle(divInterna[i], {
				background: this.corDestaque
			});
		}
	},
	
	/** 
	 * Remove o destaque da div selecionada como origem do fluxo
	 * 
	 * @param div Div a ter o destaque removido
	 * */
	removerDestaqueDiv : function(div){
		var divInterna = div.childNodes[1].childNodes;
		for(var i=0; i < divInterna.length; i++){
			Element.setStyle(divInterna[i], {
				background: this.corDefault
			});
		}
	}
};

var grafico = new Grafico();
