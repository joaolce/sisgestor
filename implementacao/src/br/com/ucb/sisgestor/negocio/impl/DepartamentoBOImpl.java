/*
 * Projeto: sisgestor
 * Cria��o: 05/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.persistencia.impl.DepartamentoDAOImpl;
import br.com.ucb.sisgestor.util.dto.PesquisaDepartamentoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Objeto de neg�cio para {@link Departamento}.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
public class DepartamentoBOImpl extends BaseBOImpl<Departamento, Integer> implements DepartamentoBO {

	private static final DepartamentoBO	instancia	= new DepartamentoBOImpl();
	private DepartamentoDAO					dao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link DepartamentoBOImpl}.
	 */
	private DepartamentoBOImpl() {
		this.dao = DepartamentoDAOImpl.getInstancia();
	}

	/**
	 * Recupera a inst�ncia de {@link DepartamentoBO}.<br />
	 * pattern singleton.
	 * 
	 * @return {@link DepartamentoBO}
	 */
	public static DepartamentoBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Departamento departamento) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.verificaDepartamentoSuperior(departamento);
			this.dao.atualizar(departamento);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by Jo�o L�cio - n�o � necess�rio ter causa exce��o
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Departamento departamento) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			if ((departamento.getDepartamentosFilhos() != null)
					&& !departamento.getDepartamentosFilhos().isEmpty()) {
				throw new NegocioException("erro.departamento.filhos");
			}
			if ((departamento.getUsuarios() != null) && !departamento.getUsuarios().isEmpty()) {
				throw new NegocioException("erro.departamento.usuarios");
			}
			//TODO Acrescentar aqui verifica��o se o departamento est� sendo referenciado para outras tabelas.
			this.dao.excluir(departamento);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual) {
		return this.dao.getBySiglaNome(sigla, nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaDepartamentoDTO dto = (PesquisaDepartamentoDTO) parametros;
		return this.dao.getTotalRegistros(dto.getSigla(), dto.getNome());
	}

	/**
	 * {@inheritDoc}
	 */
	public Departamento obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Departamento departamento) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.verificaDepartamentoSuperior(departamento);
			this.dao.salvar(departamento);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by Jo�o L�cio - n�o � necess�rio ter causa exce��o
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
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
