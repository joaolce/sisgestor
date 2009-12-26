/*
 * Projeto: sisgestor
 * Criação: 14/01/2009 por João Lúcio
 */
package br.com.sisgestor.util.constantes;


/**
 * Constantes do sistema.
 * 
 * @author João Lúcio
 * @since 14/01/2009
 */
public final class Constantes {

	/** Versão atual do sistema. */
	public static final String VERSAO = "1.0.3";

	/** Data da versão atual do sistema. */
	public static final String VERSAO_DATA = "26/12/2009";

	/** Remetente de emails do sisgestor. */
	public static final String REMETENTE_EMAIL_SISGESTOR = "sisgestor@sisgestor.com";

	/** Assunto do e-mail enviado no lembrete de senha */
	public static final String ASSUNTO_EMAIL_LEMBRETE_SENHA = "SisGestor - Lembrete de senha";

	/** Assunto do email de novo usuário. */
	public static final String ASSUNTO_EMAIL_NOVO_USUARIO = "SisGestor - Cadastro com sucesso";

	/** Assunto do email de novo usuário. */
	public static final String ASSUNTO_EMAIL_TAREFA_TRANSFERIDA = "SisGestor - Nova Tarefa";

	/**
	 * Tamanho do anexo permitido. Ao alterar esta constante, verificar valor correspondente no
	 * arquivo struts-config.xml
	 * 
	 * tag controller, propriedade: maxFileSize="10MB"
	 * 
	 * Obs.: Atualmente está definido como 10MB
	 */
	public static final Integer TAMANHO_MAX_ANEXO_PERMITIDO = Integer.valueOf(10 * 1024 * 1024);
}
