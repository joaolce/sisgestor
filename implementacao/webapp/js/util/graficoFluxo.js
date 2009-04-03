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
	 * Dist�ncia entre o ponto destino(fim da seta) e o segmento de reta que define a seta
	 * 
	 * Obs.: Chamado de 'd' nos c�lculos
	 */
	distanciaSeta: 10,

	/**
	 *  �ngulo da seta, em graus 
	 * 
	 * Obs.: Chamado de 'alfa' nos c�lculos
	 */
	anguloSeta: 45,
	
	/**
	 *  Tamanho do segmento da seta, perpendicular � linha principal.
	 *  
	 *  Dado pela f�rmula: tg � = segmento / distancia => segmento = tg � * distancia
	 *  
	 *  Obs.: Chamado de 'p' nos c�lculos
	 */
	segmentoSeta: null, 
	
	/** 
	 * Tamanho da lateral da seta 
	 *
	 * Dado pela f�rmula: sen � = segmentoSeta / lateralSeta
	 * 
	 * Obs.: Chamado de 'i' nos c�lculos
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
	 * Obt�m os novos pontos de liga��o pertencentes �s circunfer�ncias e os pontos das setas.
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
		
		//Vari�veis para os novos pontos do segmento de reta que une os elementos
		var xO;
		var yO;
		var xD;
		var yD;
		
		//Vari�veis para os pontos das setas
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
			
			//Metade do �ngulo da seta
			var anguloAlfa = this.getAnguloRadianos(this.anguloSeta/2);
			
			//�ngulo entre o segmento de reta que une os elementos e o eixo X
			var anguloTeta = this.getValorTeta(origemX, origemY, destinoX, destinoY);
			
			//Indicador para c�lculo dos valores X da seta
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
			
			//Obt�m os �ngulos para c�lculo
			var anguloZ = anguloTeta - anguloAlfa;
			var anguloT = anguloTeta + anguloAlfa;
			
			if (anguloZ < 0) {
				/*
				 * Tem o objetivo de deixar o �ngulo sempre positivo, ou seja,
				 * � o m�dulo de matem�tica /expressao/ 
				 */ 
				anguloZ *= (-1);
			}
			
			setX1 = this.getValorXSeta(xD, anguloZ, soma);
			
			if (anguloT > this.getAnguloRadianos(90)){
				/*
				 * Se o �ngulo for maior que 90 graus, seu cosseno ser� negativo, 
				 * inviabilizando os c�lculos.  
				 */
				anguloT = this.getAnguloRadianos(180) - anguloT;
				soma = !soma;
			}
			
			setX2 = this.getValorXSeta(xD, anguloT, soma);
			
			setY1 = this.getValorYSeta(xO, yO, xD, yD, setX1, anguloTeta, setaAscendente);
			setY2 = this.getValorYSeta(xO, yO, xD, yD, setX2, anguloTeta, setaAscendente);
		}
	   
		//Armazena todos os pontos calculados no array a ser retornado � fun��o que chamou esta.
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
	 * Esta fun��o retorna o ponto Y do segmento de reta que une dois elementos. Para encontrar os pontos Y da seta,<br>
	 * veja a fun��o getValorYSeta
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
		 * Monta a equa��o geral da reta perpendicular ao segmento
		 * 
		 * Equa��o da reta: y = a * x + b, tal que:
		 * a � o coeficiente angular da reta, e
		 * b o coeficiente linear
		 * 
		 * Sendo que a equa��o da reta pode ser obtida pelo c�lculo do determinante:
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
	 * Encontra novas posi��es no eixo X para origem e destino. 
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
	 * Retorna a dist�ncia entre dois pontos (Origem X Destino)
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Number} Dist�ncia entre os pontos informados
	 */
	getDistanciaEntrePontos : function(origemX, origemY, destinoX, destinoY){
		/*
		 * Trigonometria: f�rmula: Distancia(pontoA,pontoB) = Raiz[ ( Ax )� + (Ay)�]
		 * 
		 * Onde A = Delta(varia��o)
		 */
		var deltaX = origemX - destinoX;
		var deltaY = origemY - destinoY;
	   
		return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	}, 

	/**
	 * Verifica se os elementos est�o a uma dist�ncia m�nima para serem ligados.
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return <code>true</code>, se dist�ncia m�nima atingida;<br><code>false</code>, se n�o.
	 */
	distanciaMinimaAtingida : function(origemX, origemY, destinoX, destinoY){

		var distancia = this.getDistanciaEntrePontos(origemX, origemY, destinoX, destinoY);
		
		if ( distancia < (this.raio * 2 + 20)) {
			return false;
		}
		
		return true;
	},
	
	/**
	 * Passa o valor do �ngulo informado de graus para radianos.
	 * 
	 * @param {Number} �ngulo em graus
	 * 
	 * @return {Number} �ngulo em radianos
	 */
	getAnguloRadianos : function(angulo){
		return (angulo * Math.PI) / 180; 
	},
	
	/**
	 * Obt�m a dist�ncia entre o ponto destino e o ponto X correspondente da seta.<br><br>
	 * 
	 * Obs.: Esta fun��o deve ser chamada apenas pela fun��o #getValorXSeta
	 * @param {Number} �ngulo em radianos formado entre o segmento de reta da seta e o eixo horizontal
	 * 
	 * @return {Number} dist�ncia entre o destinoX e o ponto X da seta
	 */
	getValorParcialXSeta : function(angulo){
		/*
		 * Trigonometria: f�rmula: cos � = cat. adjacente / hipotenusa
		 * 
		 * Onde cos � = x/i
		 */
		return Math.cos(angulo) * this.lateralSeta;
	},
	
	/**
	 * Obt�m o valor final do ponto X da seta.
	 * 
	 * @param {Number} posi��o X do ponto destino
	 * @param {Number} �ngulo em radianos formado entre o segmento de reta da seta e o eixo horizontal
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
	 * Esta fun��o retorna o ponto Y da seta.
	 * 
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * @param {Number} Ponto X correspondente ao ponto Y desejado
	 * @param {Number} �ngulo entre o segmento de reta que une os elementos e o eixo X 
	 * @param {Number} Indicador da seta. <code>true</code>, se seta ascendente; <code>false</code>, se descendente.
	 * 
	 * @return {Number} Ponto Y do ponto X informado
	 */
	getValorYSeta : function(origemX, origemY, destinoX, destinoY, valorX, anguloTeta, setaAscendente){
		/*
		 * Monta a equa��o geral da reta perpendicular ao segmento
		 * 
		 * Equa��o da reta: y = a * x + b, tal que:
		 * a � o coeficiente angular da reta, e
		 * b o coeficiente linear
		 * 
		 * Sendo que a equa��o da reta pode ser obtida pelo c�lculo do determinante:
		 * 
		 * |            |
		 *	| Ax  Ay  1  |
		 *	|            |
		 *	| Bx  By  1  | = 0
		 *	|            |
		 *	|  X   Y  1  |
		 *
		 * Este segmento de reta � perpendicular ao segmento de reta que une os elementos.
		 * Logo, o coeficiente deve ser invertido.
		 */
		
		//Pontos referentes � dist�ncia da seta. � o ponto de intersec��o das retas.
		var distanciaX;
		var distanciaY;
		
		var divisor = (destinoX - origemX);
		
		//Coeficiente �ngular da reta perpendicular
		var a = (-(origemY - destinoY)) / divisor;
		
		var tamanho = Math.cos(anguloTeta) * this.distanciaSeta;
		
		//Obt�m o valor X do ponto referente � dist�ncia da seta
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
		
		//Obt�m o valor Y do ponto referente � dist�ncia da seta.
		distanciaY = this.getValorY(origemX, origemY, destinoX, destinoY, distanciaX);
		
		//Coeficiente linear da reta perpendicular
		var b = distanciaY + ( distanciaX / a );
		
		//Resultado da equa��o da reta perpendicular ao segmento que une os elementos que cont�m o ponto da dist�ncia da seta
		return  ( (-1/a) * (valorX) ) + b;
	},
	
	/**
	 * Recupera o �ngulo entre o segmento que une os elementos e o eixo X
	 *
	 * @param {Number} Ponto X da origem
	 * @param {Number} Ponto Y da origem
	 * @param {Number} Ponto X do destino
	 * @param {Number} Ponto Y do destino
	 * 
	 * @return {Number} �ngulo entre o segmento que une os elementos e o eixo X
	 */
	getValorTeta : function(origemX, origemY, destinoX, destinoY){
		/*
		 * Trigonometria: F�rmula: tg � = Ay / Ax
		 * 
		 * Onde A = Delta(varia��o)
		 * 
		 * Obs.: Para encontrar um �ngulo a partir da sua tangente, 
		 * deve usar a fun��o arco tangente (Math.atan(�))
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
