/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.sisgestor.util;

import br.com.sisgestor.entidade.Anexo;
import br.com.sisgestor.entidade.ObjetoPersistente;
import br.com.sisgestor.entidade.Permissao;
import br.com.sisgestor.entidade.TipoCampoEnum;
import br.com.sisgestor.entidade.Usuario;
import br.com.sisgestor.negocio.exception.NegocioException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
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

	private static MessageResources resources;
	private static ServletContext contexto;
	private static ThreadLocal<Usuario> threadUsuario = new ThreadLocal<Usuario>();
	private static final Log LOG = LogFactory.getLog(Utils.class);

	/**
	 * Pattern singleton.
	 */
	private Utils() {
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
	 * Copia uma stream para outra.
	 * 
	 * @param destino stream de destino
	 * @param origem stream de origem
	 * @throws IOException caso algum erro de i/o aconteça
	 */
	public static void copia(OutputStream destino, InputStream origem) throws IOException {
		byte[] buffer = new byte[8192];
		int qtdeLida = 0;

		while ((qtdeLida = origem.read(buffer)) > 0) { //NOPMD by João Lúcio - mais legível
			destino.write(buffer, 0, qtdeLida);
		}
		destino.close();
		origem.close();
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
	 * @param exclusao propriedade a ignorar na cópia
	 * @throws Exception caso ocorra erro na recuperação das propriedades
	 */
	public static void copyProperties(Object destino, Object origem, String... exclusao) throws Exception { //NOPMD by João Lúcio - não dá para quebrar método
		//pega os descritores
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(destino);
		//as propriedades que são do mesmo tipo serão copiadas por último
		List<String> propriedadesIguais = new ArrayList<String>();
		List<String> excluidos = Arrays.asList(exclusao);

		for (PropertyDescriptor descriptor : propertyDescriptors) {
			PropertyDescriptor descriptorOrigem;
			String nomePropriedade = descriptor.getName();
			if (excluidos.contains(nomePropriedade)) {
				continue;
			}
			try {
				descriptorOrigem = PropertyUtils.getPropertyDescriptor(origem, nomePropriedade);
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
			if (!PropertyUtils.isWriteable(destino, nomePropriedade)
					|| !PropertyUtils.isReadable(origem, nomePropriedade)) {
				continue;
			}
			//se os tipos são o mesmo separa para transportar depois
			if (propertyType.getName().equals(propertyTypeOrigem.getName())) {
				propriedadesIguais.add(nomePropriedade);
			} else {
				//o valor de origem para ser copiado deverá estar preenchido
				Object valorOrigem = PropertyUtils.getProperty(origem, descriptorOrigem.getName());

				// ================== CONVERSOR DE DATAS ==================

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
						PropertyUtils.setProperty(destino, nomePropriedade, DataUtil.stringToTimestamp(valor));
					} else {
						//CONVERSOR DATE
						PropertyUtils.setProperty(destino, nomePropriedade, DataUtil.stringToUtilDate(valor));
					}
					continue;
				} else if (eUmaString && Date.class.isInstance(valorOrigem)) {
					Date valor = (Date) PropertyUtils.getProperty(origem, descriptorOrigem.getName());
					PropertyUtils.setProperty(destino, nomePropriedade, DataUtil.utilDateToString(valor));
					continue;
				}

				// ================== ASSOCIAÇÕES ==================
				boolean ehUmObjetoPersistente = false;
				//verificar se o tipo da propriedade é um ObjetoPersistente
				Class<?> superclass = propertyType;
				while (superclass != null) {
					if (superclass.getName().equals(ObjetoPersistente.class.getName())) {
						ehUmObjetoPersistente = true;
						break;
					}
					superclass = superclass.getSuperclass();
				}
				if (ehUmObjetoPersistente) {
					Object valor = PropertyUtils.getProperty(origem, nomePropriedade);
					if ((valor != null) && (((Number) valor).intValue() == -1)) {
						PropertyUtils.setProperty(destino, nomePropriedade, null);
						continue;
					}
					//se o valor é nulo ou igual a zero pula
					if ((valor == null) || ((valor instanceof Number) && (((Number) valor).intValue() == 0))) {
						continue;
					}
					Object property = propertyType.newInstance();
					if (!PropertyUtils.getPropertyType(property, "id").getName()
							.equals(valor.getClass().getName())) {
						continue;
					}
					PropertyUtils.setProperty(property, "id", valor);
					PropertyUtils.setProperty(destino, nomePropriedade, property);
				} else if (ObjetoPersistente.class.isInstance(valorOrigem)) {
					Object property = PropertyUtils.getProperty(origem, nomePropriedade);
					//se o objeto estiver nulo pula
					if (property == null) {
						continue;
					}
					Object valor = PropertyUtils.getProperty(property, "id");
					if (!descriptor.getPropertyType().getName().equals(valor.getClass().getName())) {
						continue;
					}
					PropertyUtils.setProperty(destino, nomePropriedade, valor);
				} else if (TipoCampoEnum.class.isAssignableFrom(propertyType)) {
					PropertyUtils.setProperty(destino, nomePropriedade, TipoCampoEnum.getTipo(Integer.class
							.cast(valorOrigem)));
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
	 * Faz o download do arquivo.
	 * 
	 * @param anexo arquivo a fazer download
	 * @param response response a enviar o arquivo
	 * @throws Exception caso ocorra algum erro no download
	 */
	public static void download(Anexo anexo, HttpServletResponse response) throws Exception {
		if (anexo == null) {
			throw new NegocioException("erro.arquivo.naoEncontrado");
		}
		response.reset();

		String contentType = anexo.getContentType();
		if (StringUtils.isNotBlank(contentType)) {
			response.setContentType(contentType);
		}

		byte[] dadosArquivo = anexo.getDados();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + anexo.getNome() + "\"");
		response.setContentLength(dadosArquivo.length);

		copia(response.getOutputStream(), new ByteArrayInputStream(dadosArquivo));
	}

	/**
	 * Recupera um bean do spring.
	 * 
	 * @param <T> tipo do bean
	 * @param clazz classe do bean
	 * @return bean do spring
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getBean(Class<T> clazz) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getContexto());
		String nomeBean = StringUtils.uncapitalize(clazz.getSimpleName());
		return (T) context.getBean(nomeBean);
	}

	/**
	 * Recupera o {@link ServletContext} da aplicação.
	 * 
	 * @return {@link ServletContext} da aplicação
	 */
	public static ServletContext getContexto() {
		return contexto;
	}

	/**
	 * Pegar uma mensagem do properties pelo key
	 * 
	 * @param key chave no arquivo properties
	 * @param args argumentos da mensagem a subtituir
	 * @return String com a mensagem
	 */
	public static String getMessageFromProperties(String key, String... args) {
		synchronized (Utils.class) {
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
	 * Recupera o usuário logado no sistema.
	 * 
	 * @return usuário logado no sistema
	 */
	public static Usuario getUsuario() {
		return threadUsuario.get();
	}

	/**
	 * Faz o injection dos beans do spring no objeto informado.
	 * 
	 * @param obj objeto a setar dependências
	 */
	public static void injectionAutowired(Object obj) {
		for (Method metodo : obj.getClass().getMethods()) {
			if (metodo.isAnnotationPresent(Autowired.class)) {
				Class<?> bean = null;
				try {
					bean = metodo.getParameterTypes()[0];
					metodo.invoke(obj, getBean(bean));
				} catch (Exception e) {
					LOG.error("Erro ao inserir bean " + bean + " na action " + obj.getClass().getSimpleName(), e);
				}
			}
		}
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
	 * Transforma as primeiras letras das palavras em maiuscúlo
	 * 
	 * @param texto texto a modificar
	 * @return novo texto
	 */
	public static String primeirasLetrasMaiusculo(String texto) {
		StringBuilder result = new StringBuilder("");
		String nome;

		StringTokenizer token = new StringTokenizer(texto.toLowerCase(DataUtil.LOCALE_PT_BR), " ");

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
	 * Armazena o {@link ServletContext} da aplicação.
	 * 
	 * @param contexto {@link ServletContext} da aplicação
	 */
	public static void setContexto(ServletContext contexto) {
		Utils.contexto = contexto;
	}

	/**
	 * Armazena o usuário logado no sistema.
	 * 
	 * @param usuario usuário logado
	 */
	public static void setUsuario(Usuario usuario) {
		Utils.threadUsuario.set(usuario);
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
}
