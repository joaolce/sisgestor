/**
 * Tabelas
 * 
 * Wrapper de tabelas
 * 
 * Adiciona a todas as linhas de uma tabelas eventos como onclick ou ondblclick al�m fornecer
 * informa��es como a refer�ncia da linha selecionada (TR) ou o �ndice da linha selecionada
 * 
 * @author deinf.sdantas
 * 
 * Exemplo:
 * 
 * <code>
 * 		<!-- HTML -->
 * 		<table>
 * 			<tbody id="tabelaTeste">
 * 				<td>cel 1</td>
 * 				<td>cel 2</td>
 * 				<td>cel 3</td>
 * 			</tbody>
 * 		</table>
 * </code>
 * 
 * <code>
 * 		//javascript
 * 		var tabela = FactoryTabelas.getNewTabela("corpoTabelaClicavel");
 * 		tabela.setOnDblClick(function(){
 * 			alert("clicou duas vezes");
 * 		});
 * 		tabela.setOnClick(function(){
 * 			alert("clicou uma vez");
 * 		});

 * </code>
 * 
 */
var Tabela = Class.create();
Tabela.prototype = {
   /**
    * estilo que dever� ser aplicado a tabela
    * 
    * @type String
    */
   _className :"corpoTabelaClicavel",
   /**
    * id e estilo da linha selecionada
    * 
    * @type String
    */
   _linhaSelecionada :"linhaSelecionada",
   /**
    * linha selecionada
    * 
    * @type HTMLTableRowElement
    */
   _trSelecionada :null,
   /**
    * tabela onde o wrapper est� aplicado
    * 
    * @type HTMLTableSectionElement _tabela
    */
   _tabela :null,
   /**
    * indica se a tabela � selecion�vel ou n�o (selecion�vel no sentido de clicar segurar o mouse e
    * selecionar o texto da tabela) o que n�o � �til quando a inten��o da tabela � apenas clicar em
    * uma linha para selecionar o registro
    * 
    * @type Boolean
    */
   selecionavel :false,
   /**
    * Total de registros retornados pela consulta
    * 
    * @type Integer
    */
   totalRegistros :null,
   /**
    * Total de p�ginas
    * 
    * @type Integer
    */
   totalPaginas :null,
   /**
    * Quantidade de registros a serem exibidos por p�gina
    * 
    * @type Integer
    */
   qtdRegistrosPagina :18,
   /**
    * M�ximo de p�ginas a serem apresentadas para pagina��o
    * 
    * @type Integer
    */
   qtdPaginasExibidas :20,
   /**
    * p�gina atual
    * 
    * @type Integer
    */
   paginaAtual :0,
   /**
    * Div que cont�m a pagina��o
    * 
    * @type HTMLDivElement
    */
   divPaginacao :null,
   /**
    * Indicar se os cliques dever�o ser processados ou n�o
    * 
    * @type Boolean
    */
   desativado :false,
   /**
    * Cria o wrapper na tabela especificada pelo id
    * 
    * @constructor
    * @param {String} idTabela
    * @param {String} className
    */
   initialize : function(idTabela, className) {
	   if (className != undefined) {
		   this._className = className;
	   }
	   this._tabela = $(idTabela);
	   if ((this._className != null) && !this._className.blank()) {
		   this._tabela.className = this._className;
	   }
   },
   /**
    * aplica os manipuladores de evento na tabela
    */
   configurar : function() {
	   this.aplicarEventos(this._tabela);
   },
   /**
    * aplica o evento na tabela e em seus filhos
    * 
    * @param {HTMLElement}
    */
   aplicarEventos : function(elemento) {
	   Element.cleanWhitespace(elemento);
	   for ( var index = 0; index < elemento.childNodes.length; index++) {
		   var el = elemento.childNodes.item(index);
		   Element.cleanWhitespace(el);
		   try {
			   if (el.nodeName.toLowerCase() == "tr") {
				   el.onclick = this._selecionarLinha.bindAsEventListener(this);
				   el.ondblclick = this.onDblClick;
			   }
			   if (!this.selecionavel) {
				   el.onmouseup = this._disable;
				   el.onmousedown = this._enable;
				   el.onselectstart = this._enable;
			   }
			   if ((el.firstChild != null)
			      && [ "span", "div", "td" ].include(el.firstChild.nodeName.toLowerCase())) {
				   this.aplicarEventos(el.firstChild);
			   }

		   } catch (e) {
			   continue;
		   }
	   }
   },
   /**
    * retorna a refer�ncia da linha selecionada
    * 
    * @return {HTMLTableRowElement}
    */
   getSelectedTR : function() {
	   return this._trSelecionada;
   },
   /**
    * sobe at� chegar no n� da tr a partir do elemento que causou o evento
    * 
    * @param {Event} event
    */
   _selecionarLinha : function(event) {
	   // recupera o elemento que causou o evento
	   var tr = Event.element(event);
	   // sobe at� chegar na tr
	   while (tr.nodeName.toLowerCase() != "tr") {
		   tr = tr.parentNode;
	   }
	   this._seleciona(tr);
   },
   /**
    * cria ou retorna o bot�o voltar
    * 
    * @return o bot�o de voltar
    * @type HTMLSpanElement
    */
   _getRewindButton : function() {
	   var resultado = this.divPaginacao.select("a[id=\"voltarPagina\"]");
	   var botao = null;
	   if (resultado.length != 0) {
		   botao = resultado[0];
	   } else {
		   botao = Builder.node("a", {
		      href :"#",
		      id :"voltarPagina"
		   }, [ document.createTextNode(" Anterior ") ]);
		   Event.observe(botao, "click", this.rewindResults.bind(this));

		   var botaoPrimeira = Builder.node("a", {
		      href :"#",
		      id :"ultimaPagina"
		   }, [ document.createTextNode(" Primeira ") ]);
		   Event.observe(botaoPrimeira, "click", this.primeiraPagina.bind(this));

		   var span = Builder.node("span", [ botaoPrimeira, botao ]);
		   if (this.divPaginacao.childNodes.length == 0) {
			   this.divPaginacao.appendChild(span);
		   } else {
			   this.divPaginacao.insert( {
				   top :span
			   });
		   }
	   }
	   return $(botao.parentNode);
   },
   /**
    * cria ou retorna o bot�o avan�ar
    * 
    * @return o bot�o de avan�ar
    * @type HTMLSpanElement
    */
   _getForwardButton : function() {
	   var resultado = this.divPaginacao.select("a[id=\"avancarPagina\"]");
	   var botao = null;
	   if (resultado.length != 0) {
		   botao = resultado[0];
	   } else {
		   botao = Builder.node("a", {
		      href :"#",
		      id :"avancarPagina"
		   }, [ document.createTextNode(" Pr�ximo ") ]);
		   Event.observe(botao, "click", this.forwardResults.bind(this));

		   var botaoUltimo = Builder.node("a", {
		      href :"#",
		      id :"ultimaPagina"
		   }, [ document.createTextNode(" �ltima ") ]);
		   Event.observe(botaoUltimo, "click", this.ultimaPagina.bind(this));

		   var span = Builder.node("span", [ botao, botaoUltimo ]);
		   this.divPaginacao.appendChild(span);
	   }
	   return $(botao.parentNode);
   },
   /**
    * esconder ou mostrar o bot�o de voltar
    * 
    * @param {Boolean} exibe
    */
   toggleRewindButton : function(exibe) {
	   if (exibe) {
		   this._getRewindButton().style.visibility = "";
	   } else {
		   this._getRewindButton().style.visibility = "hidden";
	   }
   },
   /**
    * mostrar ou esconder o bot�o de avan�ar
    * 
    * @param {Boolean} exibe
    */
   toggleForwardButton : function(exibe) {
	   if (exibe) {
		   this._getForwardButton().style.visibility = "";
	   } else {
		   this._getForwardButton().style.visibility = "hidden";
	   }
   },
   /**
    * @return div que cont�m a pagina��o
    * @type HTMLDivElement
    */
   getDivPaginacao : function() {
	   return this.divPaginacao;
   },
   /**
    * mostra ou n�o o div de pagina��o
    * 
    * @param {Boolean} exibe
    */
   toggleShowDivPaginacao : function(exibe) {
	   this.desativado = !exibe;
   },
   /**
    * setar quantidade de registros por p�gina (um valor padr�o j� est� setado)
    * 
    * @see qtdRegistrosPagina
    * @param {Integer} novoValor
    */
   setQtdRegistrosPagina : function(novoValor) {
	   this.qtdRegistrosPagina = novoValor;
   },
   /**
    * Total de registros retornados pela consulta
    * 
    * @param {Integer} totalRegistros
    */
   setTotalRegistros : function(totalRegistros) {
	   this.totalRegistros = totalRegistros;
	   if (this.divPaginacao == null) {
		   this.divPaginacao = $(Builder.node("div", {
			   style :"text-align:center; margin-top: 5px;"
		   }));
		   this.divPaginacao.id = "divPaginacao";
		   var tabela = this._tabela;
		   while (tabela.nodeName.toLowerCase() != "table") {
			   tabela = $(tabela.parentNode);
		   }
		   tabela.insert( {
			   before :this.divPaginacao
		   });
	   }
	   this.divPaginacao.update("");
	   if (totalRegistros == 0) {
		   return;
	   }

	   this.totalPaginas = parseInt(this.totalRegistros / this.qtdRegistrosPagina);
	   if (this.totalRegistros % this.qtdRegistrosPagina != 0) {
		   this.totalPaginas++;
	   }
	   if (isNaN(this.totalPaginas)) {
		   this.totalPaginas = 0;
	   }

	   this.toggleForwardButton(!this.isUltimaPagina());
	   this.toggleRewindButton(!this.isPrimeiraPagina());

	   var divNumeracao = Builder.node("span");
	   this.divPaginacao.firstDescendant().insert( {
		   after :divNumeracao
	   });

	   var index = 1;
	   var maximo = this.totalPaginas;
	   if (this.totalPaginas > this.qtdPaginasExibidas) {
		   if (this.paginaAtual + 1 > this.qtdPaginasExibidas) {
			   index = (parseInt(this.paginaAtual / this.qtdPaginasExibidas) * this.qtdPaginasExibidas) + 1;
		   }
		   maximo = this.paginaAtual
		      + (this.qtdPaginasExibidas - (this.paginaAtual % this.qtdPaginasExibidas));
		   if (maximo > this.totalPaginas) {
			   maximo = this.totalPaginas;
		   }
	   }
	   for (; index <= maximo; index++) {
		   var numeracao = index.toString();

		   if (index != this.totalPaginas) {
			   numeracao = numeracao.concat(", ");
		   }
		   var ancora = null;
		   if (index == this.paginaAtual + 1) {
			   ancora = Builder.node("span", {
				   style :"background-color: #EFEFEF; color: #0065FF; border: 1px solid #0065FF"
			   }, [ document.createTextNode(numeracao) ]);
		   } else {
			   ancora = Builder.node("a", {
				   href :"#"
			   }, [ document.createTextNode(numeracao) ]);
			   Event.observe(ancora, "click", this.gotoPage.bind(this, index - 1));
		   }
		   divNumeracao.appendChild(ancora);
	   }
   },
   /**
    * @return true se primeira false se n�o
    * @type Boolean
    */
   isPrimeiraPagina : function() {
	   return this.paginaAtual == 0;
   },
   /**
    * @return true se �ltima false se n�o
    * @type Boolean
    */
   isUltimaPagina : function() {
	   return this.paginaAtual + 1 == this.totalPaginas;
   },
   trocouPagina : function() {
	   this.setTotalRegistros(this.totalRegistros);
	   this.abstractOnTrocarPagina(this.paginaAtual);
   },
   /**
    * Toda vez que o usu�rio clicar para trocar de p�gina essa fun��o ser� invocada
    * 
    * @param {Integer} novaPagina
    */
   abstractOnTrocarPagina : function(novaPagina) {
   // overhide me
   },
   /**
    * @return a p�gina atual
    * @type Integer
    */
   getPaginaAtual : function() {
	   return this.paginaAtual;
   },
   /**
    * ir para a primeira p�gina
    */
   primeiraPagina : function() {
	   this.gotoPage(0);
   },
   /**
    * reiniciar pagina��o
    */
   reiniciarPaginacao : function() {
	   this.paginaAtual = 0;
   },
   /**
    * ir para �ltima p�gina
    */
   ultimaPagina : function() {
	   this.gotoPage(this.totalPaginas - 1);
   },
   /**
    * ir para a p�gina especificada
    * 
    * @param {Integer} novaPagina
    */
   gotoPage : function(novaPagina) {
	   if (!this.desativado) {
		   this.paginaAtual = novaPagina;
		   this.trocouPagina();
	   }
   },
   /**
    * retroceder resultados
    */
   rewindResults : function() {
	   if (!this.desativado) {
		   this.toggleRewindButton(!this.isUltimaPagina());
		   if (!this.isPrimeiraPagina()) {
			   this.paginaAtual--;
			   this.trocouPagina();
		   }
	   }
   },
   /**
    * avan�ar resultados
    */
   forwardResults : function() {
	   if (!this.desativado) {
		   this.toggleForwardButton(!this.isPrimeiraPagina());
		   if (!this.isUltimaPagina()) {
			   this.paginaAtual++;
			   this.trocouPagina();
		   }
	   }
   },
   /**
    * @param {HTMLTableRowElement} tr
    */
   selecionarTR : function(tr) {
	   if (this._trSelecionada == null) {
		   this._trSelecionada = tr;
	   } else {
		   this._trSelecionada.id = "";
	   }
	   this._trSelecionada = tr;
	   this._trSelecionada.id = this._linhaSelecionada;
   },
   /**
    * seleciona a tr passada, destacando com outro estilo
    * 
    * @param {HTMLTableRowElement} tr
    */
   _seleciona : function(tr) {
	   this.selecionarTR(tr);
	   if ((this.onClick != null) && (typeof this.onClick == "function")) {
		   this.onClick(tr);
	   }
   },
   /**
    * fun��o a ser chamada ao clicar uma vez em uma linha
    * 
    * @param {HTMLTableRowElement} tr
    */
   onClick : function(tr) {},
   /**
    * fun��o a ser chamada ao clicar duas vezes em uma linha
    * 
    * @param {HTMLTableRowElement} tr
    */
   onDblClick : function(tr) {},
   /**
    * seta uma fun��o para ser chamada quando uma linha da tabela for clicada uma vez
    * 
    * @param {Function}clickFunction
    */
   setOnClick : function(clickFunction) {
	   this.onClick = clickFunction;
	   this.configurar();
   },
   /**
    * seta uma fun��o para ser chamada ao clicar duas vezes em uma linha da tabela
    * 
    * @param {Function} dblClickFunction
    */
   setOnDblClick : function(dblClickFunction) {
	   this.onDblClick = dblClickFunction;
	   this.configurar();
   },
   /**
    * seleciona o �ndice passado
    * 
    * @param {Integer} indice da linha a ser selecioando
    */
   setSelecionado : function(indice) {
	   var tr = this._tabela.childNodes.item(indice);
	   this._seleciona(tr);
   },
   /**
    * deselecionar a linha selecionada (se houver)
    */
   selecionarNenhum : function() {
	   if (this._trSelecionada != null) {
		   this._trSelecionada.id = "";
		   this._trSelecionada = null;
	   }
   },
   /**
    * colocar uma linha de registro n�o encontrado em uma tabela
    * 
    * @param {String} mensagem
    */
   semRegistros : function(mensagem) {
	   var colunas = this._tabela.parentNode.getElementsByTagName("th").length;
	   var trVazia = Builder.node("tr", null, [ Builder.node("td", {
	      colspan :colunas,
	      align :"center"
	   }, [ document.createTextNode(mensagem) ]) ]);
	   this._tabela.appendChild(trVazia);

	   /* gambi para colocar a mensagem no meio da tabela, como sempre os bugs do IE */
	   if (Prototype.Browser.IE) {
		   var fix = ( function() {
			   this._tabela.firstChild.firstChild.colSpan = colunas;
		   }).bind(this);
		   window.setTimeout(fix, 0);
	   }
   },
   /**
    * Desabilitar a sele��o de um texto da tabela, n�o permite que o usu�rio selecione um texto da
    * tabela, usado internamente
    * 
    * @see Tabela.selecionavel
    * @param {Event} e
    * @return {Boolean}
    */
   _enable : function(e) {
	   var e = e ? e : window.event;

	   if (e.button != 1) {
		   if (e.target) {
			   var targer = e.target;
		   } else if (e.srcElement) {
			   var targer = e.srcElement;
		   }

		   return false;
	   }
   },
   /**
    * habilitar a sele��o do elemento usada internamente para n�o permitir que o usu�rio selecione
    * um texto da tabela
    * 
    * @return{Boolean}
    */
   _disable : function() {
	   return true;
   }


};

var FactoryTabelas = Class.create();
FactoryTabelas = {
   tabelas :new Hash(),
   getNewTabela : function(idTabela, className) {
	   var tabela = new Tabela(idTabela, className);
	   this.tabelas.set(idTabela, tabela);
	   return tabela;
   },
   getTabelaById : function(idTabela) {
	   return this.tabelas.get(idTabela);
   }
};
