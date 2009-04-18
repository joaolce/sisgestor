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
	 * Tabela com os dados do historico.
	 */
   tabelaAbaHistorico :null,

   /**
	 * Indica se os campos do uso serão editáveis ou não
	 * 
	 * @type Boolean
	 */
   editarCampos :true,

   /**
	 * Define a máscara para campos data
	 * 
	 * @type String
	 */
   mascaraData :"##/##/####",

   /**
	 * Define a máscara para campos hora
	 * 
	 * @type String
	 */
   mascaraHora :"##:##",

   /**
	 * Tipo de campo texto
	 * 
	 * @type String
	 */
   tipoTexto :"TEXTO",

   /**
	 * Tipo de campo data
	 * 
	 * @type String
	 */
   tipoData :"DATA",
   
   /**
	 * Tipo de campo hora
	 * 
	 * @type String
	 */
   tipoHora :"HORA",
   
   /**
	 * Tipo de campo opções
	 * 
	 * @type String
	 */
   tipoOpcoes :"LISTA_DE_OPCOES",

   /**
	 * Indica se houve alteração nos campos da tela
	 * 
	 * @type Boolean
	 */
   houveAlteracao :false,
   
   /**
    * Array com os tipos de ações que podem ocorrer para o histórico do uso.
    *	obs: Cada elemento do array é composto: [0] - id, [1] - name, [2] - descrição
    *
    * @type Array 
    */
   acoesHistorico :null,

   /**
	 * @constructor
	 */
   initialize : function() {
		UsarWorkflowDWR.getAcoesHistorico(( function(acoes) {
		   this.setAcoesHistorico(acoes);
	   }).bind(this));
   },

   /**
    * Retorna o form da página de uso.
	 * 
	 * @return form da página
	 * @type HTMLFormElement
	 */
   getForm : function() {
	   return $("usoWorkflowForm");
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
		   usarWorkflow.setEditarCampos(!(usoWorkflow.dataHoraInicio == null));
		   usarWorkflow._abrePopupUsoDeWorkflow(usoWorkflow, numeroRegistro, nomeWorkflow,
		      usoWorkflow.tarefa.nome, usoWorkflow.tarefa.descricao, usoWorkflow.dataHoraInicio,
		      usoWorkflow.camposUsados);
	   }));
   },

   /**
	 * Seta a variável para editar campos
	 * 
	 * @param {Boolean} indicador para editar os campos
	 */
   setEditarCampos : function(editar) {
	   this.editarCampos = editar;
   },

   /**
	 * Cria um elemento para o campo definido.
	 * 
	 * @param campo Campo
	 */
   getCampo : function(campo) {
	   var tipoCampo = campo.tipo;
	   var defaultValue = "";
	   var mascara = "";
	   var estilo = "";
	   var descricao = "";
	   var tipo;
	   var input;
	   var identificador = "campo" + campo.id;
	   var spanObrigatorio = Builder.node("span");
	   $(spanObrigatorio).className = "obrigatorio";

	   if (!isBlankOrNull(campo.descricao)) {
		   descricao = campo.descricao;
	   }

	   if (campo.obrigatorio) {
		   spanObrigatorio.innerHTML = "&nbsp;*";
	   }

	   if ((tipoCampo == this.tipoTexto) || (tipoCampo == this.tipoData)
	      || (tipoCampo == this.tipoHora)) {
		   tipo = "text";
		   if (tipoCampo == this.tipoData) {
			   mascara = this.mascaraData;
			   estilo = "width: 80px;";
		   } else if (tipoCampo == this.tipoHora) {
			   mascara = this.mascaraHora;
			   estilo = "width: 50px;";
		   } else {
			   estilo = "width: 220px;";
		   }
		   input = Builder.node("input", {
		      type :tipo,
		      value :defaultValue,
		      name :identificador,
		      id :identificador,
		      title :descricao,
		      style :estilo
		   });
		   this.observarAlteracoes(input);
		   if (!usarWorkflow.editarCampos) {
			   $(input).disable();
		   }
		   if (!isBlankOrNull(mascara)) {
			   MaskInput(input, mascara);
		   }
		   return Builder.node("div", [
		      Builder.node("br"),
		      Builder.node("label", [ document.createTextNode(campo.nome), spanObrigatorio,
		         Builder.node("br"), input ]) ]);
	   } else {
		   var elementOpcao;
		   var divOpcao;

		   if (tipoCampo == this.tipoOpcoes) {
			   tipo = "checkbox";
			   idCampo = "ListaOpcoes";
		   } else {
			   tipo = "radio";
			   idCampo = "MultiplaEscolha";
		   }

		   var legenda = Builder.node("legend", {
			   title :descricao
		   });
		   legenda.innerHTML = campo.nome;
		   legenda.appendChild(spanObrigatorio);

		   input = Builder.node("fieldset", {
			   style :"padding: 10px; float:left;"
		   }, [ legenda ]);

		   // Para cada opção do campo, cria um elemento e adiciona ao fieldset
		   campo.opcoes.each(( function(opcao) {
			   divOpcao = Builder.node("div", {
				   style :"margin-top: 2px;"
			   });
			   elementOpcao = Builder.node("input", {
			      type :tipo,
			      value :opcao.valor,
			      name :identificador,
			      id :"opcao" + opcao.id,
			      style :estilo
			   });
			   this.observarAlteracoes(elementOpcao);
			   if (!usarWorkflow.editarCampos) {
				   $(elementOpcao).disable();
			   }
			   divOpcao.appendChild(elementOpcao);
			   divOpcao.appendChild(document.createTextNode(" " + opcao.descricao));
			   divOpcao.appendChild(Builder.node("br"));

			   input.appendChild(divOpcao);
		   }).bind(this));

		   return Builder.node("div", [ Builder.node("br"), input ]);
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
	 * Retorna a tabela da aba historico.
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyAbaHistorico : function() {
	   return $("corpoHistoricoUsarWorkflow");
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
				   this.editarCampos = !(usoWorkflow.dataHoraInicio == null);
				   this._abrePopupUsoDeWorkflow(usoWorkflow, usoWorkflow.numeroRegistro,
				      tarefa.atividade.processo.workflow.nome, tarefa.nome, tarefa.descricao,
				      usoWorkflow.dataHoraInicio, usoWorkflow.camposUsados);
			   }).bind(this));
		   }
	   }).bind(this));
   },

   /**
	 * Envia a requisição para submeter o uso do workflow.
	 */
   confirmar : function() {
	   requestUtils.submitForm(this.getForm(), null, ( function() {
		   if (requestUtils.status) {
			   this.houveAlteracao = false;
			   JanelaFactory.fecharJanela("divUsoWorkflow");
		   }
	   }));
   },

   /**
	 * Abre o popup de uso do workflow.
	 * 
	 * @param {Object} idUso identificador do uso do workflow
	 * @param {String} numeroRegistro número de registro do uso
	 * @param {String} nomeWorkflow nome do workflow em uso
	 * @param {String} nomeTarefa nome da tarefa atual
	 * @param {String} descricaoTarefa descrição da tarefa atual
	 * @param {Date} dataHoraInicio data/hora de início da tarefa atual
	 * @param {Array} Lista dos campos usados
	 */
   _abrePopupUsoDeWorkflow : function(usoWorkflow, numeroRegistro, nomeWorkflow, nomeTarefa,
      descricaoTarefa, dataHoraInicio, listaCamposUsados) {
	   var tituloPagina = numeroRegistro + " - " + nomeWorkflow;

	   var url = "usarWorkflow.do?method=popupUsoWorkflow";
	   var janela = createWindow(536, 985, 280, 10, tituloPagina, "divUsoWorkflow", url,
	      ( function() {
		      FactoryAbas.getNewAba("tabCamposAncora,tabCampos;tabHistoricoAncora,tabHistorico");
		      dwr.util.setValue("idUsoWorkflow", usoWorkflow.id);
		      dwr.util.setValue("dataHoraInicioTarefa", getStringTimestamp(dataHoraInicio));
		      dwr.util.setValue("nomeTarefa", nomeTarefa);
		      dwr.util.setValue("descricaoTarefa", descricaoTarefa);
		      this.carregarCampos(listaCamposUsados);
		      this.habilitarLinkProximaTarefa(this.editarCampos);
		      this.carregaHistorico(usoWorkflow);
	      }).bind(this));
	   janela.removerBotaoFechar();
   },

   /**
	 * Faz a observação das alterações feitas no campo.
	 * 
	 * @param campo campo a ser observado
	 */
   observarAlteracoes : function(campo) {
	   var callback = ( function() {
		   this.houveAlteracao = true;
	   }).bind(this);

	   observarAlteracao(campo, callback);
   },

   /**
	 * Verifica se houve alteração na tela e pergunta se o usuário deseja salvar as alterações.
	 */
   salvarAntesSair : function() {
	   if (this.houveAlteracao) {
		   JanelasComuns.showConfirmCancelDialog("Deseja salvar as alterações?", this.confirmar.bind(this), this.sairSemSalvar.bind(this));
	   } else {
		   JanelaFactory.fecharJanelaAtiva();
	   }
   },

   /**
	 * Carrega os campos do workflow na aba Campos.
	 * 
	 * @param {Array} Lista dos campos usados
	 */
   carregarCampos : function(listaCamposUsados) {
	   var idUsoWorkflow = dwr.util.getValue("idUsoWorkflow");
	   var divCampo;
	   var div1 = $("div1");
	   var div2 = $("div2");
	   var div3 = $("div3");
	   var div4 = $("div4");
	   var aux = 0;
	   var resto = 0;

	   UsarWorkflowDWR.getCamposByIdUsoWorkflow(idUsoWorkflow, ( function(listaCampos) {
		   listaCampos.each(( function(campo) {
			   divCampo = this.getCampo(campo);
			   if (resto == 0) {
				   div1.appendChild(divCampo);
			   } else if (resto == 1) {
				   div2.appendChild(divCampo);
			   } else if (resto == 2) {
				   div3.appendChild(divCampo);
			   } else {
				   div4.appendChild(divCampo);
			   }
			   aux++;
			   resto = aux % 4;
		   }).bind(this));
		   this.preencherCampos(listaCamposUsados);
	   }).bind(this));
   },

   /**
	 * Seta os valores do campo na página
	 * 
	 * @param {Array} Lista dos campos usados
	 */
   preencherCampos : function(listaCamposUsados) {
	   var tipoCampo;
	   var valorCampo;

	   // Caso a lista estiver vazia, nem executa
	   listaCamposUsados.each(( function(campoUsoWorkflow) {
		   tipoCampo = campoUsoWorkflow.campo.tipo;
		   valorCampo = campoUsoWorkflow.valor;
		   if ((tipoCampo == usarWorkflow.tipoTexto) || (tipoCampo == usarWorkflow.tipoData)
		      || (tipoCampo == usarWorkflow.tipoHora)) {
			   $("tabCampos").select("input[type=\"text\"]").each( function(input) {
				   if (parseInt($(input).id.substring(5)) == campoUsoWorkflow.campo.id) {
					   $(input).value = valorCampo;
				   }
			   });
		   } else {
			   if (tipoCampo == usarWorkflow.tipoOpcoes) {
				   $("tabCampos").select("input[type=\"checkbox\"]").each( function(input) {
					   if (( (parseInt($(input).name.substring(5))) == campoUsoWorkflow.campo.id) && 
							   (valorCampo.indexOf($(input).value) != -1)) {
						   $(input).checked = true;
					   }
				   });
			   } else {
				   $("tabCampos").select("input[type=\"radio\"]").each( function(input) {
					   if (( (parseInt($(input).name.substring(5))) == campoUsoWorkflow.campo.id) && 
							   (valorCampo.indexOf($(input).value) != -1)) {
						   $(input).checked = true;
					   }
				   });
			   }
		   }
	}));
   },
   /**
	 * Carrega todo o histórico do uso.
	 * 
	 * @param {Object} usoWorkflow uso do workflow
	 */
   carregaHistorico : function(usoWorkflow) {
	   if ((this.tabelaAbaHistorico == null)
	      || (this.tabelaAbaHistorico.getTabela() != this.getTBodyAbaHistorico())) {
		   this.tabelaAbaHistorico = FactoryTabelas.getNewTabela(this.getTBodyAbaHistorico());
		   this.popularHistorico(usoWorkflow.historico);
	   }
   },

   /**
	 * Popula a tabela de histórico com a lista de historicos.
	 * 
	 * @param {Array} listaHistorico lista de histórico
	 */
   popularHistorico : function(listaHistorico) {
   	if(Object.isUndefined(listaHistorico)) {
   		dwr.engine.setAsync(false);
   	   UsarWorkflowDWR.getHistorico($F("idUsoWorkflow"), ( function(historicoAtualizado) {
   	   	listaHistorico = historicoAtualizado;
   	   }));
   	   dwr.engine.setAsync(true);
   	}
	   this.tabelaAbaHistorico.removerResultado();

	   var cellfuncs = new Array();
	   cellfuncs.push( function(historico) {
		   return Builder.node("input", {
		      type :"hidden",
		      name :"idHora",
		      value :historico.dataHora
		   });
	   });
	   cellfuncs.push( function(historico) {
		   return getStringTimestamp(historico.dataHora);
	   });
	   cellfuncs.push( function(historico) {
		   return "usuario";
	   });
	   cellfuncs.push( (function(historico) {
		   return this.getDescricaoAcao(historico.acao);
	   }).bind(this));
	   this.tabelaAbaHistorico.adicionarResultadoTabela(cellfuncs, listaHistorico);
   },
   
   /**
	 * Habilita/desabilita os links se a tarefa está pendente para iniciar.
	 * 
	 * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
   habilitarLinkProximaTarefa : function(habilita) {
	   if (habilita) {
		   // se habilita link de próximas tarefas, desabilita o de iniciar
	   	$("linkIniciarTarefa").className = "btDesativado";
	   	$("linkIniciarTarefa").onclick = "";
	   	$("linkProximasTarefa").className = "";
	   	$("linkProximasTarefa").onclick = Prototype.emptyFunction;
	   } else {
	   	$("linkIniciarTarefa").className = "";
	   	$("linkIniciarTarefa").onclick = this.iniciarTarefa;
	   	$("linkProximasTarefa").className = "btDesativado";
	   	$("linkProximasTarefa").onclick = "";
	   }
   },

   /**
	 * Inicia a tarefa aberta.
	 */
   iniciarTarefa : function() {
	   requestUtils.simpleRequest("usarWorkflow.do?method=iniciarTarefa&id=" + $F("idUsoWorkflow"),
	      ( function() {
		      if (requestUtils.status) {
			      requestUtils.valoresDevolvidos.each(( function(data) {
				      dwr.util.setValue("dataHoraInicioTarefa", data.value);
			      }));
			      usarWorkflow.habilitarCampos();
			      usarWorkflow.habilitarLinkProximaTarefa(true);
			      usarWorkflow.pesquisar();
		      }
	      }));
   },

   /**
	 * Habilita os campos para serem editáveis.
	 */
   habilitarCampos : function() {
	   $("tabCampos").select("input[type=\"text\"]").each( function(input) {
		   $(input).enable();
	   });
	   $("tabCampos").select("input[type=\"radio\"]").each( function(input) {
		   $(input).enable();
	   });
	   $("tabCampos").select("input[type=\"checkbox\"]").each( function(input) {
		   $(input).enable();
	   });
   },
   
   /**
    * Atribui as ações possíveis para o histórico do uso.
    * 
    * @param {Array} acoes ações disponíveis
    */
   setAcoesHistorico : function(acoes) {
   	this.acoesHistorico = acoes;
   },
   
   /**
    * Recupera a descrição da ação ocorrida
    * 
    * @param {String} acao name da ação da enum
    * @return Descrição da ação ocorrida
    */
   getDescricaoAcao : function(acao){
	   var acaoOcorrida;
	   this.acoesHistorico.each(( function(acaoHistorico) {
		   if (acaoHistorico[1] == acao) { // verifica pelo name da enum
			   acaoOcorrida = acaoHistorico[2];
			   throw $break;
		   }
	   }));
	   return acaoOcorrida;
   },
   
   /**
    * Executado quando o usuário não quer salvar as alterações.
    */
   sairSemSalvar : function(){
   	this.houveAlteracao = false;
   	JanelaFactory.fecharJanela("divUsoWorkflow");
   }
};

var usarWorkflow = new UsarWorkflow();
