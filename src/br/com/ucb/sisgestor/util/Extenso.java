/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável de recuperar um número e transformá-lo escrito por extenso, em valor monetário real R$
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public final class Extenso {

	private List<Integer>	modulos;
	private BigInteger		numero;
	private String				numeros[][]				=
																		{
			{"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze",
			"doze", "treze", "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
			{"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
			{"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos",
			"oitocentos", "novecentos"}						};
	private String				qualificadores[][]	=
																		{ {"centavo", "centavos"}, {"", ""}, {"mil", "mil"},
			{"milhão", "milhões"}, {"bilhão", "bilhões"}, {"trilhão", "trilhões"},
			{"quatrilhão", "quatrilhões"}, {"quintilhão", "quintilhões"}, {"sextilhão", "sextilhões"},
			{"septilhão", "septilhões"}						};

	/**
	 * Cria uma nova instância do tipo Extenso
	 */
	public Extenso() {
		modulos = new ArrayList<Integer>();
	}

	/**
	 * Cria uma nova instância do tipo Extenso
	 * 
	 * @param dec valor para colocar por extenso
	 */
	public Extenso(BigDecimal dec) {
		this();
		setNumero(dec);
	}

	/**
	 * Cria uma nova instância do tipo Extenso
	 * 
	 * @param dec valor para colocar por extenso
	 */
	public Extenso(double dec) {
		this();
		setNumero(dec);
	}

	/**
	 * Armazena o número
	 * 
	 * @param dec o número a armazenar
	 */
	public void setNumero(BigDecimal dec) {
		if (dec.longValue() < 0) {
			throw new IllegalArgumentException("número não pode ser menor que zero");
		}
		// Converte para inteiro arredondando os centavos
		numero = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).toBigInteger();

		// Adiciona valores
		modulos.clear();
		if (numero.equals(BigInteger.ZERO)) {
			// Centavos
			modulos.add(new Integer(0));
			// Valor
			modulos.add(new Integer(0));
		} else {
			// Adiciona centavos
			adicionaModulo(100);

			// Adiciona grupos de 1000
			while (!numero.equals(BigInteger.ZERO)) {
				adicionaModulo(1000);
			}
		}
	}

	/**
	 * Armazena numero
	 * 
	 * @param dec valor a armazenar
	 */
	public void setNumero(double dec) {
		setNumero(new BigDecimal(dec));
	}

	/**
	 * Valor em {@link String} de {@link Extenso}
	 * 
	 * @return a String da classe
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();

		int ct;

		for (ct = modulos.size() - 1; ct > 0; ct--) {
			// Se ja existe texto e o atual não é zero
			if ((buf.length() > 0) && !ehGrupoZero(ct)) {
				buf.append(" e ");
			}
			buf.append(numToString(modulos.get(ct).intValue(), ct));
		}
		if (buf.length() > 0) {
			if (ehUnicoGrupo()) {
				buf.append(" de ");
			}
			while (buf.toString().endsWith(" ")) {
				buf.setLength(buf.length() - 1);
			}
			if (ehPrimeiroGrupoUm()) {
				buf.insert(0, "h");
			}
			if ((modulos.size() == 2) && (modulos.get(1).intValue() == 1)) {
				buf.append(" real");
			} else {
				buf.append(" reais");
			}
			if (modulos.get(0).intValue() != 0) {
				buf.append(" e ");
			}
		}
		if (modulos.get(0).intValue() != 0) {
			buf.append(numToString(modulos.get(0).intValue(), 0));
		}
		return buf.toString();
	}

	/**
	 * Faz a divisão do valor atual com o informado, é armazenado o resultado na variável modulos e é
	 * atualizado o numero com o valor do resto
	 * 
	 * @param divisor valor a fazer a divisão
	 */
	private void adicionaModulo(int divisor) {
		// Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido divisor
		BigInteger[] newNum = numero.divideAndRemainder(BigInteger.valueOf(divisor));

		// Adiciona modulo
		modulos.add(new Integer(newNum[1].intValue()));

		// Altera numero
		numero = newNum[0];
	}

	/**
	 * Verifica se um determinado valor na posição do módulo informada é igual a zero
	 * 
	 * @param ps número da posição
	 * 
	 * @return
	 */
	private boolean ehGrupoZero(int ps) {
		if ((ps <= 0) || (ps >= modulos.size())) {
			return true;
		}
		return modulos.get(ps).intValue() == 0;
	}

	/**
	 * Verifica se a primeira trinca de números do número é igual a 1
	 * 
	 * @return <code>true</code> caso seja, <code>false</code> caso contrário
	 */
	private boolean ehPrimeiroGrupoUm() {
		if (modulos.get(modulos.size() - 1).intValue() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Returned Value
	 */
	private boolean ehUnicoGrupo() {
		if (modulos.size() <= 3) {
			return false;
		}
		if (!ehGrupoZero(1) && !ehGrupoZero(2)) {
			return false;
		}
		boolean hasOne = false;
		for (int i = 3; i < modulos.size(); i++) {
			if (modulos.get(i).intValue() != 0) {
				if (hasOne) {
					return false;
				}
				hasOne = true;
			}
		}
		return true;
	}


	/**
	 * Description of the Method
	 * 
	 * @param numero Description of Parameter
	 * @param escala Description of Parameter
	 * @return Description of the Returned Value
	 */
	private String numToString(int numero, int escala) {
		int unidade = (numero % 10);
		int dezena = (numero % 100); //* nao pode dividir por 10 pois verifica de 0..19
		int centena = (numero / 100);
		StringBuffer buf = new StringBuffer();

		if (numero != 0) {
			if (centena != 0) {
				if ((dezena == 0) && (centena == 1)) {
					buf.append(numeros[2][0]);
				} else {
					buf.append(numeros[2][centena]);
				}
			}

			if ((buf.length() > 0) && (dezena != 0)) {
				buf.append(" e ");
			}
			if (dezena > 19) {
				dezena /= 10;
				buf.append(numeros[1][dezena - 2]);
				if (unidade != 0) {
					buf.append(" e ");
					buf.append(numeros[0][unidade]);
				}
			} else if ((centena == 0) || (dezena != 0)) {
				buf.append(numeros[0][dezena]);
			}

			buf.append(" ");
			if (numero == 1) {
				buf.append(qualificadores[escala][0]);
			} else {
				buf.append(qualificadores[escala][1]);
			}
		}
		return buf.toString();
	}
}
