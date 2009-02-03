Event.observe(window, "load", function(event) {
	UtilDWR.getUser(Usuario.setUsuario.bind(Usuario));
});

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
   }
};
