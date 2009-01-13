/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.validator.GenericValidator;

/**
 * Classe utilitária para manipulações com datas
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public final class DataUtil {

	/** {@link Locale} pt-BR */
	public static final Locale			LOCALE_BR	= new Locale("pt", "BR");
	/** Unidade que representa o segundo */
	public static final int				SEGUNDO		= 1;
	/** Unidade que representa o minuto */
	public static final int				MINUTO		= 2;
	/** Unidade que representa a hora */
	public static final int				HORA			= 3;
	/** Unidade que representa o dia */
	public static final int				DIA			= 4;
	/** Unidade que representa o mês */
	public static final int				MES			= 5;
	/** Unidade que representa o ano */
	public static final int				ANO			= 6;
	/** Data no formato dd/MM/yyyy */
	public static final DateFormat	DATE_MEDIUM	= DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_BR);
	/** Hora no formato HH:mm:ss */
	public static final DateFormat	HORA_MEDIUM	= DateFormat.getTimeInstance(DateFormat.MEDIUM, LOCALE_BR);
	/** Data no formato Quinta, 1 de Janeiro de 2009 */
	public static final DateFormat	DATE_FULL	= DateFormat.getDateInstance(DateFormat.FULL, LOCALE_BR);

	/**
	 * Construtor privado (classe utilitária).
	 */
	private DataUtil() {
	}

	/**
	 * Adiciona barras a uma String de data original <br>
	 * <br>
	 * ex: Rebece "01012002" e retorna "01/01/2002"
	 * 
	 * @param data data a inserir as barras
	 * @return data com as barras inseridas
	 */
	public static String adicionaBarras(String data) {
		if (data.indexOf("/") == -1) {
			return data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4);
		}
		return data;
	}

	/**
	 * Adiciona a data original o valor informado
	 * 
	 * @param tipo tipo a adicionar (SEGUNDO, MINUTO, HORA, DIA, MES ou ANO)
	 * @param valor valor a adicionar
	 * @param data data inicial
	 * @return data com os valores adicionados
	 */
	public static Date adicionar(int tipo, int valor, Date data) {
		Calendar data2 = Calendar.getInstance(LOCALE_BR);
		data2.setTime(data);

		if (tipo == SEGUNDO) {
			data2.add(Calendar.SECOND, valor);
		} else if (tipo == MINUTO) {
			data2.add(Calendar.MINUTE, valor);
		} else if (tipo == HORA) {
			data2.add(Calendar.HOUR_OF_DAY, valor);
		} else if (tipo == DIA) {
			data2.add(Calendar.DATE, valor);
		} else if (tipo == MES) {
			data2.add(Calendar.MONTH, valor);
		} else if (tipo == ANO) {
			data2.add(Calendar.YEAR, valor);
		}
		return data2.getTime();
	}

	/**
	 * Concatena a hora atual a data informada.
	 * 
	 * @param txtData data no formato dd/MM/yyyy
	 * @return data com a hora atual
	 * @throws Exception
	 */
	public static Date concatenaHoraAtual(String txtData) throws Exception {
		DateFormat dfDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");

		String txtHora = dfHora.format(new Date(System.currentTimeMillis()));

		return dfDataHora.parse(txtData + " " + txtHora);
	}

	/**
	 * Converte uma Data para String no formato dd/MM/yyyy
	 * 
	 * @param date {@link Date} a converter
	 * @return data formatada em {@link String}
	 */
	public static String converteDateToStringBR(Date date) {
		return DATE_MEDIUM.format(date);
	}

	/**
	 * Converte um {@link Date} para String no formato HH:mm:ss
	 * 
	 * @param date {@link Date} a converter
	 * @return hora formatada em {@link String}
	 */
	public static String converteDateToStringHora(Date date) {
		return HORA_MEDIUM.format(date);
	}

	/**
	 * Recebe uma data em qualquer formato BR e retorna um {@link Date}
	 * 
	 * @param data data em formato brasileiro
	 * @return objeto {@link Date} correspondente
	 */
	public static Date converteStringToDate(String data) {
		try {
			return new Date(DATE_MEDIUM.parse(data).getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Recebe uma data em qualquer formato BR e retorna um {@link Timestamp}
	 * 
	 * @param data data em formato brasileiro
	 * @return objeto {@link Timestamp} correspondente
	 */
	public static Timestamp converteStringToTimestamp(String data) {
		return new Timestamp(converteStringToDate(data).getTime());
	}

	/**
	 * Verifica se uma data está no intervalo informado
	 * 
	 * @param data data a validar
	 * @param dataInicio data de início
	 * @param dataTermino data final
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso a data esteja no intervalo, <code>false</code> caso contrário
	 */
	public static boolean entre(Date data, Date dataInicio, Date dataTermino, boolean ignoraHora) {
		return maiorOuIgual(data, dataInicio, ignoraHora) && menorOuIgual(data, dataTermino, ignoraHora);
	}

	/**
	 * Recupera o ano de uma data informada
	 * 
	 * @param data data para verificar
	 * @return ano da data
	 */
	public static int getAno(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);
		int yy = cal.get(Calendar.YEAR);
		//caso o ano 1900 seja o 0
		if (yy < 100) {
			yy += 1900;
		}
		return yy;
	}

	/**
	 * Retorna a data atual do sistema
	 * 
	 * @return a data atual
	 */
	public static Date getDataAtual() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Retorna a String da data atual no formato: Quinta, 1 de Janeiro de 2009
	 * 
	 * @return a {@link String} formatada da data atual completa
	 */
	public static String getDataAtualCompleta() {
		return DATE_FULL.format(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * Retorna a data atual do sistema com as horas, minutos e segundos zerados
	 * 
	 * @return a data atual no primeiro segundo do dia
	 */
	public static Date getDataAtualSemHHMMSS() {
		Calendar calendar = Calendar.getInstance(LOCALE_BR);
		return getDataSemHHMMSS(calendar);
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
	 * Retorna a data com as horas, minutos e segundos zerados.
	 * 
	 * @param data data inicial
	 * @return a data no primeiro segundo do dia
	 */
	public static Date getDataSemHHMMSS(Date data) {
		Calendar calendar = Calendar.getInstance(LOCALE_BR);
		calendar.setTime(data);
		return getDataSemHHMMSS(calendar);
	}

	/**
	 * Recupera o dia do mês de uma data informada
	 * 
	 * @param data data para verificar
	 * @return dia da data
	 */
	public static int getDia(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);

		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna a maior data passada na coleção
	 * 
	 * @param datas {@link List} de datas a verificar
	 * @return a maior data da lista
	 */
	public static Date getMaxDate(List<Date> datas) {
		if (datas.isEmpty()) {
			return null;
		}
		return Collections.max(datas);
	}

	/**
	 * Recupera o mês de uma data informada
	 * 
	 * @param data data para verificar
	 * @return mês da data
	 */
	public static int getMes(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);

		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * Retorna o número de entidades de diferenca entre duas datas <br />
	 * As entidades de retorno são: SEGUNDO, MINUTO, HORA, DIA, MES, ANO
	 * 
	 * @param entidadeRetorno unidade de retorno
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @return quantidade de unidades informada
	 */
	public static int getNumEntreData(int entidadeRetorno, Date data1, Date data2) {
		double diferenca = data1.getTime() - data2.getTime();
		int anoInicial, anoFinal;
		int mesInicial, mesFinal;

		if (diferenca < 0) {
			diferenca = -diferenca;
		}
		diferenca /= 1000;
		if (entidadeRetorno == SEGUNDO) { // entidade de retorno é segundo
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 60;
		if (entidadeRetorno == MINUTO) { // entidade de retorno é minuto
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 60;
		if (entidadeRetorno == HORA) { // entidade de retorno é hora
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 24;
		if (entidadeRetorno == DIA) { // entidade de retorno é dia
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 30;
		if (entidadeRetorno == MES) { // entidade de retorno é mes
			Calendar cal = Calendar.getInstance(LOCALE_BR);
			cal.setTime(data1);
			anoInicial = cal.get(Calendar.YEAR);
			mesInicial = cal.get(Calendar.MONTH);
			cal.setTime(data2);
			anoFinal = cal.get(Calendar.YEAR);
			mesFinal = cal.get(Calendar.MONTH);
			return (anoFinal - anoInicial) * 12 + (mesFinal - mesInicial);
		}
		diferenca = diferenca / 12;
		if (entidadeRetorno == ANO) { // entidade de retorno é ano
			return (int) Math.round(Math.ceil(diferenca));
		}
		return 0; // caso nao seja nenhuma das opcoes retorna o valor default 0
	}

	/**
	 * Retorna a data atual do sistema no formato dd/MM/yyyy
	 * 
	 * @return data atual no formato dd/MM/yyyy
	 */
	public static String getStringDataAtual() {
		return DATE_MEDIUM.format(getDataAtualSemHHMMSS());
	}

	/**
	 * Verifica se as datas são iguais
	 * 
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso sejam, <code>false</code> caso contrário
	 */
	public static boolean igual(Date data1, Date data2, boolean ignoraHora) {
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.equals(data2);
	}

	/**
	 * Verifica se uma data no formato dd/MM/yyyy é válida.
	 * 
	 * @param data data a validar
	 * @return <code>true</code> caso seja válida, <code>false</code> caso contrário
	 */
	public static boolean isValidDate(String data) {
		return GenericValidator.isDate(data, "dd/MM/yyyy", true);
	}

	/**
	 * Verifica se a primeira data é maior do que a segunda
	 * 
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso seja, <code>false</code> caso contrário
	 */
	public static boolean maior(Date data1, Date data2, boolean ignoraHora) {
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.after(data2);
	}

	/**
	 * Verifica se a primeira data é maior ou igual do que a segunda data
	 * 
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso a primeira seja maior ou igual, <code>false</code> caso contrário
	 */
	public static boolean maiorOuIgual(Date data1, Date data2, boolean ignoraHora) {
		return maior(data1, data2, ignoraHora) || igual(data1, data2, ignoraHora);
	}

	/**
	 * Verifica se a primeira data é menor do que a segunda
	 * 
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso seja, <code>false</code> caso contrário
	 */
	public static boolean menor(Date data1, Date data2, boolean ignoraHora) {
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.before(data2);
	}

	/**
	 * Verifica se a primeira data é menor ou igual do que a segunda data
	 * 
	 * @param data1 1ª data a comparar
	 * @param data2 2ª data a comparar
	 * @param ignoraHora se ignorará as horas
	 * @return <code>true</code> caso a primeira seja menor ou igual, <code>false</code> caso contrário
	 */
	public static boolean menorOuIgual(Date data1, Date data2, boolean ignoraHora) {
		return menor(data1, data2, ignoraHora) || igual(data1, data2, ignoraHora);
	}

	/**
	 * Substituí a data caso a primeira seja <code>null</code>
	 * 
	 * @param dataOriginal data original
	 * @param dataSubstituta data que substituirá caso a primeira seja <code>null</code>
	 * @return a dataOriginal ou a dataSubstituta
	 */
	public static Date nvl(Date dataOriginal, Date dataSubstituta) {
		if (dataOriginal == null) {
			return dataSubstituta;
		}
		return dataOriginal;
	}
}
