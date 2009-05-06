/**
 * Ação a ser realizada ao iniciar a página.
 */
Event.observe(window, "load", function() {
	MaskInput("numeroRegistroPesquisa", "####/######");
});

/**
 * Comportamentos para fazer a pesquisa de registros finalizados.
 * 
 * @extends UsarWorkflow
 * @author João Lúcio
 * @since 04/05/2009
 */
var RegistrosFinalizados = Class.create(UsarWorkflow, {

   /**
	 * @constructor
	 */
   initialize : function($super, acoesHistorico) {
	   this.acoesHistorico = acoesHistorico;
   },

   /**
	 * Retorna a tabela da tela inicial.
	 * 
	 * @override
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoVisualizarRegistros");
   },

   /**
	 * Faz a pesquisa dos registros finalizados pelos parâmetros informados.
	 * 
	 * @override
	 */
   pesquisar : function() {
	   var numeroRegistro = dwr.util.getValue("numeroRegistroPesquisa");
	   if (numeroRegistro.length != 0 && numeroRegistro.length != 11) {
		   JanelasComuns.showMessage("Número do registro inválido", false);
		   return;
	   }
	   var dto = {
	      numeroRegistro :numeroRegistro,
	      workflow :dwr.util.getValue("workflowPesquisa")
	   };
	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = UsarWorkflowDWR.pesquisarFinalizados.bind(UsarWorkflowDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_USO_FINALIZADO);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de registros.
	 * 
	 * @override
	 * @param listaRegistros lista de registros retornados
	 */
   popularTabela : function(listaRegistros) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaRegistros.length != 0) {
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
			   return usoWorkflow.workflow.nome;
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   var incio = usoWorkflow.historico[usoWorkflow.historico.length - 1];
			   return getStringTimestamp(incio.dataHora, "dd/MM/yyyy HH:mm:ss");
		   });
		   cellfuncs.push( function(usoWorkflow) {
			   return getStringTimestamp(usoWorkflow.historico[0].dataHora, "dd/MM/yyyy HH:mm:ss");
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnDblClick(this.visualizarRegistro.bind(this));
		   this.tabelaTelaPrincipal.setOnClick(this._habilitarBotaoVisualizarRegistro.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados registros finalizados");
	   }
   },

   /**
	 * Abre o popup para visualizar o registro finalizado.
	 */
   visualizarRegistro : function() {
	   var colunas = this.getTR().descendants();
	   var numeroRegistro = colunas[2].innerHTML;
	   var nomeWorkflow = colunas[3].innerHTML;

	   var id = this.getIdSelecionado();
	   UsarWorkflowDWR.getById(id, ( function(usoWorkflow) {
		   this.setEditarCampos(false);
		   this.setPodeMudarTarefa(false);
		   this._abrePopupUsoDeWorkflow(usoWorkflow, numeroRegistro, nomeWorkflow,
		      usoWorkflow.tarefa.nome, usoWorkflow.tarefa.descricao, usoWorkflow.dataHoraInicio,
		      usoWorkflow.camposUsados);
	   }).bind(this));
   },
   
   /**
	 * Abre popup com anotações do uso do workflow.
	 * @override
	 */
   popupAnotacoes : function() {
	   var url = "usarWorkflow.do?method=popupAnotacao&id=" + $F("idUsoWorkflow")
	   createWindow(250, 450, 280, 70, "Anotações", "divAnotacoes", url, ( function() {
		   this.contaChar();
		   $("anotacao").disable();
	   }).bind(this));
   },
   
   /**
	 * Habilita o botão para visualizar o registro.
	 */
   _habilitarBotaoVisualizarRegistro : function() {
	   $("botaoVisualizarRegistro").onclick = this.visualizarRegistro.bind(this);
   },

   /**
	 * Desabilita os links que podem modificar o registro.
	 * 
	 * @override
	 * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
   habilitarLinks : function(habilita) {
	   $("linkIniciarTarefa").className = "btDesativado";
	   $("linkIniciarTarefa").onclick = Prototype.emptyFunction;
	   $("linkAnotacoes").className = "";
	   $("linkAnotacoes").onclick = this.popupAnotacoes.bind(this);
	   this._habilitaLinkProximasTarefas(false);
   }
});

// passando as ações do histórico para não executar chamada novamente ao servidor
usarWorkflow = new RegistrosFinalizados(usarWorkflow.acoesHistorico);
