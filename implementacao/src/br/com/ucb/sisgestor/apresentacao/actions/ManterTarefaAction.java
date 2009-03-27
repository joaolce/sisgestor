/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterTarefaActionForm;
import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manuten��es em {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaAction extends BaseAction {

	private TarefaBO		tarefaBO;
	private AtividadeBO	atividadeBO;

	/**
	 * Atualiza uma tarefa.
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
		ManterTarefaActionForm form = (ManterTarefaActionForm) actionForm;

		Tarefa tarefa = this.tarefaBO.obter(form.getId());
		this.copyProperties(tarefa, form, "atividade");

		this.tarefaBO.atualizar(tarefa);

		this.addMessageKey("mensagem.alterar.a", "Tarefa");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		Atividade atividade = this.atividadeBO.obter(form.getAtividade());

		form.setListaUsuarios(this.usuarioBO.getByDepartamento(atividade.getDepartamento()));

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui uma tarefa.
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
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		Tarefa tarefa = this.tarefaBO.obter(form.getId());

		this.tarefaBO.excluir(tarefa);

		this.addMessageKey("mensagem.excluir.a", "Tarefa");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre popup para definir fluxo das tarefas.
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
		return this.findForward("popupDefinirFluxoTarefas");
	}

	/**
	 * Abre o popup para incluir uma nova tarefa.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exce��o seja lan�ada
	 */
	public ActionForward popupNovaTarefa(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		//Recupera a atividade para recuperar qual o departamento respons�vel.
		Atividade atividade = this.atividadeBO.obter(form.getAtividade());

		//Recupera os usu�rios inerentes ao departamento da atividade informada
		form.setListaUsuarios(this.usuarioBO.getByDepartamento(atividade.getDepartamento()));


		return this.findForward("popupNovaTarefa");
	}

	/**
	 * Salva uma tarefa.
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

		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;
		Tarefa tarefa = new Tarefa();
		this.copyProperties(tarefa, form);

		this.tarefaBO.salvar(tarefa);

		this.addMessageKey("mensagem.salvar.a", "Tarefa");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Salva os fluxos das tarefas.
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

		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		String[] fluxos = form.getFluxos();
		String[] posicoes = form.getPosicoes();

		this.tarefaBO.atualizarTransacoes(form.getAtividade(), fluxos, posicoes);

		this.addMessageKey("mensagem.fluxo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Atribui o BO de {@link Atividade}.
	 * 
	 * @param atividadeBO BO de {@link Atividade}
	 */
	@Autowired
	public void setAtividadeBO(AtividadeBO atividadeBO) {
		this.atividadeBO = atividadeBO;
	}

	/**
	 * Atribui o BO de {@link Tarefa}.
	 * 
	 * @param tarefaBO BO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaBO(TarefaBO tarefaBO) {
		this.tarefaBO = tarefaBO;
	}
}
