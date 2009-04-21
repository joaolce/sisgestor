/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.CampoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.OpcaoCampo;
import br.com.ucb.sisgestor.entidade.TipoAcaoEnum;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
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

	private UsoWorkflowDAO	usoWorkflowDAO;

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
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		return this.usoWorkflowDAO.getTotalRegistros(Utils.getUsuario());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void salvarValoresCampos(String[] valores, Integer idUsoWorkflow) throws NegocioException {
		String[] campo;
		List<CampoUsoWorkflow> listaCampos = this.getCamposUsoWorkflowByIdUsoWorkflow(idUsoWorkflow);
		List<CampoUsoWorkflow> listaCamposAtualizar = new ArrayList<CampoUsoWorkflow>();

		UsoWorkflow usoWorkflow = this.obter(idUsoWorkflow);

		for (String valor : valores) {
			campo = valor.split("£");
			listaCamposAtualizar.add(this.getCampoUsoWorkflow(campo, listaCampos));
		}

		usoWorkflow.setCamposUsados(listaCamposAtualizar);
		this.usoWorkflowDAO.atualizar(usoWorkflow);
		this.gerarHistorico(usoWorkflow, TipoAcaoEnum.ALTERACAO_CAMPOS);
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
	 * TODO DOCUMENT ME!
	 * 
	 * @param idUsoWorkflow
	 * @return
	 */
	private List<CampoUsoWorkflow> getCamposUsoWorkflowByIdUsoWorkflow(Integer idUsoWorkflow) {
		return this.usoWorkflowDAO.getCamposUsoWorkflowByIdUsoWorkflow(idUsoWorkflow);
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @param campos
	 * @param listaCampos
	 * @return
	 */
	private CampoUsoWorkflow getCampoUsoWorkflow(String[] campos, List<CampoUsoWorkflow> listaCampos) {
		CampoUsoWorkflow campoUsoWorkflowRetorno = null;

		Integer idOpcao;
		StringBuffer valor = new StringBuffer();
		Integer idCampo = Integer.valueOf(campos[0].substring(5));
		for (CampoUsoWorkflow campoUsoWorkflow : listaCampos) {
			if (campoUsoWorkflow.getCampo().getId().equals(idCampo)) {
				if (campos.length == 2) {
					valor.append(campos[1]);
				} else {
					//Tipo checkbox ou radio
					for (OpcaoCampo opcao : campoUsoWorkflow.getCampo().getOpcoes()) {
						idOpcao = Integer.valueOf(campos[1].substring(5));
						if (opcao.getId().equals(idOpcao) && Boolean.parseBoolean(campos[3])) {
							valor.append(campos[2]);
						}
						//TODO Verificar correção para salvar os valores de todas as opções
					}
				}
				campoUsoWorkflow.setValor(valor.toString());
				campoUsoWorkflowRetorno = campoUsoWorkflow;
				break;
			}
		}

		return campoUsoWorkflowRetorno;
	}
}
