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
	public final String	DATA_LOGIN		= "dataLogin";
	/** Hora do último login */
	public final String	HORA_LOGIN		= "horaLogin";
	/** Indica se foi erro do container */
	public final String	ERRO_CONTAINER	= "errorContainer";
	/** Usuário atual na sessão */
	public final String	USUARIOSESSAO	= "usuarioSessao";
}
