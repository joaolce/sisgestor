
/**
 * A��o a ser realizada ao iniciar a p�gina
 */
Event.observe(window, "load", function() {
	departamento.pesquisar();
});

var ComportamentosTela = Class.create();
ComportamentosTela.prototype = {
	initialize: function(){},
	
	tabelaTelaPrincipal: null,

	/**
	 * retorna a tabela da tela inicial do caso de uso
	 * 
	 * @return{HTMLTableSectionElement}
	 */
	getTBodyTelaPrincipal: function(){
		return $("corpoManterDepartamento");
	},	
	
	/**
	 * Recupera o form manterDepartamentoForm
	 * @return
	 */
	getForm: function(){
		return  $("manterDepartamentoForm");
	},
	
	getTR: function(){
		var tabela = FactoryTabelas.getTabelaById(this.getTBodyTelaPrincipal());
		return tabela.getSelectedTR();
	},

	/**
	 * Recupera o id selecionado que � um hidden dentro da tabela
	 */
	getIdSelecionado: function(){
		return this.getTR().select("input[type=\"hidden\"]")[0].value;
	},
	
	/**
	 * Preenche os campos do departamento selecionado
	 */
	visualizar: function(){
		Element.hide("formSalvar");
		var idDepartamento = this.getIdSelecionado();
		if(isNaN(idDepartamento)){
			return;
		}
		ManterDepartamentoDWR.getById(idDepartamento, (function(departamento){
			Effect.Appear("formSalvar");
			dwr.util.setValue($("formSalvar").id, this.getIdSelecionado());
			dwr.util.setValue("sigla", departamento.sigla);
			dwr.util.setValue("nome", departamento.nome);
			dwr.util.setValue("email", departamento.email);
		}).bind(this));
		
	},
	
	/**
	 * Pesquisa os departamentos pelo nome
	 */
	pesquisar: function() {
		Effect.Fade("formSalvar");
		var nome = dwr.util.getValue("nomePesquisa");
		
		if(this.tabelaTelaPrincipal == null){
			this.tabelaTelaPrincipal = FactoryTabelas.getNewTabela(this.getTBodyTelaPrincipal());
		}
		this.tabelaTelaPrincipal.reiniciarPaginacao();
		this.tabelaTelaPrincipal.abstractOnTrocarPagina = this.onTrocarPagina.bind(this);
		ManterDepartamentoDWR.pesquisar(nome, null, this.popularTabela.bind(this));
	},
	
	/**
	 * Evento ao trocar de p�gina, toda vez que o usu�rio avan�ar ou retroceder a pagina��o essa fun��o
	 * ser� invocada 
	 * @param {Integer} novaPagina
	 */
	onTrocarPagina: function(novaPagina){
		if(this.tabela != null){
			this.tabela.toggleShowDivPaginacao(false) ;
		}
		var nome = dwr.util.getValue("nomePesquisa");
		
		ManterDepartamentoDWR.pesquisar(nome, novaPagina, this.popularTabela.bind(this));
	},
	
	/**
	 * Popula a tabela principal com a lista de departamentos 
	 * @param resultadoDTO
	 * @return
	 */
	popularTabela: function(resultadoDTO) {
		var listaDepartamento = resultadoDTO.colecaoParcial;
		dwr.util.removeAllRows(this.getTBodyTelaPrincipal());
		
		this.tabelaTelaPrincipal.toggleShowDivPaginacao(true) ;
		this.tabelaTelaPrincipal.setQtdRegistrosPagina(9);
		this.tabelaTelaPrincipal.setTotalRegistros(resultadoDTO.totalRegistros);
		
		if(listaDepartamento.length != 0){
			var cellfuncs = new Array();
			cellfuncs.push(function(departamento){
				return Builder.node("input", {type: "hidden", name:"id", value: departamento.id});
			});
			cellfuncs.push(function(departamento){
				return departamento.sigla;
			});
			cellfuncs.push(function(departamento){
				return departamento.nome;
			});
			cellfuncs.push(function(departamento){
				return departamento.email;
			});
			cellfuncs.push(function(departamento){
				if(departamento.departamentoSuperior != null)
					return departamento.departamentoSuperior.nome;
				return "";
			});	
			dwr.util.addRows(this.getTBodyTelaPrincipal(), listaDepartamento, cellfuncs);
			this.tabelaTelaPrincipal.setOnClick(this.visualizar.bind(this));				
			
		} else{
			this.tabelaTelaPrincipal.semRegistros("N�o foram encontrados departamentos.");
		}
	},
		
	/**
	 * Envia ao action a a��o de atualizar os dados do departamento selecionado
	 * @param form
	 * @return
	 */
	atualizar: function(form) {
		requestUtils.submitForm(form, (function(){
			if(requestUtils.status){
				departamento.pesquisar();
			}
		}).bind(this)); 
	},
	
	/**
	 * Envia ao action a a��o de excluir o departamento selecionado
	 * @param form
	 * @return
	 */
	excluir: function(form) {
		var frm = form;
		frm.action = "manterDepartamento.do?method=excluir";
		JanelasComuns.showConfirmDialog("Deseja excluir o departamento selecionado?",(function(){
			requestUtils.submitForm(frm, (function(){
				if(requestUtils.status){
					departamento.pesquisar();
				}
			}).bind(this));
		}).bind(this));
	},
	
	/**
	 * Envia ao action a a��o de salvar os dados do departamento
	 * @param form
	 * @return
	 */
	salvar: function(form){
		requestUtils.submitForm(form, (function(){
			if(requestUtils.status){
				//Fecha a pr�pria janela(ativa).
				JanelaFactory.fecharJanela("divNovoDepartamento"); 
				JanelaFactory.getJanelaById("divDepartamentos").recarregarConteudo();
			}
		}).bind(this));
	},
	
	
	/**
	 * Abre a janela para novo departamento
	 */
	popupNovoDepartamento: function(){
		var url = "manterDepartamento.do?method=popupNovoDepartamento";
		createWindow(220, 500, 220, 70, "Novo Departamento", "divNovoDepartamento", url);
	}
};

var departamento = new ComportamentosTela();