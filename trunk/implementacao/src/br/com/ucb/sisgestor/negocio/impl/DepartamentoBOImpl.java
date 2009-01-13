/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.persistencia.impl.DepartamentoDAOImpl;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Objeto de negócio para {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public class DepartamentoBOImpl extends BaseBOImpl<Departamento, Integer> implements DepartamentoBO {

	private static final DepartamentoBO	instancia	= new DepartamentoBOImpl();
	private DepartamentoDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link DepartamentoBOImpl}.
	 */
	private DepartamentoBOImpl() {
		this.dao = DepartamentoDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link DepartamentoBO}.<br />
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
	public void atualizar(Departamento departamento) throws Exception {
		this.salvar(departamento);
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Departamento departamento) throws Exception {
		Transaction transaction = this.beginTransaction();
		try {
			if ((departamento.getDepartamentosFilhos() != null)
					&& !departamento.getDepartamentosFilhos().isEmpty()) {
				throw new NegocioException("erro.departamento.filhos");
			}
			this.dao.excluir(departamento);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> getByNome(String nome, Integer paginaAtual) {
		return this.dao.getByNome(nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome) {
		return this.dao.getTotalRegistros(nome);
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
	public void salvar(Departamento departamento) throws Exception {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvarOuAtualizar(departamento);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			if (ce.getConstraintName().equals("DPR_SIGLA")) {
				throw new NegocioException("erro.departamento.sigla");
			} else {
				throw ce;
			}
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			throw e;
		}
	}
}
