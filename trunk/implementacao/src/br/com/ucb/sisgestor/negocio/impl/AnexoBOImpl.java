/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.negocio.AnexoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AnexoDAO;
import java.util.List;
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
public class AnexoBOImpl extends BaseBOImpl<Anexo, Integer> implements AnexoBO {

	private AnexoDAO	anexoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Anexo obj) throws NegocioException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Anexo obj) throws NegocioException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Anexo> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Anexo anexo) throws NegocioException {
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
}
