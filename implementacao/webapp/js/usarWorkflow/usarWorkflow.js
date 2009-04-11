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
	 * Faz o uso do workflow.
	 */
   usarWorkflow : function() {
	   var colunas = usarWorkflow.getTR().descendants();
	   var numeroRegistro = colunas[2].innerHTML;
	   var nomeWorkflow = colunas[3].innerHTML;

	   var id = usarWorkflow.getIdSelecionado();
	   UsarWorkflowDWR.getById(id, ( function(usoWorkflow) {
		   usarWorkflow._abrePopupUsoDeWorkflow(id, numeroRegistro, nomeWorkflow,
		      usoWorkflow.tarefa.nome, usoWorkflow.tarefa.descricao, usoWorkflow.dataHoraInicio);
	   }));
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
		   if (Usuario.temPermissao(USAR_WORKFLOW)) {
			   this.tabelaTelaPrincipal.setOnDblClick(this.usarWorkflow);
		   }
	   } else {
		   this.tabelaTelaPrincipal
		      .semRegistros("Não foram encontradas tarefas pendentes de sua responsabilidade");
	   }
   },

   /**
	 * Inicializa um uso do workflow.
	 * 
	 * @param form formulário submetido
	 */
   iniciarUso : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   this.pesquisar();
			   var idUso = requestUtils.generatedId;
			   UsarWorkflowDWR.getById(idUso, ( function(usoWorkflow) {
				   var tarefa = usoWorkflow.tarefa;
				   JanelaFactory.fecharJanela("divIniciarWorkflow");
				   this._abrePopupUsoDeWorkflow(idUso, usoWorkflow.numeroRegistro,
				      tarefa.atividade.processo.workflow.nome, tarefa.nome, tarefa.descricao,
				      usoWorkflow.dataHoraInicio);
			   }).bind(this));
		   }
	   }).bind(this));
   },

   /**
	 * Envia a requisição para submeter o uso do workflow.
	 * 
	 * @param form formulário submetido
	 */
   confirmar : function(form) {

   },

   /**
	 * Abre o popup de uso do workflow.
	 * 
	 * @param {Number} idUso identificador do uso do workflow
	 * @param {String} numeroRegistro número de registro do uso
	 * @param {String} nomeWorkflow nome do workflow em uso
	 * @param {String} nomeTarefa nome da tarefa atual
	 * @param {String} descricaoTarefa descrição da tarefa atual
	 * @param {Date} dataHoraInicio data/hora de início da tarefa atual
	 */
   _abrePopupUsoDeWorkflow : function(idUso, numeroRegistro, nomeWorkflow, nomeTarefa,
      descricaoTarefa, dataHoraInicio) {
	   var tituloPagina = numeroRegistro + " - " + nomeWorkflow;

	   var url = "usarWorkflow.do?method=popupUsoWorkflow";
	   createWindow(536, 985, 280, 10, tituloPagina, "divUsoWorkflow", url, ( function() {
		   FactoryAbas.getNewAba("tabCamposAncora,tabCampos;tabHistoricoAncora,tabHistorico");
		   dwr.util.setValue("idUsoWorkflow", idUso);
		   dwr.util.setValue("dataHoraInicioTarefa", getStringTimestamp(dataHoraInicio));
		   dwr.util.setValue("nomeTarefa", nomeTarefa);
		   dwr.util.setValue("descricaoTarefa", descricaoTarefa);
	   }).bind(this));
   }
};

var usarWorkflow = new UsarWorkflow();
