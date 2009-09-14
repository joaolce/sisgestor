/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.sisgestor.util.hibernate;

/**
 * Constantes com os nomes de classes que representam tipos para o Hibernate.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public final class HibernateUserTypeConstants {

	/**
	 * Classe com o Hibernate user type para enumerações que implementam a interface
	 */
	public static final String CODIGO_INTEGER = "br.com.sisgestor.util.hibernate.IntegerUserType";

	/** Enum que implementa o user type. */
	public static final String TIPO_CAMPO_ENUM = "br.com.sisgestor.entidade.TipoCampoEnum";

	/** Enum que implementa o user type. */
	public static final String TIPO_ACAO_ENUM = "br.com.sisgestor.entidade.TipoAcaoEnum";
}
