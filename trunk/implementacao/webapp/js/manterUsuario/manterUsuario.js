/**
 * Ação a ser realizada ao iniciar a página
 */
Event.observe(window, "load", function() {
	usuario.pesquisar();
});

/**
 * Comportamentos para o UC Manter Usuario.
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
	 * @return{HTMLTableSectionElement}
	 */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterUsuario");
   },

   /**
	 * Recupera o form manterUsuarioForm
	 * 
	 * @return
	 */
   getForm : function() {
	   return $("manterUsuarioForm");
   },

   getTR : function() {
	   var tabela = FactoryTabelas.getTabelaById(this.getTBodyTelaPrincipal());
	   return tabela.getSelectedTR();
   },

   /**
	 * Recupera o id selecionado que é um hidden dentro da tabela.
	 */
   getIdSelecionado : function() {
	   return this.getTR().select("input[type=\"hidden\"]")[0].value;
   },

   /**
	 * Preenche os campos do usuario selecionado.
	 */
   visualizar : function() {
	   Element.hide("formSalvar");
	   var idUsuario = this.getIdSelecionado();
	   if (isNaN(idUsuario)) {
		   return;
	   }
	   ManterUsuarioDWR.getById(idUsuario, ( function(usuario) {
		   Effect.Appear("formSalvar");
		   dwr.util.setValue($("formSalvar").id, this.getIdSelecionado());
		   dwr.util.setValue("login", usuario.login);
		   dwr.util.setValue("nome", usuario.nome);
		   dwr.util.setValue("email", usuario.email);
		   dwr.util.setValue("departamento", usuario.departamento.id);
	   }).bind(this));
   },

   /**
	 * Pesquisa os usuarios por parte do nome
	 */
   pesquisar : function() {
	   Effect.Fade("formSalvar");
	   var login = dwr.util.getValue("loginPesquisa");
	   var nome = dwr.util.getValue("nomePesquisa");
	   var departamento = dwr.util.getValue("departamentoPesquisa");

	   if (this.tabelaTelaPrincipal == null) {
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabela(this.getTBodyTelaPrincipal());
	   }
	   this.tabelaTelaPrincipal.reiniciarPaginacao();
	   this.tabelaTelaPrincipal.abstractOnTrocarPagina = this.onTrocarPagina.bind(this);
	   ManterUsuarioDWR.pesquisar(login, nome, departamento, null, this.popularTabela.bind(this));
   },

   /**
	 * Evento ao trocar de página, toda vez que o usuário avançar ou retroceder a paginação essa
	 * função será invocada
	 * 
	 * @param {Integer} novaPagina
	 */
   onTrocarPagina : function(novaPagina) {
	   if (this.tabela != null) {
		   this.tabela.toggleShowDivPaginacao(false);
	   }
	   var login = dwr.util.getValue("loginPesquisa");
	   var nome = dwr.util.getValue("nomePesquisa");
	   var departamento = dwr.util.getValue("departamentoPesquisa");
	   
	   ManterUsuarioDWR.pesquisar(login, nome, departamento, novaPagina, this.popularTabela
	      .bind(this));
   },

   /**
	 * Popula a tabela principal com a lista de usuarios
	 * 
	 * @param resultadoDTO
	 * @return
	 */
   popularTabela : function(resultadoDTO) {
	   var listaUsuario = resultadoDTO.colecaoParcial;
	   dwr.util.removeAllRows(this.getTBodyTelaPrincipal());

	   this.tabelaTelaPrincipal.toggleShowDivPaginacao(true);
	   this.tabelaTelaPrincipal.setQtdRegistrosPagina(resultadoDTO.quantidadeRegistrosPagina);
	   this.tabelaTelaPrincipal.setTotalRegistros(resultadoDTO.totalRegistros);

	   if (listaUsuario.length != 0) {
		   var cellfuncs = new Array();
		   cellfuncs.push( function(usuario) {
			   return Builder.node("input", {
			      type :"hidden",
			      name :"id",
			      value :usuario.id
			   });
		   });
		   cellfuncs.push( function(usuario) {
			   return usuario.login;
		   });
		   cellfuncs.push( function(usuario) {
			   return usuario.nome;
		   });
		   cellfuncs.push( function(usuario) {
			   return usuario.email;
		   });
		   cellfuncs.push( function(usuario) {
			   return usuario.departamento.sigla;
		   });
		   dwr.util.addRows(this.getTBodyTelaPrincipal(), listaUsuario, cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("Não foram encontrados usuários.");
	   }
   },

   /**
	 * Envia ao action a ação de atualizar os dados do usuario selecionado
	 * 
	 * @param form
	 * @return
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o usuário selecionado?", ( function() {
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
				   usuario.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a ação de excluir o usuario selecionado
	 * 
	 * @param form
	 * @return
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o usuário selecionado?", ( function() {
		   var idUsuario = dwr.util.getValue($("formSalvar").id);
		   requestUtils.simpleRequest("manterUsuario.do?method=excluir&id=" + idUsuario, null,
		      ( function() {
			      if (requestUtils.status) {
				      this.pesquisar();
			      }
		      }).bind(this));
	   }).bind(this));
   },

   /**
	 * Abre a janela para novo usuario
	 */
   popupNovoUsuario : function() {
	   var url = "manterUsuario.do?method=popupNovoUsuario";
	   createWindow(420, 550, 280, 40, "Novo Usuario", "divNovoUsuario", url);
   },
   
   /**
    * Abre janela para editar permissões do usuário
    * @return
    */
   editarPermissoes : function(){
	   var id = dwr.util.getValue("id");
	   var url = "manterUsuario.do?method=popupEditarPermissoes&id="+id;
	   createWindow(340, 550, 280, 70, "Editar Permissões do Usuário", "divPermissao", url);
   },
   /**
	 * Envia ao action a ação de salvar os dados do usuario
	 * 
	 * @param form formulário
	 * @return
	 */
   salvar : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoUsuario");
			   usuario.pesquisar();
		   }
	   }).bind(this));
   },
   
   /**
    * Atualiza as permissões selecionadas para o usuário
    * @param form
    * @return
    */
   atualizarPermissoes: function(form){
	   //Desenvolver
   }
};

var usuario = new ComportamentosTela();
