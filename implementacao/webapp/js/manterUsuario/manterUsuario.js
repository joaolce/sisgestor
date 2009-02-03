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
/**
 * @author Thiago
 *
 */
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
	 * @return form do manter usu�rio
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
	 * Recupera o id do usu�rio selecionado.
	 * 
	 * @return id do usu�rio selecionado
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
	   ManterUsuarioDWR.getById(idUsuario, ( function(_usuario) {
		   Effect.Appear("formSalvar");
		   dwr.util.setValue($("formSalvar").id, idUsuario);
		   dwr.util.setValue("login", _usuario.login);
		   dwr.util.setValue("nome", _usuario.nome);
		   dwr.util.setValue("email", _usuario.email);
		   dwr.util.setValue("departamento", _usuario.departamento.id);
		   dwr.util.setValue("chefe", _usuario.chefe);
		   this.atualizarPermissoesUsuario(_usuario);
		   
		   this.verificarPermissoes();
		   
	   }).bind(this));
   },

   /**
	 * Atualiza no form do usu�rio as suas permiss�es e as permiss�es dispon�veis.
	 * 
	 * @param usuario usu�rio a ser visualizado
	 */
   atualizarPermissoesUsuario : function(usuario) {
	   dwr.util.removeAllOptions("permissoes");
	   dwr.util.addOptions("permissoes", usuario.permissoes, "id", "descricao");
	   dwr.util.removeAllOptions("permissoesInform");
	   ManterUsuarioDWR.getTodasPermissoes( function(permissoes) {
		   dwr.util.addOptions("permissoesInform", permissoes, "id", "descricao");
		   // remove das permiss�es dispon�veis, as permiss�es que o usu�rio j� tem.
		   for ( var i = 0; i < usuario.permissoes.length; i++) {
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
	   var dto = {
	      login :dwr.util.getValue("loginPesquisa"),
	      nome :dwr.util.getValue("nomePesquisa"),
	      departamento :dwr.util.getValue("departamentoPesquisa")
	   };

	   if (this.tabelaTelaPrincipal == null) {
		   var chamadaRemota = ManterUsuarioDWR.pesquisar.bind(ManterUsuarioDWR);
		   this.tabelaTelaPrincipal = FactoryTabelas.getNewTabelaPaginada(this
		      .getTBodyTelaPrincipal(), chamadaRemota, this.popularTabela.bind(this));
		   this.tabelaTelaPrincipal.setQtdRegistrosPagina(9);
	   }
	   this.tabelaTelaPrincipal.setParametros(dto);
	   this.tabelaTelaPrincipal.executarChamadaRemota();
   },

   /**
	 * Popula a tabela principal com a lista de usu�rios.
	 * 
	 * @param listaUsuario lista de usu�rios retornados
	 */
   popularTabela : function(listaUsuario) {
	   this.tabelaTelaPrincipal.removerResultado();

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
		   cellfuncs.push( function(usuario) {
		   	if(usuario.chefe) {
		   		return "Sim";
		   	} else {
		   		return "N�o";
		   	}
		   });
		   this.tabelaTelaPrincipal.adicionarResultadoTabela(cellfuncs);
		   this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));
	   } else {
		   this.tabelaTelaPrincipal.semRegistros("N�o foram encontrados usu�rios.");
	   }
   },

   /**
	 * Envia ao action a a��o de atualizar os dados do usuario selecionado.
	 * 
	 * @param form form submetido
	 */
   atualizar : function(form) {
	   JanelasComuns.showConfirmDialog("Deseja atualizar o usu�rio selecionado?", ( function() {
		   this.habilitarCampos(true);
		   ComboFunctions.selecionaCombo("permissoes");
		   requestUtils.submitForm(form, null, ( function() {
			   if (requestUtils.status) {
				   UtilDWR.getUser(function (obj){
					   if(obj.id == dwr.util.getValue($("formSalvar").id)){ 
						   UtilDWR.finalizarSessao(function(){
							   JanelasComuns.sessaoFinalizada();
						   });
					   }  else {
						   usuario.pesquisar();
					   }
				   }).bind(this);
			   }
		   }).bind(this));
	   }).bind(this));
   },

   /**
	 * Envia ao action a a��o de excluir o usu�rio selecionado.
	 * 
	 * @param form form submetido
	 */
   excluir : function() {
	   JanelasComuns.showConfirmDialog("Deseja excluir o usu�rio selecionado?", ( function() {
		   var idUsuario = dwr.util.getValue($("formSalvar").id);
		   requestUtils.simpleRequest("manterUsuario.do?method=excluir&id=" + idUsuario,
		      ( function() {
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
	 * Envia ao action a a��o de salvar os dados do usu�rio.
	 * 
	 * @param form formul�rio submetido
	 */
   salvar : function(form) {
	   ComboFunctions.selecionaCombo("permissoesNovo");
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divNovoUsuario");
			   this.pesquisar();
		   }
	   }).bind(this));
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
	 * Abre popup para o usu�rio alterar sua senha.
	 */
   popupEditarSenha : function() {
	   var url = "manterUsuario.do?method=popupEditarSenha";
	   createWindow(160, 300, 370, 70, "Alterar Minha Senha", "divSenha", url);
   },

   /**
	 * Envia para a action efetuar as altera��es.
	 * 
	 * @param form form submetido
	 */
   atualizarSenha : function(form) {
	   requestUtils.submitForm(form, ( function() {
		   if (requestUtils.status) {
			   JanelaFactory.fecharJanela("divSenha");
		   }
	   }).bind(this));
   },
   
   /**
    * Verifica se o usu�rio tem permiss�o para editar os campos
    * */
   verificarPermissoes : function(){
	   UtilDWR.getPermissaoUsuario(function(permissao){
		   UtilDWR.usuarioTemPermissao(permissao,function(possui){
			   usuario.habilitarCampos(possui);
			   
			   //Recupera o usu�rio da sess�o e verifica se ele pode editar os campos
			   UtilDWR.getUser(function(_usuario){
				   if(usuario.getIdSelecionado() != _usuario.id && !possui){
					   $("divBotoes").setStyle({display : "none"});
				   } else {
					   $("divBotoes").setStyle({display : "block"});
				   }
			   });
		   });
	   });  
   },
   
   /**
    * Habilita/desabilita os campos que o usu�rio n�o tempermiss�o para alterar
    * */
   habilitarCampos : function(habilita){
	   $("formSalvar").chefe.disabled = !habilita;
	   $("formSalvar").departamento.disabled = !habilita;
	   $("formSalvar").permissoes.disabled = !habilita;
	   $("formSalvar").permissoesInform.disabled = !habilita;
   }
};

var usuario = new ComportamentosTela();
