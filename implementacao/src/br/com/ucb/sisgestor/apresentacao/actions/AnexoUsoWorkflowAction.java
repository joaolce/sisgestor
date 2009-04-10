/*
 * Projeto: sisgestor
 * Criação: 09/04/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.AnexoUsoWorkflowActionForm;
import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.negocio.AnexoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções de {@link Anexo}.
 * 
 * @author João Lúcio
 * @since 09/04/2009
 */
public class AnexoUsoWorkflowAction extends BaseAction {

	private AnexoBO	anexoBO;

	/**
	 * Faz o download do anexo solicitado.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward download(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) formulario;
		Anexo anexo = this.anexoBO.obter(form.getId());
		Utils.download(anexo, response);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) actionForm;

		form.setAnexos(this.anexoBO.getAnexosByUsoWorkflow(form.getUsoWorkflow()));

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui o(s) anexo(s) selecionado(s).
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward excluirAnexo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) formulario;

		Integer[] anexosSelecionados = form.getAnexosSelecionados();
		this.anexoBO.excluirAnexos(anexosSelecionados);

		this.addMessageKey("mensagem.excluir", "Anexo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre a tela que é carregada por dentro de um iframe por causa do upload de anexo que não pode recarregar
	 * a página toda.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward iframeAnexo(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.findForward("iframeAnexos");
	}

	/**
	 * Inclui o anexo ao uso do workflow.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward incluirAnexo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) formulario;
		FormFile arquivo = form.getArquivo();

		Anexo anexo = this.criarAnexo(arquivo);
		this.copyProperties(anexo, form, "id"); //feito para copiar o id do usoWorkflow
		this.anexoBO.salvar(anexo);

		this.addMessageKey("mensagem.uploadConcluido");
		this.saveMessages(true);
		return this.findForward("resultadoAnexo");
	}

	/**
	 * Exibe a tela de inserção de um novo anexo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward da atualização
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupInserirAnexo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnexoUsoWorkflowActionForm form = (AnexoUsoWorkflowActionForm) formulario;

		/* Estou gravando o id do uso na sessão, pois no controller do struts (struts-config controller)
		 * existe uma configuração que não permite que seja efetuado um upload de arquivos de tamanho superior
		 * a 10MB, porém quando o usuário envia um arquivo superior a esse tamanho o struts zera o form e ele não vem 
		 * com propriedade nenhuma, o que atrapalha na hora de identificar o id do uso.
		 * Esse id da sessão é recuperado na classe AnexoUsoWorkflowValidator e recolocado na propriedade do form */
		Integer idUsoWorkflow = form.getId();
		this.setSessionAttribute("idUsoWorkflowAnexo", idUsoWorkflow);

		return this.findForward("popupInserirAnexo");
	}

	/**
	 * Atribui o BO de {@link Anexo}.
	 * 
	 * @param anexoBO BO de {@link Anexo}
	 */
	@Autowired
	public void setAnexoBO(AnexoBO anexoBO) {
		this.anexoBO = anexoBO;
	}

	/**
	 * Cria um {@link Anexo} a partir do {@link FormFile} enviado pelo struts.
	 * 
	 * @param arquivo arquivo enviado pelo struts
	 * @return {@link Anexo} correspondente
	 * @throws NegocioException caso ocorra
	 */
	private Anexo criarAnexo(FormFile arquivo) throws NegocioException {
		Anexo anexo = new Anexo();

		anexo.setNome(arquivo.getFileName());
		anexo.setContentType(arquivo.getContentType());
		anexo.setDataCriacao(DataUtil.getDataHoraAtual());
		try {
			anexo.setDados(arquivo.getFileData());
		} catch (Exception e) {
			throw new NegocioException("erro.arquivo.naoEncontrado");
		}
		return anexo;
	}
}
