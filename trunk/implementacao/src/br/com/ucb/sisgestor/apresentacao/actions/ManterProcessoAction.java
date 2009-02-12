/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterProcessoActionForm;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.impl.ProcessoBOImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manutenções em {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoAction extends BaseAction {

	private static ProcessoBO	processoBO;

	static {
		processoBO = ProcessoBOImpl.getInstancia();
	}

	/**
	 * Atualiza um processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) actionForm;
		//TODO Verificar criação do processo...
		Processo processo = new Processo();
		this.copyProperties(processo, form);

		processoBO.atualizar(processo);

		this.addMessageKey("mensagem.alterar", "Processo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		Integer idWorkflow = form.getWorkflow();

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui um processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da exclusão
	 * @throws Exception caso ocorra erro na operação
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		//TODO Verificar criação do processo...
		Processo processo = new Processo();

		processoBO.excluir(processo);

		this.addMessageKey("mensagem.excluir", "Processo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir um novo processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovoProcesso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward("popupNovoProcesso");
	}

	/**
	 * Salva um processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;
		Processo processo = new Processo();
		this.copyProperties(processo, form);

		processoBO.salvar(processo);

		this.addMessageKey("mensagem.salvar", "Processo");
		return this.sendAJAXResponse(true);
	}
}
