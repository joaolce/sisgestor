/*
 * Projeto: SisGestor
 * Cria��o: 27/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.dwr.utils.AjaxResponse;
import br.com.ucb.sisgestor.apresentacao.validator.BaseValidator;
import br.com.ucb.sisgestor.apresentacao.validator.utils.RoleValidatorReader;
import br.com.ucb.sisgestor.apresentacao.validator.utils.ValidatorReader;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.GenericsUtil;
import br.com.ucb.sisgestor.util.ParametrosURL;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.ConstantesAplicacao;
import br.com.ucb.sisgestor.util.constantes.ConstantesContexto;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import javax.security.auth.login.LoginException;
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
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * Action base para o projeto.
 * 
 * @author Jo�o L�cio
 * @since 27/10/2008
 */
public class BaseAction extends DispatchActionSupport {

	/** forward de erro gen�rico */
	protected static final String					FWD_ERRO					= "erro";
	/** forward de entrada, todas as actions devem ter. */
	protected static final String					FWD_ENTRADA				= "entrada";
	/** forward de erro de valida��o. */
	protected static final String					FWD_ERRO_VALIDACAO	= "erroValidacao";
	/** forward de acesso negado. */
	protected static final String					FWD_NEGADO				= "acessoNegado";
	private static final Log						LOG						= LogFactory.getLog(BaseAction.class);

	/** BO de {@link Usuario}. */
	protected UsuarioBO								usuarioBO;
	private ThreadLocal<ActionErrors>			actionErrorsThread	= new ThreadLocal<ActionErrors>();
	private ThreadLocal<ActionForm>				formThread				= new ThreadLocal<ActionForm>();
	private ThreadLocal<ActionMapping>			mappingThread			= new ThreadLocal<ActionMapping>();
	private ThreadLocal<HttpServletRequest>	requestThread			= new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse>	responseThread			= new ThreadLocal<HttpServletResponse>();
	private ThreadLocal<HttpSession>				sessionThread			= new ThreadLocal<HttpSession>();

