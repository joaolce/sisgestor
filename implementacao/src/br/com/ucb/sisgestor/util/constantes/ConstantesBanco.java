/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.util.constantes;


/**
 * Interface para as constantes de tamanhos de campo para o banco de dados
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface ConstantesBanco {

	/** Tamanho do campo Descricção */
	int		DESCRICAO			= 50;

	/** Tamanho do campo Nome */
	int		NOME					= 60;

	/** Tamanho do campo Login */
	int		LOGIN					= 15;

	/** Tamanho do campo E-mail */
	int		EMAIL					= 50;

	/** Definição do campo Login */
	String	DEFINICAO_LOGIN	= "CHAR(15)";

	/** Definição do campo boolean */
	String	DEFINICAO_BOOLEAN	= "CHAR(1)";

	/** Definição do campo Senha */
	String	DEFINICAO_SENHA	= "CHAR(20)";

	/** Definição do campo */
	String	DEFINICAO_SIGLA	= "CHAR(10)";
}
