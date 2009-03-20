/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaTarefaDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
@Service("tarefaBO")
public class TarefaBOImpl extends BaseBOImpl<Tarefa, Integer> implements TarefaBO {

	private TarefaDAO		tarefaDAO;
	private WorkflowBO	workflowBO;
	private AtividadeBO	atividadeBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Tarefa tarefa) throws NegocioException {
		Atividade atividade = this.atividadeBO.obter(tarefa.getAtividade().getId());
		this.validarSeWorkflowAtivo(atividade.getProcesso().getWorkflow());
		this.tarefaDAO.atualizar(tarefa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idAtividade, List<TransacaoTarefa> transacoes)
			throws NegocioException {
		List<TransacaoTarefa> atual = this.tarefaDAO.recuperarTransacoesDaAtividade(idAtividade);
		if (atual != null) {
			//excluindo as transações atuais
			for (TransacaoTarefa transacao : atual) {
				this.tarefaDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transações enviadas
		for (TransacaoTarefa transacao : transacoes) {
			this.tarefaDAO.salvarTransacao(transacao);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Tarefa tarefa) throws NegocioException {
		this.validarSeWorkflowAtivo(tarefa.getAtividade().getProcesso().getWorkflow());
		this.tarefaDAO.excluir(tarefa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Tarefa> getByAtividade(Integer atividade) {
		return this.tarefaDAO.getByAtividade(atividade);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Tarefa> getByNomeDescricaoUsuario(String nome, String descricao, Integer usuario,
			Integer idAtividade, Integer paginaAtual) {
		return this.tarefaDAO.getByNomeDescricaoUsuario(nome, descricao, usuario, idAtividade, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaTarefaDTO dto = (PesquisaTarefaDTO) parametros;
		return this.tarefaDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getUsuario(), dto
				.getIdAtividade());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Tarefa obter(Integer pk) {
		return this.tarefaDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Tarefa> obterTodos() {
		return this.tarefaDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Tarefa tarefa) throws NegocioException {
		Atividade atividade = this.atividadeBO.obter(tarefa.getAtividade().getId());
		this.validarSeWorkflowAtivo(atividade.getProcesso().getWorkflow());
		this.tarefaDAO.salvar(tarefa);
	}

	/**
	 * Atribui o BO de {@link Atividade}.
	 * 
	 * @param atividadeBO BO de {@link Atividade}
	 */
	@Autowired
	public void setAtividadeBO(AtividadeBO atividadeBO) {
		this.atividadeBO = atividadeBO;
	}

	/**
	 * Atribui o DAO de {@link Tarefa}.
	 * 
	 * @param tarefaDAO DAO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaDAO(TarefaDAO tarefaDAO) {
		this.tarefaDAO = tarefaDAO;
	}

	/**
	 * Atribui o BO de {@link Workflow}.
	 * 
	 * @param workflowBO BO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowBO(WorkflowBO workflowBO) {
		this.workflowBO = workflowBO;
	}

	/**
	 * Verifica se o {@link Workflow} está ativo, caso esteja não pode ocorrer alteração nos processos.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} esteja ativo
	 */
	private void validarSeWorkflowAtivo(Workflow workflow) throws NegocioException {
		Workflow workflowAtivo = this.workflowBO.obter(workflow.getId());
		if (workflowAtivo.getAtivo()) {
			throw new NegocioException("erro.workflowAtivo");
		}
	}
}
