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
public class DataUtil {

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

	private static DataUtil instancia = new DataUtil();

	/**
	 * Pattern singleton.
	 */
	protected DataUtil() {
		super();
	}

	/**
	 * Recupera a única instância de {@link DataUtil}.
	 * 
	 * @return {@link DataUtil}
	 */
	public static DataUtil getInstancia() {
		return instancia;
	}

	/**
	 * Verifica se uma data no formato dd/MM/yyyy é válida.
	 * 
	 * @param data data a validar
	 * @return <code>true</code> caso seja válida, <code>false</code> caso contrário
	 */
	public boolean ehDataValida(String data) {
		return GenericValidator.isDate(data, LOCALE_PT_BR);
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
	public String formataData(String dia, String mes, String ano) {
		String data = Utils.completaComZero(dia, 2) + '/' + Utils.completaComZero(mes, 2) + '/' + ano;
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
	public int getAno(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_PT_BR);
		cal.setTime(data);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Retorna a data atual do sistema.
	 * 
	 * @return a data atual
	 */
	public Date getDataAtual() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Retorna um {@link Timestamp} da data e hora atual.
	 * 
	 * @return data e hora atual
	 */
	public Timestamp getDataHoraAtual() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Zera a hora, minuto, segundo, milisegundo do {@link Calendar} informado
	 * 
	 * @param calendar {@link Calendar} a zerar
	 * @return objeto {@link Date} com a hora zerada
	 */
	public Date getDataSemHHMMSS(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Recupera um {@link Date} a partir dos parâmentros.
	 * 
	 * @param dia dia da data
	 * @param mes mês da data
	 * @param ano ano da data
	 * @return {@link Date} gerado
	 */
	public Date getDate(int dia, int mes, int ano) {
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
	public String getStringDataAtualCompleta() {
		synchronized (DATA_FULL) {
			return DATA_FULL.format(getDataAtual());
		}
	}

	/**
	 * Converte uma string no formato 'dd/MM/yyyy' para {@link Timestamp}
	 * 
	 * @param inData String a converter
	 * @return Timestamp convertido
	 */
	public Timestamp stringToTimestamp(String inData) {
		Date auxData = stringToUtilDate(inData);
		return utilDateToTimestamp(auxData);
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um {@link Date}
	 * 
	 * @param data data em formato brasileiro
	 * @return objeto {@link Date} correspondente
	 */
	public Date stringToUtilDate(String data) {
		synchronized (DATA_MEDIUM) {
			try {
				if (ehDataValida(data)) {
					return new Date(DATA_MEDIUM.parse(data).getTime());
				}
				return null;
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
	public String utilDateToString(Date date) {
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
	public Timestamp utilDateToTimestamp(Date inData) {
		return new Timestamp(inData.getTime());
	}

}
