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
    * @return {HTMLTableSectionElement}
    */
   getTBodyTelaPrincipal : function() {
	   return $("corpoManterUsuario");
   },

   /**
    * Recupera o form manterUsuarioForm.
    * 
    * @return form do manter usuário
    */
   getForm : function() {
	   return $("manterUsuarioForm");
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
    * Recupera o id do usuário selecionado.
    * 
    * @return id do usuário selecionado
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
		   this.atualizarPermissoesUsuario(usuario);
	   }).bind(this));
   },
   
   /**
    * Atualiza no form do usuário as suas permissões e as permissões disponíveis.
    * 
    * @param usuario usuário a ser visualizado
    */
   atualizarPermissoesUsuario : function(usuario) {
	   dwr.util.removeAllOptions("permissoes");
	   dwr.util.addOptions("permissoes", usuario.permissoes, "id", "descricao");
	   dwr.util.removeAllOptions("permissoesInform");
	   ManterUsuarioDWR.getTodasPermissoes( function(permissoes) {
		   dwr.util.addOptions("permissoesInform", permissoes, "id", "descricao");
		   //remove das permissões disponíveis, as permissões que o usuário já tem.
		   for(var i = 0; i < usuario.permissoes.length; i++) {
		   	ComboFunctions.removeOptionValue("permissoesInform", usuario.permissoes[i].id);
		   }
	   });
	   ComboFunctions.ordenarOptions("permissoes");
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
		   this.tabelaTelaPrincipal.abstractOnTrocarPagina = this.onTrocarPagina.bind(this);
	   }
	   this.tabelaTelaPrincipal.reiniciarPaginacao();
	   ManterUsuarioDWR.pesquisar(login, nome, departamento, null, this.popularTabela.bind(this));
   },

   /**
    * Evento ao trocar de página, toda vez que o usuário avançar ou retroceder a paginação essa
    * função será invocada.
    * 
    * @param {Integer} novaPagina número da nova página
    */
   onTrocarPagina : function(novaPagina) {
	   var login = dwr.util.getValue("loginPesquisa");
	   var nome = dwr.util.getValue("nomePesquisa");
	   var departamento = dwr.util.getValue("departamentoPesquisa");

	   ManterUsuarioDWR.pesquisar(login, nome, departamento, novaPagina, this.popularTabela.bind(this));
   },

   /**
    * Popula a tabela principal com a lista de usuários.
    * 
    * @param resultadoDTO resultado da pesquisa
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
    * Envia ao action a ação de atualizar os dados do usuario selecionado.
    * 
    * @param form form submetido
    */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o usuário selecionado?", ( function() {
	   	ComboFunctions.selecionaCombo("permissoes");
		   requestUtils.submitForm(form, ( function() {
			   if (requestUtils.status) {
			   	this.pesquisar();
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
    * Envia ao action a ação de excluir o usuário selecionado.
    * 
    * @param form form submetido
    */
   excluir: function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o usuário selecionado?", ( function() {
		   var idUsuario = dwr.util.getValue($("formSalvar").id);
		   requestUtils.simpleRequest("manterUsuario.do?method=excluir&id=" + idUsuario,( function() {
				if (requestUtils.status) {
					this.pesquisar();
				}
			}).bind(this));
	   }).bind(this));
   },

   /**
    * Abre a janela para novo usuario.
    */
   popupNovoUsuario : function() {
	   var url = "manterUsuario.do?method=popupNovoUsuario";
	   createWindow(430, 550, 280, 40, "Novo Usuario", "divNovoUsuario", url);
   },

   /**
    * Envia ao action a ação de salvar os dados do usuário.
    * 
    * @param form formulário submetido
    */
   salvar: function(form) {
	   ComboFunctions.selecionaCombo("permissoesNovo");
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoUsuario");
			   this.pesquisar();
		   }
	   }).bind(this));
   },

   /**
    * Transfere todos os itens (Permissão) de um combo para o outro e vice-versa
    * 
    * @param {String} origem combo de origem
    * @param {String} destino combo de destino
    */
   transfereTodasPermissoes : function(origem, destino) {
	   ComboFunctions.transfereTodos(origem, destino);
   },

   /**
    * Transfere as permissões de um combo para o outro onde ficarão os itens selecionados
    * 
    * @param {String} origem combo de origem
    * @param {String} destino combo de destino
    */
   transferePermissao : function(origem, destino) {
	   ComboFunctions.transfereOptions(origem, destino);
   },

   /**
    * Abre popup para o usuário alterar sua senha. 
    */
   popupEditarSenha: function(){
	   var url = "manterUsuario.do?method=popupEditarSenha";
	   createWindow(160, 300, 370, 70, "Alterar Minha Senha", "divSenha", url);
   },
   
   /**
    * Envia para a action efetuar as alterações. 
    * 
    * @param form form submetido
    */
   atualizarSenha: function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divSenha");
		   }
	   }).bind(this));
   }
};

var usuario = new ComportamentosTela();
