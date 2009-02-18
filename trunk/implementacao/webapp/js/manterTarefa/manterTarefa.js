/**
 * Comportamentos para o UC Manter Tarefa.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
var ManterTarefa = Class.create();
ManterTarefa.prototype = {
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
	   return $("corpoManterTarefa");
   },

   /**
	 * Recupera o form manterTarefaForm.
	 * 
	 * @return form do manter tarefa
	 */
   getForm : function() {
	   return $("manterTarefaForm");
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
	 * Recupera o id da tarefa selecionada.
	 * 
	 * @return id da tarefa selecionada
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos da tarefa selecionada.
	 */
   visualizar : function() {
	   Element.hide("formSalvarTarefa");
	   var idTarefa = this.getIdSelecionado();
	   if (isNaN(idTarefa)) {
		   return;
	   }
	   ManterTarefaDWR.getById(idTarefa, ( function(tarefa) {
		   Effect.Appear("formSalvarTarefa");
		   dwr.util.setValue($("formSalvarTarefa").id, idTarefa);
		   dwr.util.setValue("nomeTarefa", tarefa.nome);
		   dwr.util.setValue("descricaoTarefa", tarefa.descricao);
		   this.contaChar(false);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos tarefas pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvarTarefa");
	   var dto = {
	      nome :dwr.util.getValue("nomePesquisaTarefa"),
	      descricao :dwr.util.getValue("descricaoPesquisaTarefa"),
	      usuario :dwr.util.getValue("usuario"),
	      idAtividade :dwr.util.getValue("atividade")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterTarefaDWR.pesquisar.bind(ManterTarefaDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA_TAREFA);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de tarefas.
	 * 
	 * @param listaTarefa lista de tarefas retornadas
	 */
   popularTabela : function(listaTarefa) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaTarefa.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(tarefa) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :tarefa.id
			   });
		   });
		   cellfuncs.push( function(tarefa) {
			   return tarefa.nome;
		   });
		   cellfuncs.push( function(tarefa) {
			   return tarefa.descricao;
		   });
		   cellfuncs.push( function(tarefa) {
			   if(tarefa.usuario == null){
				   return "";
			   }
			   return tarefa.usuario.nome;
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontradas tarefas");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados da tarefa selecionada.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar a tarefa selecionada?", ( function() {
		   requestUtils.submitForm(form, null, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir a tarefa selecionada.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir a tarefa selecionada?", ( function() {
		   var idTarefa = dwr.util.getValue($("formSalvarTarefa").id);
		   requestUtils.simpleRequest("manterTarefa.do?method=excluir&id=" + idTarefa, ( function() {
			      if (requestUtils.status) {
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para nova tarefa.
	 */
   popupNovaTarefa : function() {
	   var url = "manterTarefa.do?method=popupNovaTarefa";
	   createWindow(255, 375, 280, 40, "Nova Tarefa", "divNovaTarefa", url, (function(){
		   dwr.util.setValue("idAtividade",$F("atividade"));
	   }));
   },
   
   /**
	 * Envia ao action a ação de salvar os dados da tarefa.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovaTarefa");
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
		   contaChar($("descricaoTarefa"), 200, "contagemTarefa");
	   }
	}
};

var tarefa = new ManterTarefa();