	/**
	 * M�todo padr�o para exibir a tela de entrada caso seja necess�rio carregar algum dado para exibir na
	 * tela.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward} da entrada
	 * @throws Exception caso ocorra erro na opera��o
	 */
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Sobrescrita do m�todo execute. A id�ia � fazer alguns procedimentos gen�ricos aqui, para que todas as
	 * actions possam utilizar alguns m�todos utilit�rios que existem aqui.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return {@link ActionForward}
	 * @throws Exception caso ocorra erro na opera��o
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, //NOPMD by Jo�o L�cio - n�o d� para quebrar
			HttpServletResponse response) throws Exception {

		//verificar se a requisi��o post possui um referer, quest�es de seguran�a 
		this.segurancaPost(request);
		//popula as variaveis de inst�ncia
		this.populaParametrosAction(mapping, actionForm, request, response);
		ConstantesAplicacao.setConstantes(request);

		/* se o par�metro estiver presente � porque a submiss�o foi ass�ncrona e para n�o acontecer problemas
		 * com a codifica��o do charset (problemas com acentos e caracteres especiais) utiliza-se o 
		 * Utils.decodeAjaxForm para decodificar o formul�rio que foi codificado no Javascript
		 */
		if (this.isAJAXRequest()) {
			LOG.debug("requisi��o ajax em processo");
			if ("get".equalsIgnoreCase(request.getMethod())) {
				Utils.decodeAjaxForm(actionForm);
			}
			Utils.doNoCachePagina(request, response);
			/*
			 * esse header � adicionado para o javascript identificar que 
			 * a resposta veio da aplica��o e n�o de uma p�gina de login ou algo do tipo
			 */
			response.addHeader("ajaxResponse", "true");
		}
		//pega o nome do m�todo a ser chamado
		String nomeMetodo = request.getParameter(mapping.getParameter());
		if (nomeMetodo == null) {
			//se o par�metro estiver nulo, a dispatch action vai lancar o erro
			return super.execute(mapping, actionForm, request, response);
		}
		try {
			this.doUsuario(false);
		} catch (NegocioException e) {
			return new ActionForward("dologin", true);
		} catch (InvocationTargetException e) {
			LOG.fatal(e.getMessage(), e);
			this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"" + e.getTargetException());
			return null;
		} catch (Exception e) {
			LOG.fatal(e.getMessage(), e);
			this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

		//VALIDA��O DE ROLES
		RoleValidatorReader rvr = new RoleValidatorReader();
		if (!rvr.isUserInAnyRoles(this.getUser(), this.getMapping().getType(), nomeMetodo)) {
			if (this.isAJAXRequest()) {
				this.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			} else {
				this.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			return this.findForward(FWD_NEGADO);
		}
		//VALIDA��O - executa a valida��o antes de executar o m�todo de dispatch
		BaseValidator validator = this.chamaMetodoValidacao();
		if (validator != null) {
			ActionErrors errors = validator.getActionErrors();
			String focusControl = validator.getFocusControl();
			if ((errors != null) && (errors.size() != 0)) {
				//verifica a origem da requisi��o - s�ncrona ou ass�ncrona
				return this.processaErro(mapping, validator.getForwardErroValidacao(), errors, focusControl);
			}
		}
		//FIM VALIDA��O

		try {
			if (actionForm != null) {
				Utils.doNuloNaStringVazia(actionForm);
			}
			return super.execute(mapping, actionForm, request, response);
		} catch (NegocioException e) {
			if (e.getCause() == null) {
				this.addMessageKey(e.getMessage(), e.getArgs());
			} else {
				this.addMessage(e.getMessage());
			}

			if (this.isAJAXRequest()) {
				AjaxResponse ajaxResponse = new AjaxResponse();
				ajaxResponse.setStatus(false);

				Map<String, Object> valoresDevolvidos = e.getValoresDevolvidos();
				if ((valoresDevolvidos != null) && !valoresDevolvidos.isEmpty()) {
					for (String chave : valoresDevolvidos.keySet()) {
						ajaxResponse.putValorRetorno(chave, valoresDevolvidos.get(chave));
					}
				}
				return this.sendAJAXResponse(ajaxResponse);
			}
			this.saveMessages(false);
			return mapping.findForward(validator.getForwardErroValidacao());
		} catch (Exception e) {
			LOG.fatal(e.getMessage(), e);
			if (this.isAJAXRequest()) {
				this.addMessageKey("erro.falhaMensagemAjax", e.getMessage());
				return this.sendAJAXResponse(false);
			}
			throw e;
		}
	}

	/**
	 * Atribui o BO de {@link Usuario}.
	 * 
	 * @param usuarioBO BO de {@link Usuario}
	 */
	@Autowired
	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
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
	 * Adiciona uma mensagem com v�rios replacements.
	 * 
	 * @param keyProperty chave da mensagem
	 * @param args argumentos da mensagem
	 */
	protected void addMessageKey(String keyProperty, String... args) {
		this.getActionErrors().add(keyProperty, new ActionMessage(keyProperty, args));
	}

	/**
	 * Adiciona os par�metros criados ao path (que aponte para uma url e com redirect=true) de um ActionForward
	 * 
	 * @param parametros parametros adicionados
	 * @param forward forward a alterar o path
	 */
	protected void addParametrosToForwardPath(ParametrosURL parametros, ActionForward forward) {
		forward.setPath(parametros.aggregateParams(forward.getPath()));
	}

	/**
	 * Copia propriedades de um form para uma entidade ou vice versa.
	 * 
	 * @see Utils#copyProperties(Object, Object, String...)
	 * 
	 * @param dest objeto de destino
	 * @param orig objeto de origem
	 * @param exclusao propriedade a ignorar na c�pia
	 * @throws Exception caso ocorra erro na recupera��o das propriedades
	 */
	protected void copyProperties(Object dest, Object orig, String... exclusao) throws Exception {
		Utils.copyProperties(dest, orig, exclusao);
	}

	/**
	 * Coloca o usu�rio na sess�o.
	 * 
	 * @param ignoraSessao ignora se j� tiver o usu�rio na sess�o
	 * @throws Exception caso ocorra erro na opera��o
	 */
	protected final void doUsuario(boolean ignoraSessao) throws Exception {
		if ((this.getRequest().getUserPrincipal() == null)
				|| (this.getRequest().getUserPrincipal().getName() == null)) {
			throw new LoginException("userPrincipal est� nulo!");
		}
		Usuario usuarioAtual = (Usuario) this.getSession().getAttribute(ConstantesContexto.USUARIO_SESSAO);
		String name = this.getRequest().getUserPrincipal().getName();

		if ((usuarioAtual == null) || !name.equalsIgnoreCase(usuarioAtual.getLogin().trim()) || ignoraSessao) {
			LOG.debug("completando o processo de login");

			usuarioAtual = this.usuarioBO.getByLogin(name);
			Hibernate.initialize(usuarioAtual.getPermissoes());

			this.getSession().setAttribute(ConstantesContexto.DATA_LOGIN, DataUtil.getStringDataAtualCompleta());
			this.getSession().setAttribute(ConstantesContexto.HORA_LOGIN, DataUtil.getDataAtual());
			this.getSession().setAttribute(ConstantesContexto.USUARIO_SESSAO, usuarioAtual);
			Utils.setUsuario(usuarioAtual);
		}
	}

	/**
	 * Busca um {@link ActionForward} e retorna.
	 * 
	 * @param forward {@link ActionForward} encontrado
	 * @return {@link ActionForward} encontrado
	 */
	protected ActionForward findForward(String forward) {
		return this.getMapping().findForward(forward);
	}

	/**
	 * Busca um {@link ActionForward} onde as propriedades podem ser alteradas.
	 * 
	 * @param forward {@link ActionForward} encontrado
	 * @return {@link ActionForward} encontrado
	 */
	protected ActionForward findForwardConfigurable(String forward) {
		ActionForward fwd = new ActionForward();
		try {
			PropertyUtils.copyProperties(fwd, this.findForward(forward));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
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
		if (this.actionErrorsThread.get() == null) {
			errors = new ActionErrors();
			this.actionErrorsThread.set(errors);
		} else {
			errors = this.actionErrorsThread.get();
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
	 * Busca o forward que est� sendo utilizado no momento.
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
	 * @return mensagem gerada
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
	 * @return sess�o atual
	 */
	protected HttpSession getSession() {
		return this.sessionThread.get();
	}

	/**
	 * Retorna um atributo da sess�o
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
	 * @throws MalformedURLException erro na forma��o da url
	 */
	protected URL getUrlServidor() throws MalformedURLException {
		return RequestUtils.serverURL(this.getRequest());
	}

	/**
	 * Recupera o usu�rio logado no sistema.
	 * 
	 * @return o usu�rio logado no sistema
	 * @throws Exception caso ocorra erro na opera��o
	 */
	protected Usuario getUser() throws Exception {
		this.doUsuario(false);
		return Utils.getUsuario();
	}

	/**
	 * Invoca um m�todo qualquer do form. Util quando n�o se sabe a inst�ncia do form que se est� usando. Deve
	 * ser usado com cautela.
	 * 
	 * @param methodName nome do m�todo a ser executado
	 * @param paramTypes tipo dos par�metros
	 * @param params par�metros do m�todo
	 * @return valor de retorno da invoca��o do m�todo
	 */
	@SuppressWarnings("PMD")
	protected Object invokeFormMethod(String methodName, Class<?>[] paramTypes, Object[] params) {
		Object object = null;
		try {
			Class<?> classeForm = this.getForm().getClass();
			Method method = classeForm.getMethod(methodName, paramTypes);

			object = method.invoke(this.getForm(), params);
		} catch (IllegalAccessException e) {
			LOG.error(e);
		} catch (SecurityException e) {
			LOG.error(e);
		} catch (NoSuchMethodException e) {
			LOG.error(e);
		} catch (IllegalArgumentException e) {
			LOG.error(e);
		} catch (InvocationTargetException e) {
			LOG.error(e);
		}
		return object;
	}

	/**
	 * Informa se a requisi��o � via AJAX.
	 * 
	 * @return <code>true</code> caso seja, <code>false</code> caso n�o seja
	 */
	protected boolean isAJAXRequest() {
		return this.getRequest().getHeader("X-Requested-With") != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInit() {
		super.onInit();
		Utils.injectionAutowired(this);
	}

	/**
	 * Popula as vari�veis de inst�ncia com os parametros recebidos pela Action.
	 * 
	 * Isso est� separada para que possa ser usado caso algum m�todo execute seja sobrescrito em uma classe
	 * filha.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
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
	 * Remove o atributo passado da sess�o.
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
	 * Salva as mensagens da requisi��o.
	 * 
	 * @param status <code>true</code> - verde, <code>false</code> - vermelho
	 */
	protected void saveMessages(boolean status) {
		this.setStatus(status);
		if (this.getActionErrors() != null) {
			super.saveErrors(this.getSession(), this.getActionErrors());
		}
		this.setActionErrors(null);
	}

	/**
	 * Envia as mensagens ou erros para o Javascript, resultado de uma submiss�o ass�ncrona. <br />
	 * obs: os erros ou mensagens podem ser adicionados pelos m�todos BaseAction.saveErrors
	 * 
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXErrors() throws Exception {
		AjaxResponse response = new AjaxResponse();
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Envia uma url de redirecionamento para o Javascript equivalente ao nosso conhecido findForward s� que ao
	 * inv�s de passar o forward, deve-se passar a url para qual o usu�rio ser� redirecionado.
	 * 
	 * @param forwardName nome do forward no struts-config
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXRedirect(String forwardName) throws Exception {
		AjaxResponse response = new AjaxResponse();
		response.setUrlForward(this.findForward(forwardName).getPath());
		this.printAndFlushResponse(response);
		return null;
	}

	/**
	 * Envia uma resposta (mensagens ou erros), uma url de redirecionamento e seta o estado da requisi��o para
	 * definir as cores da resposta. o ActionForward passsado dever� ser devidamente configurado no
	 * struts-config que contenha uma url onde o javascript dever� redirecionar o cliente para ela
	 * 
	 * @param forward {@link ActionForward} a executar
	 * @param estado true=verde, false=vermelho
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXResponse(ActionForward forward, boolean estado) throws Exception {
		AjaxResponse response = new AjaxResponse();
		//		if(!forward.getContextRelative()){
		//			String appName = getRequest().getContextPath();
		//			response.setUrlForward(appName+forward.getPath());	
		//		}

		response.setStatus(estado);
		this.addMessagesToResponse(this.getActionErrors(), response);
		this.printAndFlushResponse(response);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Enviar resposta AJAX: recuperar o path do actionForward passado que dever� conter uma url, agregar os
	 * par�metros passados a url e enviar a resposta para o JavaScript
	 * 
	 * @param forward forward original
	 * @param estado true=verde, false=vermelho
	 * @param parametros par�metros a adicionar
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXResponse(ActionForward forward, boolean estado, ParametrosURL parametros)
			throws Exception {
		ActionForward forwardConfigurable = this.findForwardConfigurable(forward.getName());
		forwardConfigurable.setPath(parametros.aggregateParams(forwardConfigurable.getPath()));
		return this.sendAJAXResponse(forwardConfigurable, estado);
	}

	/**
	 * Enviar resposta AJAX.
	 * 
	 * @param ajaxResponse {@link AjaxResponse} da resposta
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXResponse(AjaxResponse ajaxResponse) throws Exception {
		this.addMessagesToResponse(this.getActionErrors(), ajaxResponse);
		this.printAndFlushResponse(ajaxResponse);
		this.setActionErrors(null);
		return null;
	}

	/**
	 * Envia mensagens ou erros para o javascript que poder�o aparecer em vermelho ou verde de acordo com
	 * par�metro passado valor padr�o: vermelho
	 * 
	 * @param estado true=verde, false=vermelho
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
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
	 * Enviar uma resposta para o JavaScript o forward passado dever� conter uma URL para onde o JavaScript
	 * dever� redirecionar o cliente
	 * 
	 * @param forwardName nome do forward
	 * @param estado true=verde, false=vermelho
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXResponse(String forwardName, boolean estado) throws Exception {
		return this.sendAJAXResponse(this.findForward(forwardName), estado);
	}

	/**
	 * Envia uma resposta AJAX.
	 * 
	 * @param forwardName nome do forward
	 * @param estado true=verde, false=vermelho
	 * @param parametros par�metros a adicionar
	 * @return <code>null</code>
	 * @throws Exception caso ocorra erro no envio da resposta
	 */
	protected ActionForward sendAJAXResponse(String forwardName, boolean estado, ParametrosURL parametros)
			throws Exception {
		return this.sendAJAXResponse(this.findForward(forwardName), estado, parametros);
	}

	/**
	 * Atribu� os erros no {@link ThreadLocal} de erros da action.
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
	 * Seta um atributo na sess�o.
	 * 
	 * @param nome nome do atributo
	 * @param valor valor do atributo
	 */
	protected void setSessionAttribute(String nome, Object valor) {
		this.getSession().setAttribute(nome, valor);
	}

	/**
	 * Adiciona v�rias mensagens a resposta para o Javascript.
	 * 
	 * @param errors {@link ActionErrors} a adicionar
	 * @param ajaxResponseXML response AJAX
	 */
	private void addMessagesToResponse(ActionErrors errors, AjaxResponse ajaxResponseXML) {
		MessageResources resources = this.getResources(this.getRequest());
		String message;
		Iterator<ActionMessage> iter = GenericsUtil.checkedIterator(errors.get(), ActionMessage.class);
		while (iter.hasNext()) {
			ActionMessage actionMessage = iter.next();
			if ((actionMessage.getValues() == null) || (actionMessage.getValues().length == 0)) {
				message = resources.getMessage(actionMessage.getKey());
			} else {
				message = resources.getMessage(actionMessage.getKey(), actionMessage.getValues());
			}
			if (message != null) {
				ajaxResponseXML.addMessage(message);
			}
		}
	}

	/**
	 * Invoca o m�todo de valida��o dentro de um validator.
	 * <p>
	 * A regra � a seguinte: Busca no validator.xml por uma classe de valida��o para a action corrente. E ser�
	 * invocado um m�todo com o mesmo nome do m�todo passado no atributo parameter do Action Mapping.
	 * </p>
	 * 
	 * @return o validate em si.
	 */
	private BaseValidator chamaMetodoValidacao() throws Exception {
		//se encontrar uma classe validate mapeada segue em frente, se n�o, retorna null
		BaseValidator validator = ValidatorReader.getValidator(this.getMapping().getType());

		if (validator == null) {
			return null;
		}

		validator.setResources(this.getResources(this.getRequest()));
		LOG.debug("executando validator");
		validator.execute(this.getMapping(), this.getForm(), this.getRequest(), this.getResponse());

		return validator;
	}

	/**
	 * Imprime o XML de resposta para o Javascript.
	 * 
	 * @param response {@link AjaxResponse} da resposta
	 * @throws Exception
	 */
	private void printAndFlushResponse(AjaxResponse response) throws Exception {
		Utils.doNoCachePagina(this.getRequest(), this.getResponse());
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
	 * @param forwardValidacao {@link String} do forward de valida��o
	 * @param errors {@link ActionErrors}
	 * @param focusControl nome do campo onde ocorreu erro de valida��o
	 * @return forward do erro
	 * @throws Exception
	 */
	private ActionForward processaErro(ActionMapping mapping, String forwardValidacao, ActionErrors errors,
			String focusControl) throws Exception {

		this.setActionErrors(errors);
		if (this.isAJAXRequest()) {
			LOG.debug("processando mensagens de erro ajax");
			AjaxResponse ajaxResponse = new AjaxResponse();
			//recuperar o primeiro campo onde a valida��o falhou para mover o foco para ele 
			if (StringUtils.isNotBlank(focusControl)) {
				ajaxResponse.setFocusControl(focusControl);
			}
			ajaxResponse.setStatus(false);
			return this.sendAJAXResponse(ajaxResponse);
		}
		LOG.debug("processando mensagens de erro");
		this.saveMessages(false);

		//retorna o forward correto
		if (StringUtils.isNotBlank(forwardValidacao)) {
			return this.findForward(forwardValidacao);
		} else if (mapping.getInputForward().getName() != null) {
			//se n�o foi setado pega o input forward
			return mapping.getInputForward();
		}
		//se foi tem nenhum deles - erro padr�o
		return this.findForward(FWD_ERRO_VALIDACAO);
	}

	/**
	 * Verifica se a requisi��o post possui um referer (p�gina de origem da requisi��o) pra evitar requisi��es
	 * diretas (o que caracteriza burlagem do sistema).
	 * 
	 * @param request request atual
	 * @throws Exception caso seja detectada burlagem no sistema
	 */
	private void segurancaPost(HttpServletRequest request) throws Exception {
		String referer = request.getHeader("REFERER");
		String method = request.getMethod();
		if ("POST".equalsIgnoreCase(method) && StringUtils.isBlank(referer)) {
			throw new SecurityException("Requisi��o inv�lida!!!");
		}
	}

	/**
	 * Adiciona o status da mensagem.
	 * 
	 * @param status <code>true</code> - verde, <code>false</code> - vermelho
	 */
	private void setStatus(boolean status) {
		this.setSessionAttribute("messageStatus", Boolean.valueOf(status));
	}
}
