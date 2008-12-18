/**
 * Responsável pelas tabelas que têm data de vigência nos dados
 * 
 * @author deinf.jlucio
 */
var DataVigencia = Class.create();
DataVigencia.prototype = {
	/**
	 * @type Janela
	 */
	janela: null,
	/**
	 * @type Function
	 */
	posFunction: null,
	/**
	 * @type HTMLInputElement
	 */
	campoVigencia: null,
	
	/**
	 * @type HTMLInputElement
	 */
	campoValorPadrao: null,
	/**
	 * @constructor
	 * 
	 * @param {HTMLInputElement} campoVigencia
	 * @param {Function} posFunction
	 */
	initialize: function(campoVigencia, posFunction){
		this.posFunction = posFunction;
		this.campoVigencia = $(campoVigencia);
		var dataAtual = getDataAtual();
		
		this.janela = createWindow(120, 300, 20, 20, "Alterar data de início dos dados", "divInicio");
		this.janela.setHeight(null);
		
		var idHtmlFor = "campo"+Math.random();
		var input = $(Builder.node("input", {type: "text", value: dataAtual, id: idHtmlFor}));
		input.setValue(dataAtual);
		this.campoValorPadrao = input;
		
		var inputOK = Builder.node("input", {type: "submit", value: "OK", style: "margin-right: 5px; width: 100px;"});
		var inputCancelar = Builder.node("input", {type: "button", value: "Cancelar", style: "width: 100px;"});
		
		MaskInput(input, "##/##/####");
		Event.observe(inputCancelar, "click", this.cancelar.bind(this));
		
		var div = Builder.node("div", [
			Builder.node("div", [
				Builder.node("label", {htmlFor: idHtmlFor}, [
					document.createTextNode("Deseja alterar a vigência atual: "),
					document.createTextNode(this.campoVigencia.getValue()),
					document.createTextNode("?")
				])
			]),
			Builder.node("div", {style: "margin-top: 5px;"}, [
				Builder.node("label", [
					document.createTextNode("Nova data de vigência: "),
					Builder.node("br"),
					input
				])
			]),
			Builder.node("div", {style: "margin-top: 5px; text-align: center;"}, [
				inputOK,
				inputCancelar
			])
		]);
		var form = $(Builder.node("form", {action: ""}, [
			div
		]));
		Event.observe(form, "submit", this.confirmar.bind(this));
		this.janela.adicionarConteudo(form);
		this.janela.centralizarX();
		form.focusFirstElement();
	}, 
	confirmar: function(){
		this.campoVigencia.setValue(this.campoValorPadrao.getValue());
		this.janela.fecharJanela();
		this.posFunction();
		return false;
	},
	cancelar: function(){
		this.janela.fecharJanela();
	}
};