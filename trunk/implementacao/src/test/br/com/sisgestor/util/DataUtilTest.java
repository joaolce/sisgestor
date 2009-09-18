/*
 * Projeto: sisgestor
 * Criação: 16/09/2009 por João Lúcio
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.junit.Test;

/**
 * Testes para classe {@link DataUtil}.
 */
public class DataUtilTest {

	private DataUtil dataUtil = DataUtil.getInstancia();

	/**
	 * Teste do método: {@link DataUtil#ehDataValida(String)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testEhDataValida() {
		assertTrue(dataUtil.ehDataValida("01/01/1990"));
		assertTrue(dataUtil.ehDataValida("29/02/2000"));
		assertTrue(dataUtil.ehDataValida("31/10/2009"));
		assertFalse(dataUtil.ehDataValida("33/01/1990"));
		assertFalse(dataUtil.ehDataValida("29/02/2001"));
		assertFalse(dataUtil.ehDataValida("31/11/2009"));
	}

	/**
	 * Teste do método: {@link DataUtil#formataData(String, String, String)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testFormataData() {
		assertEquals("01/01/1990", dataUtil.formataData("1", "1", "1990"));
		assertEquals("29/02/2000", dataUtil.formataData("29", "2", "2000"));
		assertEquals("01/10/2009", dataUtil.formataData("1", "10", "2009"));
		assertEquals("27/12/2020", dataUtil.formataData("27", "12", "2020"));
		assertNull(dataUtil.formataData("32", "1", "1998"));
		assertNull(dataUtil.formataData("29", "2", "2002"));
		assertNull(dataUtil.formataData("31", "6", "2005"));
	}

	/**
	 * Teste do método: {@link DataUtil#getAno(Date)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetAno() {
		assertEquals(1969, dataUtil.getAno(new Date(0)));
		assertEquals(1970, dataUtil.getAno(new Date(100000000L)));
		assertEquals(2001, dataUtil.getAno(new Date(999999999999L)));
		assertEquals(1900, dataUtil.getAno(new Date(-2200000000000L)));
		assertEquals(1899, dataUtil.getAno(new Date(-2230000000000L)));
	}

	/**
	 * Teste do método: {@link DataUtil#getDataAtual()}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetDataAtual() {
		assertEquals(new Date(), dataUtil.getDataAtual());
	}

	/**
	 * Teste do método: {@link DataUtil#getDataHoraAtual()}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetDataHoraAtual() {
		assertEquals(new Date(), dataUtil.getDataHoraAtual());
	}
}
