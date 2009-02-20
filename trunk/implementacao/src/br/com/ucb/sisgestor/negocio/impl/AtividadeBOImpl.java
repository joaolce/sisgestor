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
import br.com.ucb.sisgestor.util.dto.PesquisaAtividadeDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
@Service("atividadeBO")
public class AtividadeBOImpl extends BaseBOImpl<Atividade, Integer> implements AtividadeBO {

	private AtividadeDAO	atividadeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Atividade atividade) throws NegocioException {
		this.atividadeDAO.atualizar(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Atividade atividade) throws NegocioException {
		List<Tarefa> lista = atividade.getTarefas();
		//Não permite excluir uma atividade que contém tarefas
		if ((lista != null) && !lista.isEmpty()) {
			throw new NegocioException("erro.atividade.tarefas");
		}
		this.atividadeDAO.excluir(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual) {
		return this.atividadeDAO.getByNomeDescricaoDepartamento(nome, descricao, departamento, idProcesso,
				paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaAtividadeDTO dto = (PesquisaAtividadeDTO) parametros;
		return this.atividadeDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getDepartamento(),
				dto.getIdProcesso());
	}

	/**
	 * {@inheritDoc}
	 */
	public Atividade obter(Integer pk) {
		return this.atividadeDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> obterTodos() {
		return this.atividadeDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Atividade atividade) throws NegocioException {
		this.atividadeDAO.salvar(atividade);
	}

	/**
	 * Atribui o DAO de {@link Atividade}.
	 * 
	 * @param atividadeDAO DAO de {@link Atividade}
	 */
	@Autowired
	public void setAtividadeDAO(AtividadeDAO atividadeDAO) {
		this.atividadeDAO = atividadeDAO;
	}
}
