/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.sisgestor.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.validator.GenericValidator;

/**
 * Classe utilitária para manipulações com data/hora.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public final class DataUtil {

	/** {@link Locale} pt-BR */
	public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
	/** Data no formato dd/MM/yyyy */
	public static final DateFormat DATA_MEDIUM = DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_PT_BR);
	/** Data-hora no formato dd/MM/yyyy HH:mm:ss */
	public static final DateFormat DATA_HORA_MEDIUM =
			DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, LOCALE_PT_BR);
	/** Hora no formato HH:mm:ss */
	public static final DateFormat HORA_MEDIUM = DateFormat.getTimeInstance(DateFormat.MEDIUM, LOCALE_PT_BR);
	/** Data no formato Quinta, 1 de Janeiro de 2009 */
	public static final DateFormat DATA_FULL = DateFormat.getDateInstance(DateFormat.FULL, LOCALE_PT_BR);

	/**
	 * Pattern singleton.
	 */
	private DataUtil() {
	}

	/**
	 * Verifica se uma data no formato dd/MM/yyyy é válida.
	 * 
	 * @param data data a validar
	 * @return <code>true</code> caso seja válida, <code>false</code> caso contrário
	 */
	public static boolean ehDataValida(String data) {
		return GenericValidator.isDate(data, "dd/MM/yyyy", true);
	}

	/**
	 * Retorna a data, recebida "quebrada", em uma data válida no formato "dd/MM/yyyy". Se a data não for
	 * válida, retorna <code>null</code>.
	 * 
	 * @param dia dia da data
	 * @param mes mês da data
	 * @param ano ano da data
	 * 
	 * @return String da data formatada
	 * 
	 */
	public static String formataData(String dia, String mes, String ano) {
		String data = dia + '/' + mes + '/' + ano;
		if (ehDataValida(data)) {
			return data;
		}
		return null;
	}

	/**
	 * Recupera o ano de uma data informada
	 * 
	 * @param data data para verificar
	 * @return ano da data
	 */
	public static int getAno(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_PT_BR);
		cal.setTime(data);
		int yy = cal.get(Calendar.YEAR);
		//caso o ano 1900 seja o 0
		if (yy < 100) {
			yy += 1900;
		}
		return yy;
	}

	/**
	 * Retorna a data atual do sistema.
	 * 
	 * @return a data atual
	 */
	public static Date getDataAtual() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Retorna um {@link Timestamp} da data e hora atual.
	 * 
	 * @return data e hora atual
	 */
	public static Timestamp getDataHoraAtual() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Zera a hora, minuto, segundo, milisegundo do {@link Calendar} informado
	 * 
	 * @param calendar {@link Calendar} a zerar
	 * @return objeto {@link Date} com a hora zerada
	 */
	public static Date getDataSemHHMMSS(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * Recupera um {@link Date} a partir dos parâmentros.
	 * 
	 * @param dia dia da data
	 * @param mes mês da data
	 * @param ano ano da data
	 * @return {@link Date} gerado
	 */
	public static Date getDate(int dia, int mes, int ano) {
		Calendar calendar = Calendar.getInstance(LOCALE_PT_BR);
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		calendar.set(Calendar.MONTH, mes - 1);
		calendar.set(Calendar.YEAR, ano);
		return getDataSemHHMMSS(calendar);
	}

	/**
	 * Retorna a String da data atual no formato: Quinta, 1 de Janeiro de 2009.
	 * 
	 * @return a {@link String} formatada da data atual completa
	 */
	public static String getStringDataAtualCompleta() {
		synchronized (DATA_FULL) {
			return DATA_FULL.format(new Timestamp(System.currentTimeMillis()));
		}
	}

	/**
	 * Converte uma string no formato 'dd/MM/yyyy' para {@link Timestamp}
	 * 
	 * @param inData String a converter
	 * @return Timestamp convertido
	 */
	public static java.sql.Timestamp stringToTimestamp(String inData) {
		java.util.Date auxData = stringToUtilDate(inData);
		return utilDateToTimestamp(auxData);
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um {@link Date}
	 * 
	 * @param data data em formato brasileiro
	 * @return objeto {@link Date} correspondente
	 */
	public static Date stringToUtilDate(String data) {
		synchronized (DATA_MEDIUM) {
			try {
				return new Date(DATA_MEDIUM.parse(data).getTime());
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * Converte uma Data para String no formato dd/MM/yyyy
	 * 
	 * @param date {@link Date} a converter
	 * @return data formatada em {@link String}
	 */
	public static String utilDateToString(Date date) {
		synchronized (DATA_MEDIUM) {
			return DATA_MEDIUM.format(date);
		}
	}

	/**
	 * Recupera o {@link Timestamp} da data informada
	 * 
	 * @param inData data informada
	 * @return Timestamp da data
	 */
	public static java.sql.Timestamp utilDateToTimestamp(java.util.Date inData) {
		return new java.sql.Timestamp(inData.getTime());
	}

}
