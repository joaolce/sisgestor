/**
 * Recuperar o usuário atual do sistema para o Javascript
 * (não é necessário importar nenhum JS na página)
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