/*
 * Projeto: sisgestor
 * Criação: 16/09/2009 por João Lúcio
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Testes para classe {@link DataUtil}.
 */
public class DataUtilTest {

	/**
	 * Teste do método: {@link DataUtil#ehDataValida(String)}. <br>
	 * Cenário: método retorna <code>true</code>
	 */
	@Test
	public void testEhDataValidaTrue() {
		assertTrue(DataUtil.ehDataValida("01/01/1990"));
		assertTrue(DataUtil.ehDataValida("29/02/2000"));
		assertTrue(DataUtil.ehDataValida("31/10/2009"));
	}

	/**
	 * Teste do método: {@link DataUtil#ehDataValida(String)}. <br>
	 * Cenário: método retorna <code>false</code>
	 */
	@Test
	public void testEhDataValidaFalse() {
		assertFalse(DataUtil.ehDataValida("33/01/1990"));
		assertFalse(DataUtil.ehDataValida("29/02/2001"));
		assertFalse(DataUtil.ehDataValida("31/11/2009"));
	}
}
