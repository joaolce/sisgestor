/*
 * Projeto: sisgestor
 * Cria��o: 04/02/2009 por Thiago
 */
package br.com.sisgestor.util.constantes;

/**
 * Classe para as constantes de tamanhos de campo para o banco de dados.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public final class ConstantesDB {

	/*
	 * ATEN��O: Se fizer mudan�a aqui, altere tamb�m no validator e nas jsp�s.
	 */

	/** Tamanho do campo content type do anexo. */
	public static final int		CONTENT_TYPE					= 50;

	/** Tamanho do campo Descri��o */
	public static final int		DESCRICAO						= 200;

	/** Tamanho do campo Nome */
	public static final int		NOME								= 100;

	/** Tamanho do valor do campo */
	public static final int		VALOR_CAMPO						= 100;

	/** Tamanho do campo Login */
	public static final int		LOGIN								= 15;

	/** Tamanho do campo E-mail */
	public static final int		EMAIL								= 50;

	/** Tamanho do campo sigla */
	public static final int		SIGLA								= 10;

	/** Tamanho do campo senha */
	public static final int		SENHA								= 20;

	/** Defini��o do campo Login */
	public static final String	DEFINICAO_LOGIN				= "CHAR(" + LOGIN + ")";

	/** Defini��o do campo boolean */
	public static final String	DEFINICAO_BOOLEAN				= "BIT";

	/** Defini��o do campo Senha */
	public static final String	DEFINICAO_SENHA				= "CHAR(" + SENHA + ")";

	/** Defini��o do campo sigla */
	public static final String	DEFINICAO_SIGLA				= "CHAR(" + SIGLA + ")";

	/** Defini��o do campo descri��o de op��o de campo. */
	public static final String	DEFINICAO_DESCRICAO_OPCAO	= "CHAR(20)";

	/** Defini��o da quantidade de caracteres para o campo anota��o */
	public static final int		ANOTACAO							= 500;
}
