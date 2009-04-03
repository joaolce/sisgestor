/**
 * @author Thiago
 * @since 13/03/2009
 */
var GraficoFluxo = Class.create();
GraficoFluxo.prototype = {

	/** Cor da seta que une os ciculos */
	corLinha: "green",
	
	/** Cor da div circular default */
	corDefault: "blue",
	
	/** Cor da div circular quando destacada */
	corDestaque: "red",
	
	/** Raio do circulo */
	raio: 40,
	
	/** 
	 * Distância entre o ponto destino(fim da seta) e o segmento de reta que define a seta
	 * 
	 * Obs.: Chamado de 'd' nos cálculos
	 */
	distanciaSeta: 10,

	/**
	 *  Ângulo da seta, em graus 
	 * 
	 * Obs.: Chamado de 'alfa' nos cálculos
	 */
	anguloSeta: 45,
	
	/**
	 *  Tamanho do segmento da seta, perpendicular à linha principal.
	 *  
	 *  Dado pela fórmula: tg â = segmento / distancia => segmento = tg â * distancia
	 *  
	 *  Obs.: Chamado de 'p' nos cálculos
	 */
	segmentoSeta: null, 
	
	/** 
	 * Tamanho da lateral da seta 
	 *
	 * Dado pela fórmula: sen â = segmentoSeta / lateralSeta
	 * 
	 * Obs.: Chamado de 'i' nos cálculos
	 */
	lateralSeta: null,
	
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
		this.segmentoSeta = this.distanciaSeta * Math.tan(this.getAnguloRadianos(this.anguloSeta/2));
		this.lateralSeta = this.segmentoSeta / Math.sin(this.getAnguloRadianos(this.anguloSeta/2));
	},
	
	/**
	 * Cria um circulo para representação da div
	 *  
	 * @param id Identificador da div que conterá o circulo
	 */
	criarCirculo: function(id){
		var circle = new jsGraphics(id);

		circle.setColor(this.corDefault); 
		circle.drawEllipse(0, 0, this.raio * 2, this.raio * 2);
		circle.paint();
	},
	
	/**
	 * Cria uma seta da origem para o destino.
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 */
	criarSeta: function(origemX, origemY, destinoX, destinoY){
		var dive = $("divFluxos");
		var jg = new jsGraphics(dive);
		jg.setColor(this.corLinha);
		
		var pontos = this.obterPontos(origemX, origemY, destinoX, destinoY);
		var pontosSetaX = new Array(pontos[2], pontos[4], pontos [6]);
		var pontosSetaY = new Array(pontos[3], pontos[5], pontos [7]);
	   
		jg.drawLine(pontos[0], pontos[1], pontos[2], pontos[3]);
		jg.fillPolygon(pontosSetaX, pontosSetaY);  
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
	 * Processa a resposta de uma requisição de definição de fluxo.
	 */
	processarResposta : function() {
		if (!requestUtils.status && (requestUtils.valoresDevolvidos != null)) {
		   requestUtils.valoresDevolvidos.each(( function(div) {
			   Effect.Pulsate(div.value);
		   }));
	   }
	},
	
	/**
	 * Obtém os novos pontos de ligação pertencentes às circunferências e os pontos das setas.
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Array} de novos pontos <novaOrigemX><novaOrigemY><novoDestinoX><novoDestinoY><setaX1><setaY1><setaX2><setaY2>
	 */
	obterPontos : function(origemX, origemY, destinoX, destinoY){
		var novosPontos = new Array();
		
		//Variáveis para os novos pontos do segmento de reta que une os elementos
		var xO;
		var yO;
		var xD;
		var yD;
		
		//Variáveis para os pontos das setas
		var setX1;
		var setX2;
		var setY1;
		var setY2;
		
		//Elementos alinhados horizontalmente ou verticalmente
		if ((origemY == destinoY) || (origemX == destinoX)) {
			if ( origemY == destinoY) {
				yO = origemY;
				yD = destinoY;
				
				//Seta os valores Y da seta
				setY1 = yD - this.segmentoSeta;
				setY2 = yD + this.segmentoSeta;
				
				if ( origemX < destinoX) {
						xO = origemX + this.raio;
						xD = destinoX - this.raio;
						setX1 = setX2 = xD - this.distanciaSeta;
				} else {
						xO = origemX - this.raio;
						xD = destinoX + this.raio;
						
						//Seta os valores X da seta
						setX1 = setX2 = xD + this.distanciaSeta;
				}
			} else { // origemX == destinoX
				xO = origemX;
				xD = destinoX;
				
				//Seta os valores X da seta
				setX1 = xD + this.segmentoSeta;
				setX2 = xD - this.segmentoSeta;
				
				if (origemY < destinoY) {
						yO = origemY + this.raio;
						yD = destinoY - this.raio;
						setY1 = setY2 = yD - this.distanciaSeta;
				} else {
						yO = origemY - this.raio;
						yD = destinoY + this.raio;
						
						//Seta os valores Y da seta
						setY1 = setY2 = yD + this.distanciaSeta;
				}
			}
		} else {
			/* 
			 * Casos:
			 * 	 _        _            
			 * 1)  /|   2) |\     3)   /   4) \  
			 *    /          \       |/_      _\| 
			 * 
			 */
			
			//Novos valores de X, no formado <origem><destino>
			var novoX;
			
			//Metade do ângulo da seta
			var anguloAlfa = this.getAnguloRadianos(this.anguloSeta/2);
			
			//Ângulo entre o segmento de reta que une os elementos e o eixo X
			var anguloTeta = this.getValorTeta(origemX, origemY, destinoX, destinoY);
			
			//Indicador para cálculo dos valores X da seta
			var soma = false;
			var setaAscendente = false;
			
			novoX = this.getValorX(origemX, origemY, destinoX, destinoY);
			
			if (origemX > destinoX) {
				soma = true;
			}
			
			if (origemY > destinoY) {
				setaAscendente = true;
			}
			
			xO = novoX[0];
			xD = novoX[1];
			yO = this.getValorY(origemX, origemY, destinoX, destinoY, xO);
			yD = this.getValorY(origemX, origemY, destinoX, destinoY, xD);
			
			//Obtém os ângulos para cálculo
			var anguloZ = anguloTeta - anguloAlfa;
			var anguloT = anguloTeta + anguloAlfa;
			
			if (anguloZ < 0) {
				/*
				 * Tem o objetivo de deixar o ângulo sempre positivo, ou seja,
				 * é o módulo de matemática /expressao/ 
				 */ 
				anguloZ *= (-1);
			}
			
			setX1 = this.getValorXSeta(xD, anguloZ, soma);
			
			if (anguloT > this.getAnguloRadianos(90)){
				/*
				 * Se o ângulo for maior que 90 graus, seu cosseno será negativo, 
				 * inviabilizando os cálculos.  
				 */
				anguloT = this.getAnguloRadianos(180) - anguloT;
				soma = !soma;
			}
			
			setX2 = this.getValorXSeta(xD, anguloT, soma);
			
			setY1 = this.getValorYSeta(xO, yO, xD, yD, setX1, anguloTeta, setaAscendente);
			setY2 = this.getValorYSeta(xO, yO, xD, yD, setX2, anguloTeta, setaAscendente);
		}
	   
		//Armazena todos os pontos calculados no array a ser retornado à função que chamou esta.
		//Novos pontos do segmento
		novosPontos.push(xO);
		novosPontos.push(yO);
		novosPontos.push(xD);
		novosPontos.push(yD);
		
		//Pontos da seta
		novosPontos.push(setX1);
		novosPontos.push(setY1);
		novosPontos.push(setX2);
		novosPontos.push(setY2);
	
		return novosPontos;
	},

	/**
	 * Encontra o valor do ponto no eixo Y para o valor X informado de acordo com os pontos determinantes da reta.<br>
	 * Esta função retorna o ponto Y do segmento de reta que une dois elementos. Para encontrar os pontos Y da seta,<br>
	 * veja a função getValorYSeta
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * @param {Number} Ponto X correspondente ao ponto Y desejado
	 * 
	 * @return {Number} Ponto Y do ponto X informado
	 */
	getValorY : function(origemX, origemY, destinoX, destinoY, valorX){
		/*
		 * Monta a equação geral da reta perpendicular ao segmento
		 * 
		 * Equação da reta: y = a * x + b, tal que:
		 * a é o coeficiente angular da reta, e
		 * b o coeficiente linear
		 * 
		 * Sendo que a equação da reta pode ser obtida pelo cálculo do determinante:
		 * 
		 * |            |
		 *	| Ax  Ay  1  |
		 *	|            |
		 *	| Bx  By  1  | = 0
		 *	|            |
		 *	|  X   Y  1  |
		 *
		 */	
		var divisor = (origemX - destinoX);
		var a = (-(destinoY - origemY)) / divisor;
		var b = ((origemX * destinoY) - (destinoX * origemY)) / divisor;
		
		return ( ( (a) * (valorX) ) + (b) );
	},

	/**
	 * Encontra novas posições no eixo X para origem e destino. 
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Array} Novos pontos X para origem e destino
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
	 * Retorna a distância entre dois pontos (Origem X Destino)
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Number} Distância entre os pontos informados
	 */
	getDistanciaEntrePontos : function(origemX, origemY, destinoX, destinoY){
		/*
		 * Trigonometria: fórmula: Distancia(pontoA,pontoB) = Raiz[ ( Ax )² + (Ay)²]
		 * 
		 * Onde A = Delta(variação)
		 */
		var deltaX = origemX - destinoX;
		var deltaY = origemY - destinoY;
	   
		return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	}, 

	/**
	 * Verifica se os elementos estão a uma distância mínima para serem ligados.
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return <code>true</code>, se distância mínima atingida;<br><code>false</code>, se não.
	 */
	distanciaMinimaAtingida : function(origemX, origemY, destinoX, destinoY){

		var distancia = this.getDistanciaEntrePontos(origemX, origemY, destinoX, destinoY);
		
		if ( distancia < (this.raio * 2 + 20)) {
			return false;
		}
		
		return true;
	},
	
	/**
	 * Passa o valor do ângulo informado de graus para radianos.
	 * 
	 * @param {Number} ângulo em graus
	 * 
	 * @return {Number} ângulo em radianos
	 */
	getAnguloRadianos : function(angulo){
		return (angulo * Math.PI) / 180; 
	},
	
	/**
	 * Obtém a distância entre o ponto destino e o ponto X correspondente da seta.<br><br>
	 * 
	 * Obs.: Esta função deve ser chamada apenas pela função #getValorXSeta
	 * @param {Number} ângulo em radianos formado entre o segmento de reta da seta e o eixo horizontal
	 * 
	 * @return {Number} distância entre o destinoX e o ponto X da seta
	 */
	getValorParcialXSeta : function(angulo){
		/*
		 * Trigonometria: fórmula: cos â = cat. adjacente / hipotenusa
		 * 
		 * Onde cos â = x/i
		 */
		return Math.cos(angulo) * this.lateralSeta;
	},
	
	/**
	 * Obtém o valor final do ponto X da seta.
	 * 
	 * @param {Number} posição X do ponto destino
	 * @param {Number} ângulo em radianos formado entre o segmento de reta da seta e o eixo horizontal
	 * @param {Boolean} Indicador para saber o quadrante da seta //TODO Melhorar...
	 * 
	 * @return {Number} valor final do ponto X da seta
	 */
	getValorXSeta : function(destinoX, angulo, soma){
		var valorX;
		if (soma) {
			valorX = destinoX + this.getValorParcialXSeta(angulo);
		} else {
			valorX = destinoX - this.getValorParcialXSeta(angulo);
		}
		return valorX;
	},
	
	/**
	 * Encontra o valor do ponto no eixo Y para o valor X informado de acordo com os pontos determinantes da reta.<br>
	 * Esta função retorna o ponto Y da seta.
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * @param {Number} Ponto X correspondente ao ponto Y desejado
	 * @param {Number} Ângulo entre o segmento de reta que une os elementos e o eixo X 
	 * @param {Number} Indicador da seta. <code>true</code>, se seta ascendente; <code>false</code>, se descendente.
	 * 
	 * @return {Number} Ponto Y do ponto X informado
	 */
	getValorYSeta : function(origemX, origemY, destinoX, destinoY, valorX, anguloTeta, setaAscendente){
		/*
		 * Monta a equação geral da reta perpendicular ao segmento
		 * 
		 * Equação da reta: y = a * x + b, tal que:
		 * a é o coeficiente angular da reta, e
		 * b o coeficiente linear
		 * 
		 * Sendo que a equação da reta pode ser obtida pelo cálculo do determinante:
		 * 
		 * |            |
		 *	| Ax  Ay  1  |
		 *	|            |
		 *	| Bx  By  1  | = 0
		 *	|            |
		 *	|  X   Y  1  |
		 *
		 * Este segmento de reta é perpendicular ao segmento de reta que une os elementos.
		 * Logo, o coeficiente deve ser invertido.
		 */
		
		//Pontos referentes à distância da seta. É o ponto de intersecção das retas.
		var distanciaX;
		var distanciaY;
		
		var divisor = (destinoX - origemX);
		
		//Coeficiente ângular da reta perpendicular
		var a = (-(origemY - destinoY)) / divisor;
		
		var tamanho = Math.cos(anguloTeta) * this.distanciaSeta;
		
		//Obtém o valor X do ponto referente à distância da seta
		if (a > 0) {
			if (setaAscendente) {
				distanciaX = destinoX + tamanho;
			} else {
				distanciaX = destinoX - tamanho;
			}
		} else {
			if (setaAscendente) {
				distanciaX = destinoX - tamanho;
			} else {
				distanciaX = destinoX + tamanho;
			}
		}
		
		//Obtém o valor Y do ponto referente à distância da seta.
		distanciaY = this.getValorY(origemX, origemY, destinoX, destinoY, distanciaX);
		
		//Coeficiente linear da reta perpendicular
		var b = distanciaY + ( distanciaX / a );
		
		//Resultado da equação da reta perpendicular ao segmento que une os elementos que contém o ponto da distância da seta
		return  ( (-1/a) * (valorX) ) + b;
	},
	
	/**
	 * Recupera o ângulo entre o segmento que une os elementos e o eixo X
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Number} Ângulo entre o segmento que une os elementos e o eixo X
	 */
	getValorTeta : function(origemX, origemY, destinoX, destinoY){
		/*
		 * Trigonometria: Fórmula: tg â = Ay / Ax
		 * 
		 * Onde A = Delta(variação)
		 * 
		 * Obs.: Para encontrar um ângulo a partir da sua tangente, 
		 * deve usar a função arco tangente (Math.atan(â))
		 */
		
		var deltaY = destinoY - origemY;
		var deltaX = destinoX - origemX;
		
		if (deltaY < 0) {
			deltaY *= (-1);
		}
		
		if (deltaX < 0) {
			deltaX *= (-1);
		}
		
		return Math.atan(deltaY / deltaX);
	}
};

var grafico = new GraficoFluxo();
