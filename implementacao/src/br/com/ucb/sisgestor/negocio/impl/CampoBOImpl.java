/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.negocio.CampoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.CampoDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaCampoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
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
public class CampoBOImpl extends BaseBOImpl<Campo, Integer> implements CampoBO {

	private CampoDAO	campoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Campo campo) throws NegocioException {
		this.campoDAO.atualizar(campo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Campo campo) throws NegocioException {
		this.campoDAO.excluir(campo);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer paginaAtual) {
		return this.campoDAO.getByNomeTipo(nome, idTipo, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaCampoDTO dto = (PesquisaCampoDTO) parametros;
		return this.campoDAO.getTotalRegistros(dto.getNome(), dto.getTipo());
	}

	/**
	 * {@inheritDoc}
	 */
	public Campo obter(Integer pk) {
		return this.campoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> obterTodos() {
		return this.campoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Campo campo) throws NegocioException {
		this.campoDAO.salvar(campo);
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
}
