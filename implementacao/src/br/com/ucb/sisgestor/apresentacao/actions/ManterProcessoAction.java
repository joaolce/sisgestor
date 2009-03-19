/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterProcessoActionForm;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções em {@link Processo}.
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
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
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
	 * @return forward da exclusão
	 * @throws Exception caso ocorra erro na operação
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
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
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
	 * @return forward da inclusão
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward salvarFluxo(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ManterProcessoActionForm form = (ManterProcessoActionForm) formulario;

		List<TransacaoProcesso> lista = this.getTransacoes(form.getFluxos());

		this.processoBO.atualizarTransacoes(form.getWorkflow(), lista);

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

	/**
	 * Recupera a lista de transações criadas para os processos.
	 * 
	 * @param fluxos fluxos definidos pelo usuário
	 * @return {@link List} de {@link TransacaoProcesso}
	 */
	private List<TransacaoProcesso> getTransacoes(String[] fluxos) {
		List<TransacaoProcesso> lista = new ArrayList<TransacaoProcesso>();
		if (fluxos != null) {
			TransacaoProcesso transacao;
			Processo processoAnterior;
			Processo processoPosterior;
			for (String fluxo : fluxos) {
				String[] processos = fluxo.split(","); //a direção vem no formato: 1,2 (origem, destino)

				transacao = new TransacaoProcesso();
				processoAnterior = new Processo();
				processoPosterior = new Processo();

				processoAnterior.setId(Integer.parseInt(processos[0]));
				processoPosterior.setId(Integer.parseInt(processos[1]));

				transacao.setAnterior(processoAnterior);
				transacao.setPosterior(processoPosterior);
				lista.add(transacao);
			}
		}
		return lista;
	}
}
