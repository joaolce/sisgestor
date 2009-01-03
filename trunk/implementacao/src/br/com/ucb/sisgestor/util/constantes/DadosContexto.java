/*
 * Projeto: SisGestor
 * Cria��o: 25/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.constantes;

/**
 * Constantes do contexto web.
 * 
 * @author Jo�o L�cio
 * @since 25/10/2008
 */
public interface DadosContexto {

	/** Data do �ltimo login */
	public final String	DATA_LOGIN		= "dataLogin";
	/** Hora do �ltimo login */
	public final String	HORA_LOGIN		= "horaLogin";
	/** Indica se foi erro do container */
	public final String	ERRO_CONTAINER	= "errorContainer";
	/** Usu�rio atual na sess�o */
	public final String	USUARIOSESSAO	= "usuarioSessao";
}
