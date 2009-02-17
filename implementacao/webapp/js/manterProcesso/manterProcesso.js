/**
 * Comportamentos para o UC Manter Processo.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
var ManterProcesso = Class.create();
ManterProcesso.prototype = {
   /**
	 * @constructor
	 */
   initialize : function() {},

   tabelaTelaPrincipal :null,

   /**
	 * Retorna a tabela da tela inicial do caso de uso
	 * 
	 * @return {HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterProcesso");
   },

   /**
	 * Recupera o form manterProcessoForm.
	 * 
	 * @return form do manter processo
	 */
   getForm : function() {
	   return $("manterProcessoForm");
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
	   Element.hide("formSalvarProcesso");
	   var idProcesso = this.getIdSelecionado();
	   if (isNaN(idProcesso)) {
		   return;
	   }
	   ManterProcessoDWR.getById(idProcesso, ( function(processo) {
		   Effect.Appear("formSalvarProcesso");
		   dwr.util.setValue($("formSalvarProcesso").id, idProcesso);
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
	   Effect.Fade("formSalvarProcesso");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaProcesso"),
	      descricao :dwr.util.getValue("descricaoPesquisaProcesso"),
	      idWorkflow :dwr.util.getValue("workflow")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterProcessoDWR.pesquisar.bind(ManterProcessoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA-3);
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
		   requestUtils.submitForm(form, null, ( function() {
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
		   var idProcesso = dwr.util.getValue($("formSalvarProcesso").id);
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
	   createWindow(255, 375, 280, 40, "Novo Processo", "divNovoProcesso", url, (function(){
		   dwr.util.setValue("idWorkflow",$F("workflow"));
	   }));
   },
   
   /**
    * Abre janela para gerenciar os processos
    * */
   popupGerenciarAtividades :function(){
	   var idProcesso = dwr.util.getValue($("formSalvarProcesso").id);
	   var url = "manterAtividade.do?method=entrada&processo="+idProcesso;
	   createWindow(536, 985, 280, 10, "Gerenciar Atividades", "divGerenciarAtividades", url, (function(){
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
   contaChar: function(novo) {
	   if(novo) {
		   contaChar($("descricaoNovo"), 200, "contagemNovo");
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
		if(habilita) {
			$("linkGerenciarAtividades").className = "";
			$("linkGerenciarAtividades").onclick = this.popupGerenciarAtividades;
		} else {
			$("linkGerenciarAtividades").className = "btDesativado";
			$("linkGerenciarAtividades").onclick = "";
		}
	}
};

var processo = new ManterProcesso();
