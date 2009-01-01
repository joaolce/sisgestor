/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.ValidatorForm;

/**
 * Base para validações de apresentação.
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
public class BaseValidator {

	private MessageResources		resources;
	//Campos que devem ser informados em caso de erro.
	private ActionErrors				actionErrors;
	private String						forwardErroValidacao;
	private String						focusControl;

	private ValidatorForm			validatorForm;
	private HttpServletRequest		request;
	private ActionMapping			mapping;
	private HttpSession				session;
	private HttpServletResponse	response;
	private static Log				logger;

	static {
		logger = LogFactory.getLog(BaseValidator.class);
	}

	/**
	 * Método de execução padrão, deve ser executado para preencher variáveis locais e facilitar a vida do
	 * programador. A principio, a DispatchActionGenérica chama esse método.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws InvocationTargetException
	 */
	public void execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException {
		//faz o cast do form, para não ser feito em todos os métodos

		if (form instanceof ValidatorForm) {
			this.validatorForm = (ValidatorForm) form;
		}

		//seta o request para ser usado em outros métodos. não remover
		this.request = request;
		this.response = response;

		//seta o mapping para ser usado em outros métodos. não remover
		this.mapping = mapping;

		//seta a sessão para ser usada em outros métodos
		this.session = request.getSession();

		//buscando método de validação
		String nomeMetodo = request.getParameter(mapping.getParameter());

		try {
			Method method = this.getClass().getMethod(nomeMetodo);
			method.invoke(this);
		} catch (SecurityException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.info("método de validação não encontrado");
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		}
	}

	/**
	 * Retorna os erros atuais.
	 * 
	 * @return {@link ActionErrors} atuais
	 */
	public ActionErrors getActionErrors() {
		if (this.actionErrors == null) {
			this.actionErrors = new ActionErrors();
		}
		return this.actionErrors;
	}

	/**
	 * Recupera o controle de focus de campo.
	 * 
	 * @return controle de focus de campo
	 */
	public String getFocusControl() {
		return this.focusControl;
	}

	/**
	 * Recupera a {@link String} do forward de erro de validação.
	 * 
	 * @return {@link String} do forward
	 */
	public String getForwardErroValidacao() {
		return this.forwardErroValidacao;
	}

	/**
	 * Recupera o {@link MessageResources} da aplicação.
	 * 
	 * @return {@link MessageResources}
	 */
	public MessageResources getResources() {
		return this.resources;
	}

	/**
	 * Armazena os erros atuais.
	 * 
	 * @param actionErrors {@link ActionErrors} atuais
	 */
	public void setActionErrors(ActionErrors actionErrors) {
		this.actionErrors = actionErrors;
	}

	/**
	 * Armazena o {@link MessageResources} da aplicação.
	 * 
	 * @param resources {@link MessageResources} da aplicação
	 */
	public void setResources(MessageResources resources) {
		this.resources = resources;
	}

	/**
	 * Adiciona uma mensagem de erro.
	 * 
	 * @param key chave da mensagem
	 * @param args replacements da mensagem
	 */
	protected void addError(String key, String... args) {
		this.getActionErrors().add(key, new ActionMessage(key, args));
	}

	/**
	 * Adiciona uma mensagem de erro.
	 * 
	 * @param keyProperty chave da mensagem no properties
	 * @param keyValue chave do label no properties
	 */
	protected void addErrorKey(String keyProperty, String keyValue) {
		this.addError(keyProperty, this.getMessageKey(keyValue));
	}

	/**
	 * Recupera o valor da propriedade no form atual.
	 * 
	 * @param formProperty nome da propriedade
	 * @return valor da propriedade no form
	 */
	protected Object getFormValue(String formProperty) {
		Object value = null;
		try {
			value = PropertyUtils.getProperty(this.getValidatorForm(), formProperty);
		} catch (Exception e) {
			value = null;
		}
		return value;
	}

	/**
	 * Recupera o {@link ActionMapping} atual.
	 * 
	 * @return {@link ActionMapping} atual
	 */
	protected ActionMapping getMapping() {
		return this.mapping;
	}

	/**
	 * Retorna o valor do key especificado no properties
	 * 
	 * @param key chave da mensagem no properties
	 * @param args argumentos da mensagem
	 * @return {@link String} com a mensagem gerada
	 */
	protected String getMessageKey(String key, String... args) {
		return this.getResources().getMessage(key, args);
	}

	/**
	 * Recupera o {@link HttpServletRequest} atual.
	 * 
	 * @return {@link HttpServletRequest} atual
	 */
	protected HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * Recupera o {@link HttpServletResponse} atual.
	 * 
	 * @return {@link HttpServletResponse} atual
	 */
	protected HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * Recupera a {@link HttpSession} atual.
	 * 
	 * @return {@link HttpSession} atual
	 */
	protected HttpSession getSession() {
		return this.session;
	}

	/**
	 * Recupera o usuário da sessão
	 * 
	 * @return o usuário logado no sistema
	 * @throws Exception
	 */
	protected Usuario getUser() throws Exception {
		return (Usuario) this.getSession().getAttribute(DadosContexto.USUARIOSESSAO);
	}

	/**
	 * Recupera o {@link ValidatorForm} atual.
	 * 
	 * @return {@link ValidatorForm} atual
	 */
	protected ValidatorForm getValidatorForm() {
		return this.validatorForm;
	}

	/**
	 * Valida uma propriedade do form como número diferente de zero e diferente de null e não adiciona mensagem
	 * 
	 * @param formProperty nome da propriedade no form
	 * @return <code>true</code> caso seja número válido, <code>false</code> caso contrário
	 */
	protected boolean isValidNumber(String formProperty) {
		Object numero = this.getFormValue(formProperty);
		if (numero instanceof Number) {
			return ((Number) numero).intValue() != 0;
		}
		return false;
	}

	/**
	 * Armazena o controle de focus no campo.
	 * 
	 * @param focusControl campo a ter o focus
	 */
	protected void setFocusControl(String focusControl) {
		if (this.focusControl == null) {
			this.focusControl = focusControl;
		}
	}

	/**
	 * Armazena o controle de focus no campo.
	 * 
	 * @param focusControl campo a ter o focus
	 * @param i índice do controle
	 */
	protected void setFocusControl(String focusControl, int i) {
		if (this.focusControl == null) {
			this.focusControl = focusControl + "[" + i + "]";
		}
	}

	/**
	 * Atribuí o forward de erro de validação.
	 * 
	 * @param forwardErroValidcao forward de erro de validação
	 */
	protected void setForwardErroValidacao(String forwardErroValidcao) {
		this.forwardErroValidacao = forwardErroValidcao;
	}

	/**
	 * Armazena o {@link ActionMapping} atual.
	 * 
	 * @param mapping {@link ActionMapping} atual
	 */
	protected void setMapping(ActionMapping mapping) {
		this.mapping = mapping;
	}

	/**
	 * Armazena o {@link HttpServletRequest} atual.
	 * 
	 * @param request {@link HttpServletRequest} atual
	 */
	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Armazena o {@link HttpServletResponse} atual.
	 * 
	 * @param response {@link HttpServletResponse} atual
	 */
	protected void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * Armazena a {@link HttpSession} atual.
	 * 
	 * @param session {@link HttpSession} atual
	 */
	protected void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * Armazena o {@link ValidatorForm} atual.
	 * 
	 * @param validatorForm {@link ValidatorForm} atual
	 */
	protected void setValidatorForm(ValidatorForm validatorForm) {
		this.validatorForm = validatorForm;
	}

	/**
	 * Verifica se um campo possui somente caracteres alfa-numéricos.
	 * 
	 * @param labelKeyProp chave do label do campo
	 * @param formProperty propriedade do form
	 */
	protected void validaAlfaNumerico(String labelKeyProp, String formProperty) {
		Object value = this.getFormValue(formProperty);
		if ((value != null) && !((String) value).matches("\\w*")) {
			this.addError("erro.alfaNumerico", this.getMessageKey(labelKeyProp));
			this.setFocusControl(formProperty);
		}
	}

	/**
	 * Valida um CNPJ.
	 * 
	 * @param formProperty propriedade do form a ser validada como cnpj
	 */
	protected void validaCNPJ(String formProperty) {
		String str_cnpj = (String) this.getFormValue(formProperty);
		if (!Utils.isCNPJ(str_cnpj)) {
			this.addError("erro.cnpjInvalido");
			this.setFocusControl(formProperty);
		}
	}

	/**
	 * Valida um CPF.
	 * 
	 * @param formProperty propriedade do form a ser validada como cpf
	 */
	protected void validaCPF(String formProperty) {
		String s_aux = (String) this.getFormValue(formProperty);
		if (!Utils.isCPF(s_aux)) {
			this.addError("erro.cpfInvalido");
			this.setFocusControl(formProperty);
		}
	}

	/**
	 * Valida uma data.
	 * 
	 * @param labelProperty chave do label da data
	 * @param formProperty propriedade do form
	 */
	protected void validaData(String labelProperty, String formProperty) {
		String dataInformadaString = (String) this.getFormValue(formProperty);
		if (StringUtils.isNotBlank(dataInformadaString) && !DataUtil.isValidDate(dataInformadaString)) {
			this.addError("erro.dataInvalida", this.getMessageKey(labelProperty));
			this.setFocusControl(formProperty);
		}
	}

	/**
	 * Valida se uma data informada no formProperty é maior que a data atual, desprezando os minutos e
	 * segundos.
	 * 
	 * @param labelProperty chave do label no properties
	 * @param formProperty propriedade do form
	 */
	protected void validaDataMaiorDataAtual(String labelProperty, String formProperty) {
		String dataInformadaString = (String) this.getFormValue(formProperty);
		if (StringUtils.isNotBlank(dataInformadaString)) {
			//Testa se a data informada é válida
			if (!DataUtil.isValidDate(dataInformadaString)) {
				this.addError("erro.dataInvalida", this.getMessageKey(labelProperty));
				this.setFocusControl(formProperty);
				return;
			}

			Date dataInformadaDate = DataUtil.converteStringToDate(dataInformadaString);
			Date dataAtual = DataUtil.getDataAtualSemHHMMSS();
			if (dataInformadaDate.after(dataAtual)) {
				String strDataAtual = DataUtil.converteDateToStringBR(dataAtual);
				this.addError("erro.dataMaiorDataAtual", this.getMessageKey(labelProperty), strDataAtual);
				this.setFocusControl(formProperty);
			}
		}
	}

	/**
	 * Valida se uma data informada no formProperty é menor que a data atual, desprezando os minutos e
	 * segundos.
	 * 
	 * @param labelProperty chave do label no properties
	 * @param formProperty propriedade do form
	 */
	protected void validaDataMenorDataAtual(String labelProperty, String formProperty) {
		String dataInformadaString = (String) this.getFormValue(formProperty);
		if (StringUtils.isNotBlank(dataInformadaString)) {
			//Testa se a data informada é válida
			if (!DataUtil.isValidDate(dataInformadaString)) {
				this.addError("erro.dataInvalida", this.getMessageKey(labelProperty));
				this.setFocusControl(formProperty);
				return;
			}

			Date dataInformadaDate = DataUtil.converteStringToDate(dataInformadaString);
			Date dataAtual = DataUtil.getDataAtualSemHHMMSS();
			if (dataInformadaDate.before(dataAtual)) {
				String strDataAtual = DataUtil.converteDateToStringBR(dataAtual);
				this.addError("erro.dataMenorDataAtual", this.getMessageKey(labelProperty), strDataAtual);
				this.setFocusControl(formProperty);
			}
		}
	}

	/**
	 * Valida um campo de e-mail.
	 * 
	 * @param labelProperty
	 * @param formProperty nome da propriedade representante do campo e-mail no form
	 */
	protected void validaEmail(String labelProperty, String formProperty) {
		String value = (String) this.getFormValue(formProperty);
		if (StringUtils.isNotBlank(value) && !GenericValidator.isEmail(value)) {
			this.addError("erro.emailInvalido", this.getMessageKey(labelProperty));
			this.setFocusControl(formProperty);
		}
	}

	/**
	 * Valida uma mensagem customizada sobre um determinado campo do form para que o mesmo seja destacado em
	 * vermelho quando a validação for processada e enviada para o cliente Javascript
	 * 
	 * @param keyProperty chave da mensagem no properties
	 * @param formProperty nome do campo no form
	 */
	protected void validaMensagem(String keyProperty, String formProperty) {
		this.setFocusControl(formProperty);
		this.addError(keyProperty);
	}

	/**
	 * Salva uma mensagem de erro para campo requerido se o valor do mesmo não tiver sido preenchido se for um
	 * array de Integer ou String, ele validará o tamanho != 0
	 * 
	 * @param labelProperty nome do campo no arquivo .properties
	 * @param formProperty nome da propriedade do form a ser verificada
	 */
	protected void validaRequerido(String labelProperty, String formProperty) {
		Object value = this.getFormValue(formProperty);
		if (value == null) {
			Class<?> propertyType;
			try {
				propertyType = PropertyUtils.getPropertyType(this.getValidatorForm(), formProperty);
			} catch (Exception e) {
				propertyType = null;
			}
			if ((propertyType != null) && propertyType.isArray()) {
				this.addErrorKey("erro.necessarioSelecao", labelProperty);
			} else {
				this.addErrorKey("erro.required", labelProperty);
			}
			this.setFocusControl(formProperty);
		}
		if (value instanceof String) {
			if (GenericValidator.isBlankOrNull((String) value)) {
				this.addErrorKey("erro.required", labelProperty);
				this.setFocusControl(formProperty);
			}
		}
		if (value instanceof Number) {
			if (((Number) value).intValue() == 0) {
				this.addErrorKey("erro.required", labelProperty);
				this.setFocusControl(formProperty);
			}
		}
		if (value instanceof Object[]) {
			Object[] valorArray = (Object[]) value;
			if (valorArray.length == 0) {
				this.addErrorKey("erro.necessarioSelecao", labelProperty);
				this.setFocusControl(formProperty);
			}
		}
		if (value instanceof Character) {
			if (GenericValidator.isBlankOrNull(((Character) value).toString())) {
				this.addErrorKey("erro.required", labelProperty);
				this.setFocusControl(formProperty);
			}
		}
	}

	/**
	 * Valida uma string para saber se o tamanho máximo de caracteres foi ultrapassado valida um integer para
	 * saber se o valor do mesmo é superior ao valor passado no parâmetro
	 * 
	 * @param keyProperty nome do campo no properties
	 * @param formProperty nome da propriedade do form a ser verificada
	 * @param maximo máximo a ser validado
	 */
	protected void validaTamanhoMaximo(String keyProperty, String formProperty, int maximo) {
		Object value = this.getFormValue(formProperty);
		if (value instanceof String) {
			String valor = (String) value;
			if (valor.length() > maximo) {
				this.addError("erro.maxlength", this.getMessageKey(keyProperty), "" + maximo);
				this.setFocusControl(formProperty);
			}
		}
		if (value instanceof Integer) {
			Integer valor = (Integer) value;
			if (valor.intValue() > maximo) {
				this.addError("erro.maxinteger", this.getMessageKey(keyProperty), "" + maximo);
				this.setFocusControl(formProperty);
			}
		}
	}
}
