/**
 * Comportamentos para o UC Manter Atividade.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
var ManterAtividade = Class.create();
ManterAtividade.prototype = {
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
	   return $("corpoManterAtividade");
   },

   /**
	 * Recupera o form manterAtividadeForm.
	 * 
	 * @return form do manter atividade
	 */
   getForm : function() {
	   return $("manterAtividadeForm");
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
	 * Recupera o id da atividade selecionada.
	 * 
	 * @return id da atividade selecionada
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos da atividade selecionada.
	 */
   visualizar : function() {
	   Element.hide("formSalvarAtividade");
	   var idAtividade = atividade.getIdSelecionado();
	   if (isNaN(idAtividade)) {
		   return;
	   }
	   ManterAtividadeDWR.getById(idAtividade, ( function(atividade) {
		   Effect.Appear("formSalvarAtividade");
		   dwr.util.setValue($("formSalvarAtividade").id, idAtividade);
		   dwr.util.setValue("nomeAtividade", atividade.nome);
		   dwr.util.setValue("descricaoAtividade", atividade.descricao);
		   dwr.util.setValue("departamento", atividade.departamento.id);
		   this.habilitarLinks(true);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos atividades pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvarAtividade");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaAtividade"),
	      descricao :dwr.util.getValue("descricaoPesquisaAtividade"),
	      departamento :dwr.util.getValue("departamentoPesquisa"),
	      idProcesso :dwr.util.getValue("processo")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterAtividadeDWR.pesquisar.bind(ManterAtividadeDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_ATIVIDADE);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de atividades.
	 * 
	 * @param listaAtividade lista de atividades retornadas
	 */
   popularTabela : function(listaAtividade) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaAtividade.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(atividade) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :atividade.id
			   });
		   });
		   cellfuncs.push( function(atividade) {
			   return atividade.nome;
		   });
		   cellfuncs.push( function(atividade) {
			   return atividade.descricao;
		   });
		   cellfuncs.push( function(atividade) {
			   if(atividade.departamento == null){
				   return "";
			   }
			   return atividade.departamento.sigla;
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontradas atividades");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados da atividade selecionada.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar a atividade selecionada?", ( function() {
		   requestUtils.submitForm(form, null, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir a atividade selecionada.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir a atividade selecionada?", ( function() {
		   var idAtividade = dwr.util.getValue($("formSalvarAtividade").id);
		   requestUtils.simpleRequest("manterAtividade.do?method=excluir&id=" + idAtividade,( function() {
			      if (requestUtils.status) {
			    	  this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para nova atividade.
	 */
   popupNovaAtividade : function() {
	   var url = "manterAtividade.do?method=popupNovaAtividade";
	   createWindow(285, 375, 280, 40, "Nova Atividade", "divNovaAtividade", url, (function(){
		   dwr.util.setValue("idProcesso",$F("processo"));
	   }));
   },
   
   /**
    * Abre janela para gerenciar as atividades
    * */
   popupGerenciarTarefas :function(){
	   var idAtividade = dwr.util.getValue($("formSalvarAtividade").id);
	   var url = "manterTarefa.do?method=entrada&atividade="+idAtividade;
	   createWindow(496, 985, 280, 50, "Gerenciar Tarefas", "divGerenciarTarefas", url, (function(){
		   tarefa.pesquisar();
	   }));
   }, 
   
   /**
	 * Envia ao action a ação de salvar os dados da atividade.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovaAtividade");
			   atividade.pesquisar();
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
		   contaChar($("descricaoAtividade"), 200, "contagemAtividade");
	   }
	},
	
	/**
	 * Habilita/desabilita os links, para quando um processo seja selecionado.
	 * 
    * @param (Boolean) caso seja para habilitar ou desabilitar
	 */
	habilitarLinks : function(habilita) {
		if(habilita) {
			$("linkGerenciarTarefas").className = "";
			$("linkGerenciarTarefas").onclick = this.popupGerenciarTarefas;
		} else {
			$("linkGerenciarTarefas").className = "btDesativado";
			$("linkGerenciarTarefas").onclick = "";
		}
	}
};

var atividade = new ManterAtividade();
