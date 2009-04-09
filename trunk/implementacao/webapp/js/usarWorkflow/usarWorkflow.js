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
	 * Tabela com os anexos
	 */
   tabelaTelaAnexo :null,

   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * Inclui um novo anexo. Envia requisição para o servidor.
	 */
   incluirAnexo : function() {
   // REDIRECIONAR A PAGINA DE USO
   },

   /**
	 * Exclui o(s) anexo(s) selecionado(s)
	 */
   excluirAnexo : function() {
	   var anexosSelecionados = dwr.util.getValue("anexosSelecionados");
	   requestUtils.simpleRequest("usarWorkflow.do?method=excluirAnexo&anexosSelecionados="
	      + anexosSelecionados, ( function() {
		   if (requestUtils.status) {
			   this.pesquisarAnexos();
		   }
	   }).bind(this));
   },

   /**
	 * Inicia o workflow selecionado no combo
	 */
   popupIniciarWorkflow : function() {
	   var url = "usarWorkflow.do?method=popupIniciarWorkflow";
	   createWindow(115, 375, 280, 70, "Iniciar Workflow", "divIniciarWorkflow", url);
   },

   /**
	 * Abre popup para visualizar os anexos.
	 */
   popupVisualizarAnexos : function() {
	   var idUsoWorkflow = dwr.util.getValue("idUsoWorkflow");
	   var url = "usarWorkflow.do?method=popupVisualizarAnexos&id=" + idUsoWorkflow;
	   createWindow(355, 550, 300, 40, "Visualizar Anexos", "divVisualizarAnexos", url,
	      ( function() {
		      dwr.util.setValue("idUsoWorkflowAnexo", $F("idUsoWorkflow"));
		      this.pesquisarAnexos();
	      }).bind(this));
   },

   /**
	 * Abre o popup de uso do workflow.
	 */
   popupUsoDeWorkflow : function() {
   	var colunas = usarWorkflow.getTR().descendants();
   	var numeroRegistro = colunas[2].innerHTML;
   	var nomeWorkflow = colunas[3].innerHTML;
   	var nomeProcesso = colunas[4].innerHTML;
	   var tituloPagina = numeroRegistro + " - " + nomeWorkflow + " - " + nomeProcesso;

	   var url = "usarWorkflow.do?method=popupUsoWorkflow";
	   createWindow(536, 985, 280, 10, tituloPagina, "divUsoWorkflow", url, ( function() {
		   FactoryAbas.getNewAba("tabCamposAncora,tabCampos;tabHistoricoAncora,tabHistorico");
		   var id = usarWorkflow.getIdSelecionado();
		   dwr.util.setValue("idUsoWorkflow", id);
		   UsarWorkflowDWR.getById(id, (function(usoWorkflow){
		   	dwr.util.setValue("dataHoraInicioTarefa", usoWorkflow.dataHoraInicio);
		   	dwr.util.setValue("nomeTarefa", usoWorkflow.tarefa.nome);
		   	dwr.util.setValue("descricaoTarefa", usoWorkflow.tarefa.descricao);
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Retorna a tabela da tela inicial do caso de uso.
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoUsarWorkflow");
   },

   /**
	 * Retorna a tabela da tela de anexo.
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaAnexo : function() {
	   return $("corpoAnexos");
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
	 * Faz a pesquisa dos workflows pelos parâmetros informados.
	 */
   pesquisar : function() {
	   if ((this.tabelaTelaPrincipal == null)
	      || (this.tabelaTelaPrincipal.getTabela() != this.getTBodyTelaPrincipal())) {
		   var chamadaRemota = UsarWorkflowDWR.pesquisar.bind(UsarWorkflowDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_USO_WORKFLOW);
	   }
	   this.tabelaTelaPrincipal.setParametros( {});
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Faz a pesquisa dos anexos do workflow.
	 */
   pesquisarAnexos : function() {
	   if ((this.tabelaTelaAnexo == null)
	      || (this.tabelaTelaAnexo.getTabela() != this.getTBodyTelaAnexo())) {
		   var chamadaRemota = UsarWorkflowDWR.pesquisarAnexos.bind(UsarWorkflowDWR);
		   this.tabelaTelaAnexo = FactoryTabelas.getNewTabela(this.getTBodyTelaAnexo());
		   this.tabelaTelaAnexo.setRemoteCall(chamadaRemota);
		   this.tabelaTelaAnexo.setCallBack(this.popularTabelaAnexos.bind(this));
	   }
	   var parametro = {
		   idUsoWorkflow :dwr.util.getValue("idUsoWorkflow")
	   };
	   this.tabelaTelaAnexo.setParametros(parametro);
	   this.tabelaTelaAnexo.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de uso de workflows.
	 * 
	 * @param listaUsoWorkflow lista de uso de workflows retornados
	 */
   popularTabela : function(listaUsoWorkflow) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaUsoWorkflow.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(usoWorkflow) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :usoWorkflow.id
			   });
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return usoWorkflow.numeroRegistro;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return usoWorkflow.tarefa.atividade.processo.workflow.nome;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return usoWorkflow.tarefa.atividade.processo.nome;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return usoWorkflow.tarefa.atividade.nome;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return usoWorkflow.tarefa.nome;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   if (usoWorkflow.dataHoraInicio != null) {
				   return getStringTimestamp(usoWorkflow.dataHoraInicio);
			   }
			   return "&nbsp;";
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnDblClick(this.popupUsoDeWorkflow);
	   } else {
		   this.tabelaTelaPrincipal
		      .semRegistros("Não foram encontradas tarefas pendentes de sua responsabilidade");
	   }
   },

   /**
	 * Popula a tabela de anexos com os anexos do workflow
	 * 
	 * @param listaAnexos lista de anexos
	 */
   popularTabelaAnexos : function(listaAnexos) {
	   this.tabelaTelaAnexo.removerResultado();

	   if (listaAnexos.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(anexo) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :anexo.id
			   });
		   });
		   cellfuncs.push( function(anexo) {
			   return Builder.node("input", {
			      type :"checkbox",
			      name :"anexosSelecionados",
			      value :anexo.id
			   });
		   });
		   cellfuncs.push( function(anexo) {
			   return getStringTimestamp(anexo.dataCriacao);
		   });
		   cellfuncs.push( function(anexo) {
			   return anexo.nome;
		   });
		   this.tabelaTelaAnexo.adicionarResultadoTabela(cellfuncs);
	   } else {
		   this.tabelaTelaAnexo.semRegistros("Não foram encontrados anexos");
	   }
   },

   /**
	 * Inicializa um uso do workflow.
	 */
   iniciarWorkflow : function() {
	   return true; // APOS SELECIONAR O WORKFLOW NA INICIALIZACAO
	},

	/**
 	 * Envia a requisição para submeter o uso do workflow.
 	 * 
 	 * @param form formulário submetido
 	 */
	confirmar : function(form) {

	}
};

var usarWorkflow = new UsarWorkflow();
