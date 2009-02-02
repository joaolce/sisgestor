/*
 * Projeto: sisgestor
 * Criação: 02/02/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Usuario;

/**
 * DWR para utilidades.
 * 
 * @author João Lúcio
 * @since 02/02/2009
 */
public class UtilDWR extends BaseDWR {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario getUser() { //NOPMD by João Lúcio - deixar público o método para o javascript
		return super.getUser();
	}
}
