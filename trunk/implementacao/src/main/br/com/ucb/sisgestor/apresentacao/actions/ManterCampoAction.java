/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterCampoActionForm;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.OpcaoCampo;
import br.com.ucb.sisgestor.entidade.TipoCampoEnum;
import br.com.ucb.sisgestor.negocio.CampoBO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action para manutenções em {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class ManterCampoAction extends BaseAction {

	private CampoBO	campoBO;

	/**
	 * Atualiza um campo.
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
		ManterCampoActionForm form = (ManterCampoActionForm) actionForm;

		Campo campo = this.campoBO.obter(form.getId());
		this.copyProperties(campo, form);
		campo.setOpcoes(this.getOpcoes(campo, form.getOpcoes()));

		this.campoBO.atualizar(campo);

		this.addMessageKey("mensagem.alterar", "Campo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward entrada(ActionMapping mapping, ActionForm formulario, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterCampoActionForm form = (ManterCampoActionForm) formulario;

		form.setTipos(Arrays.asList(TipoCampoEnum.values()));

		return this.findForward(FWD_ENTRADA);
	}

	/**
	 * Exclui um campo.
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
		ManterCampoActionForm form = (ManterCampoActionForm) formulario;

		Campo campo = this.campoBO.obter(form.getId());

		this.campoBO.excluir(campo);

		this.addMessageKey("mensagem.excluir", "Campo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Abre o popup para incluir um novo campo.
	 * 
	 * @param mapping objeto mapping da action
	 * @param formulario objeto form da action
	 * @param request request atual
	 * @param response response atual
	 * @return forward do popup
	 * @throws Exception caso exceção seja lançada
	 */
	public ActionForward popupNovoCampo(ActionMapping mapping, ActionForm formulario,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ManterCampoActionForm form = (ManterCampoActionForm) formulario;

		form.setTipos(Arrays.asList(TipoCampoEnum.values()));

		return this.findForward("popupNovoCampo");
	}

	/**
	 * Salva um campo.
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
		ManterCampoActionForm form = (ManterCampoActionForm) formulario;

		Campo campo = new Campo();
		this.copyProperties(campo, form);
		campo.setOpcoes(this.getOpcoes(campo, form.getOpcoes()));

		this.campoBO.salvar(campo);

		this.addMessageKey("mensagem.salvar", "Campo");
		return this.sendAJAXResponse(true);
	}

	/**
	 * Atribui o BO de {@link Campo}.
	 * 
	 * @param campoBO BO de {@link Campo}
	 */
	@Autowired
	public void setCampoBO(CampoBO campoBO) {
		this.campoBO = campoBO;
	}

	/**
	 * Adiciona as opções restantes na lista das opções do campo.
	 * 
	 * @param opcoesAtuais lista de opções atuais
	 * @param opcoesForm novas opções do campo
	 */
	private void adicionarOpcoes(Campo campo, List<OpcaoCampo> opcoesAtuais, String[] opcoesForm) {
		OpcaoCampo opcao = null;
		for (int valorCampo = 0; valorCampo < opcoesForm.length; valorCampo++) {
			boolean isOpcaoNova = true;
			for (OpcaoCampo opcaoAtual : opcoesAtuais) {
				if (opcaoAtual.getDescricao().equalsIgnoreCase(opcoesForm[valorCampo])) { //opção já existe no BD
					isOpcaoNova = false;
					opcao = opcaoAtual;
					break;
				}
			}
			if (isOpcaoNova) {
				opcao = new OpcaoCampo();
				opcao.setDescricao(StringUtils.substring(opcoesForm[valorCampo], 0, 20));
				opcao.setCampo(campo);
				opcoesAtuais.add(opcao);
			}
			opcao.setValor(valorCampo);
		}
	}

	/**
	 * Recupera um {@link List} de {@link OpcaoCampo} a partir da descrição das opções.
	 * 
	 * @param campo {@link Campo} trabalhado
	 * @param opcoesForm descrições das opções
	 * @return {@link List} de {@link OpcaoCampo}
	 */
	private List<OpcaoCampo> getOpcoes(Campo campo, String[] opcoesForm) {
		List<OpcaoCampo> opcoes = campo.getOpcoes();
		TipoCampoEnum tipo = campo.getTipo();
		if ((opcoesForm != null)
				&& ((tipo == TipoCampoEnum.LISTA_DE_OPCOES) || (tipo == TipoCampoEnum.MULTIPLA_ESCOLHA))) {
			if (opcoes == null) { //inserção
				opcoes = new ArrayList<OpcaoCampo>();
			} else { //atualização
				this.removerOpcoesExcluidas(opcoes, opcoesForm);
			}
			this.adicionarOpcoes(campo, opcoes, opcoesForm);
		} else if (opcoes != null) { //inserindo ou atualizando para campo que não contém opções
			opcoes.clear();
		}
		return opcoes;
	}

	/**
	 * Remove as opções que foram excluídas do campo.
	 * 
	 * @param opcoesAtuais lista de opções atuais
	 * @param opcoesForm novas opções do campo
	 */
	private void removerOpcoesExcluidas(List<OpcaoCampo> opcoesAtuais, String[] opcoesForm) {
		List<OpcaoCampo> opcoesRemovidas = new ArrayList<OpcaoCampo>();
		for (OpcaoCampo opcaoAntiga : opcoesAtuais) {
			boolean achou = false;
			for (String opcaoNova : opcoesForm) {
				if (opcaoAntiga.getDescricao().equalsIgnoreCase(opcaoNova)) {
					achou = true;
					break;
				}
			}
			if (!achou) {
				opcoesRemovidas.add(opcaoAntiga);
			}
		}
		opcoesAtuais.removeAll(opcoesRemovidas);
	}
}
