/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.persistencia.impl.DepartamentoDAOImpl;
import java.util.List;

import org.hibernate.Transaction;

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
	 * Recupera a instância de {@link DepartamentoBO}.<br> 
	 * pattern singleton.
	 * 
	 * @return {@link DepartamentoBO}
	 */
	public static DepartamentoBO getInstancia() {
		return instancia;
	}

	/**
	 * 
	 * Atualiza os dados do {@link Departamento}
	 *
	 * @see br.com.ucb.sisgestor.negocio.impl.BaseBOImpl#atualizar(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void atualizar(Departamento departamento) throws Exception {
		Transaction transaction = beginTransaction();
		try {
			super.atualizar(departamento);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Departamento obter(Integer pk) {
		return this.dao.obter(pk);
	}
	
	/**
	 * 
	 * Retorna um {@link List} de {@link Departamento} a partir do nome
	 *
	 * @see br.com.ucb.sisgestor.negocio.DepartamentoBO#getByNome(java.lang.String, java.lang.Integer)
	 */
	public List<Departamento> getByNome(String nome, Integer paginaAtual){
		return this.dao.getByNome(nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * 
	 * Salva um {@link Departamento} na base de dados
	 * @throws Exception 
	 *
	 * @see br.com.ucb.sisgestor.negocio.BaseBO#salvar(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void salvar(Departamento departamento) throws Exception {
		Transaction transaction = beginTransaction();
		try {
			super.salvar(departamento);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}
	
	/**
	 * 
	 * Remove um {@link Departamento} da base de dados
	 *
	 * @see br.com.ucb.sisgestor.negocio.impl.BaseBOImpl#excluir(br.com.ucb.sisgestor.entidade.ObjetoPersistente)
	 */
	public void excluir(Departamento departamento) throws Exception {
		Transaction transaction = beginTransaction();
		try {
			super.excluir(departamento);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}
	
	/**
	 * 
	 * Retorna o total de {@link Departamento} 
	 *
	 * @see br.com.ucb.sisgestor.negocio.DepartamentoBO#getTotalRegistros(java.lang.String)
	 */
	public Integer getTotalRegistros(String nome) {
		return this.dao.getTotalRegistros(nome);
	}
}
