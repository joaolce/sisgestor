/**
 * Recupera o usu�rio atual do sistema para o javascript. <br />
 * obs: (n�o � necess�rio importar nenhum JS na p�gina)
 */
var Usuario = Class.create();
Usuario = {
   _usuario :null,

   /**
	 * Recupera o usu�rio logado.
	 * 
	 * @return usu�rio logado
	 */
   getUsuario : function() {
	   return this._usuario;
   },

   /**
	 * Armazena o usu�rio logado.
	 * 
	 * @param usuario usu�rio a armazenar
	 */
   setUsuario : function(usuario) {
	   this._usuario = usuario;
   },

   /**
	 * Verifica se o usu�rio logado possui a permiss�o informada.
	 * 
	 * @param {String} permissao c�digo da permiss�o
	 * @return <code>true</code> caso usu�rio possui permiss�o, <code>false</code> caso n�o
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
	 * Notifica o usu�rio para informar que foi transferido o registro do uso.
	 * 
	 * @param {Object} usoWorkflow usoWorkflow com a tarefa modificada
	 */
   notificarTransferenciaRegistro : function(usoWorkflow) {
	   var usuarioTarefa = usoWorkflow.tarefa.usuario;
	   if (this._usuario.id == usuarioTarefa.id) {
		   var dataHora = usoWorkflow.historico[0].dataHora;
		   JanelasComuns.showInformation("O registro " + usoWorkflow.numeroRegistro
		      + " foi transferido para a sua responsabilidade em "
		      + getStringTimestamp(dataHora, "dd/MM/yyyy") + " �s "
		      + getStringTimestamp(dataHora, "HH:mm:ss") + '.');
	   }
   }
};

/* Para garantir que seja a primeira instru��o executada. */
dwr.engine.setAsync(false);
UtilDWR.getUser(Usuario.setUsuario.bind(Usuario));
dwr.engine.setAsync(true);