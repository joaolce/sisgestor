/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.persistencia.impl.DepartamentoDAOImpl;
import br.com.ucb.sisgestor.util.dto.PesquisaDepartamentoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
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
	public void atualizar(Departamento departamento) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.verificaDepartamentoSuperior(departamento);
			this.dao.atualizar(departamento);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by João Lúcio - não é necessário ter causa exceção
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
			if(this.isDepartamentoResponsavelAtividade(departamento)){
				throw new NegocioException("erro.departamento.responsavel");
			}
			
			this.dao.excluir(departamento);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * Verifica se o departamento está responsável por alguma atividade
	 * 
	 * @param departamento Departamento a ser verificado
	 * @return <code>true</code>, se está;<br><code>false</code>, se não.
	 */
	private boolean isDepartamentoResponsavelAtividade(Departamento departamento) {
		return departamentoTemAtividades(departamento);
	}
	
	/**
	 * Verifica se o departamento possui alguma atividade subordinada.
	 *
	 * @param departamento Departamento a ser verificado
	 * @return <code>true</code>, se tiver;<br><code>false</code>, se não.
	 */
	private boolean departamentoTemAtividades(Departamento departamento){
		Hibernate.initialize(departamento.getAtividades());
		List<Atividade> listaAtividades = departamento.getAtividades();
		
		if(listaAtividades != null && !listaAtividades.isEmpty()){
			return true;
		}
		return false;
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
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by João Lúcio - não é necessário ter causa exceção
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
