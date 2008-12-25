/**
 * 
 * @param {String} id
 */
function aplicarEstiloInput(id){
	if(Prototype.Browser.IE && window.navigator.appVersion.indexOf("MSIE 6.0") != -1){
		var inputs = null;
		var estiloCheckBox = {
			height: "15px",
			verticalAlign: "middle",
			margin: "0px"
			
		};
		var estiloRadio = {
			height: "16px",
			verticalAlign: "middle"
		};
		var estiloInput = {
			marginTop: "2px",
			color: "#003366",
			fontFamily: "verdana",
			color: "#003366",
			fontSize:"85%"
		}
		if(id == undefined){
			inputs = document.getElementsByTagName("input");
		}else{
			inputs = $(id).getElementsByTagName("input");
		}
		for(var i=0; i<inputs.length; i++){
			if(inputs.item(i).type == "checkbox"){
				Element.setStyle(inputs.item(i), estiloCheckBox);
			}
			if(inputs.item(i).type == "radio"){
				Element.setStyle(inputs.item(i), estiloCheckBox);
			}
			if(inputs.item(i).type == "text" ){
				Element.setStyle(inputs.item(i), estiloInput);
			}
			if(inputs.item(i).type == "submit" ){
				Element.setStyle(inputs.item(i), estiloInput);
			}
			if(inputs.item(i).type == "button" ){
				Element.setStyle(inputs.item(i), estiloInput);
			}
		}
	}
}
/**
 * 
 * @param {String} id
 */
function aplicarEstiloSelects(id){
	var selects = null;
	if(id == undefined){
		selects = document.getElementsByTagName("select");
	}else{
		selects = $(id).getElementsByTagName("select");
	}

	for(var i=0; i<selects.length; i++){
		if(Prototype.Browser.Gecko){
			Element.setStyle(selects.item(i), {
				marginTop: "2px"
			});
		}
		if(Prototype.Browser.IE){
			Element.setStyle(selects.item(i), {
				marginTop: "4px"
			});		
		}
		if(Prototype.Browser.Opera){
			selects.item(i).onclick = selects.item(i).ondblclick;
		}
	}
}
/**
 * 
 * @param {String} id
 */
function configuraLabel(id){
	if(!Prototype.Browser.IE)
		return;

	var labels = null;
	if(id == undefined){
		labels = document.getElementsByTagName("label");
	}else{
		labels = $(id).getElementsByTagName("label");
	}

	for(var i=0; i<labels.length; i++){
		var label = labels.item(i)
		Element.cleanWhitespace(label);
		if(label.firstChild == null || label.firstChild == undefined)
			continue;
		if(label.firstChild.nodeName == "INPUT" && (label.firstChild.type == "radio" || label.firstChild.type == "checkbox")){
			var radio = label.firstChild;
			if(isBlankOrNull(radio.id)){
				var id = "id"+((Math.random()*10));
				radio.id = id;
				label.htmlFor = id;
			}
		}
	}
}
function configurarAncorasOpera(id){
	if(!Prototype.Browser.Opera)
		return;
	var ancoras
	if(id == undefined){
		ancoras = document.getElementsByTagName("a");
	}else{
		ancoras = $(id).getElementsByTagName("a");
	}
	
	for(var index=0; index<ancoras.length; index++) {
		var a = ancoras.item(index);
		if(a.href == window.location.href){
			a.href = "javascript: ;";
		}
	}
}
/**
 * 
 * @param {Object} id
 */
function configuraPagina(id){
	configuraLabel(id);
	aplicarEstiloInput(id);
	aplicarEstiloSelects(id);
	configurarAncorasOpera(id);
}
Event.observe(window, "load", function(){
	configuraPagina();
	if(Prototype.Browser.IE && window.navigator.appVersion.indexOf("MSIE 6.0") != -1){
		$(document.body).select("div[class=\"estiloTabela\"]").each(function(div){
			div.firstDescendant().setStyle({
				styleFloat: "left",
				width: "360px"
			});
		});	
		document.styleSheets[0].addRule("input", "margin-top: 2px;color: #003366;font-family: verdana;color: #003366;font-size:85%;")
	}
});