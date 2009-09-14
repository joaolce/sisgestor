/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.sisgestor.negocio.impl;

import br.com.sisgestor.util.Utils;
import br.com.sisgestor.util.dto.PesquisaManterCampoDTO;
import br.com.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.sisgestor.persistencia.CampoDAO;
import br.com.sisgestor.negocio.CampoBO;
import br.com.sisgestor.negocio.WorkflowBO;
import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.Campo;
import br.com.sisgestor.entidade.Workflow;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
@Service("campoBO")
public class CampoBOImpl extends BaseBOImpl<Campo> implements CampoBO {

	private WorkflowBO	workflowBO;
	private CampoDAO		campoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Campo campo) throws NegocioException {
		Workflow workflow = this.getWorkflowBO().obter(campo.getWorkflow().getId());
		this.validarSePodeAlterarWorkflow(workflow);
		this.campoDAO.atualizar(campo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Campo campo) throws NegocioException {
		this.validarSePodeAlterarWorkflow(campo.getWorkflow());
		this.campoDAO.excluir(campo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer idWorkflow, Integer paginaAtual) {
		return this.campoDAO.getByNomeTipo(nome, idTipo, idWorkflow, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> getByWorkflow(Integer idWorkflow) {
		return this.campoDAO.getByWorkflow(idWorkflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterCampoDTO dto = (PesquisaManterCampoDTO) parametros;
		return this.campoDAO.getTotalRegistros(dto.getNome(), dto.getTipo(), dto.getIdWorkflow());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Campo obter(Integer pk) {
		return this.campoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Campo> obterTodos() {
		return this.campoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Campo campo) throws NegocioException {
		Workflow workflow = this.getWorkflowBO().obter(campo.getWorkflow().getId());
		this.validarSePodeAlterarWorkflow(workflow);
		return this.campoDAO.salvar(campo);
	}

	/**
	 * Atribui o DAO de {@link Campo}.
	 * 
	 * @param campoDAO DAO de {@link Campo}
	 */
	@Autowired
	public void setCampoDAO(CampoDAO campoDAO) {
		this.campoDAO = campoDAO;
	}

	/**
	 * Recupera o BO de {@link Workflow}.
	 * 
	 * @return BO de {@link Workflow}
	 */
	private WorkflowBO getWorkflowBO() {
		if (this.workflowBO == null) {
			this.workflowBO = Utils.getBean(WorkflowBO.class);
		}
		return this.workflowBO;
	}

	/**
	 * Verifica se o {@link Workflow} pode ser alterado, para não poder ocorrer alteração nos campos.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} não possa ser alterado
	 */
	private void validarSePodeAlterarWorkflow(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo() || (workflow.getDataHoraExclusao() != null)) {
			throw new NegocioException("erro.workflow.alterar");
		}
	}
}
