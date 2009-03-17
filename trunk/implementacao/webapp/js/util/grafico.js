/**
 * @author Thiago
 * @since 13/03/2009
 */
var Grafico = Class.create();
Grafico.prototype = {

	/** Cor da seta que une os ciculos */
	corLinha: "#ff0000", //azul
	
	/** Cor do circulo */
	corCirculo: "#0000ff", //vermelho
	
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
		
	/**
	 * @constructor
	 */
	initialize : function() {
		this.linhas = new Array();
	},
	
	/**
	 * Cria um circulo para representação da div
	 *  
	 * @param id Identificador da div que conterá o circulo
	 */
	criarCirculo: function(id){
		var a = new jsGraphics(id);
		
		a.setColor(this.corCirculo); 
		a.drawEllipse(0, 0, this.larguraCirculo, this.alturaCirculo);
		a.paint();
	},
	
	/**
	 * Cria uma seta da origem para o destino.
	  */
	criarSeta: function(origemX, origemY, destinoX, destinoY){
		var dive = document.getElementById("divFluxos");
		var jg = new jsGraphics(dive);
		jg.setColor(this.corLinha);
		
		//TODO Em testes...
//		var pontos = this.formatarPosicaoPontos(origemX, origemY, destinoX, destinoY);
		
//		jg.drawLine(pontos[0], pontos[1], pontos[2], pontos[3]);
		jg.drawLine(origemX, origemY, destinoX, destinoY);
		jg.paint();
		
		this.linhas.push(jg);
	},
	
	// Em testes...
	limpar: function(){
		var totalLinhas = this.linhas.length;
		var linha = null;
		for(var i=0; i < totalLinhas; i++){
			linha = this.linhas[i];
			linha.clear();
			delete linha;
		}
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
	}
};

var grafico = new Grafico();
