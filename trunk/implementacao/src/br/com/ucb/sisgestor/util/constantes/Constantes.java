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
public final class Constantes {

	/** Vers�o atual do sistema. */
	public static final String	VERSAO		= "0.2.1";

	/** Data da vers�o atual do sistema. */
	public static final String	VERSAO_DATA	= DataUtil.utilDateToString(DataUtil.getDate(7, 3, 2009));
	
	/** Assunto do e-mail enviado no lembrete de senha */
	public static final String LEMBRETE_EMAIL_ASSUNTO = "SisGestor - Lembrete de senha";
}
