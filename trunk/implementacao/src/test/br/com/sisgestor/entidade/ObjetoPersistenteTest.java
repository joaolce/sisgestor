/*
 * Projeto: sisgestor
 * Criação: 16/12/2009 por João Lúcio
 */
package br.com.sisgestor.entidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * Testes para {@link ObjetoPersistente}.
 */
public class ObjetoPersistenteTest {

	private ObjetoPersistente objetoPersistente;

	/**
	 * Teste do método: {@link ObjetoPersistente#toString()}.
	 */
	@Test
	public void testEqualsTrue() {
		objetoPersistente = new ObjetoPersistente();
		objetoPersistente.setId(1);
		ObjetoPersistente objetoPersistenteIgual = new ObjetoPersistente();
		objetoPersistenteIgual.setId(1);

		assertEquals(objetoPersistenteIgual, objetoPersistente);
	}

	/**
	 * Teste do método: {@link ObjetoPersistente#toString()}.
	 */
	@Test
	public void testEqualsNull() {
		objetoPersistente = new ObjetoPersistente();
		objetoPersistente.setId(1);

		assertFalse(objetoPersistente.equals(null)); //NOPMD
	}

	/**
	 * Teste do método: {@link ObjetoPersistente#toString()}.
	 */
	@Test
	public void testEqualsClasseDiferente() {
		objetoPersistente = new ObjetoPersistente();
		objetoPersistente.setId(1);
		ObjetoPersistente objetoPersistenteDiferente = new ObjetoPersistente() {};
		objetoPersistenteDiferente.setId(1);

		assertFalse(objetoPersistente.equals(objetoPersistenteDiferente));
	}
}
