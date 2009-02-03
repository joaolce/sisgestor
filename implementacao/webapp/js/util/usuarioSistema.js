Event.observe(window, "load", function(event) {
	UtilDWR.getUser(Usuario.setUsuario.bind(Usuario));
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
   }
};
