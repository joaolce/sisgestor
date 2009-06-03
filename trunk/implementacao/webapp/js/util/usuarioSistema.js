/**
 * Recupera o usuário atual do sistema para o javascript. <br />
 * obs: (não é necessário importar nenhum JS na página)
 */
var Usuario = Class.create();
Usuario = {
   _usuario :null,

   /**
	 * Recupera o usuário logado.
	 * 
	 * @return usuário logado
	 */
   getUsuario : function() {
	   return this._usuario;
   },

   /**
	 * Armazena o usuário logado.
	 * 
	 * @param usuario usuário a armazenar
	 */
   setUsuario : function(usuario) {
	   this._usuario = usuario;
   },

   /**
	 * Verifica se o usuário logado possui a permissão informada.
	 * 
	 * @param {String} permissao código da permissão
	 * @return <code>true</code> caso usuário possui permissão, <code>false</code> caso não
	 *         possua
	 */
   temPermissao : function(permissao) {
	   permissao = parseInt(permissao);
	   for ( var i = 0; i < this._usuario.permissoes.length; i++) {
		   if (parseInt(this._usuario.permissoes[i].id) == permissao) {
			   return true;
		   }
	   }
	   return false;
   },

   /**
	 * Notifica o usuário para informar que foi transferido o registro do uso.
	 * 
	 * @param {Object} usoWorkflow usoWorkflow com a tarefa modificada
	 */
   notificarTransferenciaRegistro : function(usoWorkflow) {
	   var usuarioTarefa = usoWorkflow.tarefa.usuario;
	   if (this._usuario.id == usuarioTarefa.id) {
		   var dataHora = usoWorkflow.historico[0].dataHora;
		   JanelasComuns.showInformation("O registro " + usoWorkflow.numeroRegistro
		      + " foi transferido para a sua responsabilidade em "
		      + getStringTimestamp(dataHora, "dd/MM/yyyy") + " às "
		      + getStringTimestamp(dataHora, "HH:mm:ss") + '.');
	   }
   }
};

/* Para garantir que seja a primeira instrução executada. */
dwr.engine.setAsync(false);
UtilDWR.getUser(Usuario.setUsuario.bind(Usuario));
dwr.engine.setAsync(true);