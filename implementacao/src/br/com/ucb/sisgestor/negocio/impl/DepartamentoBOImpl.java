/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaManterDepartamentoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
@Service("departamentoBO")
public class DepartamentoBOImpl extends BaseBOImpl<Departamento> implements DepartamentoBO {

	private DepartamentoDAO	departamentoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		try {
			this.departamentoDAO.atualizar(departamento);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.departamento.sigla");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Departamento departamento) throws NegocioException {
		if ((departamento.getDepartamentosFilhos() != null) && !departamento.getDepartamentosFilhos().isEmpty()) {
			throw new NegocioException("erro.departamento.filhos");
		}
		if ((departamento.getUsuarios() != null) && !departamento.getUsuarios().isEmpty()) {
			throw new NegocioException("erro.departamento.usuarios");
		}
		if ((departamento.getAtividades() != null) && !departamento.getAtividades().isEmpty()) {
			throw new NegocioException("erro.departamento.responsavel");
		}
		this.departamentoDAO.excluir(departamento);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual) {
		return this.departamentoDAO.getBySiglaNome(sigla, nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterDepartamentoDTO dto = (PesquisaManterDepartamentoDTO) parametros;
		return this.departamentoDAO.getTotalRegistros(dto.getSigla(), dto.getNome());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Departamento obter(Integer pk) {
		return this.departamentoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Departamento> obterTodos() {
		return this.departamentoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		try {
			return this.departamentoDAO.salvar(departamento);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.departamento.sigla");
		}
	}

	/**
	 * Atribui o DAO de {@link Departamento}.
	 * 
	 * @param departamentoDAO DAO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoDAO(DepartamentoDAO departamentoDAO) {
		this.departamentoDAO = departamentoDAO;
	}

	/**
	 * Verifica o {@link Departamento} superior do departamento.
	 * 
	 * @param departamento departamento a verificar
	 * @throws NegocioException caso departamento seja superior dele mesmo
	 */
	private void verificaDepartamentoSuperior(Departamento departamento) throws NegocioException {
		if ((departamento.getDepartamentoSuperior() != null)
				&& departamento.getDepartamentoSuperior().getId().equals(departamento.getId())) {
			throw new NegocioException("erro.departamento.superiorIgual");
		}
	}
}
