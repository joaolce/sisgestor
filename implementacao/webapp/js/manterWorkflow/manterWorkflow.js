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
var ComportamentosTela = Class.create();
ComportamentosTela.prototype = {
   /**
	 * @constructor
	 */
   initialize : function() {},

   tabelaTelaPrincipal :null,

   /**
	 * Retorna a tabela da tela inicial do caso de uso
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterWorkflow");
   },

   /**
	 * Recupera o form manterWorkflowForm.
	 * 
	 * @return form do manter workflow
	 */
   getForm : function() {
	   return $("manterWorkflowForm");
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
	   Element.hide("formSalvar");
	   var idWorkflow = this.getIdSelecionado();
	   if (isNaN(idWorkflow)) {
		   return;
	   }
	   ManterWorkflowDWR.getById(idWorkflow, ( function(workflow) {
		   Effect.Appear("formSalvar");
		   dwr.util.setValue($("formSalvar").id, idWorkflow);
		   dwr.util.setValue("nome", workflow.nome);
		   dwr.util.setValue("descricao", workflow.descricao);
		   dwr.util.setValue("ativo", workflow.ativo);
		   this.contaChar();
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos workflows pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvar");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisa"),
	      descricao :dwr.util.getValue("descricaoPesquisa"),
	      ativo :dwr.util.getValue("ativoPesquisa")
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
		   requestUtils.submitForm(form, null, ( function() {
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
		   var idWorkflow = dwr.util.getValue($("formSalvar").id);
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
    */
	contaChar: function() {
		contaChar($("descricao"), 200);
	}
};

var workflow = new ComportamentosTela();
