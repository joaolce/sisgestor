/*
 * Projeto: sisgestor
 * Cria��o: 02/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.apresentacao.login.LoginHelper;
import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * DWR para utilidades.
 * 
 * @author Jo�o L�cio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * Finaliza sess�o do usu�rio.
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
	public Usuario getUser() { //NOPMD by Jo�o L�cio - deixar p�blico o m�todo para o javascript
		return super.getUser();
	}

	/**
	 * Verifica se o usu�rio atual possui a permiss�o.
	 * 
	 * @param permissao c�digo da permiss�o
	 * @return <code>true</code> se possuir;<br>
	 *         <code>false</code>se n�o.
	 */
	public boolean usuarioTemPermissao(String permissao) {
		return this.getRequest().isUserInRole(permissao);
	}
}
