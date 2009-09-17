/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.sisgestor.negocio.impl;

import br.com.sisgestor.entidade.Anexo;
import br.com.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.sisgestor.entidade.TipoAcaoEnum;
import br.com.sisgestor.entidade.UsoWorkflow;
import br.com.sisgestor.negocio.AnexoBO;
import br.com.sisgestor.negocio.UsoWorkflowBO;
import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.persistencia.AnexoDAO;
import br.com.sisgestor.util.DataUtil;
import br.com.sisgestor.util.Utils;
import br.com.sisgestor.util.constantes.Constantes;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
@Service("anexoBO")
public class AnexoBOImpl extends BaseBOImpl<Anexo> implements AnexoBO {

	private AnexoDAO anexoDAO;
	private UsoWorkflowBO usoWorkflowBO;

	private DataUtil dataUtil = DataUtil.getInstancia();

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public void atualizar(Anexo anexo) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Anexo anexo) throws NegocioException {
		this.anexoDAO.excluir(anexo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException {
		Anexo anexo = null;
		for (Integer idAnexo : anexosSelecionados) {
			anexo = this.anexoDAO.obter(idAnexo);
			this.anexoDAO.excluir(anexo);
		}
		this.salvarHistoricoUso(anexo.getUsoWorkflow(), TipoAcaoEnum.EXCLUSAO_DE_ANEXO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow) {
		return this.anexoDAO.getAnexosByUsoWorkflow(idUsoWorkflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Anexo obter(Integer pk) {
		return this.anexoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Anexo> obterTodos() {
		return this.anexoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Anexo anexo) throws NegocioException {
		this.validarArquivo(anexo);
		Integer idAnexo = this.anexoDAO.salvar(anexo);
		this.salvarHistoricoUso(anexo.getUsoWorkflow(), TipoAcaoEnum.INCLUSAO_DE_ANEXO);
		return idAnexo;
	}

	/**
	 * Atribui o DAO de {@link Anexo}.
	 * 
	 * @param anexoDAO DAO de {@link Anexo}
	 */
	@Autowired
	public void setAnexoDAO(AnexoDAO anexoDAO) {
		this.anexoDAO = anexoDAO;
	}

	/**
	 * Recupera o BO de {@link UsoWorkflow}.
	 * 
	 * @return BO de {@link UsoWorkflow}
	 */
	private UsoWorkflowBO getUsoWorkflowBO() {
		if (this.usoWorkflowBO == null) {
			this.usoWorkflowBO = Utils.getBean(UsoWorkflowBO.class);
		}
		return this.usoWorkflowBO;
	}

	/**
	 * Salva um {@link HistoricoUsoWorkflow} em relação aos anexos.
	 * 
	 * @param usoWorkflow {@link UsoWorkflow} em questão
	 * @param acao {@link TipoAcaoEnum} ocorrida
	 */
	private void salvarHistoricoUso(UsoWorkflow usoWorkflow, TipoAcaoEnum acao) {
		HistoricoUsoWorkflow historicoUsoWorkflow = new HistoricoUsoWorkflow();
		historicoUsoWorkflow.setUsoWorkflow(usoWorkflow);
		historicoUsoWorkflow.setUsuario(Utils.getUsuario());
		historicoUsoWorkflow.setDataHora(dataUtil.getDataHoraAtual());
		historicoUsoWorkflow.setAcao(acao);
		this.getUsoWorkflowBO().salvarHistorico(historicoUsoWorkflow);
	}

	/**
	 * Efetua verificações para saber se o arquivo é válido.
	 */
	private void validarArquivo(Anexo anexo) throws NegocioException {
		if (StringUtils.isBlank(anexo.getNome())) {
			throw new NegocioException("erro.arquivo.nomeVazio");
		}
		if (anexo.getDados().length > Constantes.TAMANHO_MAX_ANEXO_PERMITIDO) {
			throw new NegocioException("erro.arquivo.tamanhoMaximoExcedido", String
					.valueOf(((Constantes.TAMANHO_MAX_ANEXO_PERMITIDO / 1024) / 1024)));
		}
	}
}
