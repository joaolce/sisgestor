/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe respons�vel de recuperar um n�mero e transform�-lo escrito por extenso, em valor monet�rio real R$
 * 
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public final class Extenso {

	private List<Integer>	modulos;
	private BigInteger		numero;
	private String				numeros[][]				=
																		{
			{"zero", "um", "dois", "tr�s", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze",
			"doze", "treze", "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
			{"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
			{"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos",
			"oitocentos", "novecentos"}						};
	private String				qualificadores[][]	=
																		{ {"centavo", "centavos"}, {"", ""}, {"mil", "mil"},
			{"milh�o", "milh�es"}, {"bilh�o", "bilh�es"}, {"trilh�o", "trilh�es"},
			{"quatrilh�o", "quatrilh�es"}, {"quintilh�o", "quintilh�es"}, {"sextilh�o", "sextilh�es"},
			{"septilh�o", "septilh�es"}						};

	/**
	 * Cria uma nova inst�ncia do tipo Extenso
	 */
	public Extenso() {
		this.modulos = new ArrayList<Integer>();
	}

	/**
	 * Cria uma nova inst�ncia do tipo Extenso
	 * 
	 * @param dec valor para colocar por extenso
	 */
	public Extenso(BigDecimal dec) {
		this();
		this.setNumero(dec);
	}

	/**
	 * Cria uma nova inst�ncia do tipo Extenso
	 * 
	 * @param dec valor para colocar por extenso
	 */
	public Extenso(double dec) {
		this();
		this.setNumero(dec);
	}

	/**
	 * Armazena o n�mero
	 * 
	 * @param dec o n�mero a armazenar
	 */
	public void setNumero(BigDecimal dec) {
		if (dec.longValue() < 0) {
			throw new IllegalArgumentException("n�mero n�o pode ser menor que zero");
		}
		// Converte para inteiro arredondando os centavos
		this.numero =
				dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).toBigInteger();

		// Adiciona valores
		this.modulos.clear();
		if (this.numero.equals(BigInteger.ZERO)) {
			// Centavos
			this.modulos.add(Integer.valueOf(0));
			// Valor
			this.modulos.add(Integer.valueOf(0));
		} else {
			// Adiciona centavos
			this.adicionaModulo(100);

			// Adiciona grupos de 1000
			while (!this.numero.equals(BigInteger.ZERO)) {
				this.adicionaModulo(1000);
			}
		}
	}

	/**
	 * Armazena numero
	 * 
	 * @param dec valor a armazenar
	 */
	public void setNumero(double dec) {
		this.setNumero(new BigDecimal(dec));
	}

	/**
	 * Valor em {@link String} de {@link Extenso}
	 * 
	 * @return a String da classe
	 */
	@Override
	public String toString() { //NOPMD by Jo�o L�cio - n�o d� para quebrar
		StringBuffer buf = new StringBuffer();

		int ct;

		for (ct = this.modulos.size() - 1; ct > 0; ct--) {
			// Se ja existe texto e o atual n�o � zero
			if ((buf.length() > 0) && !this.ehGrupoZero(ct)) {
				buf.append(" e ");
			}
			buf.append(this.numToString(this.modulos.get(ct).intValue(), ct));
		}
		if (buf.length() > 0) {
			if (this.ehUnicoGrupo()) {
				buf.append(" de ");
			}
			while (buf.toString().endsWith(" ")) {
				buf.setLength(buf.length() - 1);
			}
			if (this.ehPrimeiroGrupoUm()) {
				buf.insert(0, "h");
			}
			if ((this.modulos.size() == 2) && (this.modulos.get(1).intValue() == 1)) {
				buf.append(" real");
			} else {
				buf.append(" reais");
			}
			if (this.modulos.get(0).intValue() != 0) {
				buf.append(" e ");
			}
		}
		if (this.modulos.get(0).intValue() != 0) {
			buf.append(this.numToString(this.modulos.get(0).intValue(), 0));
		}
		return buf.toString();
	}

	/**
	 * Faz a divis�o do valor atual com o informado, � armazenado o resultado na vari�vel modulos e �
	 * atualizado o numero com o valor do resto
	 * 
	 * @param divisor valor a fazer a divis�o
	 */
	private void adicionaModulo(int divisor) {
		// Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido divisor
		BigInteger[] newNum = this.numero.divideAndRemainder(BigInteger.valueOf(divisor));

		// Adiciona modulo
		this.modulos.add(Integer.valueOf(newNum[1].intValue()));

		// Altera numero
		this.numero = newNum[0];
	}

	/**
	 * Verifica se um determinado valor na posi��o do m�dulo informada � igual a zero
	 * 
	 * @param ps n�mero da posi��o
	 * 
	 * @return <code>true</code>, se igual a zero;<br>
	 *         <code>false</code>, se n�o.
	 */
	private boolean ehGrupoZero(int ps) {
		if ((ps <= 0) || (ps >= this.modulos.size())) {
			return true;
		}
		return this.modulos.get(ps).intValue() == 0;
	}

	/**
	 * Verifica se a primeira trinca de n�meros do n�mero � igual a 1
	 * 
	 * @return <code>true</code> caso seja, <code>false</code> caso contr�rio
	 */
	private boolean ehPrimeiroGrupoUm() {
		if (this.modulos.get(this.modulos.size() - 1).intValue() == 1) {
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
		if (this.modulos.size() <= 3) {
			return false;
		}
		if (!this.ehGrupoZero(1) && !this.ehGrupoZero(2)) {
			return false;
		}
		boolean hasOne = false;
		for (int i = 3; i < this.modulos.size(); i++) {
			if (this.modulos.get(i).intValue() != 0) {
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
					buf.append(this.numeros[2][0]);
				} else {
					buf.append(this.numeros[2][centena]);
				}
			}

			if ((buf.length() > 0) && (dezena != 0)) {
				buf.append(" e ");
			}
			if (dezena > 19) {
				dezena /= 10;
				buf.append(this.numeros[1][dezena - 2]);
				if (unidade != 0) {
					buf.append(" e ");
					buf.append(this.numeros[0][unidade]);
				}
			} else if ((centena == 0) || (dezena != 0)) {
				buf.append(this.numeros[0][dezena]);
			}

			buf.append(' ');
			if (numero == 1) {
				buf.append(this.qualificadores[escala][0]);
			} else {
				buf.append(this.qualificadores[escala][1]);
			}
		}
		return buf.toString();
	}
}
