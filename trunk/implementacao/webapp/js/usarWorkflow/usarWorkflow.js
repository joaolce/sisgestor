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
    * Imagem que contém o calendário.
    * 
    * @type String
    */
   imagemCalendario : "imagens/calendar.gif",
   
   /**
    * Array com os links para os campos de data.
    * obs: [0] id do campo de data, [1] id da trigger de data.
    * 
    * @type Array
    */
   camposData : new Array(),

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
     * Abre popup com anotação do uso workflow 
     */
   popupAnotacao : function() {
	   JanelasComuns.showInformation("Implemente-me");
   },

   /**
	 * Inicia o workflow selecionado no combo
	 */
   popupIniciarWorkflow : function() {
	   var url = "usarWorkflow.do?method=popupIniciarWorkflow";
	   createWindow(115, 330, 280, 70, "Iniciar Workflow", "divIniciarWorkflow", url);
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
	   var colunas = this.getTR().descendants();
	   var numeroRegistro = colunas[2].innerHTML;
	   var nomeWorkflow = colunas[3].innerHTML;

	   var id = this.getIdSelecionado();
	   UsarWorkflowDWR.getById(id, ( function(usoWorkflow) {
		   this.setEditarCampos(!(usoWorkflow.dataHoraInicio == null));
		   this._abrePopupUsoDeWorkflow(usoWorkflow, numeroRegistro, nomeWorkflow,
		      usoWorkflow.tarefa.nome, usoWorkflow.tarefa.descricao, usoWorkflow.dataHoraInicio,
		      usoWorkflow.camposUsados);
	   }).bind(this));
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
	 * @param {Object} Campo
	 * @return {Object} Elemento a ser adicionado na página
	 */
   _getCampo : function(campo) {
	   var tipoCampo = campo.tipo;
	   var descricao = "";
	   var tipo;
	   var input;
	   var identificador = "campo" + campo.id;
	   var spanObrigatorio = this._getSpanObrigatorio(campo.obrigatorio);
	   if (!isBlankOrNull(campo.descricao)) {
		   descricao = campo.descricao;
	   }

	   if (!this._isListaOpcoesOrMultiplaEscolha(tipoCampo)) {
	   	var mascara;
	   	var estilo;
	   	var maxLength;
	   	var calendario = null;
		   tipo = "text";
		   if (tipoCampo == this.tipoData) {
			   mascara = this.mascaraData;
			   estilo = "width: 70px;";
			   maxLength = 10;
			   calendario = Builder.node("img", {
			      id :"trigger-" + identificador,
			      src :this.imagemCalendario,
			      CLASS: "calendar-trigger",
			      title :"Calendário",
			      alt :"Calendário"
			   });
		   } else if (tipoCampo == this.tipoHora) {
			   mascara = this.mascaraHora;
			   estilo = "width: 40px;";
			   maxLength = 5;
		   } else {
		   	mascara = "";
			   estilo = "width: 220px;";
			   maxLength = 100;
		   }
		   input = Builder.node("input", {
		      type :tipo,
		      name :identificador,
		      id :identificador,
		      title :descricao,
		      style :estilo,
		      maxlength :maxLength
		   });
		   observarAlteracao(input, this.aposMudancaEmCampo.bind(this));
		   if (!usarWorkflow.editarCampos) {
			   $(input).disable();
		   }
		   if (!isBlankOrNull(mascara)) {
			   MaskInput(input, mascara);
		   }
		   var divCampo = Builder.node("div", [
		                  Builder.node("br"),
		                  Builder.node("label", [ 
		                          document.createTextNode(campo.nome), 
		                          spanObrigatorio,
		                          Builder.node("br"), 
		                          input ]) ]);
		   if (calendario != null) {
		   	$(input).className = "data";
			   divCampo.appendChild(calendario);
			   this.camposData.push(new Array(identificador, calendario.id));
		   }
		   return divCampo;
	   } else {
		   var elementOpcao;
		   var divOpcao;

		   if (tipoCampo == this.tipoOpcoes) {
			   tipo = "checkbox";
		   } else {
			   tipo = "radio";
		   }

		   var legenda = Builder.node("legend", {
			   title :descricao
		   }, [ document.createTextNode(campo.nome), spanObrigatorio ]);

		   input = Builder.node("fieldset", {
			   style :"padding: 10px; float:left;"
		   }, [ legenda ]);

		   // Para cada opção do campo, cria um elemento e adiciona ao fieldset
		   campo.opcoes.each(( function(opcao) {
			   divOpcao = Builder.node("div", {style :"margin-top: 2px;"});
			   elementOpcao = Builder.node("input", {
			      type :tipo,
			      value :opcao.valor,
			      name :identificador,
			      id :"opcao" + opcao.id
			   });
			   observarAlteracao(elementOpcao, this.aposMudancaEmCampo.bind(this));
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
			   this.tabelaTelaPrincipal.setOnDblClick(this.usarWorkflow.bind(usarWorkflow));
			   this.tabelaTelaPrincipal.setOnClick(this.habilitarBotaoUsarTarefa.bind(usarWorkflow));
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
	   var idUsoWorkflow = dwr.util.getValue("idUsoWorkflow");
	   var valor = this._getValoresCampos();
	   if(this.houveAlteracao) {
	   	requestUtils.simpleRequest("usarWorkflow.do?method=confirmar&id=" + idUsoWorkflow + valor, ( function() {
	   		if (requestUtils.status) {
	   			this.houveAlteracao = false;
	   			this.camposData = new Array();
	   			JanelaFactory.fecharJanela("divUsoWorkflow");
	   		}
	   	}).bind(this));
	   }
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
		      dwr.util.setValue("dataHoraInicioTarefa", getStringTimestamp(dataHoraInicio, "dd/MM/yyyy HH:mm:ss"));
		      dwr.util.setValue("nomeTarefa", nomeTarefa);
		      dwr.util.setValue("descricaoTarefa", descricaoTarefa);
		      this.carregarCampos(listaCamposUsados);
		      this.habilitarLinks(this.editarCampos);
		      this.carregarHistorico(usoWorkflow);
	      }).bind(this));
	   janela.removerBotaoFechar();
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
			   divCampo = this._getCampo(campo);
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
		   this._preencherCampos(listaCamposUsados);
		   if(this.editarCampos) {
		   	this._adicionarLinksDatas();
		   }
	   }).bind(this));
   },

   /**
	 * Seta os valores do campo na página.
	 * 
	 * @param {Array} Lista dos campos usados
	 */
   _preencherCampos : function(listaCamposUsados) {
	   var tipoCampo;

	   // Caso a lista estiver vazia, nem executa
	   listaCamposUsados.each((function(campoUsoWorkflow) {
		   tipoCampo = campoUsoWorkflow.campo.tipo;
		   if (!usarWorkflow._isListaOpcoesOrMultiplaEscolha(tipoCampo)) {
			   usarWorkflow._preencherCampo(campoUsoWorkflow.campo.id, campoUsoWorkflow.valor);
		   } else {
			   usarWorkflow._preencherCampoComOpcoes(campoUsoWorkflow.campo.id, campoUsoWorkflow.valor, tipoCampo);
		   }
	   }));
   },
   
   /**
	 * Carrega todo o histórico do uso.
	 * 
	 * @param {Object} usoWorkflow uso do workflow
	 */
   carregarHistorico : function(usoWorkflow) {
	   if ((this.tabelaAbaHistorico == null)
	      || (this.tabelaAbaHistorico.getTabela() != this.getTBodyAbaHistorico())) {
		   this.tabelaAbaHistorico = FactoryTabelas.getNewTabela(this.getTBodyAbaHistorico());
		   this.popularHistorico(usoWorkflow.historico);
	   }
   },

   /**
	 * Popula a tabela de histórico com a lista de históricos.
	 * 
	 * @param {Array} listaHistorico lista de histórico (opcional)
	 */
   popularHistorico : function(listaHistorico) {
   	if(Object.isUndefined(listaHistorico)) {
   	   UsarWorkflowDWR.getHistorico($F("idUsoWorkflow"), ( function(historicoAtualizado) {
   	   	usarWorkflow._preencherHistorico(historicoAtualizado);
   	   }));
   	} else {
   		usarWorkflow._preencherHistorico(listaHistorico);
   	}
   },
   
   /**
    * Preenche a tabela de histórico do uso.
    * 
    * @param {Array} listaHistorico lista de histórico
    */
   _preencherHistorico : function(listaHistorico) {
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
		   return getStringTimestamp(historico.dataHora, "dd/MM/yyyy HH:mm:ss");
	   });
	   cellfuncs.push( function(historico) {
		   return historico.usuario.nome;
	   });
	   cellfuncs.push( (function(historico) {
		   return this.getDescricaoAcao(historico.acao);
	   }).bind(this));
	   this.tabelaAbaHistorico.adicionarResultadoTabela(cellfuncs, listaHistorico);
   },
   
   /**
	 * Habilita/desabilita os links caso a tarefa esteja pendente para iniciar.
	 * 
	 * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
	habilitarLinks : function(habilita) {
		if (habilita) {
			// se habilita link de próximas tarefas, desabilita o de iniciar
			$("linkIniciarTarefa").className = "btDesativado";
			$("linkIniciarTarefa").onclick = Prototype.emptyFunction;
			$("linkProximasTarefa").className = "";
			$("linkProximasTarefa").onclick = this.popupProximasTarefas;
			$("linkAnotacao").className = "";
			$("linkAnotacao").onclick = this.popupAnotacao;
		} else {
			$("linkIniciarTarefa").className = "";
			$("linkIniciarTarefa").onclick = this.iniciarTarefa;
			$("linkProximasTarefa").className = "btDesativado";
			$("linkProximasTarefa").onclick = Prototype.emptyFunction;
		}
	},
   
   /**
	 * Habilita o botão para uso da tarefa
	 */
	habilitarBotaoUsarTarefa : function() {
		$("botaoUsarTarefa").onclick = this.usarWorkflow.bind(usarWorkflow);
	},

   /**
	 * Inicia a tarefa aberta.
	 */
   iniciarTarefa : function() {
   	var idUso = $F("idUsoWorkflow");
   	requestUtils.simpleRequest("usarWorkflow.do?method=iniciarTarefa&id=" + idUso, ( function() {
   		if (requestUtils.status) {
   			requestUtils.valoresDevolvidos.each(( function(data) {
   				dwr.util.setValue("dataHoraInicioTarefa", data.value);
   			}));
   			usarWorkflow.popularHistorico();
   			usarWorkflow.habilitarCampos();
   			usarWorkflow.habilitarLinks(true);
   			usarWorkflow.pesquisar();
   			usarWorkflow._adicionarLinksDatas();
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
	 * Recupera os ids dos campos com seus valores.
	 * 
	 * @return {String} valores
	 */
	_getValoresCampos : function() {
	   var valores = "&";
	   valores +=this._getValoresTexto();
	   valores +=this._getValoresRadio();
	   valores +=this._getValoresCheckBox();
	   
	   return valores;
	},
	
	/**
	  * Recupera os ids dos campos do tipo texto com seus respectivos valores.
	  * 
	  * @return {String} valores
	  */
	_getValoresTexto : function() {
		var valor = "";
		$("tabCampos").select("input[type=\"text\"]").each(function(input) {
			valor += "valor=" + $(input).id + "£" + $(input).value + "&";  
		});
		return valor;
	},
	
	/**
	  * Recupera os ids dos campos do tipo radio com seus respectivos valores.
	  * 
	  * @return {String} valores
	  */
	_getValoresRadio : function() {
		var valor = "";
		$("tabCampos").select("input[type=\"radio\"]").each(function(input) {
			valor += "valor=" + $(input).name + "£" + $(input).value + "£" + $(input).checked + "&";  
		});
		return valor;
	},	
	
	/**
	 * Recupera os ids dos campos do tipo checkbox com seus respectivos valores.
	 * 
	 * @return {String} valores
	 */
	_getValoresCheckBox : function() {
		var valor = "";
		$("tabCampos").select("input[type=\"checkbox\"]").each(function(input) {
			valor += "valor=" + $(input).name + "£" + $(input).value + "£" + $(input).checked + "&";  
		});
		return valor;
	},	
   
   /**
	 * Verifica se o campo é do tipo lista de opções ou múltipla escolha.
	 * 
	 * @param {String} tipoCampo tipo do campo
	 * @return <code>true</code>, se for lista de opções ou múltipla escolha;
	 *         <code>false</code>, se for texto, data ou hora
	 */
	_isListaOpcoesOrMultiplaEscolha : function(tipoCampo) {
	   if ((tipoCampo == usarWorkflow.tipoTexto) || (tipoCampo == usarWorkflow.tipoData)
	      || (tipoCampo == usarWorkflow.tipoHora)) {
		   return false;
	   }
	   return true;
	},
	
	/**
	 * Preenche os campos do tipo data, hora e texto.
	 * 
	 * @param {Number} idCampo Código identificador do campo
	 * @param {String} valor Valor a ser setado. 
	 */
	_preencherCampo : function(idCampo, valor) {
		$("tabCampos").select("input[type=\"text\"]").each(function(input) {
			if (parseInt($(input).id.substring(5)) == idCampo) { // retira o "campo" do id html
				$(input).value = valor;
				throw $break;
			}
		});
	},
	
	/**
	 * Preenche os campos de múltipla escolha e lista de opções.
	 * 
	 * @param {Number} idCampo Código identificador do campo
	 * @param {String} valor Valor a ser setado. 
	 * @param {String} tipoCampo Tipo do campo a ser setado 
	 * 
	 * Obs.: Nesse caso, o valor do campo conterá quais opções deverão estar marcadas (checked)  
	 */
	_preencherCampoComOpcoes : function(idCampo, valor, tipoCampo) {
		if (tipoCampo == usarWorkflow.tipoOpcoes) {
			$("tabCampos").select("input[type=\"checkbox\"]").each(function(input) {
				//Se o valor do campo está contido em algum value do input, deve ser setado
				if (((parseInt($(input).name.substring(5))) == idCampo) //retira o "campo" do name html
						&& (valor.indexOf($(input).value) != -1)) {
					$(input).checked = true;
				}
			});
		} else {
			$("tabCampos").select("input[type=\"radio\"]").each(function(input) {
				//Se o valor do campo está contido em algum value do input, deve ser setado
				if (((parseInt($(input).name.substring(5))) == idCampo) //retira o "campo" do name html
						&& (valor.indexOf($(input).value) != -1)) {
					$(input).checked = true;
				}
			});
		}
	},
   
   /**
	 * Executado quando o usuário não quer salvar as alterações.
	 */
   sairSemSalvar : function() {
	   this.houveAlteracao = false;
	   this.camposData = new Array();
	   JanelaFactory.fecharJanela("divUsoWorkflow");
   },

   /**
	 * Abre o popup das próximas tarefas disponíveis.
	 */
   popupProximasTarefas : function() {
	   var idUso = $F("idUsoWorkflow");
	   var url = "usarWorkflow.do?method=popupProximasTarefas&id=" + idUso;
	   createWindow(115, 375, 280, 70, "Próxima Tarefa", "divProximasTarefas", url, ( function() {
		   dwr.util.setValue("idUsoWorkflowProximaTarefa", idUso);
	   }));
   },

   /**
	 * Muda a tarefa atual do uso do workflow.
	 * 
	 * @param form formulário submetido
	 */
   proximaTarefa : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   this.pesquisar();
		   }
	   }).bind(this), ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanelaAtiva(); // fecha janela da mudança de tarefa
			   JanelaFactory.fecharJanelaAtiva(); // fecha janela do uso
		   }
	   }));
   },
   
   /**
	 * Recupera o span de campo obrigatório.
	 * 
	 * @param {Boolean} obrigatorio caso o campo seja obrigatório
	 * @return span criado
	 */
   _getSpanObrigatorio : function(obrigatorio) {
	   var span = Builder.node("span", {
		   CLASS :"obrigatorio"
	   });
	   if (obrigatorio) {
		   span.innerHTML = "&nbsp;*";
	   }
	   return span;
   },
   
   /**
    * Executado após cada mudança em algum campo do uso.
    */
   aposMudancaEmCampo : function() {
	   this.houveAlteracao = true;
	   $("botaoSalvarUso").enable();
   },

   /**
	 * Adiciona os eventos aos links de campos de datas.
	 */
   _adicionarLinksDatas : function() {
	   this.camposData.each(( function(campoData) {
		   Calendar.registerDateCalendar(campoData[0], campoData[1], this.aposMudancaEmCampo
		      .bind(this));
	   }).bind(this));
	   this.camposData = new Array();
   }
};

var usarWorkflow = new UsarWorkflow();
