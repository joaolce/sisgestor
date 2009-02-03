/*
 * Projeto: sisgestor
 * Criação: 02/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.apresentacao.login.LoginHelper;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;

/**
 * DWR para utilidades.
 * 
 * @author João Lúcio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * Finaliza sessão do usuário.<br>
	 * Método chamado a partir do javascript
	 * 
	 */
	public void finalizarSessao() {
		new LoginHelper().doLogout(super.getRequest());
	}

	/**
	 * Recupera a permissão de manter usuário.
	 * 
	 * @return permissão
	 */
	public String getPermissaoUsuario() {
		return ConstantesRoles.MANTER_USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario getUser() { //NOPMD by João Lúcio - deixar público o método para o javascript
		return super.getUser();
	}

	/**
	 * Verifica se o usuário atual possui a permissão.
	 * 
	 * @param permissao código da permissao
	 * @return <code>true</code> se estiver;<br>
	 *         <code>false</code>se não.
	 */
	public boolean usuarioTemPermissao(String permissao) {
		return this.getRequest().isUserInRole(permissao);
	}
}
