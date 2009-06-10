/**
 * Comportamentos para o UC Manter Campo.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
var ManterCampo = Class.create();
ManterCampo.prototype = {

   /**
	 * Tabela com os dados da pesquisa.
	 */
   tabelaTelaPrincipal :null,

   /**
	 * Tipos de campo.
	 */
   tiposCampo :null,

   /**
	 * Número máximo de opções para lista de opções e múltipla escolha.
	 */
   MAXIMO_OPCOES :20,

   /**
	 * @constructor
	 */
   initialize : function() {
	   ManterCampoDWR.getTipos(( function(tipos) {
		   this.setTiposCampo(tipos);
	   }).bind(this));
   },

   /**
	 * Retorna a tabela da tela inicial do caso de uso
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterCampo");
   },

   /**
	 * Recupera a linha selecionada.
	 * 
	 * @return linha selecionada
	 */
   getTR : function() {
	   return FactoryTabelas.getTabelaById(this.getTBodyTelaPrincipal()).getSelectedTR();
   },

   /**
	 * Recupera o id do campo selecionado.
	 * 
	 * @return id do campo selecionado
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do campo selecionado.
	 */
   visualizar : function() {
	   Element.hide("formAtualizarCampo");
	   Element.hide("divOpcoesCampo");
	   var idCampo = this.getIdSelecionado();
	   if (isNaN(idCampo)) {
		   return;
	   }
	   ManterCampoDWR.getById(idCampo, ( function(campo) {
		   Effect.Appear("formAtualizarCampo");
		   dwr.util.setValue($("formAtualizarCampo").id, idCampo);
		   dwr.util.setValue("nomeCampo", campo.nome);
		   dwr.util.setValue("tipoCampo", this.getTipoCampo(campo)[0]);
		   dwr.util.setValue("descricaoCampo", campo.descricao);
		   dwr.util.setValue("obrigatorioCampo", campo.obrigatorio);
		   dwr.util.removeAllOptions("opcoesCampo");
		   dwr.util.addOptions("opcoesCampo", campo.opcoes, "descricao", "descricao");
		   this.gerenciarPreDefinidos(false);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos campos pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formAtualizarCampo");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaCampo"),
	      tipo :dwr.util.getValue("tipoPesquisaCampo"),
	      idWorkflow :dwr.util.getValue("workflow")
	   };

	   if ((this.tabelaTelaPrincipal == null)
	      || (this.tabelaTelaPrincipal.getTabela() != this.getTBodyTelaPrincipal())) {
		   var chamadaRemota = ManterCampoDWR.pesquisar.bind(ManterCampoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_CAMPO);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de campos.
	 * 
	 * @param listaCampo lista de campos retornados
	 */
   popularTabela : function(listaCampo) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaCampo.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(campo) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :campo.id
			   });
		   });
		   cellfuncs.push( function(campo) {
			   return campo.nome;
		   });
		   cellfuncs.push( function(campo) {
			   if (campo.obrigatorio) {
				   return "Sim";
			   }
			   return "Não";
		   });
		   cellfuncs.push( function(campoObj) {
			   return campo.getTipoCampo(campoObj)[2];
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados campos");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do campo selecionado.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o campo selecionado?", ( function() {
		   ComboFunctions.selecionaCombo("opcoesCampo");
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o campo selecionado.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o campo selecionado?", ( function() {
		   var idCampo = dwr.util.getValue($("formAtualizarCampo").id);
		   requestUtils.simpleRequest("manterCampo.do?method=excluir&id=" + idCampo, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo campo.
	 */
   popupNovoCampo : function() {
	   var url = "manterCampo.do?method=popupNovoCampo";
	   createWindow(255, 445, 280, 40, "Novo Campo", "divNovoCampo", url, ( function() {
		   dwr.util.setValue("workflowNovoCampo", $F("workflowCampo"));
		   this.contaChar(true);
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de salvar os dados do campo.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   ComboFunctions.selecionaCombo("opcoesNovoCampo");
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoCampo");
			   this.pesquisar();
		   }
	   }).bind(this));
   },

   /**
	 * Recupera o tipo do campo a ser visualizado. <br />
	 * obs: método feito pois o tipo é uma enum, e o DWR não converte da forma esperada.
	 * 
	 * @param campo campo a recuperar o tipo
	 * @return array com o tipo do campo, onde: [0] - id do tipo, [1] - name do tipo, [2] - descrição
	 *         do tipo
	 */
   getTipoCampo : function(campo) {
	   var tipoCampo;
	   this.tiposCampo.each(( function(tipo) {
		   if (tipo[1] == campo.tipo) { // verifica pelo name da enum
			   tipoCampo = tipo;
			   throw $break;
		   }
	   }));
	   return tipoCampo;
   },

   /**
	 * Armazena os tipos de campo.
	 * 
	 * @param tipos tipos de campo
	 */
   setTiposCampo : function(tipos) {
	   this.tiposCampo = tipos;
   },

   /**
	 * Limita a quantidade de caracteres do campo descrição.
	 * 
	 * @param (Boolean) novo se for novo campo
	 */
   contaChar : function(novo) {
	   if (novo) {
		   contaChar($("descricaoNovoCampo"), 200, "contagemNovoCampo");
	   } else {
		   contaChar($("descricaoCampo"), 200, "contagemCampo");
	   }
   },

   /**
	 * Abre div para editar valores pré-definidos do campo.
	 * 
	 * @param {Boolean} novo se é novo campo
	 */
   gerenciarPreDefinidos : function(novo) {
	   var divOpcoes = novo ? "divOpcoesNovoCampo" : "divOpcoesCampo";
	   if (this.ehCampoComOpcoes(novo)) {
		   if (!$(divOpcoes).visible()) {
			   if (novo) {
				   $($("divNovoCampo").parentNode).morph("height: 400px;");
				   Effect.BlindDown(divOpcoes);
			   } else {
				   $("fieldsetCampo").setStyle( {
					   width :"790px"
				   });
				   $(divOpcoes).show();
			   }
		   }
	   } else {
		   dwr.util.removeAllOptions(novo ? "opcoesNovoCampo" : "opcoesCampo");
		   if (novo) {
			   Effect.BlindUp(divOpcoes);
			   $($("divNovoCampo").parentNode).morph("height: 255px;");
		   } else {
			   $("fieldsetCampo").setStyle( {
				   width :"70%"
			   });
			   $(divOpcoes).hide();
		   }
	   }
   },

   /**
	 * Verifica se o tipo de campo atual possui opções.
	 * 
	 * @param {Boolean} novo se é novo campo
	 * @return {Boolean} <code>true</code> caso possua, <code>false</code> caso contrário
	 */
   ehCampoComOpcoes : function(novo) {
	   var tipo = $F(novo ? "tipoNovoCampo" : "tipoCampo");
	   return ((tipo == LISTA_DE_OPCOES) || (tipo == MULTIPLA_ESCOLHA));
   },

   /**
	 * Remove uma opção do campo.
	 * 
	 * @param {Boolean} novo se é novo campo
	 */
   removeOpcao : function(novo) {
	   ComboFunctions.removeOption(novo ? "opcoesNovoCampo" : "opcoesCampo");
   },

   /**
	 * Adiciona uma opção a lista de opções do campo
	 * 
	 * @param {Boolean} novo se é novo campo
	 */
   adicionaOpcao : function(novo) {
	   var novaOpcao = novo ? "opcaoNovoCampo" : "opcaoCampo";
	   var comboOpcoes = novo ? "opcoesNovoCampo" : "opcoesCampo";

	   if ($(comboOpcoes).options.length >= this.MAXIMO_OPCOES) {
		   JanelasComuns.showInformation("Número máximo de opções permitidas: " + this.MAXIMO_OPCOES);
	   } else {
		   var opcao = dwr.util.getValue(novaOpcao);
		   if (!opcao.empty()) { // opção em branco
			   if (ComboFunctions.getAllOptionInnerLabel(comboOpcoes).include(opcao)) { // já existe a opção
				   JanelasComuns.showInformation("A opção '" + opcao + "' já foi adicionada!");
			   } else {
				   dwr.util.addOptions(comboOpcoes, [ opcao ]);
				   ComboFunctions.ordenarOptions(comboOpcoes);
				   $(novaOpcao).clear();
			   }
		   }
	   }
   }
};

var campo = new ManterCampo();
