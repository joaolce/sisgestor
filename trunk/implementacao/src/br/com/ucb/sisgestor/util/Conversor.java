/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;

/**
 * Classe de métodos para fazer conversões de utilidades
 * 
 * @author João Lúcio
 */
public final class Conversor {

	/**
	 * Construtor privado (classe utilitária).
	 */
	private Conversor() {
	}

	/**
	 * Transforma um {@link Blob} em um {@link ByteArrayOutputStream}
	 * 
	 * @param blob {@link Blob} a transformar
	 * @return ByteArrayOutputStream transformado
	 * @throws Exception caso tenha erro na conversão
	 */
	public static ByteArrayOutputStream blobToByteArrayOutputStream(Blob blob) throws Exception {
		int size;
		ByteArrayOutputStream baos;

		try {
			size = (int) blob.length();
		} catch (Exception e) {
			return null;
		}

		byte[] buffer = new byte[size];
		int readBytes = 0;
		int totalRead = 0;
		InputStream is = blob.getBinaryStream();

		while ((readBytes = is.read(buffer, totalRead, size - totalRead)) != -1) {
			totalRead += readBytes;
		}

		baos = new ByteArrayOutputStream();
		baos.write(buffer);
		baos.flush();

		return baos;
	}

	/**
	 * Recupera um {@link java.util.Date} da data do final do mês
	 * 
	 * @param data data que deseja ver o último dia do mês
	 * @return data com o último dia do mês
	 */
	public static Date calculaDataFimMes(Date data) {
		Date dt = calculaDataInicioMesSeguinte(data);
		return DataUtil.adicionar(DataUtil.DIA, -1, dt);
	}

	/**
	 * Recupera um {@link java.util.Date} da data do final do próximo mês
	 * 
	 * @return data do final do próximo mês
	 */
	public static Date calculaDataFimMesSeguinte() {
		Date data = new java.util.Date();
		data = DataUtil.adicionar(DataUtil.MES, 1, data);
		return calculaDataFimMes(data);
	}

