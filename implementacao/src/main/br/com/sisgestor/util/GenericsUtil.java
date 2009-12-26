/*
 * Projeto: SisGestor
 * Cria��o: 23/10/2008 por Jo�o L�cio
 */
package br.com.sisgestor.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Classe utilit�ria para auxiliaar a convers�o de objetos sem "generics" para objetos com
 * "generics".
 * <p>
 * Serve tamb�m para reduzir a quantidade de {@literal @SuppressWarnings("unchecked")} espalhados
 * pelo c�digo-fonte, mantendo-os dentro desta classe.
 * </p>
 * 
 * @author Jo�o L�cio
 * @since 23/10/2008
 */
@SuppressWarnings("unchecked")
public final class GenericsUtil {

	/**
	 * Construtor privado (classe utilit�ria).
	 */
	private GenericsUtil() {
	}

	/**
	 * Retorna uma cole��o do tipo {@link Collection} que usa "generics" a partir de uma cole��o que
	 * n�o usa "generics".
	 * 
	 * @see Collections#checkedCollection(Collection, Class)
	 * 
	 * @param <T> O tipo dos elementos que a cole��o contem.
	 * @param collection uma cole��o com elementos do tipo T.
	 * @param type a classe T correspondente.
	 * @return cole��o 'checked' correspondente a collection.
	 */
	public static <T> Collection<T> checkedCollection(Collection collection, Class<T> type) {
		if (collection == null) {
			return null;
		}
		return Collections.checkedCollection(collection, type);
	}

	/**
	 * Retorna um {@link Iterator} que usa "generics" a partir de um {@link Iterator} que n�o usa
	 * "generics".
	 * 
	 * @param <T> o tipo dos elementos do iterator.
	 * @param it o iterator com elementos do tipo T.
	 * @param type a classe T correspondente.
	 * @return iterator 'checked' correspondente a it.
	 */
	public static <T> Iterator<T> checkedIterator(Iterator it, Class<T> type) {
		return new CheckedIterator(it, type);
	}

	/**
	 * Retorna uma lista do tipo {@link List} que usa "generics" a partir de uma lista que n�o usa
	 * "generics".
	 * 
	 * @see Collections#checkedList(List, Class)
	 * 
	 * @param <T> O tipo dos elementos que a lista cont�m.
	 * @param list uma lista com elementos do tipo T
	 * @param type a classe T correspondente
	 * @return lista 'checked' correspondente a list
	 */
	public static <T> List<T> checkedList(List list, Class<T> type) {
		return Collections.checkedList(list, type);
	}

}
