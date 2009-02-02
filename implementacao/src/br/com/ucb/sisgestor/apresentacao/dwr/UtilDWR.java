/*
 * Projeto: sisgestor
 * Cria��o: 02/02/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * DWR para utilidades.
 * 
 * @author Jo�o L�cio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario getUser() { //NOPMD by Jo�o L�cio - deixar p�blico o m�todo para o javascript
		return super.getUser();
	}
}
