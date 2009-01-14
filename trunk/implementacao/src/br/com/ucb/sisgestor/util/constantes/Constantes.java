/*
 * Projeto: sisgestor
 * Cria��o: 14/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.constantes;

import br.com.ucb.sisgestor.util.DataUtil;

/**
 * Constantes do sistema.
 * 
 * @author Jo�o L�cio
 * @since 14/01/2009
 */
public interface Constantes {

	/** Vers�o atual do sistema. */
	public final String	VERSAO		= "0.1";

	/** Data da vers�o atual do sistema. */
	public final String	VERSAO_DATA	= DataUtil.converteDateToString(DataUtil.getDate(12, 1, 2009));
}
