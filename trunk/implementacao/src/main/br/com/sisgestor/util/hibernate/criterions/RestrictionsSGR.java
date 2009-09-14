/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por João Lúcio
 */
package br.com.sisgestor.util.hibernate.criterions;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 * Restrições personalizadas do SisGestor para o {@link Criteria}.
 * 
 * @author João Lúcio
 * @since 07/04/2009
 */
public class RestrictionsSGR {

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
	 * @return {@link Criterion} gerado
	 */
	public static Criterion eqPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da função
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform eqWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "=");
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '>='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
	 * @return {@link Criterion} gerado
	 */
	public static Criterion gePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, ">=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '>='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da função
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform geWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, ">=");
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '>'.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
	 * @return {@link Criterion} gerado
	 */
	public static Criterion gtPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, ">",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '>'.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da função
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform gtWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, ">");
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<='.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
	 * @return {@link Criterion} gerado
	 */
	public static Criterion lePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<=",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<='.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da função
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform leWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<=");
	}

/**
	* Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<'.
	* 
	* @param primeiraPropriedade nome da primeira propriedade
	* @param segundaPropriedade nome da segunda propriedade
	* @param primeiraFuncao nome da primeira função
	* @param segundaFuncao nome da segunda função
	* @return {@link Criterion} gerado
	*/
	public static Criterion ltPropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<",
				primeiraFuncao, segundaFuncao);
	}

/**
		 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<'.
		 * 
		 * @param propriedade nome da propriedade
		 * @param funcao nome da função
		 * @param valor valor a comparar
		 * @return {@link Criterion} gerado
		 */
	public static SimpleExpressionWithTransform ltWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<");
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<>'.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
	 * @return {@link Criterion} gerado
	 */
	public static Criterion nePropertyWithTransform(String primeiraPropriedade, String segundaPropriedade,
			String primeiraFuncao, String segundaFuncao) {
		return new PropertyExpressionWithTransform(primeiraPropriedade, segundaPropriedade, "<>",
				primeiraFuncao, segundaFuncao);
	}

	/**
	 * Cria uma expressão com transformação de dados para o hibernate, utilizando o operador '<>'.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao nome da função
	 * @param valor valor a comparar
	 * @return {@link Criterion} gerado
	 */
	public static SimpleExpressionWithTransform neWithTransform(String propriedade, String funcao, Object valor) {
		return new SimpleExpressionWithTransform(propriedade, funcao, valor, "<>");
	}
}
