/**
 * Comportamentos para o UC Manter Atividade.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
var ManterAtividade = Class.create();
ManterAtividade.prototype = {

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
	   return $("corpoManterAtividade");
   },

   /**
	 * Recupera a linha selecionada.
	 * 
	 * @return linha selecionada
	 */
   getTR : function() {
	   return FactoryTabelas.getTabelaById(atividade.getTBodyTelaPrincipal()).getSelectedTR();
   },

   /**
	 * Recupera o id da atividade selecionada.
	 * 
	 * @return id da atividade selecionada
	 */
   getIdSelecionado : function() {
	   return atividade.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos da atividade selecionada.
	 */
   visualizar : function() {
	   Element.hide("formAtualizarAtividade");
	   var idAtividade = atividade.getIdSelecionado();
	   if (isNaN(idAtividade)) {
		   return;
	   }
	   ManterAtividadeDWR.getById(idAtividade, ( function(atividade) {
		   Effect.Appear("formAtualizarAtividade");
		   dwr.util.setValue($("formAtualizarAtividade").id, idAtividade);
		   dwr.util.setValue("nomeAtividade", atividade.nome);
		   dwr.util.setValue("descricaoAtividade", atividade.descricao);
		   dwr.util.setValue("departamentoAtividade", atividade.departamento.id);
		   this.habilitarLinks(true);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos atividades pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formAtualizarAtividade");
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
		   requestUtils.submitForm(form, ( function() {
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
		   var idAtividade = dwr.util.getValue($("formAtualizarAtividade").id);
		   requestUtils.simpleRequest("manterAtividade.do?method=excluir&id=" + idAtividade,
		      ( function() {
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
	   createWindow(285, 375, 280, 40, "Nova Atividade", "divNovaAtividade", url, ( function() {
		   dwr.util.setValue("processoNovaAtividade", $F("processoAtividade"));
	   }));
   },

   /**
	 * Abre janela para gerenciar as atividades
	 */
   popupGerenciarTarefas : function() {
	   var idAtividade = dwr.util.getValue($("formAtualizarAtividade").id);
	   var nomeAtividade = dwr.util.getValue($("formAtualizarAtividade").nomeAtividade);
	   var url = "manterTarefa.do?method=entrada&atividade=" + idAtividade;
	   createWindow(496, 985, 280, 50, "Gerenciar Tarefas - " + nomeAtividade,
	      "divGerenciarTarefas", url, ( function() {
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
   contaChar : function(novo) {
	   if (novo) {
		   contaChar($("descricaoNovaAtividade"), 200, "contagemNovaAtividade");
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
	   if (habilita) {
		   $("linkGerenciarTarefas").className = "";
		   $("linkGerenciarTarefas").onclick = this.popupGerenciarTarefas;
	   } else {
		   $("linkGerenciarTarefas").className = "btDesativado";
		   $("linkGerenciarTarefas").onclick = "";
	   }
   }
};

var atividade = new ManterAtividade();
