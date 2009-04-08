/*
 * Projeto: sisgestor
 * Criação: 31/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.negocio.AnexoBO;
import br.com.ucb.sisgestor.negocio.UsoWorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.Constantes;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
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
public class UsoWorkflowBOImpl extends BaseBOImpl<UsoWorkflow, Integer> implements UsoWorkflowBO {

	private UsoWorkflowDAO	usoWorkflowDAO;
	private AnexoBO			anexoBO;

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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException {
		Anexo anexo;
		for (Integer idAnexo : anexosSelecionados) {
			anexo = this.anexoBO.obter(idAnexo);
			this.anexoBO.excluir(anexo);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Anexo> getAnexos(Integer idUsoWorkflow) {
		return this.anexoBO.getAnexosByUsoWorkflow(idUsoWorkflow);
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void incluirAnexo(FormFile arquivo, Integer idUsoWorkflow) throws NegocioException {

		this.validarArquivo(arquivo);

		Anexo anexo = new Anexo();
		//Teste
		UsoWorkflow usoWorkflow = new UsoWorkflow();
		usoWorkflow.setId(Integer.valueOf(1));

		anexo.setUsoWorkflow(usoWorkflow);
		anexo.setNome(arquivo.getFileName());
		anexo.setContentType(arquivo.getContentType());
		anexo.setDataCriacao(DataUtil.getDataHoraAtual());
		try {
			anexo.setDados(arquivo.getFileData());
		} catch (Exception e) {
			throw new NegocioException("erro.arquivo.naoEncontrado"); //NOPMD
		}

		this.anexoBO.salvar(anexo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public UsoWorkflow obter(Integer pk) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<UsoWorkflow> obterTodos() {
		return null;
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
	public void salvar(UsoWorkflow usoWorkflow) throws NegocioException {
		usoWorkflow.setDataHoraInicio(DataUtil.getDataHoraAtual());
		this.gerarNumeroDoRegistro(usoWorkflow);
		this.usoWorkflowDAO.salvar(usoWorkflow);
		this.gerarHistorico(usoWorkflow);
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
	 */
	private void gerarHistorico(UsoWorkflow usoWorkflow) {
		//TODO GERAR HISTORICO
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

	/**
	 * Efetua verificações para saber se o arquivo é válido.
	 */
	private void validarArquivo(FormFile arquivo) throws NegocioException {
		if (StringUtils.isBlank(arquivo.getFileName())) {
			throw new NegocioException("erro.arquivo.nomeVazio");
		}

		if (arquivo.getFileSize() > Constantes.TAMANHO_MAX_ANEXO_PERMITIDO) {
			throw new NegocioException("erro.arquivo.tamanhoMaximoExcedido", String
					.valueOf(((Constantes.TAMANHO_MAX_ANEXO_PERMITIDO / 1024) / 1204)));
		}
	}
}
