/*
 * Projeto: sisgestor
 * Cria��o: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterProcessoActionForm;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manuten��es em {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ManterProcessoAction extends BaseAction {

	private ProcessoBO	processoBO;

	/**
	 * Atualiza um processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param actionForm objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualiza��o
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) actionForm;

		Processo processo = this.processoBO.obter(form.getId());
		this.copyProperties(processo, form);

		this.processoBO.atualizar(processo);

		this.addMessageKey("mensagem.alterar", "Processo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Exclui um processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da exclus�o
	 * @throws Exception caso ocorra erro na opera��o
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		Processo processo = this.processoBO.obter(form.getId());

		this.processoBO.excluir(processo);

		this.addMessageKey("mensagem.excluir", "Processo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre popup para definir fluxo dos processos.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclus�o
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupDefinirFluxo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.findForward("popupDefinirFluxoProcessos");
	}

	/**
	 * Abre o popup para incluir um novo processo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param form objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exce��o seja lan�ada
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
	 * @return forward da inclus�o
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		Processo processo = new Processo();
		this.copyProperties(processo, form);

		this.processoBO.salvar(processo);

		this.addMessageKey("mensagem.salvar", "Processo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Salva os fluxos dos processos.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da inclus�o
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward salvarFluxo(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		String[] fluxos = form.getFluxos();
		String[] posicoes = form.getPosicoes();

		this.processoBO.atualizarTransacoes(form.getWorkflow(), fluxos, posicoes);

		this.addMessageKey("mensagem.fluxo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Atribui o BO de {@link Processo}.
	 * 
	 * @param processoBO BO de {@link Processo}
	 */
	@Autowired
	public void setProcessoBO(ProcessoBO processoBO) {
		this.processoBO = processoBO;
	}
}
