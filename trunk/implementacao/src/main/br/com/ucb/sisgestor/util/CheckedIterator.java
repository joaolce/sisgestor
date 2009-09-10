/*
 * Projeto: SisGestor
 * Cria��o: 02/12/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util;

import java.util.Iterator;

/**
 * Wrapper de Iterator, usado para converter um {@link java.util.Iterator} sem "generics" para um iterador com
 * "generics", isto �, com o tipo do elemento).
 * 
 * @param <T> o tipo do elemento utilizado na itera��o.
 * @author Jo�o L�cio
 * @since 02/12/2008
 */
@SuppressWarnings("unchecked")
public final class CheckedIterator<T> implements Iterator {

	private Class<T>	elementClass;
	private Iterator	source;

	/**
	 * Construtor.
	 * 
	 * @param source Iterator original, n�o gen�rico.
	 * @param elementClass Classe dos elementos do iterator.
	 */
	public CheckedIterator(Iterator source, Class<T> elementClass) {
		this.source = source;
		this.elementClass = elementClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasNext() {
		return this.source.hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	public T next() {
		return this.elementClass.cast(this.source.next());
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove() {
		this.source.remove();
	}
}
