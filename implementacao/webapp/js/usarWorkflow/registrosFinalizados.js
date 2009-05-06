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
usarWorkflow = Object.extend(UsarWorkflow.prototype, {

   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * Retorna a tabela da tela inicial.
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoVisualizarRegistros");
   },

   /**
	 * Faz a pesquisa dos registros finalizados pelos parâmetros informados.
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
		   this.tabelaTelaPrincipal.setOnClick(this.habilitarBotaoVisualizarRegistro.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados registros finalizados");
	   }
   },

   /**
	 * 
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
	 * Habilita o botão para visualizar o registro.
	 */
   habilitarBotaoVisualizarRegistro : function() {
	   $("botaoVisualizarRegistro").onclick = this.visualizarRegistro.bind(this);
   }
});
