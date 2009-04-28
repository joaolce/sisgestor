Event.observe(window, "load", function(event) {
	dwr.engine.setAsync(false);
	UtilDWR.getUser(Usuario.setUsuario.bind(Usuario));
	dwr.engine.setAsync(true);
});

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
   }
};
