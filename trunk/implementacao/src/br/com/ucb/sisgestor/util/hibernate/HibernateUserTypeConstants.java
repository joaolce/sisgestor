/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;


/**
 * Constantes com os nomes de classes que representam tipos para o Hibernate.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public interface HibernateUserTypeConstants {

	/**
	 * Classe com o Hibernate user type para enumerações que implementam a interface
	 */
	public static final String	CODIGO_INTEGER	= "br.com.ucb.sisgestor.util.hibernate.IntegerUserType";
}
