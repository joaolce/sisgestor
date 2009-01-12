/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.ajaxUtils.AjaxResponse;
import br.com.ucb.sisgestor.apresentacao.validator.BaseValidator;
import br.com.ucb.sisgestor.apresentacao.validator.utils.RoleValidatorReader;
import br.com.ucb.sisgestor.apresentacao.validator.utils.ValidatorReader;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.GenericsUtil;
import br.com.ucb.sisgestor.util.ParametrosURL;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.ConstantesAplicacao;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

/**
 * Action base para o projeto.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public class BaseAction extends DispatchAction {

	private static Log logger;
	/** forward de entrada, todas as actions devem ter. */
	public static final String FWD_ENTRADA = "entrada";
	/** forward de erro de validação. */
	public static final String FWD_ERRO_VALIDACAO = "erroValidacao";

	private ThreadLocal<ActionMapping> mappingThread = new ThreadLocal<ActionMapping>();
	private ThreadLocal<ActionForm> formThread = new ThreadLocal<ActionForm>();
	private ThreadLocal<HttpServletRequest> requestThread = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> responseThread = new ThreadLocal<HttpServletResponse>();
	private ThreadLocal<HttpSession> sessionThread = new ThreadLocal<HttpSession>();
	private ThreadLocal<ActionErrors> actionErrorsThread = new ThreadLocal<ActionErrors>();

	static {
		logger = LogFactory.getLog(BaseAction.class);
	}

	/**
	 * Método padrão para exibir a tela de entrada caso seja necessário carregar algum dado para
	 * exibir na tela.
	 * 
	 * @return entrada
	 * @throws Exception
	 */
	public ActionForward entrada() throws Exception {
		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Método padrão para exibir a tela de entrada caso seja necessário carregar algum dado para
	 * exibir na tela.
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return {@link ActionForward}
	 * @throws Exception
	 */
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.entrada();
	}

	/**
	 * Sobrescrita do método execute. A idéia é fazer alguns procedimentos genéricos aqui, para que
	 * todas as actions possam utilizar alguns métodos utilitários que existem aqui.
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return {@link ActionForward}
	 * @throws Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//popula as variaveis de instância
		this.populaParametrosAction(mapping, actionForm, request, response);
		ConstantesAplicacao.setConstantes(request);
		//verificar se a requisição post possui um referer, questões de segurança 
		this.segurancaPost();

		/* se o parâmetro estiver presente é porque a submissão foi assíncrona e para não acontecer problemas
		 * com a codificação do charset (problemas com acentos e caracteres especiais) utiliza-se o 
		 * Utils.decodeAjaxForm para decodificar o formulário que foi codificado no Javascript
		 */
		if (this.isAJAXRequest()) {
			logger.debug("requisição ajax em processo");
			if ("get".equalsIgnoreCase(request.getMethod())) {
				Utils.decodeAjaxForm(actionForm);
			}
			Utils.doNoCachePagina(request, response);
			/*
			 * esse header é adicionado para o javascript identificar que 
			 * a resposta veio da aplicação e não de uma página de login ou algo do tipo
			 */
			response.addHeader("ajaxResponse", "true");
		}
		//pega o nome do método a ser chamado
		String nomeMetodo = request.getParameter(mapping.getParameter());
		if (nomeMetodo == null) {
			//se o parâmetro estiver nulo, a dispatch action vai lancar o erro
			return super.execute(mapping, actionForm, request, response);
		}
		try {
			this.doUsuario(false);
		} catch (NegocioException e) {
			return new ActionForward("dologin", true);
		} catch (InvocationTargetException e) {
			logger.fatal(e.getMessage(), e);
			this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"" + e.getTargetException());
			return null;
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

		//VALIDAÇÃO DE ROLES
		RoleValidatorReader rvr = new RoleValidatorReader();
		if (!rvr.isUserInAnyRoles(this.getUser(), this.getMapping().getType(), nomeMetodo)) {
			if (this.isAJAXRequest()) {
				this.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			} else {
				this.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			return this.findForward("acessoNegado");
		}
		//VALIDAÇÃO - executa a validação antes de executar o método de dispatch
		BaseValidator validator = this.chamaMetodoValidacao();
		if (validator != null) {
			ActionErrors errors = validator.getActionErrors();
			String focusControl = validator.getFocusControl();
			if ((errors != null) && (errors.size() != 0)) {
				//verifica a origem da requisição - síncrona ou assíncrona
				return this.processaErro(mapping, validator.getForwardErroValidacao(), errors,
						focusControl);
			}
		}
		//FIM VALIDAÇÃO

		try {
			return super.execute(mapping, actionForm, request, response);
		} catch (NegocioException e) {
			if (e.getArgs() != null) {
				this.addMessageKey(e.getMessage(), e.getArgs());
			} else {
				this.addMessageKey(e.getMessage());
			}

			if (this.isAJAXRequest()) {
				return this.sendAJAXResponse(false);
			}
			this.saveMessages(false);
			return mapping.findForward(validator.getForwardErroValidacao());
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			if (this.isAJAXRequest()) {
				this.addMessageKey("erro.falhaMensagemAjax", e.getMessage());
				return this.sendAJAXResponse(false);
			}
			throw e;
		}
	}

	/**
	 * Adiciona uma mensagem.
	 * 
	 * @param mensagem {@link String} com a mensagem
	 */
	protected void addMessage(String mensagem) {
		this.getActionErrors().add("erro.mensagem", new ActionMessage("erro.mensagem", mensagem));
	}

	/**
	 * Adiciona uma mensagem pela chave.
	 * 
	 * @param keyProperty chave da mensagem
	 */
	protected void addMessageKey(String keyProperty) {
		this.getActionErrors().add(keyProperty, new ActionMessage(keyProperty));
	}

	/**
	 * Adiciona uma mensagem com vários replacements
	 * 
	 * @param keyProperty chave da mensagem
	 * @param args argumentos da mensagem
	 */
	protected void addMessageKey(String keyProperty, String... args) {
		this.getActionErrors().add(keyProperty, new ActionMessage(keyProperty, args));
	}

	/**
	 * Adiciona uma mensagem pela chave e pela chave do replacement.
	 * 
	 * @param keyProperty chave da mensagem
	 * @param keyValue chave do replacement do arg0
	 */
	protected void addMessageKeyValue(String keyProperty, String keyValue) {
		this.addMessageKey(keyProperty, this.getMessageKey(keyValue));
	}

	/**
	 * Adiciona os parâmetros criados ao path (que aponte para uma url e com redirect=true) de um
	 * ActionForward
	 * 
	 * @param parametros parametros adicionados
	 * @param forward forward a alterar o path
	 */
	protected void addParametrosToForwardPath(ParametrosURL parametros, ActionForward forward) {
		forward.setPath(parametros.aggregateParams(forward.getPath()));
	}

	/**
	 * Copia propriedades de um form para uma entidade ou vice versa
	 * 
	 * @see Utils#copyProperties(Object, Object)
	 * 
	 * @param dest objeto de destino
	 * @param orig objeto de origem
	 * @throws Exception
	 */
	protected void copyProperties(Object dest, Object orig) throws Exception {
		Utils.copyProperties(dest, orig);
	}

	/**
	 * Coloca o usuário na sessão
	 * 
	 * @param ignoraSessao ignora se já tiver o usuário na sessão
	 * @throws Exception
	 */
	protected final void doUsuario(boolean ignoraSessao) throws Exception {
		if ((this.getRequest().getUserPrincipal() == null)
				|| (this.getRequest().getUserPrincipal().getName() == null)) {
			throw new Exception("userPrincipal está nulo!");
		}

		Usuario usuarioAtual = (Usuario) this.getSession().getAttribute(DadosContexto.USUARIOSESSAO);
		String name = this.getRequest().getUserPrincipal().getName();

		if ((usuarioAtual == null) || !name.equalsIgnoreCase(usuarioAtual.getLogin().trim())
				|| ignoraSessao) {
			logger.debug("completando o processo de login");

			UsuarioBO bo = UsuarioBOImpl.getInstancia();

			usuarioAtual = bo.recuperarPorLogin(name);

			this.getSession().setAttribute(DadosContexto.DATA_LOGIN, DataUtil.getDataAtualCompleta());
			this.getSession().setAttribute(DadosContexto.HORA_LOGIN, DataUtil.getDataAtual());
			this.getSession().setAttribute(DadosContexto.USUARIOSESSAO, usuarioAtual);
		}
	}

	/**
	 * Busca um {@link ActionForward} e retorna.
	 * 
	 * @param forward {@link ActionForward} encontrado
	 * @return ActionForward
	 */
	protected ActionForward findForward(String forward) {
		return this.getMapping().findForward(forward);
	}

	/**
	 * Busca um {@link ActionForward} onde as propriedades podem ser alteradas.
	 * 
	 * @param forward {@link ActionForward} encontrado
	 * @return ActionForward
	 */
	protected ActionForward findForwardConfigurable(String forward) {
		ActionForward fwd = new ActionForward();
		try {
			PropertyUtils.copyProperties(fwd, this.findForward(forward));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fwd;
	}

	/**
	 * Recupera o {@link ActionErrors} atual.
	 * 
	 * @return actionErrors atual
	 */
	protected ActionErrors getActionErrors() {
		ActionErrors errors;
		if (this.actionErrorsThread.get() != null) {
			errors = this.actionErrorsThread.get();
		} else {
			errors = new ActionErrors();
			this.actionErrorsThread.set(errors);
		}
		return errors;
	}

	/**
	 * Recupera um atributo do request.
	 * 
	 * @param name nome do atributo
	 * @return valor do atributo
	 */
	protected Object getAttribute(String name) {
		return this.getRequest().getAttribute(name);
	}

	/**
	 * Retorna o {@link ActionForm} atual.
	 * 
	 * @return form atual
	 */
	protected ActionForm getForm() {
		return this.formThread.get();
	}

	/**
	 * Busca o inputForward do forward que está sendo utilizado no momento.
	 * 
	 * @return {@link ActionForward}
	 */
	protected ActionForward getInputForward() {
		return this.getMapping().getInputForward();
	}

	/**
	 * Retorna o {@link ActionMapping} atual.
	 * 
	 * @return mapping atual.
	 */
	protected ActionMapping getMapping() {
		return this.mappingThread.get();
	}

	/**
	 * Retorna o valor do key especificado no properties
	 * 
	 * @param key chave da mensagem
	 * @param args argumentos da mensagem
	 * @return mensagem
	 */
	protected String getMessageKey(String key, String... args) {
		return this.getResources(this.getRequest()).getMessage(key, args);
	}

	/**
	 * Recupera o {@link HttpServletRequest} atual.
	 * 
	 * @return request atual
	 */
	protected HttpServletRequest getRequest() {
		return this.requestThread.get();
	}

	/**
	 * Recupera o {@link HttpServletResponse} atual.
	 * 
	 * @return response atual
	 */
	protected HttpServletResponse getResponse() {
		return this.responseThread.get();
	}

	/**
	 * Retorna a {@link HttpSession} atual.
	 * 
	 * @return sessão atual
	 */
	protected HttpSession getSession() {
		return this.sessionThread.get();
	}

	/**
	 * Retorna um atributo da sessão
	 * 
	 * @param nome nome do atributo
	 * @return valor do atributo
	 */
	protected Object getSessionAttribute(String nome) {
		return this.getSession().getAttribute(nome);
	}

	/**
	 * Retorna a url do servidor. <br />
	 * Exemplo http://localhost:8080/sisgestor
	 * 
	 * @return {@link URL} do servidor
	 * @throws MalformedURLException
	 */
	protected URL getUrlServidor() throws MalformedURLException {
		return RequestUtils.serverURL(this.getRequest());
	}

	/**
	 * Recupera o usuário logado no sistema
	 * 
	 * @return o usuário logado no sistema
	 * @throws Exception
	 */
	protected Usuario getUser() throws Exception {
		this.doUsuario(false);
		return (Usuario) this.getSession().getAttribute(DadosContexto.USUARIOSESSAO);
	}

	/**
	 * Invoca um método qualquer do form. Util quando não se sabe a instância do form que se está
	 * usando. Deve ser usado com cautela.
	 * 
	 * @param methodName nome do método a ser executado
	 * @param paramTypes tipo dos parâmetros
	 * @param params parâmetros do método
	 * @return d
	 */
	protected Object invokeFormMethod(String methodName, Class<?>[] paramTypes, Object[] params) {
		Object object = null;
		try {
			Class<?> classeForm = this.getForm().getClass();
			Method method = classeForm.getMethod(methodName, paramTypes);

			object = method.invoke(this.getForm(), params);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (SecurityException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
		return object;
	}

	/**
	 * Informa se a requisição é via AJAX.
	 * 
	 * @return <code>true</code> caso seja, <code>false</code> caso não seja
	 */
	protected boolean isAJAXRequest() {
		return this.getRequest().getHeader("X-Requested-With") != null;
	}

	/**
	 * Popula as variáveis de instância com os parametros recebidos pela Action.
	 * 
	 * Isso está separada para que possa ser usado caso algum método execute seja sobrescrito em uma
	 * classe filha.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected final void populaParametrosAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		this.mappingThread.set(mapping);
		this.formThread.set(form);
		this.requestThread.set(request);
		this.responseThread.set(response);
		this.sessionThread.set(request.getSession());
	}

	/**
	 * Remove o atributo passado da sessão.
	 * 
	 * @param nome nome do atributo
	 */
	protected void removeSessionAttribute(String nome) {
		this.getSession().removeAttribute(nome);
	}

	/**
	 * Reseta as propriedades do form atual.
	 */
	protected void reset() {
		if (this.getForm() != null) {
			this.getForm().reset(this.getMapping(), this.getRequest());
		}
	}

	/**
	 * Salva os erros no request
	 * 
	 * @param actionErrors erros a salvar
	 */
	protected void saveErrors(ActionErrors actionErrors) {
		super.saveErrors(this.getRequest(), actionErrors);
	}

	/**
	 * Salva os erros no request.
	 * 
	 * @param status <code>true</code> - verde, <code>false</code> - vermelho
	 */
	protected void saveMessages(boolean status) {
		this.setStatus(status);
		if (this.getActionErrors() != null) {
			super.saveErrors(this.getRequest(), this.getActionErrors());
		}
		this.setActionErrors(null);
	}

	/**
	 * Salva os erros na session.
	 * 
	 * @param status <code>true</code> - verde, <code>false</code> - vermelho
	 */
	protected void saveMessagesOnSession(boolean status) {
		this.setStatus(status);
		if (this.getActionErrors() != null) {
			super.saveErrors(this.getSession(), this.getActionErrors());
		}
		this.setActionErrors(null);
	}

	/**
	 * Envia as mensagens ou erros para o Javascript, resultado de uma submissão assíncrona os erros
	 * ou mensagens podem ser adicionados pelos métodos BaseAction.saveErrors
	 * 
	 * @param errors
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXErrors() throws Exception {
		AjaxResponse response = new AjaxResponse();
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Envia uma url de redirecionamento para o Javascript equivalente ao nosso conhecido findForward
	 * só que ao invés de passar o forward, deve-se passar a url para qual o usuário será
	 * redirecionado.
	 * 
	 * @param forwardName nome do forward no struts-config
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXRedirect(String forwardName) throws Exception {
		AjaxResponse response = new AjaxResponse();
		response.setUrlForward(this.findForward(forwardName).getPath());
		this.printAndFlushResponse(response);
		return null;
	}

	/**
	 * Alias de BaseAction#sendAJAXErrors();
	 * 
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse() throws Exception {
		return this.sendAJAXErrors();
	}

	/**
	 * Envia uma resposta (mensagens ou erros), uma url de redirecionamento e seta o estado da
	 * requisição para definir as cores da resposta (se é vermelho ou verde) o padrão da da cor é
	 * vermelho os erros ou mensagens podem ser adicionados pelos métodos BaseAction.addMessage o
	 * forwardName passsado deverá ser o nome de um ActionForward devidamente configurado no
	 * struts-config que contenha uma url onde o javascript deverá redirecionar o cliente para ela
	 * 
	 * @param forward
	 * @param estado
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(ActionForward forward, boolean estado) throws Exception {
		AjaxResponse response = new AjaxResponse();

		//		if (!forward.getContextRelative()) {
		//			String appName = this.getRequest().getContextPath();
		//			response.setUrlForward(appName + forward.getPath());
		//		}

		response.setStatus(estado);
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Enviar resposta AJAX: recuperar o path do actionForward passado que deverá conter uma url,
	 * agregar os parâmetros passados a url e enviar a resposta para o JavaScript
	 * 
	 * @param forward
	 * @param estado
	 * @param parametros
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(ActionForward forward, boolean estado,
			ParametrosURL parametros) throws Exception {
		forward = this.findForwardConfigurable(forward.getName());
		forward.setPath(parametros.aggregateParams(forward.getPath()));
		return this.sendAJAXResponse(forward, estado);
	}

	/**
	 * Enviar resposta AJAX
	 * 
	 * @param ajaxResponse
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(AjaxResponse ajaxResponse) throws Exception {
		this.addMessagesToResponse(this.getActionErrors(), ajaxResponse);
		this.printAndFlushResponse(ajaxResponse);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * envia mensagens ou erros para o javascript que poderão aparecer em vermelho ou verde de acordo
	 * com parâmetro passado valor padrão: vermelho
	 * 
	 * @param estado true=verde, false=vermelho
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(boolean estado) throws Exception {
		AjaxResponse response = new AjaxResponse();
		response.setStatus(estado);
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Envia uma resposta (mensagens ou erros) e uma url de redirecionamento para o Javascript os
	 * erros ou mensagens podem ser adicionados pelos métodos BaseAction.addError
	 * 
	 * @param forwardName
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(String forwardName) throws Exception {
		AjaxResponse response = new AjaxResponse();
		this.findForward(forwardName);
		//		if (!forward.getContextRelative()) {
		//			String appName = this.getRequest().getContextPath();
		//			response.setUrlForward(appName + forward.getPath());
		//		}
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Enviar uma resposta para o JavaScript o forward passado deverá conter uma URL para onde o
	 * JavaScript deverá redirecionar o cliente
	 * 
	 * @param forwardName
	 * @param estado
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(String forwardName, boolean estado) throws Exception {
		return this.sendAJAXResponse(this.findForward(forwardName), estado);
	}

	/**
	 * Envia uma resposta AJAX.
	 * 
	 * @param forwardName
	 * @param estado
	 * @param parametros
	 * @return d
	 * @throws Exception
	 */
	protected ActionForward sendAJAXResponse(String forwardName, boolean estado,
			ParametrosURL parametros) throws Exception {
		return this.sendAJAXResponse(this.findForward(forwardName), estado, parametros);
	}

	/**
	 * Atribuí os erros no {@link ThreadLocal} de erros da action.
	 * 
	 * @param errors erros a salvar
	 */
	protected void setActionErrors(ActionErrors errors) {
		this.actionErrorsThread.set(errors);
		this.saveErrors(errors);
	}

	/**
	 * Seta um atributo no request.
	 * 
	 * @param nome nome do atributo
	 * @param valor valor do atributo
	 */
	protected void setAttribute(String nome, Object valor) {
		this.getRequest().setAttribute(nome, valor);
	}

	/**
	 * Seta um atributo na sessão.
	 * 
	 * @param nome nome do atributo
	 * @param valor valor do atributo
	 */
	protected void setSessionAttribute(String nome, Object valor) {
		this.getSession().setAttribute(nome, valor);
	}

	/**
	 * Adiciona várias mensagens a resposta para o Javascript.
	 * 
	 * @param errors {@link ActionErrors}
	 * @param ajaxResponseXML response AJAX
	 */
	private void addMessagesToResponse(ActionErrors errors, AjaxResponse ajaxResponseXML) {
		MessageResources resources = this.getResources(this.getRequest());
		String message;
		Iterator<ActionMessage> iter =
				GenericsUtil.checkedIterator(errors.get(), ActionMessage.class);
		while (iter.hasNext()) {
			ActionMessage actionMessage = iter.next();
			if ((actionMessage.getValues() != null) && (actionMessage.getValues().length != 0)) {
				message = resources.getMessage(actionMessage.getKey(), actionMessage.getValues());
			} else {
				message = resources.getMessage(actionMessage.getKey());
			}
			if (message != null) {
				ajaxResponseXML.addMessage(message);
			}
		}
	}

	/**
	 * Invoca o método de validação dentro de um validator.
	 * <p>
	 * A regra é a seguinte: Busca no validator.xml por uma classe de validação para a action
	 * corrente. E será invocado um método com o mesmo nome do método passado no atributo parameter
	 * do Action Mapping.
	 * </p>
	 * 
	 * @return o validate em si.
	 */
	private BaseValidator chamaMetodoValidacao() throws Exception {
		//se encontrar uma classe validate mapeada segue em frente, se não, retorna null
		BaseValidator validatorInstance = ValidatorReader.getValidator(this.getMapping().getType());

		if (validatorInstance == null) {
			return null;
		}

		validatorInstance.setResources(this.getResources(this.getRequest()));
		logger.debug("executando validator");
		validatorInstance.execute(this.getMapping(), this.getForm(), this.getRequest(), this
				.getResponse());

		return validatorInstance;
	}

	/**
	 * Imprime o XML de resposta para o Javascript.
	 * 
	 * @param response {@link AjaxResponse} da resposta
	 * @throws Exception
	 */
	private void printAndFlushResponse(AjaxResponse response) throws Exception {
		Utils.doNoCachePagina(this.getRequest(), this.getResponse());
		//isso daqui ta me dando problemas ... getResponse().reset();
		this.getResponse().addHeader("ajaxResponse", "true");
		this.getResponse().setHeader("Content-Type", "text/xml; charset=UTF-8");
		this.getResponse().setHeader("Content-Language", "pt-BR");

		PrintWriter writer = this.getResponse().getWriter();
		writer.print(response);
		writer.flush();
	}

	/**
	 * Efetua o processamento de erros. Decide se deve retornar como AJAXResponse ou Normal
	 * 
	 * @param mapping {@link ActionMapping}
	 * @param forwardValidacao {@link String} do forward de validação
	 * @param errors {@link ActionErrors}
	 * @param focusControl nome do campo onde ocorreu erro de validação
	 * @return
	 * @throws Exception
	 */
	private ActionForward processaErro(ActionMapping mapping, String forwardValidacao,
			ActionErrors errors, String focusControl) throws Exception {

		this.setActionErrors(errors);
		if (this.isAJAXRequest()) {
			logger.debug("processando mensagens de erro ajax");
			AjaxResponse ajaxResponse = new AjaxResponse();
			//recuperar o primeiro campo onde a validação falhou para mover o foco para ele 
			if (StringUtils.isNotBlank(focusControl)) {
				ajaxResponse.setFocusControl(focusControl);
			}
			ajaxResponse.setStatus(false);
			return this.sendAJAXResponse(ajaxResponse);
		}
		logger.debug("processando mensagens de erro");
		this.saveMessagesOnSession(false);

		//retorna o forward correto
		if (StringUtils.isNotBlank(forwardValidacao)) {
			return this.findForward(forwardValidacao);
		} else if (mapping.getInputForward().getName() != null) {
			//se não foi setado pega o input forward
			return mapping.getInputForward();
		}
		//se foi tem nenhum deles - erro padrão
		return this.findForward(FWD_ERRO_VALIDACAO);
	}

	/**
	 * Verifica se a requisição post possui um referer (página de origem da requisição) pra evitar
	 * requisições diretas (o que caracteriza burlagem do sistema).
	 * 
	 * @throws Exception
	 */
	private void segurancaPost() throws Exception {
		String referer = this.getRequest().getHeader("REFERER");
		String method = this.getRequest().getMethod();
		if ("POST".equalsIgnoreCase(method) && StringUtils.isBlank(referer)) {
			throw new Exception("Requisição inválida!!!");
		}
	}

	/**
	 * Adiciona o status da mensagem.
	 * 
	 * @param status <code>true</code> - verde, <code>false</code> - vermelho
	 */
	private void setStatus(boolean status) {
		this.setSessionAttribute("messageStatus", new Boolean(status));
	}
}
