/*
 * Projeto: sisgestor
 * Cria��o: 03/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Form para a action {@link ManterUsuarioAction}.
 * 
 * @author Jo�o L�cio
 * @since 03/01/2009
 */
public class ManterUsuarioActionForm extends BaseForm {

	private List<Usuario>	usuarios;

	/**
	 * Recupera o valor de usuarios
	 * 
	 * @return usuarios
	 */
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Atribui usuarios
	 * 
	 * @param usuarios o valor a ajustar em usuarios
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
