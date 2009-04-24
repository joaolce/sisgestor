/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	departamento.pesquisar();
});

/**
 * Comportamentos para o UC Manter Departamento.
 * 
 * @author João Lúcio
 */
var ManterDepartamento = Class.create();
ManterDepartamento.prototype = {

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
	 * @return{HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterDepartamento");
   },

   /**
	 * Retorna a linha selecionada da tabela de departamentos.
	 * 
	 * @return linha selecionada da tabela
	 */
   getTR : function() {
	   return FactoryTabelas.getTabelaById(this.getTBodyTelaPrincipal()).getSelectedTR();
   },

   /**
	 * Recupera o id do departamento selecionado.
	 * 
	 * @return id do departamento selecionado
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do departamento selecionado.
	 */
   visualizar : function() {
	   Element.hide("formSalvar");
	   var idDepartamento = this.getIdSelecionado();
	   if (isNaN(idDepartamento)) {
		   return;
	   }
	   ManterDepartamentoDWR.getById(idDepartamento, ( function(departamento) {
		   Effect.Appear("formSalvar");
		   dwr.util.setValue($("formSalvar").id, idDepartamento);
		   dwr.util.setValue("sigla", departamento.sigla);
		   dwr.util.setValue("nome", departamento.nome);
		   dwr.util.setValue("email", departamento.email);
		   if (departamento.departamentoSuperior != null) {
			   dwr.util.setValue("departamentoSuperior", departamento.departamentoSuperior.id);
		   } else {
			   dwr.util.setValue("departamentoSuperior", "");
		   }
	   }).bind(this));
   },

   /**
	 * Pesquisa os departamentos por parte da sigla e do nome.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvar");
	   var dto = {
	      sigla :dwr.util.getValue("siglaPesquisa"),
	      nome :dwr.util.getValue("nomePesquisa")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterDepartamentoDWR.pesquisar.bind(ManterDepartamentoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de departamentos.
	 * 
	 * @param listaDepartamento lista de departamentos retornados
	 */
   popularTabela : function(listaDepartamento) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaDepartamento.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(departamento) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :departamento.id
			   });
		   });
		   cellfuncs.push( function(departamento) {
			   return departamento.sigla;
		   });
		   cellfuncs.push( function(departamento) {
			   return departamento.nome;
		   });
		   cellfuncs.push( function(departamento) {
			   return nobreakSpace(departamento.email);
		   });
		   cellfuncs.push( function(departamento) {
			   if (departamento.departamentoSuperior != null) {
				   return departamento.departamentoSuperior.sigla;
			   }
			   return "&nbsp;";
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados departamentos");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do departamento selecionado.
	 * 
	 * @param form formulário submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o departamento selecionado?", ( function() {
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
				   this.atualizarDepartamentosSuperior();
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o departamento selecionado.
	 * 
	 * @param form formulário submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o departamento selecionado?", ( function() {
		   var idDepartamento = dwr.util.getValue($("formSalvar").id);
		   requestUtils.simpleRequest("manterDepartamento.do?method=excluir&id=" + idDepartamento,
		      ( function() {
			      if (requestUtils.status) {
				      this.atualizarDepartamentosSuperior();
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de salvar os dados do novo departamento.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoDepartamento");
			   this.atualizarDepartamentosSuperior();
			   this.pesquisar();
		   }
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo departamento.
	 */
   popupNovoDepartamento : function() {
	   var url = "manterDepartamento.do?method=popupNovoDepartamento";
	   createWindow(270, 310, 280, 70, "Novo Departamento", "divNovoDepartamento", url);
   },

   /**
	 * Atualiza os departamentos na combo de departamento superior.
	 */
   atualizarDepartamentosSuperior : function() {
	   dwr.util.removeAllOptions("departamentoSuperior");
	   ManterDepartamentoDWR.obterTodos(( function(departamentos) {
		   dwr.util.addOptions("departamentoSuperior", [ " " ]);
		   dwr.util.addOptions("departamentoSuperior", departamentos, "id", "sigla");
	   }));
   }
};

var departamento = new ManterDepartamento();
