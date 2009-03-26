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
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaTarefaDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private AtividadeBO	atividadeBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Tarefa tarefa) throws NegocioException {
		this.validarSeWorkflowAtivo(tarefa.getAtividade().getProcesso().getWorkflow());
		this.tarefaDAO.atualizar(tarefa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idAtividade, List<TransacaoTarefa> transacoes)
			throws NegocioException {
		this.validarFluxo(transacoes, idAtividade);
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
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public boolean temFluxoDefinido(Integer idAtividade) {
		List<Tarefa> tarefas = this.getByAtividade(idAtividade);
		List<TransacaoTarefa> anteriores;
		List<TransacaoTarefa> posteriores;
		//caso tenha apenas uma tarefa, ela já é inicial e final.
		if ((tarefas != null) && (tarefas.size() > 1)) {
			for (Tarefa tarefa : tarefas) {
				anteriores = tarefa.getTransacoesAnteriores();
				posteriores = tarefa.getTransacoesPosteriores();
				if (((anteriores == null) || anteriores.isEmpty())
						&& ((posteriores == null) || posteriores.isEmpty())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Inicializa os mapas para a validação do fluxo.
	 * 
	 * @param transacoes transações de tarefas
	 * @param mapAnteriores {@link Map} para armazenar as transações anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transações posteriores
	 * @param listaTarefas {@link List} com as tarefas
	 * @throws NegocioException caso não haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoTarefa> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores, List<Tarefa> listaTarefas)
			throws NegocioException {
		//Valida para que haja definição de fluxos
		if ((listaTarefas != null) && !listaTarefas.isEmpty() && ((transacoes != null) && transacoes.isEmpty())) {
			throw new NegocioException("erro.fluxo.definicao.tarefa");
		}

		for (Tarefa tarefa : listaTarefas) {
			mapAnteriores.put(tarefa.getId(), null);
			mapPosteriores.put(tarefa.getId(), null);
		}

		Integer anterior;
		Integer posterior;
		for (TransacaoTarefa transacaoTarefa : transacoes) {
			anterior = transacaoTarefa.getAnterior().getId();
			posterior = transacaoTarefa.getPosterior().getId();

			mapAnteriores.put(posterior, anterior);
			mapPosteriores.put(anterior, posterior);
		}
	}

	/**
	 * Realiza as validações das {@link TransacaoTarefa} informadas.
	 * 
	 * @param transacoes transações definidas pelo usuário
	 * @param idAtividade código identificador da atividade
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	private void validarFluxo(List<TransacaoTarefa> transacoes, Integer idAtividade) throws NegocioException {
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		boolean inicio = false;
		boolean fim = false;
		List<Tarefa> listaTarefas = this.tarefaDAO.getByAtividade(idAtividade);

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, listaTarefas);

		//Valida para que haja ao menos um processo final, e exatamente um inicial
		Integer id;
		Integer tarefaAnterior;
		Integer tarefaPosterior;
		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.tarefa");
		for (Tarefa tarefa : listaTarefas) {
			id = tarefa.getId();
			tarefaAnterior = mapAnteriores.get(id);
			tarefaPosterior = mapPosteriores.get(id);
			if ((tarefaAnterior == null) && (tarefaPosterior == null)) {
				NegocioException ex = new NegocioException("erro.fluxo.isolado.tarefa");
				ex.putValorDevolvido("id", id);
				throw ex;
			}
			if (tarefaAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (inicio) {
					throw exceptionInicial;
				}
				inicio = true;
			}
			if (tarefaPosterior == null) {
				fim = true;
			}
		}
		if (!inicio) {
			throw exceptionInicial;
		}
		if (!fim) {
			throw new NegocioException("erro.fluxo.final.tarefa");
		}
	}

	/**
	 * Verifica se o {@link Workflow} está ativo, caso esteja não pode ocorrer alteração nos processos.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} esteja ativo
	 */
	private void validarSeWorkflowAtivo(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo()) {
			throw new NegocioException("erro.workflowAtivo");
		}
	}
}
