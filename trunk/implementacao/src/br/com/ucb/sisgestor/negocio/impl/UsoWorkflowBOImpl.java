/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.TipoAcaoEnum;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 31/03/2009
 */
@Service("usoWorkflowBO")
public class UsoWorkflowBOImpl extends BaseBOImpl<UsoWorkflow> implements UsoWorkflowBO {

	private UsoWorkflowDAO	usoWorkflowDAO;
	private WorkflowBO		workflowBO;
	private CampoBO			campoBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(UsoWorkflow usoWorkflow) throws NegocioException {
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public void excluir(UsoWorkflow obj) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> getCamposByIdUsoWorkflow(Integer idUsoWorkflow) {
		Workflow workflow = this.workflowBO.getByIdUsoWorkflow(idUsoWorkflow);
		return this.campoBO.getByWorkflow(workflow.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		return this.usoWorkflowDAO.getTotalRegistros(Utils.getUsuario());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public UsoWorkflow obter(Integer pk) {
		return this.usoWorkflowDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<UsoWorkflow> obterTodos() {
		return this.usoWorkflowDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<UsoWorkflow> recuperarPendentesUsuarioAtual(Integer paginaAtual) {
		return this.usoWorkflowDAO.recuperarPendentesUsuario(Utils.getUsuario(), paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Integer salvar(UsoWorkflow usoWorkflow) throws NegocioException {
		usoWorkflow.setDataHoraInicio(DataUtil.getDataHoraAtual());
		this.gerarNumeroDoRegistro(usoWorkflow);
		Integer id = this.usoWorkflowDAO.salvar(usoWorkflow);
		this.gerarHistorico(usoWorkflow);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow) {
		this.usoWorkflowDAO.salvarHistorico(historicoUsoWorkflow);
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
	 * Atribui o DAO de {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflowDAO DAO de {@link UsoWorkflow}
	 */
	@Autowired
	public void setUsoWorkflowDAO(UsoWorkflowDAO usoWorkflowDAO) {
		this.usoWorkflowDAO = usoWorkflowDAO;
	}

	/**
	 * Atribui o BO de {@link Workflow}.
	 * 
	 * @param workflowBO BO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowBO(WorkflowBO workflowBO) {
		this.workflowBO = workflowBO;
	}


	/**
	 * Gera um registro de histórico para o {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a gerar histórico
	 */
	private void gerarHistorico(UsoWorkflow usoWorkflow) {
		HistoricoUsoWorkflow historico = new HistoricoUsoWorkflow();
		historico.setUsoWorkflow(usoWorkflow);
		historico.setDataHora(DataUtil.getDataHoraAtual());
		if (CollectionUtils.isEmpty(usoWorkflow.getHistorico())) {
			historico.setAcao(TipoAcaoEnum.INICIO_USO);
		}
		this.salvarHistorico(historico);
	}


	/**
	 * Gera um número de registro para o {@link UsoWorkflow} a ser iniciado.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a ser iniciado
	 */
	private void gerarNumeroDoRegistro(UsoWorkflow usoWorkflow) {
		int ano = DataUtil.getAno(usoWorkflow.getDataHoraInicio());
		Integer ultimoNumero = this.usoWorkflowDAO.recuperarUltimoNumeroDoAno(ano);
		if (ultimoNumero == null) {
			ultimoNumero = 1;
		} else {
			ultimoNumero++;
		}
		usoWorkflow.setNumero(ultimoNumero);
	}
}
