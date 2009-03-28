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
	
	/** Raio do circulo */
	raio: 40,
	
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
	 * Cria um circulo para representa��o da div
	 *  
	 * @param id Identificador da div que conter� o circulo
	 */
	criarCirculo: function(id){
		var circle = new jsGraphics(id);

		circle.setColor(this.corDefault); 
		circle.drawEllipse(0, 0, this.raio * 2, this.raio * 2);
		circle.paint();
	},
	
	/**
	 * Cria uma seta da origem para o destino.
	  */
	criarSeta: function(origemX, origemY, destinoX, destinoY){
		var dive = $("divFluxos");
		var jg = new jsGraphics(dive);
		jg.setColor(this.corLinha);
		
		var pontos = this.obterPontos(origemX, origemY, destinoX, destinoY);
		
		jg.drawLine(pontos[0], pontos[1], pontos[2], pontos[3]);
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
	
	/** 
	 * Coloca em destaque a div selecionada como origem do fluxo
	 * 
	 * @param div Div a ser destacada
	 */
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
	 */
	removerDestaqueDiv : function(div){
		var divInterna = div.childNodes[1].childNodes;
		for(var i=0; i < divInterna.length; i++){
			Element.setStyle(divInterna[i], {
				background: this.corDefault
			});
		}
	},
	
	/**
	 * Processa a resposta de uma requisi��o de defini��o de fluxo.
	 */
	processarResposta : function() {
		if (!requestUtils.status && (requestUtils.valoresDevolvidos != null)) {
		   requestUtils.valoresDevolvidos.each(( function(div) {
			   Effect.Pulsate(div.value);
		   }));
	   }
	},
	
	/**
	 * Obt�m os novos pontos de liga��o que pertencem �s circunfer�ncias.
	 * 
	 * @return novos pontos <novaOrigemX><novaOrigemY><novoDestinoX><novoDestinoY> 
	 */
	obterPontos : function(origemX, origemY, destinoX, destinoY){
		var novosPontos = new Array();
		var xO;
		var yO;
		var xD;
		var yD;
		
		if ((origemY == destinoY) || (origemX == destinoX)) {
			if ( origemY == destinoY) {
				yO = origemY;
				yD = destinoY;
				if ( origemX < destinoX) {
					xO = origemX + this.raio;
					xD = destinoX - this.raio;
				} else {
					xO = origemX - this.raio;
					xD = destinoX + this.raio;
				}
			} else { // origemX == destinoX
				xO = origemX;
				xD = destinoX;
				if (origemY < destinoY) {
					yO = origemY + this.raio;
					yD = destinoY - this.raio;
				} else {
					yO = origemY - this.raio;
					yD = destinoY + this.raio;
				}
			}
		} else {
			//Armazena os dois novos valores de X, no formado <origem><destino>
			var novoX;
			if (origemY < destinoY) {
				if (origemX < destinoX) {
					novoX = this.getValorX(origemX, origemY, destinoX, destinoY);
				  	yO = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[0]);
				  	yD = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[1]);
				} else {
					//Par�metros foram invertidos propositalmente para efetuar c�lculo
					novoX = this.getValorX(destinoX, destinoY, origemX, origemY);
					yO = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[0]);
				  	yD = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[1]);
				}
			} else {
				if (origemX < destinoX) {
					novoX = this.getValorX(origemX, origemY, destinoX, destinoY);
					yO = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[0]);
					yD = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[1]);
					
				} else {
					//Par�metros foram invertidos propositalmente para efetuar c�lculo
					novoX = this.getValorX(destinoX, destinoY, origemX, origemY);
					yO = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[0]);
					yD = this.getValorY(origemX, origemY, destinoX, destinoY, novoX[1]);
				}
			}
			xO = novoX[0];
			xD = novoX[1];
		}
		
		novosPontos.push(xO);
		novosPontos.push(yO);
		novosPontos.push(xD);
		novosPontos.push(yD);

		return novosPontos;
	},

	/**
	 * Recupera o valor do eixo Y para o valor X informado de acordo com os pontos determinantes da reta.
	 */
	getValorY : function(origemX, origemY, destinoX, destinoY, valorX){
		/*
		 * Monta a equa��o geral da reta que passa pelos dois pontos para encontrar
		 * os novos pontos adjacentes � circunfer�ncia
		 */ 
		var divisor = (origemX - destinoX);
		var a = (-(destinoY - origemY)) / divisor;
		var b = ((origemX * destinoY) - (destinoX * origemY)) / divisor;
		
		return ( ( (a) * (valorX) ) + (b) );
	},

	/**
	 * Recupera novas posi��es do eixo X para origem e destino.  
	 */
	getValorX : function (origemX, origemY, destinoX, destinoY){
		var valoresX = new Array();
		
		var distancia = this.getDistanciaEntrePontos(origemX, origemY, destinoX, destinoY);
		
		var parcial = (this.raio * (destinoX - origemX)) / distancia;
		
		var origem  = origemX + parcial;
		var destino = destinoX - parcial;
		
		valoresX.push(origem);
		valoresX.push(destino);
		
		return valoresX;
	},

	/**
	 * Retorna a dist�ncia entre dois pontos (Origem X Destino)
	 *
	 */
	getDistanciaEntrePontos : function(origemX, origemY, destinoX, destinoY){
		//F�rmula: Distancia(pontoA,pontoB) = Raiz[ ( Ax - Bx )� + (Ay - By)�]
		var par1 = origemX - destinoX;
		var par2 = origemY - destinoY;
		
		par1 = par1 * par1;
		par2 = par2 * par2;
		
		return Math.sqrt(par1 + par2);
	}, 

	/**
	 * Verifica se os elementos est�o a uma dist�ncia m�nima para serem ligados.
	 * 
	 * @return <code>true</code>, se dist�ncia m�nima atingida;<br><code>false</code>, se n�o.
	 */
	distanciaMinimaAtingida : function(origemX, origemY, destinoX, destinoY){

		var distancia = this.getDistanciaEntrePontos(origemX, origemY, destinoX, destinoY);
		
		if ( distancia < (this.raio * 2 + 20)) {
			return false;
		}
		
		return true;
	}
};

var grafico = new Grafico();
