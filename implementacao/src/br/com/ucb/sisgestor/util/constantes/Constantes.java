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
public final class Constantes {

	/** Versão atual do sistema. */
	public static final String		VERSAO								= "0.3.0";

	/** Data da versão atual do sistema. */
	public static final String		VERSAO_DATA							=
																							DataUtil.utilDateToString(DataUtil
																									.getDate(11, 4, 2009));

	/** Assunto do e-mail enviado no lembrete de senha */
	public static final String		ASSUNTO_EMAIL_LEMBRETE_SENHA	= "SisGestor - Lembrete de senha";

	/** Remetente de emails do sisgestor. */
	public static final String		REMETENTE_EMAIL_SISGESTOR		= "SisGestor";

	/** Assunto do email de novo usuário. */
	public static final String		ASSUNTO_EMAIL_NOVO_USUARIO		= "SisGestor - Cadastro com sucesso";

	/**
	 * Tamanho do anexo permitido. Ao alterar esta constante, verificar valor correspondente no arquivo
	 * struts-config.xml
	 * 
	 * tag controller, propriedade: maxFileSize="10MB"
	 * 
	 * Obs.: Atualmente está definido como 10MB
	 */
	public static final Integer	TAMANHO_MAX_ANEXO_PERMITIDO	= Integer.valueOf(10 * 1024 * 1024);
}
