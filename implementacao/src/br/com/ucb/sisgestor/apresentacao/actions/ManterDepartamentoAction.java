/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.actions;

import br.com.ucb.sisgestor.apresentacao.forms.ManterDepartamentoActionForm;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para manutenções em {@link Departamento}.
 * 
 * @author Thiago 
 * @since 10/01/2009
 */
public class ManterDepartamentoAction extends BaseAction {

	/**
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward salvar(ActionMapping mapping, ActionForm formulario, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		
		Departamento departamento = new Departamento();
		
		Utils.copyProperties(departamento, form);
		
		DepartamentoBO bo = DepartamentoBOImpl.getInstancia();
		
		bo.salvar(departamento);
		
		addMessageKey("mensagem.departamento.salvar");
		
		return sendAJAXResponse(true);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward atualizar(ActionMapping mapping, ActionForm formulario, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		
		Departamento departamento = new Departamento();
		
		Utils.copyProperties(departamento, form);
		
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		
		departamentoBO.atualizar(departamento);

		addMessageKey("mensagem.departamento.alterar");
		
		return sendAJAXResponse(true);
	}
	
	/**
	 * Tela para incluir um novo departamento
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward popupNovoDepartamento(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		
		ManterDepartamentoActionForm frm = (ManterDepartamentoActionForm) form;
		
		frm.setListaDepartamentos(departamentoBO.obterTodos());
		
		return findForward("popupNovoDepartamento");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm formulario, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ManterDepartamentoActionForm form = (ManterDepartamentoActionForm) formulario;
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		Departamento departamento = departamentoBO.obter(form.getId()); 
		
		departamentoBO.excluir(departamento);
		
		addMessageKey("mensagem.departamento.excluir");
		
		return sendAJAXResponse(true);
	}
}
