/*
 * Sistema: sisgestor
 * Criação: 26/12/2009
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Teste para classe {@link ParametrosURL}.
 */
public class ParametrosURLTest {

	private ParametrosURL parametrosURL;

	/**
	 * Configuração do teste.
	 */
	@Before
	public void setUp() {
		parametrosURL = new ParametrosURL();
	}

	/**
	 * Teste do método {@link ParametrosURL#addParametro(String, Object)}.
	 */
	@Test
	public void testAddParametroNull() {
		parametrosURL.addParametro("teste", null);
		assertEquals("url?teste=", parametrosURL.aggregateParams("url"));
	}

	/**
	 * Teste do método {@link ParametrosURL#addParametro(String, Object)}.
	 */
	@Test
	public void testAddParametroValorValido() {
		parametrosURL.addParametro("teste1", 1);
		parametrosURL.addParametro("teste2", 2);
		assertEquals("url?teste1=1&teste2=2", parametrosURL.aggregateParams("url"));
	}
}
