/*
 * Projeto: sisgestor
 * Cria��o: 15/09/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.Test;

/**
 * Testes para a classe {@link Extenso}.
 */
public class ExtensoTest {

	private Extenso extenso;

	/**
	 * Teste do m�todo: {@link Extenso#toString()}. <br>
	 * Cen�rio: n�mero double, decimal
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValorMenorQueZero() {
		extenso = new Extenso(-1);
	}

	/**
	 * Teste do m�todo: {@link Extenso#toString()}. <br>
	 * Cen�rio: n�mero double, inteiro
	 */
	@Test
	public void testValorInteiroDouble() {
		extenso = new Extenso(22);
		assertEquals("vinte e dois reais", extenso.toString());
		extenso = new Extenso(22.00);
		assertEquals("vinte e dois reais", extenso.toString());
		extenso = new Extenso(2000000000);
		assertEquals("dois bilh�es de reais", extenso.toString());
		extenso = new Extenso(100);
		assertEquals("cem reais", extenso.toString());
	}

	/**
	 * Teste do m�todo: {@link Extenso#toString()}. <br>
	 * Cen�rio: n�mero double, decimal
	 */
	@Test
	public void testValorDecimalDouble() {
		extenso = new Extenso(22.674);
		assertEquals("vinte e dois reais e sessenta e sete centavos", extenso.toString());
		extenso = new Extenso(20.01);
		assertEquals("vinte reais e um centavo", extenso.toString());
	}

	/**
	 * Teste do m�todo: {@link Extenso#toString()}. <br>
	 * Cen�rio: n�mero BigDecimal, inteiro
	 */
	@Test
	public void testValorInteiroBigDecimal() {
		extenso = new Extenso(BigDecimal.ZERO);
		assertEquals("", extenso.toString());
		extenso = new Extenso(BigDecimal.ONE);
		assertEquals("um real", extenso.toString());
		extenso = new Extenso(new BigDecimal("5622346"));
		assertEquals("cinco milh�es e seiscentos e vinte e dois mil e trezentos e quarenta e seis reais",
				extenso.toString());
		extenso = new Extenso(new BigDecimal("42300000046"));
		assertEquals("quarenta e dois bilh�es e trezentos milh�es e quarenta e seis reais", extenso.toString());
	}

	/**
	 * Teste do m�todo: {@link Extenso#toString()}. <br>
	 * Cen�rio: n�mero BigDecimal, inteiro
	 */
	@Test
	public void testValorDecimalBigDecimal() {
		extenso = new Extenso(new BigDecimal("40316.34"));
		assertEquals("quarenta mil e trezentos e desesseis reais e trinta e quatro centavos", extenso
				.toString());
	}
}
