/**
 * Recuperar o usu�rio atual do sistema para o Javascript
 * (n�o � necess�rio importar nenhum JS na p�gina)
 * 
 * @author deinf.sdantas
 */
var Usuario = Class.create();
Usuario = {
	_usuarioVO: null,
	getUsuario: function(){
		return this._usuarioVO;
	},
	setUsuario: function(usuarioVO){
		this._usuarioVO = usuarioVO;
	}
};
Event.observe(window, "load", function(event){
	UsuarioSistema.getUser(Usuario.setUsuario.bind(Usuario));
});