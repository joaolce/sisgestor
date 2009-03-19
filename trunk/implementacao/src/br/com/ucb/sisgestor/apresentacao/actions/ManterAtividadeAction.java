/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterAtividadeActionForm;
import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções em {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class ManterAtividadeAction extends BaseAction {

	private AtividadeBO		atividadeBO;
	private DepartamentoBO	departamentoBO;

	/**
	 * Atualiza uma atividade.
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
		ManterAtividadeActionForm form = (ManterAtividadeActionForm) actionForm;

		Atividade atividade = this.atividadeBO.obter(form.getId());
		this.copyProperties(atividade, form);

		this.atividadeBO.atualizar(atividade);

		this.addMessageKey("mensagem.alterar.a", "Atividade");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterAtividadeActionForm form = (ManterAtividadeActionForm) formulario;

		form.setListaDepartamentos(this.departamentoBO.obterTodos());

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui uma atividade.
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
		ManterAtividadeActionForm form = (ManterAtividadeActionForm) formulario;

		Atividade atividade = this.atividadeBO.obter(form.getId());

		this.atividadeBO.excluir(atividade);

		this.addMessageKey("mensagem.excluir.a", "Atividade");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir uma nova atividade.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovaAtividade(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ManterAtividadeActionForm form = (ManterAtividadeActionForm) formulario;

		//Recupera os departamentos disponíveis para uma nova atividade
		form.setListaDepartamentos(this.departamentoBO.obterTodos());

		return this.findForward("popupNovaAtividade");
	}

	/**
	 * Salva (inclui) uma atividade.
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
		ManterAtividadeActionForm form = (ManterAtividadeActionForm) formulario;

		Atividade atividade = new Atividade();
		this.copyProperties(atividade, form);

		this.atividadeBO.salvar(atividade);

		this.addMessageKey("mensagem.salvar.a", "Atividade");
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
	 * Atribui o BO de {@link Departamento}.
	 * 
	 * @param departamentoBO BO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoBO(DepartamentoBO departamentoBO) {
		this.departamentoBO = departamentoBO;
	}
}
