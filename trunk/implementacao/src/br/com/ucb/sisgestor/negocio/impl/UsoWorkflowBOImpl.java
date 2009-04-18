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
import java.util.ArrayList;
import java.util.List;
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

	private UsoWorkflowDAO usoWorkflowDAO;
	private WorkflowBO workflowBO;
	private CampoBO campoBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(UsoWorkflow usoWorkflow) throws NegocioException {
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.ALTERACAO_CAMPOS);
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
		Workflow workflow = this.getWorkflowBO().getByIdUsoWorkflow(idUsoWorkflow);
		return this.getCampoBO().getByWorkflow(workflow.getId());
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @see br.com.ucb.sisgestor.negocio.UsoWorkflowBO#getHistoricoByIdUsoWorkflow(java.lang.Integer)
	 */
	@Override
	public List<HistoricoUsoWorkflow> getHistoricoByIdUsoWorkflow(Integer idUsoWorkflow) {
		//FAKER
		List<HistoricoUsoWorkflow> lista = new ArrayList<HistoricoUsoWorkflow>();
		HistoricoUsoWorkflow his = new HistoricoUsoWorkflow();

		his.setAcao(TipoAcaoEnum.INICIO_USO);
		his.setDataHora(DataUtil.getDataHoraAtual());

		lista.add(his);
		return lista;
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
	public void iniciarTarefa(UsoWorkflow usoWorkflow) throws NegocioException {
		usoWorkflow.setDataHoraInicio(DataUtil.getDataHoraAtual());
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.INICIO_TAREFA);
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(UsoWorkflow usoWorkflow) throws NegocioException {
		this.gerarNumeroDoRegistro(usoWorkflow, DataUtil.getAno(DataUtil.getDataAtual()));
		Integer id = this.usoWorkflowDAO.salvar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.INICIO_USO);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow) {
		this.usoWorkflowDAO.salvarHistorico(historicoUsoWorkflow);
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
	 * Gera um registro de histórico para o {@link UsoWorkflow}.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a gerar histórico
	 * @para acao ação ocorrida no {@link UsoWorkflow}
	 */
	private void gerarHistorico(UsoWorkflow usoWorkflow, TipoAcaoEnum acao) {
		HistoricoUsoWorkflow historico = new HistoricoUsoWorkflow();
		historico.setUsoWorkflow(usoWorkflow);
		historico.setDataHora(DataUtil.getDataHoraAtual());
		historico.setAcao(acao);
		this.salvarHistorico(historico);
	}

	/**
	 * Gera um número de registro para o {@link UsoWorkflow} a ser iniciado.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} a ser iniciado
	 * @param ano Ano de início do uso do workflow
	 */
	private void gerarNumeroDoRegistro(UsoWorkflow usoWorkflow, int ano) {
		Integer ultimoNumero = this.usoWorkflowDAO.recuperarUltimoNumeroDoAno(ano);
		if (ultimoNumero == null) {
			ultimoNumero = 1;
		} else {
			ultimoNumero++;
		}
		usoWorkflow.setNumero(ultimoNumero);
	}

	/**
	 * Recupera o BO de {@link Campo}.
	 * 
	 * @return BO de {@link Campo}
	 */
	private CampoBO getCampoBO() {
		if (this.campoBO == null) {
			this.campoBO = Utils.getBean(CampoBO.class);
		}
		return this.campoBO;
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
}
