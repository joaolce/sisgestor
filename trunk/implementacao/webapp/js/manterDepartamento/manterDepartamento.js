/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	departamento.pesquisar();
});

/**
 * Comportamentos para o UC Manter Departamento.
 */
var ComportamentosTela = Class.create();
ComportamentosTela.prototype = {
   initialize : function() {},

   tabelaTelaPrincipal :null,

   /**
    * Retorna a tabela da tela inicial do caso de uso
    * 
    * @return{HTMLTableSectionElement}
    */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterDepartamento");
   },

   /**
    * Recupera o form manterDepartamentoForm
    * 
    * @return formulário
    */
   getForm : function() {
	   return $("manterDepartamentoForm");
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
    * Pesquisa os departamentos por parte do nome
    */
   pesquisar : function() {
	   Effect.Fade("formSalvar");
	   var sigla = dwr.util.getValue("siglaPesquisa");
	   var nome = dwr.util.getValue("nomePesquisa");

	   if (this.tabelaTelaPrincipal == null) {
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabela(this.getTBodyTelaPrincipal());
		   this.tabelaTelaPrincipal.abstractOnTrocarPagina = this.onTrocarPagina.bind(this);
	   }
	   this.tabelaTelaPrincipal.reiniciarPaginacao();
	   ManterDepartamentoDWR.pesquisar(sigla, nome, null, this.popularTabela.bind(this));
   },

   /**
    * Evento ao trocar de página, toda vez que o usuário avançar ou retroceder a paginação essa
    * função será invocada.
    * 
    * @param {Integer} novaPagina número da nova página
    */
   onTrocarPagina : function(novaPagina) {
	   var sigla = dwr.util.getValue("siglaPesquisa");
	   var nome = dwr.util.getValue("nomePesquisa");

	   ManterDepartamentoDWR.pesquisar(sigla, nome, novaPagina, this.popularTabela.bind(this));
   },

   /**
    * Popula a tabela principal com a lista de departamentos.
    * 
    * @param resultadoDTO resultado da pesquisa
    */
   popularTabela : function(resultadoDTO) {
	   var listaDepartamento = resultadoDTO.colecaoParcial;
	   dwr.util.removeAllRows(this.getTBodyTelaPrincipal());

	   this.tabelaTelaPrincipal.toggleShowDivPaginacao(true);
	   this.tabelaTelaPrincipal.setQtdRegistrosPagina(resultadoDTO.quantidadeRegistrosPagina);
	   this.tabelaTelaPrincipal.setTotalRegistros(resultadoDTO.totalRegistros);

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
			   return departamento.email;
		   });
		   cellfuncs.push( function(departamento) {
			   if (departamento.departamentoSuperior != null) {
				   return departamento.departamentoSuperior.sigla;
			   }
			   return "";
		   });
		   dwr.util.addRows(this.getTBodyTelaPrincipal(), listaDepartamento, cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados departamentos.");
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
		   requestUtils.simpleRequest("manterDepartamento.do?method=excluir&id=" + idDepartamento, ( function() {
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
	   createWindow(270, 430, 280, 70, "Novo Departamento", "divNovoDepartamento", url);
   },
   
   /**
    * Atualiza os departamentos na combo de departamento superior.
    */
   atualizarDepartamentosSuperior : function() {
   	dwr.util.removeAllOptions("departamentoSuperior");
	   ManterDepartamentoDWR.obterTodos((function(departamentos){
	   	dwr.util.addOptions("departamentoSuperior", [" "]);
	   	dwr.util.addOptions("departamentoSuperior", departamentos, "id", "sigla");
	   }));
   }
};

var departamento = new ComportamentosTela();
