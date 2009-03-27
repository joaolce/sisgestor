/**
 * Comportamentos para o UC Manter Processo.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
var ManterProcesso = Class.create();
ManterProcesso.prototype = {

   /**
	 * Tabela com os dados da pesquisa.
	 */
   tabelaTelaPrincipal :null,

   /**
	 * @constructor
	 */
   initialize : function() {},

   /**
	 * Retorna a tabela da tela inicial do caso de uso
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterProcesso");
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
	 * Recupera o id do processo selecionado.
	 * 
	 * @return id do processo selecionado
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do processo selecionado.
	 */
   visualizar : function() {
	   Element.hide("formAtualizarProcesso");
	   var idProcesso = this.getIdSelecionado();
	   if (isNaN(idProcesso)) {
		   return;
	   }
	   ManterProcessoDWR.getById(idProcesso, ( function(processo) {
		   Effect.Appear("formAtualizarProcesso");
		   dwr.util.setValue($("formAtualizarProcesso").id, idProcesso);
		   dwr.util.setValue("nomeProcesso", processo.nome);
		   dwr.util.setValue("descricaoProcesso", processo.descricao);
		   this.habilitarLinks(true);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos processos pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formAtualizarProcesso");
	   this.habilitarLinks(false);
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaProcesso"),
	      descricao :dwr.util.getValue("descricaoPesquisaProcesso"),
	      idWorkflow :dwr.util.getValue("workflow")
	   };

	   if ((this.tabelaTelaPrincipal == null)
	      || (this.tabelaTelaPrincipal.getTabela() != this.getTBodyTelaPrincipal())) {
		   var chamadaRemota = ManterProcessoDWR.pesquisar.bind(ManterProcessoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_PROCESSO);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de processos.
	 * 
	 * @param listaProcesso lista de processos retornados
	 */
   popularTabela : function(listaProcesso) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaProcesso.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(processo) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :processo.id
			   });
		   });
		   cellfuncs.push( function(processo) {
			   return processo.nome;
		   });
		   cellfuncs.push( function(processo) {
			   return processo.descricao;
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados processos");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do processo selecionado.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o processo selecionado?", ( function() {
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o processo selecionado.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o processo selecionado?", ( function() {
		   var idProcesso = dwr.util.getValue($("formAtualizarProcesso").id);
		   requestUtils.simpleRequest("manterProcesso.do?method=excluir&id=" + idProcesso,
		      ( function() {
			      if (requestUtils.status) {
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo processo.
	 */
   popupNovoProcesso : function() {
	   var url = "manterProcesso.do?method=popupNovoProcesso";
	   createWindow(255, 375, 280, 40, "Novo Processo", "divNovoProcesso", url, ( function() {
		   dwr.util.setValue("workflowNovoProcesso", $F("workflowProcesso"));
	   }));
   },

   /**
	 * Abre a janela para definir fluxo dos processos.
	 */
   popupDefinirFluxoProcessos : function() {
	   var url = "manterProcesso.do?method=popupDefinirFluxo";
	   createWindow(486, 840, 280, 40, "Definir Fluxo dos Processos", "divFluxoProcesso", url,
	      ( function() {
		      var idWorkflow = $F("workflowProcesso");
		      dwr.util.setValue("workflowFluxo", idWorkflow);
		      
		      var processosPosteriores;
		      ManterProcessoDWR.temFluxoDefinido(idWorkflow, ( function(temFluxo) {
		    	  fluxo = new DefinirFluxo();
		    	  ManterProcessoDWR.getByWorkflow(idWorkflow, ( function(listaProcessos) {
		    		  //Gera as reprensentações
				      listaProcessos.colecaoParcial.each( function(processo) {
				    	  fluxo.gerarRepresentacao(processo.id, processo.nome, processo.left, processo.top);
				      });
				      
				      //Efetua as ligações
				      listaProcessos.colecaoParcial.each( function(processo) {
				    	  processosPosteriores = processo.transacoesPosteriores;
				    	  if (temFluxo && (processosPosteriores != null) && (processosPosteriores.length > 0) ) {
				    		  processosPosteriores.each( function(processoPosterior){
				    			  fluxo.ligar(processo.id, processoPosterior.posterior.id);
				    		  });
				    	  }
				      });
		    	  }));
		      }));
	      }));
   },

   /**
	 * Envia ao action a ação de salvar o fluxo.
	 */
   salvarFluxo : function() {
	   var form = getForm($("definirFluxoManterProcessoForm"));
	   form.fluxos = fluxo.listaFluxos;
	   form.posicoes = fluxo.obterPosicoes();
	   JanelasComuns.showConfirmDialog("Deseja definir o fluxo criado?", ( function() {
		   requestUtils.simpleRequest("manterProcesso.do?method=salvarFluxo&"
		      + Object.toQueryString(form), ( function() {
			   if (requestUtils.status) {
				   JanelaFactory.fecharJanela("divFluxoProcesso");
			   }
		   }), grafico.processarResposta);
	   }));
   },

   /**
	 * Abre janela para gerenciar os processos.
	 */
   popupGerenciarAtividades : function() {
	   var idProcesso = dwr.util.getValue($("formAtualizarProcesso").id);
	   var nomeProcesso = dwr.util.getValue($("formAtualizarProcesso").nomeProcesso);
	   var url = "manterAtividade.do?method=entrada&processo=" + idProcesso;
	   createWindow(516, 985, 280, 30, "Gerenciar Atividades - " + nomeProcesso,
	      "divGerenciarAtividades", url, ( function() {
		      atividade.pesquisar();
	      }));
   },

   /**
	 * Envia ao action a ação de salvar os dados do processo.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoProcesso");
			   this.pesquisar();
		   }
	   }).bind(this));
   },

   /**
	 * Limita a quantidade de caracteres do campo descrição.
	 */
   contaChar : function(novo) {
	   if (novo) {
		   contaChar($("descricaoNovoProcesso"), 200, "contagemNovoProcesso");
	   } else {
		   contaChar($("descricaoProcesso"), 200, "contagemProcesso");
	   }
   },

   /**
	 * Habilita/desabilita os links, para quando um processo seja selecionado.
	 * 
	 * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
   habilitarLinks : function(habilita) {
	   if (habilita) {
		   $("linkGerenciarAtividades").className = "";
		   $("linkGerenciarAtividades").onclick = this.popupGerenciarAtividades;
	   } else {
		   $("linkGerenciarAtividades").className = "btDesativado";
		   $("linkGerenciarAtividades").onclick = "";
	   }
   }
};

var processo = new ManterProcesso();
