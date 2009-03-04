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
	 * Janela auxiliar de popup para gerenciar os valores pré-definidos.
	 */
   janelaValoresPreDefinidos :null,

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
	   var idCampo = this.getIdSelecionado();
	   if (isNaN(idCampo)) {
		   return;
	   }
	   ManterCampoDWR.getById(idCampo, ( function(campo) {
		   Effect.Appear("formAtualizarCampo");
		   dwr.util.setValue("nomeCampo", campo.nome);
		   dwr.util.setValue("tipoCampo", this.getTipoCampo(campo)[0]);
		   dwr.util.setValue("descricaoCampo", campo.descricao);
		   dwr.util.setValue("obrigatorioCampo", campo.obrigatorio);
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

	   if (this.tabelaTelaPrincipal == null) {
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
	   }));
   },

   /**
	 * Envia ao action a ação de salvar os dados do campo.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
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
	 * Abre popup para gerenciar os valores pré-definidos do campo.
	 */
   gerenciarPreDefinidos : function() {
	   if (this.janelaValoresPreDefinidos == null) {
		   var url = "manterCampo.do?method=popupGerenciarPreDefinidos&workflow=" + $F("workflowCampo");
		   this.janelaValoresPreDefinidos = createWindow(255, 445, 280, 295, "Valores Pré-Definidos",
		      "divValoresPreDefinidos", url);
		   this.janelaValoresPreDefinidos.undoModal();
	   } else {
		   this.janelaValoresPreDefinidos.fecharJanela();
		   this.janelaValoresPreDefinidos = null;
	   }
   }
};

var campo = new ManterCampo();
