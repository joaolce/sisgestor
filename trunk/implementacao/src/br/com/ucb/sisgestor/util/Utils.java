/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;
import org.directwebremoting.util.LocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Utilitários da aplicação.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public final class Utils {

	private static MessageResources	resources;
	private static final Log			LOG	= LogFactory.getLog(Utils.class);

	/**
	 * Construtor privado (classe utilitária).
	 */
	private Utils() {
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

		while ((readBytes = is.read(buffer, totalRead, size - totalRead)) != -1) { //NOPMD by João Lúcio - mais legível
			totalRead += readBytes;
		}

		baos = new ByteArrayOutputStream();
		baos.write(buffer);
		baos.flush();

		return baos;
	}

	/**
	 * Completa com zeros a esquerda o número informado
	 * 
	 * @param valor valor a ser completado
	 * @param numeroCasas número de casas decimais no total
	 * @return número formatado com zeros
	 */
	public static String completaComZero(String valor, int numeroCasas) {
		StringBuffer numeracaoFinal = new StringBuffer();
		int casasNumeracao = valor.length();
		for (int i = 0; i < (numeroCasas - casasNumeracao); i++) {
			numeracaoFinal.append(0);
		}
		numeracaoFinal.append(valor);
		return numeracaoFinal.toString();
	}

	/**
	 * Copia as propriedades de um objeto para outro, bastando que as propriedades tenham o mesmo nome e o
	 * mesmo tipo seguindo essas restrições:
	 * <p>
	 * - <b>(java.util.Date) origem.dataExemplo -> (java.lang.String) destino.dataExemplo</b> a data origem
	 * será formatada para String no formato dd/MM/yyyy ou dd/MM/yyyy HH:mm e atribuída na propriedade destino
	 * </p>
	 * <p>
	 * - <b>(java.lang.String) origem.dataExemplo -> (java.util.Date) destino.dataExemplo</b> haverá uma
	 * tentativa de parse na data origem para java.util.Date, se suceder o valor será configurado na
	 * propriedade destino, caso contrário será configurado null
	 * </p>
	 * <p>
	 * - <b>(java.lang.Integer) origem.objetoPersistente -> (ObjetoPersistente) destino.objetoPersistente</b> o
	 * valor da propriedade origem será configurado na propriedade <b>id</b> do objeto destino
	 * </p>
	 * <p>
	 * - <b>(ObjetoPersistente) origem.objetoPersistente -> (java.lang.Integer) destino.objetoPersistente</b>
	 * será extraído o valor da propriedade <b>id</b> do objeto origem e configurado no objeto destino
	 * </p>
	 * 
	 * @see PropertyUtils#copyProperties
	 * 
	 * @param destino objeto de destino
	 * @param origem objeto origem
	 * @throws Exception caso ocorra erro na recuperação das propriedades
	 */
	public static void copyProperties(Object destino, Object origem) throws Exception { //NOPMD by João Lúcio - não dá para quebrar método
		//pega os descritores
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(destino);
		//as propriedades que são do mesmo tipo serão copiadas por último
		List<String> propriedadesIguais = new ArrayList<String>();

		for (PropertyDescriptor descriptor : propertyDescriptors) {
			PropertyDescriptor descriptorOrigem;
			try {
				descriptorOrigem = PropertyUtils.getPropertyDescriptor(origem, descriptor.getName());
				//se o descritor é null então o objeto não existe no destino
				if (descriptorOrigem == null) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			Class<?> propertyType = descriptor.getPropertyType();
			Class<?> propertyTypeOrigem = descriptorOrigem.getPropertyType();
			//se a propriedade origem não é legível e a destino não é gravável pula para o próximo
			if (!PropertyUtils.isWriteable(destino, descriptor.getName())
					|| !PropertyUtils.isReadable(origem, descriptor.getName())) {
				continue;
			}
			//se os tipos são o mesmo separa para transportar depois
			if (propertyType.getName().equals(propertyTypeOrigem.getName())) {
				propriedadesIguais.add(descriptor.getName());
			} else {
				//o valor de origem para ser copiado deverá estar preenchido
				Object valorOrigem = PropertyUtils.getProperty(origem, descriptorOrigem.getName());

				//================== CONVERSOR DE DATAS ==================  

				//isso é feito porque a propriedade destino sempre está nula e quando é nula não passa no instanceof
				boolean eUmaData =
						propertyType.getName().equals(Timestamp.class.getName())
								|| propertyType.getName().equals(Date.class.getName())
								|| propertyType.getName().equals(java.sql.Date.class.getName());
				boolean eUmaString = propertyType.getName().equals(String.class.getName());

				if (eUmaData && String.class.isInstance(valorOrigem)) {
					String valor = (String) PropertyUtils.getProperty(origem, descriptorOrigem.getName());
					if (propertyType.getName().equals(Timestamp.class.getName())) {
						//CONVERSOR TIMESTAMP
						PropertyUtils.setProperty(destino, descriptor.getName(), DataUtil.stringToTimestamp(valor));
					} else {
						//CONVERSOR DATE
						PropertyUtils.setProperty(destino, descriptor.getName(), DataUtil.stringToUtilDate(valor));
					}
				} else if (eUmaString && Date.class.isInstance(valorOrigem)) {
					Date valor = (Date) PropertyUtils.getProperty(origem, descriptorOrigem.getName());
					PropertyUtils.setProperty(destino, descriptor.getName(), DataUtil.utilDateToString(valor));
				}

				// ================== ASSOCIAÇÕES ================== 
				boolean eUmObjetoPersistente = false;
				//verificar se o tipo da propriedade é um ObjetoPersistente
				Class<?> superclass = propertyType;
				while (superclass != null) {
					if (superclass.getName().equals(ObjetoPersistente.class.getName())) {
						eUmObjetoPersistente = true;
						break;
					}
					superclass = superclass.getSuperclass();
				}
				if (eUmObjetoPersistente) {
					Object valor = PropertyUtils.getProperty(origem, descriptor.getName());
					//se o valor é nulo ou igual a zero pula
					if (valor == null) {
						continue;
					}
					if ((valor instanceof Number) && (((Number) valor).intValue() == 0)) {
						continue;
					}
					Object property = propertyType.newInstance();
					if (!PropertyUtils.getPropertyType(property, "id").getName()
							.equals(valor.getClass().getName())) {
						continue;
					}
					PropertyUtils.setProperty(property, "id", valor);
					PropertyUtils.setProperty(destino, descriptor.getName(), property);
				} else if (ObjetoPersistente.class.isInstance(valorOrigem)) {
					Object property = PropertyUtils.getProperty(origem, descriptor.getName());
					//se o objeto estiver nulo pula
					if (property == null) {
						continue;
					}
					Object valor = PropertyUtils.getProperty(property, "id");
					if (!descriptor.getPropertyType().getName().equals(valor.getClass().getName())) {
						continue;
					}
					PropertyUtils.setProperty(destino, descriptor.getName(), valor);
				}
			}
		}
		for (String propriedade : propriedadesIguais) {
			PropertyUtils.setProperty(destino, propriedade, PropertyUtils.getProperty(origem, propriedade));
		}
	}

	/**
	 * Todos os formulários submetidos assíncronamente (pelo método GET), são codificados por causa do charset,
	 * ao utilizar esse método ele irá decodificar todas as propriedades do form para aparecer acentos e
	 * caracteres especiais
	 * 
	 * @param form form submetido
	 */
	public static void decodeAjaxForm(ActionForm form) {
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(form);
		try {
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				if (!PropertyUtils.isReadable(form, descriptor.getName())) {
					continue;
				}

				if (descriptor.getPropertyType().getName().equals(String.class.getName())) {
					Object object = PropertyUtils.getProperty(form, descriptor.getName());
					boolean isWritable = PropertyUtils.isWriteable(form, descriptor.getName());
					String property = (String) object;
					if (GenericValidator.isBlankOrNull(property) || !isWritable) {
						continue;
					}

					String decoded = LocalUtil.decode(property);
					PropertyUtils.setProperty(form, descriptor.getName(), decoded);
				}
			}
		} catch (Exception e) {
			LOG.error("Provavelmente alguma propriedade do form lançando exceção... nada grave", e);
		}
	}

	/**
	 * Faz com que o navegador não faça cache das páginas.
	 * 
	 * @param request request atual
	 * @param response response atual
	 */
	public static void doNoCachePagina(HttpServletRequest request, HttpServletResponse response) {
		response.setDateHeader(" Expires", 0);
		response.setHeader(" Pragma", "no- cache");
		if (request.getProtocol().equals("HTTP/ 1.1")) {
			response.setHeader("Cache- Control", "no- cache");
		}
	}

	/**
	 * Seta <code>null</code> em todas as strings vazias.
	 * 
	 * @param objeto objeto a verificar
	 */
	public static void doNuloNaStringVazia(Object objeto) {
		PropertyDescriptor[] descritores = PropertyUtils.getPropertyDescriptors(objeto);
		for (PropertyDescriptor descritor : descritores) {
			boolean readable = PropertyUtils.isReadable(objeto, descritor.getName());
			boolean writeable = PropertyUtils.isWriteable(objeto, descritor.getName());
			if (!readable || !writeable) {
				continue;
			}
			Object propriedade;
			try {
				propriedade = PropertyUtils.getProperty(objeto, descritor.getName());
			} catch (Exception e) {
				continue;
			}
			if ((propriedade instanceof String) && (StringUtils.isBlank((String) propriedade))) {
				try {
					PropertyUtils.setProperty(objeto, descritor.getName(), null);
				} catch (Exception e) {
					LOG.warn("erro ao atribuir null em " + descritor.getName(), e);
				}
			} else if ((propriedade instanceof Character)
					&& (StringUtils.isBlank(((Character) propriedade).toString()))) {
				try {
					PropertyUtils.setProperty(objeto, descritor.getName(), null);
				} catch (Exception e) {
					LOG.warn("erro ao atribuir null em " + descritor.getName(), e);
				}
			}
		}
	}

	/**
	 * Retorna uma String vazia caso a informada seja nula
	 * 
	 * @param texto {@link String} informada
	 * @return <code>null</code> ou a própria String
	 */
	public static String ehNulo(String texto) {
		if (texto == null) {
			return "";
		}
		return texto;
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
		return new ByteArrayInputStream(baos.toByteArray());
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
	 * Recupera um bean do spring.
	 * 
	 * @param bean nome do bean
	 * @param servletContext contexto da servlet
	 * @return bean do spring
	 */
	public static Object getBean(String bean, ServletContext servletContext) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return context.getBean(bean);
	}

	/**
	 * Pegar uma mensagem do properties pelo key
	 * 
	 * @param key chave no arquivo properties
	 * @param args argumentos da mensagem a subtituir
	 * @return String com a mensagem
	 */
	public static String getMessageFromProperties(String key, String... args) {
		synchronized (resources) {
			if (resources == null) {
				PropertyMessageResourcesFactory.setFactoryClass(PropertyMessageResourcesFactory.class.getName());
				PropertyMessageResourcesFactory fac = new PropertyMessageResourcesFactory();
				fac.setReturnNull(true);
				resources = fac.createResources("sisgestor");
			}
		}
		return resources.getMessage(key, args);
	}

	/**
	 * Faz o injection dos beans no objeto informado.
	 * 
	 * @param obj objeto a setar dependências
	 * @param context contexto da servlet
	 */
	public static void injectionAutowired(Object obj, ServletContext context) {
		for (Method metodo : obj.getClass().getMethods()) {
			if (metodo.isAnnotationPresent(Autowired.class)) {
				String bean = StringUtils.uncapitalize(metodo.getName().substring(3));
				try {
					metodo.invoke(obj, getBean(bean, context));
				} catch (Exception e) {
					LOG.error("Erro ao inserir bean " + bean + " na action" + obj.getClass().getSimpleName(), e);
				}
			}
		}
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

		while ((bytesLidos = iStream.read(buffer)) != -1) { //NOPMD by João Lúcio - mais legível
			baos.write(buffer, 0, bytesLidos);
		}
		return baos;
	}

	/**
	 * Verifica se todas as propriedades passadas no array dos dois objetos passados são iguais.
	 * 
	 * @param o1 primeiro objeto a comparar
	 * @param o2 segundo objeto a comparar
	 * @param propriedades que deverão ser iguais nos dois objetos
	 * @return <code>true</code> caso as todas propriedades iguais, <code>false</code> caso contrário
	 * @throws Exception caso exceção seja lançada
	 */
	public static boolean isAllEqual(Object o1, Object o2, String... propriedades) throws Exception {
		for (String propriedade : propriedades) {
			Object valor1 = PropertyUtils.getProperty(o1, propriedade);
			Object valor2 = PropertyUtils.getProperty(o2, propriedade);
			if ((valor1 instanceof ObjetoPersistente) && (valor2 instanceof ObjetoPersistente)) {
				valor1 = PropertyUtils.getProperty(valor1, "id");
				valor2 = PropertyUtils.getProperty(valor2, "id");
			}
			if (!isEqual(valor1, valor2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Valida um CNPJ
	 * 
	 * @param cnpj cnpj a ser validado
	 * @return <code>true</code> caso cnpj válido, <code>false</code> caso contrário
	 */
	public static boolean isCNPJ(String cnpj) { //NOPMD by João Lúcio - necessário para limpar formatação
		cnpj = limpaMascara(cnpj);
		if (cnpj.length() != 14) {
			return false;
		}

		int soma = 0, dig;
		StringBuilder cnpj_calc = new StringBuilder(cnpj.substring(0, 12));

		char[] chr_cnpj = cnpj.toCharArray();

		/* Primeira parte */
		for (int i = 0; i < 4; i++) {
			if ((chr_cnpj[i] - 48 >= 0) && (chr_cnpj[i] - 48 <= 9)) {
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[i + 4] - 48 >= 0) && (chr_cnpj[i + 4] - 48 <= 9)) {
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
			}
		}
		dig = 11 - (soma % 11);

		cnpj_calc.append(((dig == 10) || (dig == 11)) ? "0" : Integer.toString(dig));

		/* Segunda parte */
		soma = 0;
		for (int i = 0; i < 5; i++) {
			if ((chr_cnpj[i] - 48 >= 0) && (chr_cnpj[i] - 48 <= 9)) {
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[i + 5] - 48 >= 0) && (chr_cnpj[i + 5] - 48 <= 9)) {
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
			}
		}
		dig = 11 - (soma % 11);
		cnpj_calc.append(((dig == 10) || (dig == 11)) ? "0" : Integer.toString(dig));

		if (!cnpj.equals(cnpj_calc)) {
			return false;
		}
		return true;
	}

	/**
	 * Valida um CPF.
	 * 
	 * @param cpf cpf a ser validado
	 * @return <code>true</code> caso cpf válido, <code>false</code> caso contrário
	 */
	public static boolean isCPF(String cpf) { //NOPMD by João Lúcio - necessário para limpar formatação
		cpf = limpaMascara(cpf);
		if (cpf.length() == 11) {
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;
			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;
			for (int n_Count = 1; n_Count < cpf.length() - 1; n_Count++) {
				digitoCPF = Integer.valueOf(cpf.substring(n_Count - 1, n_Count));
				d1 = d1 + (11 - n_Count) * digitoCPF;
				d2 = d2 + (12 - n_Count) * digitoCPF;
			}
			resto = (d1 % 11);
			if (resto < 2) {
				digito1 = 0;
			} else {
				digito1 = 11 - resto;
			}
			d2 += 2 * digito1;
			resto = (d2 % 11);
			if (resto < 2) {
				digito2 = 0;
			} else {
				digito2 = 11 - resto;
			}
			String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
			nDigResult = String.valueOf(digito1) + digito2;
			if (nDigVerific.equals(nDigResult)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se os objetos são iguais.
	 * 
	 * @param o1 primeiro objeto a verificar
	 * @param o2 segundo objeto a verificar
	 * @return <code>true</code> caso sejam iguais, <code>false</code> caso contrário
	 */
	public static boolean isEqual(Object o1, Object o2) { //NOPMD by João Lúcio - não dá para quebrar
		if (((o1 instanceof Collection) ^ (o2 instanceof Collection))
				&& (((o1 != null) && ((Collection<?>) o1).isEmpty() && (o2 == null)) || ((o2 != null)
						&& ((Collection<?>) o2).isEmpty() && (o1 == null)))) {
			return true;
		}
		if (((o1 == null) ^ (o2 == null))) {
			return false;
		}
		if ((o1 == null) && (o2 == null)) {
			return true;
		}
		if ((o1 instanceof String) && (o2 instanceof String)) {
			String str1 = (String) o1;
			String str2 = (String) o2;
			return str1.trim().equals(str2.trim());
		}
		if (o2 instanceof Collection) {
			Collection<?> colecao2 = (Collection<?>) o2;
			Collection<?> colecao1 = (Collection<?>) o1;
			return (colecao2).containsAll(colecao1) && (colecao1.size() == colecao2.size());
		}
		return o2.equals(o1);
	}

	/**
	 * Verifica se todas as propriedades do objeto passado possuem valores não significativos Exemplo: zero,
	 * strings vazias, qualquer propriedade nula, ou arrays vazios são considerados
	 * 
	 * Útil quando você tem um objeto onde é necessário que ao menos uma propriedade esteja preenchida
	 * 
	 * @param objeto objeto a verificar
	 * @param excludes propriedades que deverão ser desconsideradas da validação
	 * 
	 * @return <code>true</code> se for encontrado alguma propriedade vazia de acordo com critério
	 */
	public static boolean isTodasPropriedadesVazias(Object objeto, String... excludes) { //NOPMD by João Lúcio - não dá para quebrar método
		if (excludes != null) {
			Arrays.sort(excludes);
		}
		PropertyDescriptor[] descritores = PropertyUtils.getPropertyDescriptors(objeto);
		int totalPropriedades = 0;
		int totalVazias = 0;
		for (PropertyDescriptor descritor : descritores) {
			boolean readable = PropertyUtils.isReadable(objeto, descritor.getName());
			boolean writeable = PropertyUtils.isWriteable(objeto, descritor.getName());
			if (!readable || !writeable) {
				continue;
			}
			if ((excludes != null) && (Arrays.binarySearch(excludes, descritor.getName()) >= 0)) {
				continue;
			}
			Object propriedade;
			try {
				propriedade = PropertyUtils.getProperty(objeto, descritor.getName());
			} catch (Exception e) {
				continue;
			}
			if (descritor.getPropertyType().getName().equals(String.class.getName())) {
				totalPropriedades++;
				if (StringUtils.isBlank((String) propriedade)) {
					totalVazias++;
				}
				continue;
			}
			if (propriedade instanceof Number) {
				totalPropriedades++;
				if (((Number) propriedade).intValue() == 0) {
					totalVazias++;
				}
				continue;
			}
			if (propriedade instanceof Object[]) {
				totalPropriedades++;
				if (((Object[]) propriedade).length == 0) {
					totalVazias++;
				}
				continue;
			}
			if ((propriedade instanceof Date) || (propriedade instanceof Timestamp)) {
				totalPropriedades++;
			}
		}
		return totalPropriedades == totalVazias;
	}

	/**
	 * Limpar qualquer tipo de máscara e deixar somente números
	 * 
	 * @param caracteres {@link String} com máscara
	 * @return String sem máscara
	 */
	public static String limpaMascara(String caracteres) {
		if (caracteres != null) {
			return caracteres.replaceAll("\\W", "");//remove os não alfa-numéricos
		}
		return null;
	}

	/**
	 * Retorna <code>null</code> caso o valor da String seja vazio.
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
	 * Recupera o valor informado do número por extenso.
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
	 * Transforma uma {@link ByteArrayInputStream} para um {@link InputStream}
	 * 
	 * @param baos {@link ByteArrayInputStream} a tranformar
	 * @return InputStream associado
	 */
	public static InputStream outputStreamToInputStream(ByteArrayOutputStream baos) {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * Transforma as primeiras letras das palavras em maiuscúlo
	 * 
	 * @param texto texto a modificar
	 * @return novo texto
	 */
	public static String primeirasLetrasMaiusculo(String texto) {
		StringBuilder result = new StringBuilder("");
		String nome;

		StringTokenizer token = new StringTokenizer(texto.toLowerCase(DataUtil.LOCALE_BR), " ");

		while (token.hasMoreElements()) {
			nome = token.nextToken();

			if ("da".equals(nome)) {
				result.append(nome + " ");
			} else {
				result.append(nome.substring(0, 1).toUpperCase() + nome.substring(1) + " ");
			}
		}
		return result.toString();
	}

	/**
	 * Seta nulo em todas as propriedades do bean que forem "writable" e que não forem primitivas
	 * 
	 * @param objeto objeto a "resetar"
	 */
	public static void resetProperties(Object objeto) {
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(objeto);
		try {
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				boolean isWritable = PropertyUtils.isWriteable(objeto, descriptor.getName());
				boolean isPrimitive = descriptor.getPropertyType().isPrimitive();
				if (isWritable && !isPrimitive) {
					PropertyUtils.setProperty(objeto, descriptor.getName(), null);
				}
			}
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
		}
	}

	/**
	 * Faz a subtração de todos os elementos da segunda lista na primeira lista.
	 * 
	 * @param <T> tipo das listas
	 * @param original lista original
	 * @param subtracao lista a ser reduzida da lista original
	 * @param tipo tipo das listas
	 * @return novo {@link List} da diferença
	 */
	public static <T>List<T> subtrair(List<T> original, List<T> subtracao, Class<T> tipo) {
		return GenericsUtil.checkedList(ListUtils.subtract(original, subtracao), tipo);
	}

	/**
	 * Trata uma String convertendo códigos unicode em seus respectivos caracteres. Obs: Somente os caracteres
	 * % e & estão sendo tratados atualmente.
	 * 
	 * @param str String contendo os códigos unicode no lugar do caracteres especiais.
	 * @return String com os caracteres especiais substituídos.
	 */
	public static String trataCaracteresEspeciais(String str) {
		String resultado = str;
		if (str.indexOf("/u0025") != -1) {
			resultado = resultado.replaceAll("/u0025", "%");
		}
		if (str.indexOf("/u0026") != -1) {
			resultado = resultado.replaceAll("/u0026", "&");
		}
		return resultado;
	}

	/**
	 * Verifica se o usuário possui pelo menos uma das permissões informadas, separadas por vírgulas.
	 * 
	 * @param usuario usuário a verificar
	 * @param roles identificadores das permissões a verificar
	 * @return <code>true</code> caso tenha alguma permissão, <code>false</code> caso contrário
	 */
	public static boolean usuarioTemPermissao(Usuario usuario, String roles) {
		if (StringUtils.isNotBlank(roles)) {
			String[] rolesArray = roles.split(",");

			for (Permissao permissao : usuario.getPermissoes()) {
				for (String role : rolesArray) {
					if (permissao.getId().equals(Integer.parseInt(role))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Recupera um valor, e retorna em uma String formatada com duas casas decimais.
	 * 
	 * @param valor valor a formatar
	 * @return valor formatado em String
	 */
	public static String valorMonetarioToString(double valor) {
		return new DecimalFormat("#,##0.00").format(valor);
	}
}
