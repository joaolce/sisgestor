/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	usarWorkflow.pesquisar();
});

/**
 * Comportamentos para o UC Usar Workflow.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
var UsarWorkflow = Class.create();
UsarWorkflow.prototype = {

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
	   return $("corpoUsarWorkflow");
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
	   UsarWorkflowDWR.getById(idWorkflow, ( function(workflow) {
		   Effect.Appear("formAtualizarWorkflow");
		   dwr.util.setValue($("formAtualizarWorkflow").id, idWorkflow);
		   dwr.util.setValue("nomeWorkflow", workflow.nome);
		   dwr.util.setValue("descricaoWorkflow", workflow.descricao);
		   dwr.util.setValue("ativoWorkflow", workflow.ativo);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos workflows pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formAtualizarWorkflow");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaWorkflow"),
	      descricao :dwr.util.getValue("descricaoPesquisaWorkflow"),
	      ativo :dwr.util.getValue("ativoPesquisaWorkflow"),
	      excluidos :false
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = UsarWorkflowDWR.pesquisar.bind(UsarWorkflowDWR);
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
   }
};

var usarWorkflow = new UsarWorkflow();
