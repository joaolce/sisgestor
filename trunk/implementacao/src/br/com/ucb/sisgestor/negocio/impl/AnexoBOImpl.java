/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.negocio.AnexoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AnexoDAO;
import br.com.ucb.sisgestor.util.constantes.Constantes;
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

	private AnexoDAO	anexoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Anexo anexo) throws NegocioException {
		this.anexoDAO.atualizar(anexo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Anexo anexo) throws NegocioException {
		this.anexoDAO.excluir(anexo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluirAnexos(Integer[] anexosSelecionados) throws NegocioException {
		Anexo anexo;
		for (Integer idAnexo : anexosSelecionados) {
			anexo = this.anexoDAO.obter(idAnexo);
			this.anexoDAO.excluir(anexo);
		}
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Anexo anexo) throws NegocioException {
		this.validarArquivo(anexo);
		this.anexoDAO.salvar(anexo);
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
	 * Efetua verificações para saber se o arquivo é válido.
	 */
	private void validarArquivo(Anexo anexo) throws NegocioException {
		if (StringUtils.isBlank(anexo.getNome())) {
			throw new NegocioException("erro.arquivo.nomeVazio");
		}
		if (anexo.getDados().length > Constantes.TAMANHO_MAX_ANEXO_PERMITIDO) {
			throw new NegocioException("erro.arquivo.tamanhoMaximoExcedido", String
					.valueOf(((Constantes.TAMANHO_MAX_ANEXO_PERMITIDO / 1024) / 1204)));
		}
	}
}
