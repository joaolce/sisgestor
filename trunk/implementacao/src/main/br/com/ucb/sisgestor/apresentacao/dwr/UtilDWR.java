/*
 * Projeto: sisgestor
 * Criação: 02/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.apresentacao.login.LoginHelper;
import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * DWR para utilidades.
 * 
 * @author João Lúcio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * Finaliza sessão do usuário.
	 */
	public void finalizarSessao() {
		new LoginHelper().doLogout(this.getRequest());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage(String key, String... args) {
		return super.getMessage(key, args);
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
	 * @param permissao código da permissão
	 * @return <code>true</code> se possuir;<br>
	 *         <code>false</code>se não.
	 */
	public boolean usuarioTemPermissao(String permissao) {
		return this.getRequest().isUserInRole(permissao);
	}
}
