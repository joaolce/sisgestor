/*
 * Sistema: sisgestor
 * Criação: 24/12/2009
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Testes para {@link Utils}.
 */
public class UtilsTest {

	private Utils utils = Utils.get();

	/**
	 * Teste do método: {@link Utils#completaComZero(String, int)}.
	 */
	@Test
	public void testCompletaComZeroNull() {
		assertNull(utils.completaComZero(null, 0));
	}

	/**
	 * Teste do método: {@link Utils#completaComZero(String, int)}.
	 */
	@Test
	public void testCompletaComZeroStringVazia() {
		assertEquals("0", utils.completaComZero("", 1));
		assertEquals("", utils.completaComZero("", 0));
	}

	/**
	 * Teste do método: {@link Utils#completaComZero(String, int)}.
	 */
	@Test
	public void testCompletaComZeroOk() {
		assertEquals("0013", utils.completaComZero("13", 4));
		assertEquals("16", utils.completaComZero("16", 2));
	}
}
