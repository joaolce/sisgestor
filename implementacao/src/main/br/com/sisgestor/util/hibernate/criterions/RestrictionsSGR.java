/*
 * Projeto: sisgestor
 * Cria��o: 07/04/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util.hibernate.criterions;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 * Restri��es personalizadas do SisGestor para o {@link Criteria}.
 * 
 * @author Jo�o L�cio
 * @since 07/04/2009
 */
public class RestrictionsSGR {

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @return {@link Criterion} gerado
	 */
	public static Criterion eqPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da fun��o
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform eqWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "=");
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '>='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @return {@link Criterion} gerado
	 */
	public static Criterion gePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, ">=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '>='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da fun��o
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform geWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, ">=");
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '>'.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @return {@link Criterion} gerado
	 */
	public static Criterion gtPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, ">",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '>'.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da fun��o
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform gtWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, ">");
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @return {@link Criterion} gerado
	 */
	public static Criterion lePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da fun��o
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform leWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<=");
	}

/**
	* Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<'.
	* 
	* @param primeiraPropriedade nome da primeira propriedade
	* @param segundaPropriedade nome da segunda propriedade
	* @param primeiraFuncao nome da primeira fun��o
	* @param segundaFuncao nome da segunda fun��o
	* @return {@link Criterion} gerado
	*/
	public static Criterion ltPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<",
				primeiraFuncao, segundaFuncao);
	}

/**
		 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<'.
		 * 
		 * @param propriedade nome da propriedade
		 * @param funcao nome da fun��o
		 * @param valor valor a comparar
		 * @return {@link Criterion} gerado
		 */
	public static SimpleExpressionWithTransform ltWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<");
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<>'.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @return {@link Criterion} gerado
	 */
	public static Criterion nePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<>",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma express�o com transforma��o de dados para o hibernate, utilizando o operador '<>'.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da fun��o
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform neWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<>");
	}
}
