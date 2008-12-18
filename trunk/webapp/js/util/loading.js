/**
 * Controle de exibição da imagem de loading
 * @author deinf.sdantas
 */
 var definicoesJanela = {
	/** caminho da imagem de loading */
	loadingImage: "imagens/aguarde_azul.gif",
	
	/** texto que aparecerá ao lado da imagem de loading */
	textoLoading: null,
	
	/** altura do loading */
	loadingHeight: "16px",
	
	/** largura do loading */
	loadingWidth: "100px",
	
	/** posição x onde loading irá aparecer na tela*/
	loadingLeft: "400px",
	
	/** configurações de borda do loading*/
	loadingBorder: "",
	
	/** cor de fundo do loading */
	loadingBackground: "rgb(250, 250, 250)",
	
	/** id do div de loading*/
	idLoading: "loadingIcon"
};
 
/**
 * Controle de exibição da imagem de loading
 */
var LoadingControl = Class.create();
LoadingControl.prototype = {
	/**
	 * @constructor
	 */
	initialize: function(){},
	controle: 0,
	/**
	 * 
	 * @return {HTMLDivElement}
	 */
	getLoading: function(){
		var corpo = Builder.node("div", {id: definicoesJanela.idLoading});
		var imagem = Builder.node("img", {src: definicoesJanela.loadingImage, alt: "carregando"});
		
		Element.setStyle(imagem, {
			marginBottom: "-3px"
		});
		
		Element.setStyle(corpo, {
			height: definicoesJanela.loadingHeight,
			width: definicoesJanela.loadingWidth,
			border: definicoesJanela.loadingBorder,
			backgroundColor: definicoesJanela.loadingBackground
		});
		corpo.appendChild(imagem);
		
		if(definicoesJanela.textoLoading != null){
			var b = document.createElement("b");
			b.innerHTML = definicoesJanela.textoLoading;
			corpo.appendChild(b);
		}
		return corpo;
	},
	/**
	 *  exibir a imagem de loading
	 */
	showLoading: function(){
		this.controle++;

		if($(definicoesJanela.idLoading) != null){
			Element.setStyle($(definicoesJanela.idLoading), {
				top: (document.documentElement.scrollTop)+"px"
			});
			Element.show(definicoesJanela.idLoading);
			return;
		}
		var corpo = Builder.node("div", {id: definicoesJanela.idLoading});
		var imagem = Builder.node("img", {src: definicoesJanela.loadingImage, alt: "carregando"});
		
		Element.setStyle(imagem, {
			marginBottom: "-3px"
		});
		
		Element.setStyle(corpo, {
			height: definicoesJanela.loadingHeight,
			width: definicoesJanela.loadingWidth,
			left: definicoesJanela.loadingLeft,
			top: (document.documentElement.scrollTop)+"px",
			position: "absolute",
			border: definicoesJanela.loadingBorder,
			backgroundColor: definicoesJanela.loadingBackground,
			zIndex: 100
		});
		corpo.appendChild(imagem);
		
		if(definicoesJanela.textoLoading != null){
			var b = document.createElement("b");
			b.innerHTML = definicoesJanela.textoLoading;
			corpo.appendChild(b);
		}
		$(Configuracao.DivPrincipal).appendChild(corpo);
	},
	/**
	 * esconder a imagem de loading
	 */
	hideLoading: function(){
		this.controle--;
		if(this.isNotRequesting())
			Element.hide(definicoesJanela.idLoading);
	},
	isNotRequesting: function(){
		return this.controle <= 0;
	}
};
var loadingFactory = new LoadingControl();

function showLoading(){
	loadingFactory.showLoading();
}
function hideLoading(){
	loadingFactory.hideLoading();
}
Event.observe(window, "load", function(event){
	preloadImages(definicoesJanela.loadingImage);
});
