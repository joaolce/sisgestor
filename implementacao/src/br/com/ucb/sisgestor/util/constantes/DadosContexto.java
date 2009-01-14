/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.constantes;

/**
 * Constantes do contexto web.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public interface DadosContexto {

	/** Data do último login */
	public final String	DATA_LOGIN					= "dataLogin";
	/** Indica se foi erro do container */
	public final String	ERRO_CONTAINER				= "errorContainer";
	/** Hora do último login */
	public final String	HORA_LOGIN					= "horaLogin";
	/** Total de registros da pesquisa de paginação */
	public final String	TOTAL_PESQUISA_SESSAO	= "totalPesquisa";
	/** Usuário atual na sessão */
	public final String	USUARIOSESSAO				= "usuarioSessao";
}
