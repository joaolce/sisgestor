/**
 * A��o a ser realizada ao iniciar a p�gina
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
	   var idUsoWorkflow = $F("idUsoWorkflow");
	   var url = "anexoUsoWorkflow.do?method=entrada&usoWorkflow=" + idUsoWorkflow;
	   createWindow(250, 500, 300, 40, "Visualizar Anexos", "divVisualizarAnexos", url,
	      ( function() {
		      dwr.util.setValue("idUsoWorkflowAnexo", idUsoWorkflow);
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
		   	dwr.util.setValue("dataHoraInicioTarefa", getStringTimestamp(usoWorkflow.dataHoraInicio));
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
	 * Faz a pesquisa dos workflows pelos par�metros informados.
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
		   if(Usuario.temPermissao(USAR_WORKFLOW)) {
		   	this.tabelaTelaPrincipal.setOnDblClick(this.popupUsoDeWorkflow);
		   }		   	
	   } else {
		   this.tabelaTelaPrincipal
		      .semRegistros("N�o foram encontradas tarefas pendentes de sua responsabilidade");
	   }
   },

   /**
	 * Inicializa um uso do workflow.
	 */
   iniciarWorkflow : function() {
	   return true; // APOS SELECIONAR O WORKFLOW NA INICIALIZACAO
	},

	/**
 	 * Envia a requisi��o para submeter o uso do workflow.
 	 * 
 	 * @param form formul�rio submetido
 	 */
	confirmar : function(form) {

	}
};

var usarWorkflow = new UsarWorkflow();
