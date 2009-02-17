/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterTarefaActionForm;
import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.impl.AtividadeBOImpl;
import br.com.ucb.sisgestor.negocio.impl.TarefaBOImpl;
import br.com.ucb.sisgestor.negocio.impl.UsuarioBOImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manutenções em {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterTarefaAction extends BaseAction {

	private static TarefaBO	tarefaBO;

	static {
		tarefaBO = TarefaBOImpl.getInstancia();
	}

	/**
	 * Atualiza uma tarefa.
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
		ManterTarefaActionForm form = (ManterTarefaActionForm) actionForm;

		Tarefa tarefa = new Tarefa();
		
		this.copyProperties(tarefa, form);

		tarefaBO.atualizar(tarefa);

		this.addMessageKey("mensagem.alterar", "Tarefa");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionForward entrada(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		AtividadeBO atividadeBO = AtividadeBOImpl.getInstancia();
		Atividade atividade = atividadeBO.obter(form.getAtividade());
		
		UsuarioBO usuarioBO = UsuarioBOImpl.getInstancia();
		
		form.setListaUsuarios(usuarioBO.getByDepartamento(atividade.getDepartamento()));

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui uma tarefa.
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
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;

		Tarefa tarefa = tarefaBO.obter(form.getId());

		tarefaBO.excluir(tarefa);

		this.addMessageKey("mensagem.excluir", "Tarefa");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir uma nova tarefa.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovaTarefa(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;
		
		AtividadeBO atividadeBO = AtividadeBOImpl.getInstancia();

		//Recupera a atividade para recuperar qual o departamento responsável.
		Atividade atividade = atividadeBO.obter(form.getAtividade());

		//Recupera os usuários inerentes ao departamento da atividade informada
		UsuarioBO usuarioBO = UsuarioBOImpl.getInstancia();
		form.setListaUsuarios(usuarioBO.getByDepartamento(atividade.getDepartamento()));


		return this.findForward("popupNovaTarefa");
	}

	/**
	 * Salva uma tarefa.
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

		ManterTarefaActionForm form = (ManterTarefaActionForm) formulario;
		Tarefa tarefa = new Tarefa();
		this.copyProperties(tarefa, form);

		tarefaBO.salvar(tarefa);

		this.addMessageKey("mensagem.salvar", "Tarefa");
		return this.sendAJAXResponse(true);
	}
}
