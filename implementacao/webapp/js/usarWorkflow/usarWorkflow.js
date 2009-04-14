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
     * Cria um elemento para o campo definido
     * 
     * @param campo Campo
     */
  getCampo : function(campo) {
	   var tipoCampo = campo.tipo;
	   var defaultValue = "";
	   var mascara = "";
	   var estilo = "";
	   var tipo;
	   var input;
	   
	   if (tipoCampo == "TEXTO" || tipoCampo == "DATA" || tipoCampo == "HORA" ) {
		   tipo = "text";
		   estilo = "width: 250px;";
		   if (tipoCampo == "DATA") {
			   mascara = "##/##/####";
			   estilo = "width: 80px;";
		   } else {
			   if (tipoCampo == "HORA") {
				   mascara = "##:##";
				   estilo = "width: 50px;";
			   }
		   }
		   input = $(Builder.node("input", {type: tipo, value: defaultValue, id: campo.id, title: campo.descricao, style: estilo}));
		   if (!isBlankOrNull(mascara)){
			   MaskInput(input, mascara);   
		   }
		   return Builder.node("div", [
		                   		    Builder.node("br"),
		                			Builder.node("label", {htmlFor: campo.id}, [
		                				document.createTextNode(campo.nome),
		                				Builder.node("br"),
		                				input
		                			])
		   ]);
	   } else {
		   var idCampo;
		   var elementOpcao;
		   var classNome;
		   
		   if (tipoCampo == "LISTA_DE_OPCOES") {
			   tipo = "checkbox";
			   idCampo = "ListaOpcoes";
			   classNome = "";
		   } else {
			   tipo = "radio";
			   idCampo = "MultiplaEscolha";
			   classNome = "radioInput";
		   }
		   var legenda = Builder.node("legend");
		   legenda.innerHTML = campo.nome;
		   input = $(Builder.node("fieldset", {id: "fieldset" + idCampo, style: "padding: 10px; float:left;"}, [legenda]));
		   
		   //Para cada opção do campo, cria um elemento e adiciona ao fieldset
		   campo.opcoes.each((function(opcao){
			   elementOpcao = $(Builder.node("input", {type: tipo, value: opcao.valor, id: opcao.id, style: estilo, className :classNome}));
			   input.appendChild(elementOpcao);
			   input.appendChild(document.createTextNode(opcao.descricao));
			   input.appendChild($(Builder.node("br")));
		   }));
		   
		   return Builder.node("div", [
              Builder.node("br"),
              input
		   ]);
	   }
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
		   this.carregarCampos();
	   }).bind(this));
   },
   
   /**
    * Carrega os campos do workflow na aba Campos 
    */
  carregarCampos : function(){
	   var idUsoWorkflow = dwr.util.getValue("idUsoWorkflow");
	   var divCampo;
	   var div1 = Builder.node("div", {id: "div1", style: "padding: 10px; float:left; "});
	   var div2 = Builder.node("div", {id: "div2", style: "padding: 10px; float:left; "});
	   var div3 = Builder.node("div", {id: "div3", style: "padding: 10px; float:left; "});
	   var div4 = Builder.node("div", {id: "div4", style: "padding: 10px; float:left; "});
	   var aux = 0;
	   var resto = 0;
	   
	  UsarWorkflowDWR.getCamposByIdUsoWorkflow(idUsoWorkflow, ( function(listaCampos) {
		 listaCampos.each( (function(campo) {
			 divCampo = this.getCampo(campo);
			 if (resto == 0) {
				 div1.appendChild(divCampo); 
			 } else {
				 if (resto == 1) {
					 div2.appendChild(divCampo); 
				 } else {
					 if (resto ==3 ) {
						 div3.appendChild(divCampo); 
					 } else {
						 div4.appendChild(divCampo); 
					 }
				 }
			 }
			 aux++;
			 resto = aux % 4;
		 }).bind(this));
		 if (listaCampos.size != 0) {
			 $("tabCampos").appendChild(div1);
			 $("tabCampos").appendChild(div2);
			 $("tabCampos").appendChild(div3);
			 $("tabCampos").appendChild(div4);
		 }
	  }).bind(this));
  }
};

var usarWorkflow = new UsarWorkflow();
