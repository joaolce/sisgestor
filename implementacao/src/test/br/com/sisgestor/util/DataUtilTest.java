/*
 * Projeto: sisgestor
 * Criação: 16/09/2009 por João Lúcio
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
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
		assertTrue(dataUtil.ehDataValida("1/1/1992"));
		assertTrue(dataUtil.ehDataValida("01/01/1990"));
		assertTrue(dataUtil.ehDataValida("29/02/2000"));
		assertTrue(dataUtil.ehDataValida("31/10/2009"));
		assertTrue(dataUtil.ehDataValida("31/1/1932"));
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

	/**
	 * Teste do método: {@link DataUtil#getDataSemHHMMSS(Calendar)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetDataSemHHMMSS() {
		Calendar calendar = Calendar.getInstance(DataUtil.LOCALE_PT_BR);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 2002);
		assertEquals(calendar.getTime(), dataUtil.getDataSemHHMMSS(calendar));
	}

	/**
	 * Teste do método: {@link DataUtil#getDate(int, int, int)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetDate() {
		Calendar calendar = Calendar.getInstance(DataUtil.LOCALE_PT_BR);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 2000);
		assertEquals(calendar.getTime(), dataUtil.getDate(1, 1, 2000));
	}

	/**
	 * Teste do método: {@link DataUtil#getStringDataAtualCompleta()}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testGetStringDataAtualCompleta() {
		assertEquals(DataUtil.DATA_FULL.format(new Date()), dataUtil.getStringDataAtualCompleta());
	}

	/**
	 * Teste do método: {@link DataUtil#stringToUtilDate(String)}. <br>
	 * Cenário: validações do método
	 */
	@Test
	public void testStringToUtilDate() {
		assertNull(dataUtil.stringToUtilDate("31/02/2000"));
		assertNull(dataUtil.stringToUtilDate("29/02/2001"));
		assertNull(dataUtil.stringToUtilDate("31/11/2006"));
		assertNull(dataUtil.stringToUtilDate("14/24/2004"));
		assertNull(dataUtil.stringToUtilDate("1/2004"));
		assertEquals(dataUtil.getDate(4, 12, 2004), dataUtil.stringToUtilDate("4/12/2004"));
		assertEquals(dataUtil.getDate(20, 2, 2005), dataUtil.stringToUtilDate("20/2/2005"));
		assertEquals(dataUtil.getDate(7, 10, 2009), dataUtil.stringToUtilDate("07/10/2009"));
	}

}
