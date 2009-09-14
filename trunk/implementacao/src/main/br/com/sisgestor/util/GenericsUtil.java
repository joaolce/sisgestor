/*
 * Projeto: SisGestor
 * Cria��o: 23/10/2008 por Jo�o L�cio
 */
package br.com.sisgestor.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Classe utilit�ria para auxiliaar a convers�o de objetos sem "generics" para objetos com "generics".
 * <p>
 * Serve tamb�m para reduzir a quantidade de {@literal @SuppressWarnings("unchecked")} espalhados pelo
 * c�digo-fonte, mantendo-os dentro desta classe.
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
	 * Realiza um "type-cast" de uma classe para uma subclasse de T. Equivalente a Class.asSubclass sem o
	 * "extends".
	 * <p>
	 * Exemplo de uso:
	 * <code>Class&lt;MinhaClasse&gt; classe = GenericsUtil.asClass(umaClasse, MinhaClasse.class);</code>
	 * </p>
	 * 
	 * @param <T> o tipo da classe de destino.
	 * @param oldClass a classe de origem.
	 * @param newClass a classe T correspondente.
	 * @return o "type-cast" do objeto.
	 */
	public static <T>Class<T> asClass(Class oldClass, Class<T> newClass) {
		if (newClass.isAssignableFrom(oldClass)) {
			return oldClass;
		}
		throw new ClassCastException(oldClass.getCanonicalName() + " does not conform to"
				+ newClass.getCanonicalName());
	}

	/**
	 * Realiza um "type-cast" de um objeto para a interface {@link Comparable}.
	 * 
	 * @param <T> o tipo da classe do objeto.
	 * @param object o objeto
	 * @return o "type-cast" do objeto.
	 */
	public static <T>Comparable<T> asComparable(T object) {
		return (Comparable<T>) object;
	}

	/**
	 * Retorna uma cole��o do tipo {@link Collection} que usa "generics" a partir de uma cole��o que n�o usa
	 * "generics".
	 * 
	 * @see Collections#checkedCollection(Collection, Class)
	 * 
	 * @param <T> O tipo dos elementos que a cole��o contem.
	 * @param collection uma cole��o com elementos do tipo T.
	 * @param type a classe T correspondente.
	 * @return cole��o 'checked' correspondente a collection.
	 */
	public static <T>Collection<T> checkedCollection(Collection collection, Class<T> type) {
		if (collection == null) {
			return null;
		}
		return Collections.checkedCollection(collection, type);
	}

	/**
	 * Retorna um {@link Iterator} que usa "generics" a partir de um {@link Iterator} que n�o usa "generics".
	 * 
	 * @param <T> o tipo dos elementos do iterator.
	 * @param it o iterator com elementos do tipo T.
	 * @param type a classe T correspondente.
	 * @return iterator 'checked' correspondente a it.
	 */
	public static <T>Iterator<T> checkedIterator(Iterator it, Class<T> type) {
		return new CheckedIterator(it, type);
	}

	/**
	 * Retorna uma lista do tipo {@link List} que usa "generics" a partir de uma lista que n�o usa "generics".
	 * 
	 * @see Collections#checkedList(List, Class)
	 * 
	 * @param <T> O tipo dos elementos que a lista cont�m.
	 * @param list uma lista com elementos do tipo T
	 * @param type a classe T correspondente
	 * @return lista 'checked' correspondente a list
	 */
	public static <T>List<T> checkedList(List list, Class<T> type) {
		return Collections.checkedList(list, type);
	}

	/**
	 * Retorna um mapa do tipo {@link Map} que usa "generics" a partir de um mapa que n�o usa "generics."
	 * 
	 * @see Collections#checkedMap(Map, Class, Class)
	 * 
	 * @param <K> O tipo das chaves do mapa.
	 * @param <V> O tipo dos valores do mapa.
	 * @param map um mapa com elementos do tipo <K,V>
	 * @param keyType a classe K da chave.
	 * @param valueType a classe V do valor.
	 * @return mapa 'checked' correspondente a map.
	 */
	public static <K, V>Map<K, V> checkedMap(Map map, Class<K> keyType, Class<V> valueType) {
		if (map == null) {
			return null;
		}
		return Collections.checkedMap(map, keyType, valueType);
	}

	/**
	 * Retorna a classe de um objeto.
	 * 
	 * @param <T> o tipo da classe do objeto.
	 * @param object o objeto.
	 * @return a classe do objeto.
	 */
	public static <T>Class<T> getStrictClass(T object) {
		return (Class<T>) object.getClass();
	}
}
