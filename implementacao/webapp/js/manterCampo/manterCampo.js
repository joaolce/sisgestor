/**
 * Comportamentos para o UC Manter Campo.
 * 
 * @author Thiago
 * @since 17/02/2009
 */

var ManterCampo = Class.create();
ManterCampo.prototype = {
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
	   return $("corpoManterCampo");
   },

   /**
	 * Recupera o form manterCampoForm.
	 * 
	 * @return form do manter campo
	 */
   getForm : function() {
	   return $("manterCampoForm");
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
	 * Recupera o id do campo selecionado.
	 * 
	 * @return id do campo selecionado
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do campo selecionado.
	 */
   visualizar : function() {
	   Element.hide("formSalvarCampo");
	   var idCampo = this.getIdSelecionado();
	   if (isNaN(idCampo)) {
		   return;
	   }
	   ManterCampoDWR.getById(idCampo, ( function(campo) {
		   Effect.Appear("formSalvarCampo");
		   dwr.util.setValue("nomeCampo", campo.nome);
		   dwr.util.setValue("tipo", campo.tipo.id);
	   }).bind(this));
   },

   /**
	 * Faz a pesquisa dos campos pelos parâmetros informados.
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvarCampo");
	   var dto = {
		  nome :dwr.util.getValue("nomePesquisaCampo"),
		  tipo :dwr.util.getValue("tipoPesquisa")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterCampoDWR.pesquisar.bind(ManterCampoDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(QTD_REGISTROS_PAGINA);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de campos.
	 * 
	 * @param listaCampo lista de campos retornados
	 */
   popularTabela : function(listaCampo) {
	   this.tabelaTelaPrincipal.removerResultado();

	   if (listaCampo.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(campo) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :campo.id
			   });
		   });
		   cellfuncs.push( function(campo) {
			   return campo.nome;
		   });
		   cellfuncs.push( function(campo) {
			   return campo.tipo.descricao;
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados campos");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do campo selecionado.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o campo selecionado?", ( function() {
		   requestUtils.submitForm(form, null, ( function() {
			   if (requestUtils.status) {
				   this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o campo selecionado.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o campo selecionado?", ( function() {
		   var idCampo = dwr.util.getValue($("formSalvarCampo").id);
		   requestUtils.simpleRequest("manterCampo.do?method=excluir&id=" + idCampo,
		      ( function() {
			      if (requestUtils.status) {
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo campo.
	 */
   popupNovoCampo : function() {
	   var url = "manterCampo.do?method=popupNovoCampo";
	   createWindow(255, 375, 280, 40, "Novo Campo", "divNovoCampo", url );
   },
   
   /**
	 * Envia ao action a ação de salvar os dados do campo.
	 * 
	 * @param form formulário submetido
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoCampo");
			   this.pesquisar();
		   }
	   }).bind(this));
   }   
};

var campo = new ManterCampo();
