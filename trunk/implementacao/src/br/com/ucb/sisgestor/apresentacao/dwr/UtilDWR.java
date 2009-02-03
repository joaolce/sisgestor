/*
 * Projeto: sisgestor
 * Cria��o: 02/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.apresentacao.login.LoginHelper;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;

/**
 * DWR para utilidades.
 * 
 * @author Jo�o L�cio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * Finaliza sess�o do usu�rio.<br>
	 * M�todo chamado a partir do javascript
	 * 
	 */
	public void finalizarSessao() {
		new LoginHelper().doLogout(super.getRequest());
	}

	/**
	 * Recupera a permiss�o de manter usu�rio.
	 * 
	 * @return permiss�o
	 */
	public String getPermissaoUsuario() {
		return ConstantesRoles.MANTER_USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario getUser() { //NOPMD by Jo�o L�cio - deixar p�blico o m�todo para o javascript
		return super.getUser();
	}

	/**
	 * Verifica se o usu�rio atual possui a permiss�o.
	 * 
	 * @param permissao c�digo da permissao
	 * @return <code>true</code> se estiver;<br>
	 *         <code>false</code>se n�o.
	 */
	public boolean usuarioTemPermissao(String permissao) {
		return this.getRequest().isUserInRole(permissao);
	}
}
