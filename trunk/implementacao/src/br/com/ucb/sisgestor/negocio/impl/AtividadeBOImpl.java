/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.persistencia.impl.AtividadeDAOImpl;
import br.com.ucb.sisgestor.util.dto.PesquisaAtividadeDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Transaction;

/**
 * Objeto de negócio para {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class AtividadeBOImpl extends BaseBOImpl<Atividade, Integer> implements AtividadeBO {

	private static final AtividadeBO	instancia	= new AtividadeBOImpl();
	private AtividadeDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link AtividadeBOImpl}.
	 */
	private AtividadeBOImpl() {
		this.dao = AtividadeDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link AtividadeBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link AtividadeBO}
	 */
	public static AtividadeBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Atividade atividade) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(atividade);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Atividade atividade) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			List<Tarefa> lista = atividade.getTarefas();
			//Não permite excluir uma atividade que contém tarefas
			if ((lista != null) && !lista.isEmpty()) {
				throw new NegocioException("erro.atividade.tarefas");
			}
			this.dao.excluir(atividade);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual) {
		return this.dao.getByNomeDescricaoDepartamento(nome, descricao, departamento, idProcesso, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaAtividadeDTO dto = (PesquisaAtividadeDTO) parametros;
		return this.dao.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getDepartamento(), dto
				.getIdProcesso());
	}

	/**
	 * {@inheritDoc}
	 */
	public Atividade obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Atividade atividade) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.salvar(atividade);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}
}