	/**
	 * Recupera a data de hoje na hora 00:00:00
	 * 
	 * @return Data de hoje às 00:00:00 horas
	 */
	public static Date calculaDataHojeMeiaNoite() {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.SECOND, 0);
		return data.getTime();
	}

	/**
	 * Recupera o primeiro dia do próximo mês da data informada
	 * 
	 * @param data data informada
	 * @return nova data
	 */
	public static Date calculaDataInicioMesSeguinte(Date data) {
		Calendar data2 = Calendar.getInstance();
		data2.setTime(data);
		data2.set(Calendar.DAY_OF_MONTH, 1);
		data2.add(Calendar.MONTH, 1);

		return data2.getTime();
	}

	/**
	 * Formata um valor do tipo double para {@link String} <br />
	 * obs: a {@link String} será com ','
	 * 
	 * @param valor valor a formatar
	 * @return String formatada
	 */
	public static String doubleToString(double valor) {
		String stringValor = Double.toString(valor);
		return stringValor.replace('.', ',');
	}

	/**
	 * Verifica se a data no padrão dd/MM/yyyy HH:mm:ss, com a hora variando entre 00:00:00 e 23:59:59 é
	 * válida.
	 * 
	 * @param inData horário formatado a ser validado.
	 * @return <code>true</code> se o horário for válido, <code>false</code> caso contrário.
	 */
	public static boolean ehDataHoraValida(String inData) {
		if ((inData == null) || (inData.length() != 19)) {
			return false;
		}

		DateFormat df =
				DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, new Locale("pt", "BR"));
		df.setLenient(false);

		try {
			df.parse(inData);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Verifica se uma data no formato 'dd/MM/yyyy' é válida
	 * 
	 * @param inData {@link String} da data a verificar
	 * @return <code>true</code> caso seja válida, <code>false</code> caso contrário
	 */
	public static boolean ehDataValida(String inData) {
		if ((inData == null) || (inData.length() != 10)) {
			return false;
		}

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
		df.setLenient(false);

		try {
			df.parse(inData);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Verifica se um horário no padrão 99:99, variando entre 00:00 e 23:59 é válido.
	 * 
	 * @param sHorario String - horário formatado a ser validado.
	 * @return <code>true</code> se o horário for válido, <code>false</code> caso contrário.
	 */
	public static boolean ehHorarioValido(String sHorario) {
		if (sHorario.length() != 5) {
			return false;
		}

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, DataUtil.LOCALE_BR);
		df.setLenient(false);

		try {
			df.parse(getDataAtual() + " " + sHorario);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Verifica se um horário no padrão 99:99:99, variando entre 00:00:00 e 23:59:59 é válido.
	 * 
	 * @param sHorario horário formatado a ser validado.
	 * @return true se o horário for válido, false, caso contrário.
	 */
	public static boolean ehHorarioValidoHHMMSS(String sHorario) {
		if (sHorario.length() != 8) {
			return false;
		}
		return ehDataHoraValida(getDataAtual() + " " + sHorario);
	}

	/**
	 * Retorna uma String vazia caso a informada seja nula
	 * 
	 * @param inEntrada {@link String} informada
	 * @return <code>null</code> ou a própria String
	 */
	public static String ehNulo(String inEntrada) {
		if (inEntrada == null) {
			return "";
		} else {
			return inEntrada;
		}
	}

	/**
	 * Lê o arquivo e coloca em um {@link ByteArrayInputStream}
	 * 
	 * @param inFile arquivo recuperar dados
	 * @return ByteArrayInputStream com os dados do arquivo
	 * @throws Exception caso tenha erro na conversão
	 */
	public static ByteArrayInputStream fileToByteArrayInputStream(File inFile) throws Exception {
		FileInputStream fis = new FileInputStream(inFile);
		int tamanho = (int) inFile.length();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bytesLidos = 0;
		byte[] buffer = new byte[tamanho];
		while (bytesLidos < tamanho) {
			bytesLidos += fis.read(buffer, bytesLidos, tamanho - bytesLidos);
		}
		baos.write(buffer);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		return bais;
	}

	/**
	 * Lê o arquivo e coloca em um {@link ByteArrayOutputStream}
	 * 
	 * @param inFile arquivo recuperar dados
	 * @return ByteArrayOutputStream com os dados do arquivo
	 * @throws Exception caso ocorra erro na conversão
	 */
	public static ByteArrayOutputStream fileToByteArrayOutputStream(File inFile) throws Exception {
		FileInputStream fis = new FileInputStream(inFile);
		final int TAMANHO_BYTES_ARQUIVO = (int) inFile.length();
		final int TAMANHO_BUFFER = 1000;

		ByteArrayOutputStream baos = new ByteArrayOutputStream(TAMANHO_BYTES_ARQUIVO);
		int bytesLidos = 0, totalBytesLidos = 0;
		byte[] buffer = new byte[TAMANHO_BUFFER];

		while (totalBytesLidos < TAMANHO_BYTES_ARQUIVO) {
			bytesLidos = fis.read(buffer, 0, TAMANHO_BUFFER);
			if (bytesLidos == -1) {
				break;
			}
			baos.write(buffer, 0, bytesLidos);
			totalBytesLidos += bytesLidos;
		}
		fis.close();
		return baos;
	}

	/**
	 * Transforma um float para uma string
	 * 
	 * @param valor valor em float
	 * @return String do valor
	 */
	public static String floatToString(float valor) {
		String stringValor = Float.toString(valor);
		return stringValor.replace('.', ',');
	}

	/**
	 * Retorna a data, recebida "quebrada", em uma data válida no formato "dd/MM/yyyy". Se a data não for
	 * válida, retorna null.
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
		return ehDataValida(data) ? data : null;
	}

	/**
	 * Retorna a String de um número
	 * 
	 * @param valor valor a formatar
	 * @param casasDecimais número de casas decimais a String deve ter
	 * @return String do número
	 */
	public static String formataDoubleToString(double valor, int casasDecimais) {
		return new DecimalFormat("#,###,###,###,##0." + "0000000000".substring(0, casasDecimais)).format(valor);
	}

	/**
	 * Formata o valor Double recebido em String no formato 999.999.999,99.
	 * 
	 * @param valor O valor a ser formatado.
	 * @return Uma String contendo o valor já formatado.
	 */
	public static String formataValorParaString(Double valor) {
		String strValor = valor.toString();
		strValor = strValor.replace('.', ',');
		String[] temp = strValor.split(",");

		StringBuffer buffValor = new StringBuffer(temp[0]);
		if ((temp.length > 0) && (temp[0].length() > 3)) {
			int pos = temp[0].length() - 3;
			for (int i = temp[0].length() - 1; i >= 0; i--) {
				if ((i > 0) && (i == pos)) {
					buffValor.insert(i, ".");
					pos -= 3;
				}
			}
		}
		if ((temp[1] != null) && (temp[1].length() == 1)) {
			temp[1] = temp[1].concat("0");
		}
		strValor = buffValor.toString() + "," + temp[1];
		return strValor;
	}

	/**
	 * Obtem a data da JVM em String no formato "dd/MM/yyyy"
	 * 
	 * @return String da data atual
	 */
	public static String getDataAtual() {
		return utilDateToString(new java.util.Date());
	}

	/**
	 * Obtem a data/hora da JVM em String no formato "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @return String da data/hora atual
	 */
	public static String getDataHoraAtual() {
		return utilDateTimeToString(new java.util.Date());
	}

	/**
	 * Obtem a hora da JVM em String no formato "HH:mm:ss"
	 * 
	 * @return String da hora atual
	 */
	public static String getHoraAtual() {
		return utilTimeToString(new java.util.Date());
	}

	/**
	 * Transforma um {@link InputStream} para um {@link ByteArrayOutputStream}
	 * 
	 * @param iStream {@link InputStream} a transformar
	 * @return ByteArrayOutputStream convertido
	 * @throws Exception caso ocorra erro na conversão
	 */
	public static ByteArrayOutputStream inputStreamToByteArrayOutputStream(InputStream iStream)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bytesLidos;
		byte[] buffer = new byte[1024];

		while ((bytesLidos = iStream.read(buffer)) != -1) {
			baos.write(buffer, 0, bytesLidos);
		}
		return baos;
	}

	/**
	 * Retorna <code>null</code> caso o valor da String seja vazio
	 * 
	 * @param valor String a verificar
	 * @return <code>null</code> caso vazio, String original caso contenha algo
	 */
	public static String nullSeVazio(String valor) {
		if (StringUtils.isBlank(valor)) {
			return null;
		}
		return valor;
	}

	/**
	 * Recupera o valor informado do número em extenso
	 * 
	 * @param valor valor informado
	 * @return valor por extenso
	 */
	public static String numeroExtenso(double valor) {
		String porExtenso;
		int tamanho;

		Extenso extenso = new Extenso(valor);
		porExtenso = extenso.toString();
		tamanho = porExtenso.length();
		if (tamanho < 5) {
			return null;
		}
		return porExtenso.substring(0, tamanho - 5);
	}

	/**
	 * Recupera uma String do valor informado
	 * 
	 * @param inEntrada número informado
	 * @return String do número
	 */
	public static String numeroToString(double inEntrada) {
		DecimalFormat formatador = new DecimalFormat("#,###,###,###,###,###");
		String outData = formatador.format(inEntrada);
		return outData.replace(',', '.');
	}

	/**
	 * Retorna a data Atual no formato de String.
	 * 
	 * @return String que representa a data atual
	 */
	public static String recuperaDataDeHoje() {
		return utilDateToString(new Date());
	}

	/**
	 * Verifica se o campo está null/vazio
	 * 
	 * @param inEntrada String a verificar
	 * @return true caso seja nulo/vazio
	 */
	public static boolean seNulo(String inEntrada) {
		return (inEntrada == null) || (inEntrada.trim().length() == 0);
	}

	/**
	 * Soma uma determinada quantidade de dias, meses e/ou anos a data Atual. (Subtrai se a quantidade for
	 * negativa)
	 * 
	 * @param qtdDias quantidade de dias a adicionar
	 * @param qtdMeses quantidade de meses a adicionar
	 * @param qtdAnos quantidade de anos a adicionar
	 * 
	 * @return String que representa a data modificada
	 */
	public static String somaDiasDataAtual(int qtdDias, int qtdMeses, int qtdAnos) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(GregorianCalendar.DAY_OF_MONTH, qtdDias);
		calendario.add(GregorianCalendar.MONTH, qtdMeses);
		calendario.add(GregorianCalendar.YEAR, qtdAnos);
		return utilDateToString(calendario.getTime());
	}

	/**
	 * Converte uma data {@link java.sql.Date} para String no formato "dd/MM/yyyy"
	 * 
	 * @param inData data a converter
	 * @return String formada
	 */
	public static String sqlDateToString(java.sql.Date inData) {
		return new SimpleDateFormat("dd/MM/yyyy").format(inData);
	}

	/**
	 * Converte uma data {@link java.sql.Date} para String no formato "ddMMyyyy"
	 * 
	 * @param inData data a converter
	 * @return String formada
	 */
	public static String sqlDateToStringSemDelimitadores(java.sql.Date inData) {
		return new SimpleDateFormat("ddMMyyyy").format(inData);
	}

	/**
	 * Transforma uma data {@link java.sql.Date} em {@link java.util.Date}
	 * 
	 * @param inData String da data a converter
	 * @return data em java.sql.Date
	 */
	public static Date sqlDateToUtilDate(java.sql.Date inData) {
		return new java.util.Date(inData.getTime());
	}

	/**
	 * Converte uma data String no formato "yyyyMMddHHmmss" para {@link java.util.Date}
	 * 
	 * @param inDataHora String da data a converter
	 * @return data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date StringAAAAMMDDHHMMSSToDate(String inDataHora) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmmss").parse(inDataHora);
	}

	/**
	 * Converte uma data String no formato "yyyyMMdd" para {@link java.util.Date}
	 * 
	 * @param inData String da data a converter
	 * @return data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date StringAAAAMMDDToDate(String inData) throws ParseException {
		return new SimpleDateFormat("yyyyMMdd").parse(inData);
	}

	/**
	 * Converte uma data String no formato "ddMMyyyyHHmmss" para {@link java.util.Date}
	 * 
	 * @param inDataHora String da data a converter
	 * @return data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date StringDDMMAAAAHHMMSSToDate(String inDataHora) throws ParseException {
		return new SimpleDateFormat("ddMMyyyyHHmmss").parse(inDataHora);
	}

	/**
	 * Converte uma data String no formato "ddMMyyyy" para {@link java.util.Date}
	 * 
	 * @param inData String da data a converter
	 * @return data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date StringDDMMAAAAToDate(String inData) throws ParseException {
		return new SimpleDateFormat("ddMMyyyy").parse(inData);
	}

	/**
	 * Converte uma data String no formato "dd/MM/yyyy HH:mm:ss" para {@link java.util.Date}
	 * 
	 * @param inDataHora String da data a converter
	 * @return data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date StringToDateTime(String inDataHora) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(inDataHora);
	}

	/**
	 * Converte uma String em um double
	 * 
	 * @param valor String
	 * @return double correspondente
	 */
	public static double stringToDouble(String valor) {
		valor = valor.replace(',', '.');
		return Double.parseDouble(valor);
	}

	/**
	 * Converte uma String em um float
	 * 
	 * @param inEntrada valor em String
	 * @return float correspondente
	 */
	public static float stringToFloat(String inEntrada) {
		inEntrada = inEntrada.replace(',', '.');
		return Float.parseFloat(inEntrada);
	}

	/**
	 * Converte uma data String no formato "dd/MM/yyyy" para o fromato {@link java.sql.Date}
	 * 
	 * @param inData String a converter
	 * @return java.sql.Date Data convertida
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.sql.Date stringToSqlDate(String inData) throws ParseException {
		java.util.Date auxData = stringToUtilDate(inData);
		return utilDateToSqlDate(auxData);
	}

	/**
	 * Converte uma string no formato 'dd/MM/yyyy' para {@link Timestamp}
	 * 
	 * @param inData String a converter
	 * @return Timestamp convertido
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.sql.Timestamp stringToTimestamp(String inData) throws ParseException {
		java.util.Date auxData = stringToUtilDate(inData);
		return utilDateToTimestamp(auxData);
	}

	/**
	 * Converte uma data String no formato "dd/MM/yyyy" para {@link java.util.Date}
	 * 
	 * @param inData String data
	 * @return java.util.Date
	 * @throws ParseException caso ocorra erro na conversão
	 */
	public static java.util.Date stringToUtilDate(String inData) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy").parse(inData);
	}

	/**
	 * Transforma um {@link Timestamp} na String no formato 'dd/MM/yyyy'
	 * 
	 * @param inData {@link Timestamp} a transformar
	 * @return String do Timestamp formatado
	 */
	public static String TimestampToString(java.sql.Timestamp inData) {
		return TimestampToString(inData, "dd/MM/yyyy");
	}

	/**
	 * Transforma um {@link Timestamp} em uma string formatada
	 * 
	 * @param inData {@link Timestamp} a transformar
	 * @param formato formato da String a transformar
	 * 
	 * @return String do Timestamp formatado
	 */
	public static String TimestampToString(java.sql.Timestamp inData, String formato) {
		SimpleDateFormat formatador = new SimpleDateFormat(formato);
		formatador.setLenient(false);
		return formatador.format(inData);
	}

	/**
	 * Transforma um {@link Timestamp} em {@link java.util.Date}
	 * 
	 * @param dataHora {@link Timestamp} a transformar
	 * @return Data transformada
	 */
	public static java.util.Date TimestampToUtilDate(Timestamp dataHora) {
		return new java.util.Date(dataHora.getTime());
	}

	/**
	 * Transforma uma {@link ByteArrayInputStream} para um {@link InputStream}
	 * 
	 * @param baos {@link ByteArrayInputStream} a tranformar
	 * @return InputStream associado
	 */
	public static InputStream transformaOutputInput(ByteArrayOutputStream baos) {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * Transforma as primeiras letras das palavras em maiuscúlo
	 * 
	 * @param texto texto a modificar
	 * @return novo texto
	 */
	public static String transformaPrimeirasLetrasMaiusculo(String texto) {
		String result = "";
		String nome = new String();

		StringTokenizer token = new StringTokenizer(texto.toLowerCase(), " ");

		while (token.hasMoreElements()) {
			nome = token.nextToken();

			if (!nome.equals("da")) {
				result += nome.substring(0, 1).toUpperCase() + nome.substring(1) + " ";
			} else {
				result += nome + " ";
			}
		}
		return result;
	}

	/**
	 * Trata uma String convertendo códigos unicode em seus respectivos caracteres. Obs: Somente os caracteres
	 * % e & estão sendo tratados atualmente.
	 * 
	 * @param str String contendo os códigos unicode no lugar do caracteres especiais.
	 * @return String com os caracteres especiais substituídos.
	 */
	public static String trataCaracteresEspeciais(String str) {
		if (str.indexOf("/u0025") != -1) {
			str = str.replaceAll("/u0025", "%");
		}
		if (str.indexOf("/u0026") != -1) {
			str = str.replaceAll("/u0026", "&");
		}
		return str;
	}

	/**
	 * Converte uma java.util.Date para String no formato 'dd/MM/yyyy HH:mm:ss'
	 * 
	 * @param inData data a converter
	 * @return data formatada
	 */
	public static String utilDateTimeToString(java.util.Date inData) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(inData);
	}

	/**
	 * Converte uma java.util.Date para String no formato 'dd/MM/yyyy HH:mm'
	 * 
	 * @param inData data a converter
	 * @return data formatada
	 */
	public static String utilDateTimeToStringSemSegundos(java.util.Date inData) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(inData);
	}

	/**
	 * Converte java.util.Date para 'ddMMyyyy'
	 * 
	 * @param inData data a converter
	 * @return data formatada
	 */
	public static String utilDateToDDMMAAAA(java.util.Date inData) {
		return new SimpleDateFormat("ddMMyyyy").format(inData);
	}

	/**
	 * Transforma uma {@link java.util.Date} para {@link java.sql.Date}
	 * 
	 * @param inData data a converter
	 * @return data do tipo java.sql.Date
	 */
	public static java.sql.Date utilDateToSqlDate(java.util.Date inData) {
		return new java.sql.Date(inData.getTime());
	}

	/**
	 * Retorna a data formatada no formato 'dd/MM/yyyy' da data informada
	 * 
	 * @param inData data informada
	 * @return data formatada
	 */
	public static String utilDateToString(java.util.Date inData) {
		return new SimpleDateFormat("dd/MM/yyyy").format(inData);
	}

	/**
	 * Converte uma data java.util.Date para String no formato "yyyyMMdd"
	 * 
	 * @param inData data a converter
	 * @return String formatada
	 */
	public static String utilDateToStringAAAAMMDD(java.util.Date inData) {
		return new SimpleDateFormat("yyyyMMdd").format(inData);
	}

	/**
	 * Converte uma data java.util.Date para String no formato "ddMMyyyyHHmmss"
	 * 
	 * @param inData data a converter
	 * @return String formatada
	 */
	public static String utilDateToStringDDMMAAAAHHMMSS(java.util.Date inData) {
		return new SimpleDateFormat("ddMMyyyyHHmmss").format(inData);
	}

	/**
	 * Converte uma data no formato java.util.Date para String no formato "dia de mês de ano" <br>
	 * Ex: "25 de janeiro de 2002"
	 * 
	 * @param inData data a pegar a String
	 * @return String
	 */
	public static String utilDateToStringPorExtenso(java.util.Date inData) {
		String mes = "";
		String descricao = "";
		descricao = DataUtil.getDia(inData) + " de ";

		switch (DataUtil.getMes(inData)) {
			case 1:
				mes = "janeiro";
				break;
			case 2:
				mes = "fevereiro";
				break;
			case 3:
				mes = "março";
				break;
			case 4:
				mes = "abril";
				break;
			case 5:
				mes = "maio";
				break;
			case 6:
				mes = "junho";
				break;
			case 7:
				mes = "julho";
				break;
			case 8:
				mes = "agosto";
				break;
			case 9:
				mes = "setembro";
				break;
			case 10:
				mes = "outubro";
				break;
			case 11:
				mes = "novembro";
				break;
			case 12:
				mes = "dezembro";
				break;
		}

		descricao += mes + " de " + DataUtil.getAno(inData);
		return descricao;
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

	/**
	 * Retorna a hora formatada no formato 'HH:mm:ss' da data informada
	 * 
	 * @param inData data informada
	 * @return hora formatada
	 */
	public static String utilTimeToString(java.util.Date inData) {
		return new SimpleDateFormat("HH:mm:ss").format(inData);
	}

	/**
	 * Retorna a hora formatada no formato 'HH:mm:ss:SS' da data informada
	 * 
	 * @param inData data informada
	 * @return hora formatada
	 */
	public static String utilTimeToStringComMilisegundos(java.util.Date inData) {
		return new SimpleDateFormat("HH:mm:ss:SS").format(inData);
	}

	/**
	 * Retorna a hora formatada no formato 'HH:mm' da data informada
	 * 
	 * @param inData data informada
	 * @return hora formatada
	 */
	public static String utilTimeToStringSemSegundos(java.util.Date inData) {
		return new SimpleDateFormat("HH:mm").format(inData);
	}

	/**
	 * Recupera um valor, e retorna em uma String formatada
	 * 
	 * @param valor valor a formatar
	 * @return valor formatado em String
	 */
	public static String valorMonetarioToString(double valor) {
		return new DecimalFormat("#,##0.00").format(valor);
	}
}
