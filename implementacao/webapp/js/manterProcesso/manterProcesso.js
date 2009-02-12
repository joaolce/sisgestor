/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	processo.pesquisar();
});

/**
 * Comportamentos para o UC Manter Processo.
 * 
 * @author Thiago
 * @since 09/02/2009
 */
var ComportamentosTela = Class.create();
ComportamentosTela.prototype = {
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
	   Element.hide("formSalvar");
	   var idProcesso = this.getIdSelecionado();
	   if (isNaN(idProcesso)) {
		   return;
	   }
	   ManterProcessoDWR.getById(idProcesso, ( function(processo) {
		   Effect.Appear("formSalvar");
		   dwr.util.setValue($("formSalvar").id, idProcesso);
		   dwr.util.setValue("nome", processo.nome);
		   dwr.util.setValue("descricao", processo.descricao);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos processos pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvar");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisa"),
	      descricao :dwr.util.getValue("descricaoPesquisa"),
	      idWorkflow :dwr.util.getValue("idWorkflow")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterProcessoDWR.pesquisar.bind(ManterProcessoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA);
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
		   var idProcesso = dwr.util.getValue($("formSalvar").id);
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
	   createWindow(255, 375, 280, 40, "Novo Processo", "divNovoProcesso", url);
   },
   
   /**
    * Abre janela para gerenciar os processos
    * */
   popupGerenciarAtividades :function(){
	   JanelasComuns.showMessage("Implemente-me");
//	   var url = "manterProcesso.do?method=popupGerenciarProcessos";
//	   createWindow(536, 985, 280, 10, "Gerenciar Processos", "divGerenciarProcessos", url);
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
	contaChar: function() {
		contaChar($("descricao"), 200);
	}
};

var processo = new ComportamentosTela();
