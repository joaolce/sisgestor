/*
 * Projeto: sisgestor
 * Criação: 14/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.util.constantes;

import br.com.ucb.sisgestor.util.DataUtil;

/**
 * Constantes do sistema.
 * 
 * @author João Lúcio
 * @since 14/01/2009
 */
public interface Constantes {

	/** Versão atual do sistema. */
	public final String	VERSAO		= "0.1";

	/** Data da versão atual do sistema. */
	public final String	VERSAO_DATA	= DataUtil.converteDateToString(DataUtil.getDate(12, 1, 2009));
}
