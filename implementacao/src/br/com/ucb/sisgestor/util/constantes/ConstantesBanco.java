/*
 * Projeto: sisgestor
 * Cria��o: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.constantes;


/**
 * Interface para as constantes de tamanhos de campo para o banco de dados
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface ConstantesBanco {

	/** Tamanho do campo Descric��o */
	int		DESCRICAO			= 50;

	/** Tamanho do campo Nome */
	int		NOME					= 60;

	/** Tamanho do campo Login */
	int		LOGIN					= 15;

	/** Tamanho do campo E-mail */
	int		EMAIL					= 50;

	/** Defini��o do campo Login */
	String	DEFINICAO_LOGIN	= "CHAR(15)";

	/** Defini��o do campo boolean */
	String	DEFINICAO_BOOLEAN	= "CHAR(1)";

	/** Defini��o do campo Senha */
	String	DEFINICAO_SENHA	= "CHAR(20)";

	/** Defini��o do campo */
	String	DEFINICAO_SIGLA	= "CHAR(10)";
}
