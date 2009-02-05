/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.constantes;

/**
 * Classe para as constantes de tamanhos de campo para o banco de dados.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public final class ConstantesDB {

	/** Tamanho do campo Descrição */
	public static final int		DESCRICAO			= 50;

	/** Tamanho do campo Nome */
	public static final int		NOME					= 60;

	/** Tamanho do campo Login */
	public static final int		LOGIN					= 15;

	/** Tamanho do campo E-mail */
	public static final int		EMAIL					= 50;

	/** Definição do campo Login */
	public static final String	DEFINICAO_LOGIN	= "CHAR(15)";

	/** Definição do campo boolean */
	public static final String	DEFINICAO_BOOLEAN	= "BIT";

	/** Definição do campo Senha */
	public static final String	DEFINICAO_SENHA	= "CHAR(20)";

	/** Definição do campo */
	public static final String	DEFINICAO_SIGLA	= "CHAR(10)";
}
