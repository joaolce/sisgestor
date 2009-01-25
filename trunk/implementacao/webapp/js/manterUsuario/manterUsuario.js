/**
 * A��o a ser realizada ao iniciar a p�gina
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
    * Recupera o id selecionado que � um hidden dentro da tabela.
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
		   dwr.util.setValue($("formSalvar").id, idUsuario);
		   dwr.util.setValue("login", usuario.login);
		   dwr.util.setValue("nome", usuario.nome);
		   dwr.util.setValue("email", usuario.email);
		   dwr.util.setValue("departamento", usuario.departamento.id);
		   this.removerPermissoesUsuario();
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
    * Evento ao trocar de p�gina, toda vez que o usu�rio avan�ar ou retroceder a pagina��o essa
    * fun��o ser� invocada
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
		   this.tabelaTelaPrincipal.semRegistros("N�o foram encontrados usu�rios.");
	   }
   },

   /**
    * Envia ao action a a��o de atualizar os dados do usuario selecionado
    * 
    * @param form
    * @return
    */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o usu�rio selecionado?", ( function() {
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
				   usuario.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
    * Envia ao action a a��o de excluir o usuario selecionado
    * 
    * @param form
    * @return
    */
   excluir: function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o usu�rio selecionado?", ( function() {
		   var idUsuario = dwr.util.getValue($("formSalvar").id);
		   requestUtils.simpleRequest("manterUsuario.do?method=excluir&id=" + idUsuario,( function() {
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
	   createWindow(430, 550, 280, 40, "Novo Usuario", "divNovoUsuario", url);
   },

   /**
    * Abre janela para editar permiss�es do usu�rio
    */
   editarPermissoes : function() {
	   var id = $("formSalvar").id.value;
	   var url = "manterUsuario.do?method=popupEditarPermissoes&id=" + id;
	   var janela = createWindow(260, 550, 280, 70, "Editar Permiss�es do Usu�rio", "divPermissao", url);
	   janela.addOnComplete((function() {
	   	ComboFunctions.ordenarOptions("permissoes"); 
	   	} 
	   ));
   },
   
   /**
    * Envia ao action a a��o de salvar os dados do usuario
    * 
    * @param form formul�rio
    * @return
    */
   salvar: function(form) {
	   ComboFunctions.selecionaCombo("permissoes");
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoUsuario");
			   usuario.pesquisar();
		   }
	   }).bind(this));
   },

   /**
    * Atualiza as permiss�es selecionadas para o usu�rio.
    * 
    * @param form formul�rio da submiss�o
    */
   atualizarPermissoes : function(form) {
   	this.removerPermissoesUsuario();
	   ComboFunctions.selecionaCombo("permissoes"); //TODO verificar se � necess�rio
	   var formSalvar = $("formSalvar");
	   var permissoes = ComboFunctions.getValues("permissoes");
	   for (var count = 0; count < permissoes.length; count++) {
	   	formSalvar.appendChild(Builder.node("input", {
		      type :"hidden",
		      name :"permissoes",
		      value :permissoes[count]
		   }));
	   }
   },
   
   /**
    * Remove as permiss�es do usu�rio no form de altera��o.
    */
   removerPermissoesUsuario : function() {
	   var formSalvar = $("formSalvar");
	   var inputsForm = formSalvar.getElementsByTagName("input");
	   for (var count = 0; count < inputsForm.length; count++) {
	   	if((inputsForm.item(count).type == "hidden") && (inputsForm.item(count).name == "permissoes")) {
	   		formSalvar.removeChild(inputsForm.item(count));
	   	}
	   }
   },
   
   /**
    * Transfere todos os itens (Permiss�o) de um combo para o outro e vice-versa
    * 
    * @param {String} origem combo de origem
    * @param {String} destino combo de destino
    */
   transfereTodasPermissoes : function(origem, destino) {
	   ComboFunctions.transfereTodos(origem, destino);
   },

   /**
    * Transfere as permiss�es de um combo para o outro onde ficar�o os itens selecionados
    * 
    * @param {String} origem combo de origem
    * @param {String} destino combo de destino
    */
   transferePermissao : function(origem, destino) {
	   ComboFunctions.transfereOptions(origem, destino);
   },

   /**
    * Recupera todas permiss�es.
    * 
    * @return
    */
   getTodasPermissoes : function() {
	   ManterUsuarioDWR.getTodasPermissoes( function(permissoes) {
		   dwr.util.removeAllOptions("permissoesInform");
		   dwr.util.addOptions("permissoesInform", permissoes, "id", "descricao");
	   });
   },
   
   /**
    * Abre popup para o usu�rio alterar sua senha. 
    */
   popupEditarSenha: function(){
	   var url = "manterUsuario.do?method=popupEditarSenha";
	   createWindow(180, 300, 370, 70, "Alterar Minha Senha", "divSenha", url);
   },
   
   /**
    * Envia para a action efetuar as altera��es. 
    */
   atualizarSenha: function(form){
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divSenha");
		   }
	   }).bind(this));
   }
};

var usuario = new ComportamentosTela();
