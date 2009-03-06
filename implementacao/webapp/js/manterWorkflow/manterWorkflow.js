/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	workflow.pesquisar();
});

/**
 * Comportamentos para o UC Manter Workflow.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
var ManterWorkflow = Class.create();
ManterWorkflow.prototype = {

   /**
	 * Tabela com os dados da pesquisa.
	 */
   tabelaTelaPrincipal :null,

   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * Retorna a tabela da tela inicial do caso de uso.
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterWorkflow");
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
	 * Recupera o id do workflow selecionado.
	 * 
	 * @return id do workflow selecionado
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do workflow selecionado.
	 */
   visualizar : function() {
	   Element.hide("formAtualizarWorkflow");
	   var idWorkflow = this.getIdSelecionado();
	   if (isNaN(idWorkflow)) {
		   return;
	   }
	   ManterWorkflowDWR.getById(idWorkflow, ( function(workflow) {
		   Effect.Appear("formAtualizarWorkflow");
		   dwr.util.setValue($("formAtualizarWorkflow").id, idWorkflow);
		   dwr.util.setValue("nomeWorkflow", workflow.nome);
		   dwr.util.setValue("descricaoWorkflow", workflow.descricao);
		   dwr.util.setValue("ativoWorkflow", workflow.ativo);
		   this.habilitarLinks(true);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos workflows pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formAtualizarWorkflow");
	   this.habilitarLinks(false);
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaWorkflow"),
	      descricao :dwr.util.getValue("descricaoPesquisaWorkflow"),
	      ativo :dwr.util.getValue("ativoPesquisaWorkflow")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterWorkflowDWR.pesquisar.bind(ManterWorkflowDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de workflows.
	 * 
	 * @param listaWorkflow lista de workflows retornados
	 */
   popularTabela : function(listaWorkflow) {
   	this.tabelaTelaPrincipal.removerResultado();

	   if (listaWorkflow.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(workflow) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :workflow.id
			   });
		   });
		   cellfuncs.push( function(workflow) {
			   return workflow.nome;
		   });
		   cellfuncs.push( function(workflow) {
			   return workflow.descricao;
		   });
		   cellfuncs.push( function(workflow) {
			   if (workflow.ativo) {
				   return "Sim";
			   }
			   return "Não";
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados workflows");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do workflow selecionado.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o workflow selecionado?", ( function() {
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
			   	this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o workflow selecionado.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o workflow selecionado?", ( function() {
		   var idWorkflow = dwr.util.getValue($("formAtualizarWorkflow").id);
		   requestUtils.simpleRequest("manterWorkflow.do?method=excluir&id=" + idWorkflow,
		      ( function() {
			      if (requestUtils.status) {
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo workflow.
	 */
   popupNovoWorkflow : function() {
	   var url = "manterWorkflow.do?method=popupNovoWorkflow";
	   createWindow(255, 375, 280, 40, "Novo Workflow", "divNovoWorkflow", url);
   },

   /**
	 * Abre janela para gerenciar os processos.
	 */
   popupGerenciarProcessos : function() {
	   var idWorkflow = dwr.util.getValue($("formAtualizarWorkflow").id);
	   var nomeWorkflow = dwr.util.getValue($("formAtualizarWorkflow").nomeWorkflow);
	   var url = "manterProcesso.do?method=entrada&workflow=" + idWorkflow;
	   createWindow(536, 985, 280, 10, "Gerenciar Processos - " + nomeWorkflow,
	      "divGerenciarProcessos", url, ( function() {
		      processo.pesquisar();
	      }));
   },

   /**
	 * Abre janela para gerenciar os campos.
	 */
   popupGerenciarCampos : function() {
	   var idWorkflow = dwr.util.getValue($("formAtualizarWorkflow").id);
	   var nomeWorkflow = dwr.util.getValue($("formAtualizarWorkflow").nomeWorkflow);
	   var url = "manterCampo.do?method=entrada&workflow=" + idWorkflow;
	   createWindow(536, 985, 280, 10, "Gerenciar Campos - " + nomeWorkflow, "divGerenciarCampos",
	      url, ( function() {
		      campo.pesquisar();
	      }));
   },

   /**
	 * Envia ao action a ação de salvar os dados do workflow.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoWorkflow");
			   this.pesquisar();
		   }
	   }).bind(this));
   },

   /**
	 * Limita a quantidade de caracteres do campo descrição.
	 * 
	 * @param (Boolean) novo se for novo workflow
	 */
   contaChar : function(novo) {
	   if (novo) {
		   contaChar($("descricaoNovoWorkflow"), 200, "contagemNovoWorkflow");
	   } else {
		   contaChar($("descricaoWorkflow"), 200, "contagemWorkflow");
	   }
   },

   /**
	 * Habilita/desabilita os links, para quando um workflow seja selecionado.
	 * 
	 * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
   habilitarLinks : function(habilita) {
	   if (habilita) {
		   $("linkGerenciarProcessos").className = "";
		   $("linkGerenciarProcessos").onclick = this.popupGerenciarProcessos;
		   $("linkGerenciarCampos").className = "";
		   $("linkGerenciarCampos").onclick = this.popupGerenciarCampos;
	   } else {
		   $("linkGerenciarProcessos").className = "btDesativado";
		   $("linkGerenciarProcessos").onclick = "";
		   $("linkGerenciarCampos").className = "btDesativado";
		   $("linkGerenciarCampos").onclick = "";
	   }
   }
};

var workflow = new ManterWorkflow();
