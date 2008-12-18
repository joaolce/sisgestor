/*
 * Projeto: SisGestor
 * Criação: 10/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 10/12/2008
 */
public class LoginConstants {

	/** */
	public static final String	LOGIN_BUNDLE_NAME							= "bc_login";
	/** */
	public static final String	AMBIENTE_PROPERTY							= "bacen.security.ambiente";
	/** */
	public static final String	AMBIENTE_DESENVOLVIMENTO				= "DESEN";
	/** */
	public static final String	AMBIENTE_HOMOLOGACAO						= "HOMOLOGA";
	/** */
	public static final String	AMBIENTE_PRODUCAO							= "PROD";
	/** */
	public static final String	TELEFONE_PADRAO_PROPERTY				= "bacen.security.telefone.default";
	/** */
	public static final String	TITULO_PADRAO_PROPERTY					= "bacen.security.titulo.default";
	/** */
	public static final String	IMAGEM_PADRAO_PROPERTY					= "bacen.security.imagem.default";
	/** */
	public static final String	POLITICA_PADRAO_PROPERTY				= "bacen.security.politica.default";
	/** */
	public static final String	STATIC_URL_PARAM_DEPRECATED			= "bacen.security.static.url";
	/** */
	public static final String	STYLE_URL_PARAM_DEPRECATED				= "styleURL";
	/** */
	public static final String	CPF_ONLY_PARAM								= "bacen.security.cpf_only";
	/** */
	public static final String	IMAGE_BIG_URL_PARAM						= "imgbigURL";
	/** */
	public static final String	TITLE_PARAM									= "bacen.security.title";
	/** */
	public static final String	TITLE_LOGOUT_PARAM						= "bacen.security.title_logout";
	/** */
	public static final String	HOME_AFTER_LOGOUT_PARAM					= "bacen.security.homeafterlogout";
	/** */
	public static final String	HOME_AFTER_LOGOUT_OLD_PARAM			= "HOME_AFTER_LOGOUT";
	/** */
	public static final String	HOME_AFTER_LOGIN_PARAM					= "bacen.security.homeafterlogin";
	/** */
	public static final String	SEM_DEPENDENCIA_PARAM					= "bacen.security.semdependencia";
	/** */
	public static final String	TELEFONE_PARAM								= "bacen.security.telefone";
	/** */
	public static final String	IMAGEM_SISTEMA_PARAM						= "bacen.security.imagemsistema";
	/** */
	public static final String	NOME_SISTEMA_PARAM						= "bacen.security.nomesistema";
	/** */
	public static final String	PRIVACIDADE_URL_PARAM					= "bacen.security.urlpoliticaprivacidade";
	/** */
	public static final String	REDIRECT_ACTION							= "redirect";
	/** */
	public static final String	LOGOUT_ACTION								= "logout";
	/** */
	public static final String	MUDASENHA_ACTION							= "mudasenha";
	/** */
	public static final String	ERROR_ACTION								= "error";
	/** */
	public static final String	PWD_EXPIRATION_ACTION					= "expire";
	/** */
	public static final String	AJUDA_ACTION								= "ajuda";
	/** */
	public static final String	OPERACAO_PARAMETRO						= "operacao";
	/** */
	public static final String	UNIDADE_PARAMETRO							= "unidade";
	/** */
	public static final String	DEPENDENCIA_PARAMETRO					= "dependencia";
	/** */
	public static final String	OPERADOR_PARAMETRO						= "operador";
	/** */
	public static final String	CPF_PARAMETRO								= "cpf";
	/** */
	public static final String	SENHA_PARAMETRO							= "senha";
	/** */
	public static final String	SENHA_NOVA_PARAMETRO						= "senhanova";
	/** */
	public static final String	CONFIRMACAO_SENHA_NOVA_PARAMETRO		= "senhanova2";
	/** */
	public static final String	TROCA_SENHA_PARA_VENCER_PARAMETRO	= "changePwdOpt";
	/** */
	public static final String	CONF_TROCA_SENHA_PARA_VENCER_VALOR	= "SIM";
	/** */
	public static final String	PARAMETRO_CANCELAR_TROCA_SENHA		= "cancelar_troca";
	/** */
	public static final String	PARAMETRO_CONFIRMAR_TROCA_SENHA		= "confirmar_troca";
	/** */
	public static final String	LOGIN_OPERATION							= "login";
	/** */
	public static final String	CHANGE_PWD_OPERATION						= "altera";
	/** */
	public static final String	LOGOUT_OPERATION							= "logout";
	/** */
	public static final String	URL_LOGIN_SUCESSO							= "dologin?redirect=true";
	/** */
	public static final String	URL_TROCA_SENHA_SUCESSO					= "dologin?redirect=true";
	/** */
	public static final String	URL_TROCA_SENHA							= "dologin?mudasenha=true";
	/** */
	public static final String	URL_SENHA_PARA_VENCER					= "dologin?expire=true";
	/** */
	public static final String	URL_LOGIN									= "dologin";
	/** */
	public static final String	URL_AJUDA									= "dologin?ajuda=true";
	/** */
	public static final String	MENSAGEM_LOGIN_INVALIDO					=
																								"Identifica&ccedil;&atilde;o e/ou senha inv&aacute;lidas";
	/** */
	public static final String	MENSAGEM_ERRO_TROCA_SENHA				=
																								"N&atilde;o foi poss&iacute;vel trocar a senha.";
	/** */
	public static final String	MENSAGEM_SENHA_VENCIDA					= "A senha venceu e deve ser trocada.";
	/** */
	public static final String	BC_REMOTE_USER_ATTR						= "BC_REMOTE_USER";
	/** */
	public static final String	UNIDADE_ATTR								= "bacen.security.unidade";
	/** */
	public static final String	DEPENDENCIA_ATTR							= "bacen.security.dependencia";
	/** */
	/** */
	public static final String	OPERADOR_ATTR								= "bacen.security.operador";
	/** */
	public static final String	CPF_ATTR										= "bacen.security.cpf";
	/** */
	public static final String	REDIRECT_URL_ATTR							= "bacen.redirectURL";
	/** */
	public static final int		LOGIN_INVALIDO								= 1;
	/** */
	public static final int		LOGIN_BLOQUEADO							= 2;
	/** */
	public static final int		SENHA_VENCIDA								= 3;
	/** */
	public static final int		FALHA_TROCA_SENHA							= 4;
	/** */
	public static final int		FALHA_TROCA_SENHA_REDE					= 5;
	/** */
	public static final int		JBOSS_SERVER								= 0;
	/** */
	/** */
	public static final int		WEBSPHERE_SERVER							= 1;
	/** */
	public static final int		WEBLOGIC_SERVER							= 2;
	/** */
	public static final int		JBOSS4_SERVER								= 3;
	/** */
	public static final int		UNIDADE_LENGTH								= 5;
	/** */
	public static final int		DEPENDENCIA_LENGTH						= 4;
	/** */
	public static final int		OPERADOR_LENGTH							= 10;
	/** */
	public static final int		CPF_LENGTH									= 9;
	/** */
	public static final int		SENHA_LENGTH								= 8;

	private LoginConstants() {
	}
}
