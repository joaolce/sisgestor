/*
 * Projeto: sisgestor
 * Criação: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.dto.PesquisaManterTarefaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
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
public class TarefaBOImpl extends BaseWorkflowBOImpl<Tarefa> implements TarefaBO {

	private AtividadeBO	atividadeBO;
	private TarefaDAO		tarefaDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Tarefa tarefa) throws NegocioException {
		Usuario usuarioLogado = Utils.getUsuario();
		Atividade atividadeTarefa = tarefa.getAtividade();
		Workflow workflowTarefa = atividadeTarefa.getProcesso().getWorkflow();
		if (usuarioLogado.getDepartamento().equals(atividadeTarefa.getDepartamento())
				&& usuarioLogado.getChefe()) {
			if (workflowTarefa.getDataHoraExclusao() != null) {
				throw new NegocioException("erro.workflowExcluido");
			}
			if (workflowTarefa.getAtivo() && (tarefa.getUsuario() == null)) {
				throw new NegocioException("erro.workflowAtivo.semUsuario");
			}
		} else {
			this.validarSePodeAlterarWorkflow(workflowTarefa);
		}
		this.tarefaDAO.atualizar(tarefa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizarTransacoes(Integer idAtividade, String[] fluxos, String[] posicoes)
			throws NegocioException {
		Workflow workflow = this.getAtividadeBO().obter(idAtividade).getProcesso().getWorkflow();
		this.validarSePodeAlterarWorkflow(workflow);

		List<TransacaoTarefa> transacoes = this.getTransacoes(fluxos);
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

		//Salvando as novas posições dos processos
		Tarefa tarefa;
		String[] tarefas;
		for (String posicao : posicoes) {
			tarefas = posicao.split(","); //Formato: <id>,<left>,<top>
			tarefa = this.tarefaDAO.obter(Integer.valueOf(tarefas[0]));

			tarefa.setLeft(Integer.valueOf(tarefas[1]));
			tarefa.setTop(Integer.valueOf(tarefas[2]));

			this.tarefaDAO.atualizar(tarefa);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Tarefa tarefa) throws NegocioException {
		this.validarSePodeAlterarWorkflow(tarefa.getAtividade().getProcesso().getWorkflow());
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
		PesquisaManterTarefaDTO dto = (PesquisaManterTarefaDTO) parametros;
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
	@Transactional(readOnly = true)
	public Tarefa recuperarPrimeiraTarefa(Workflow workflow) {
		return this.tarefaDAO.recuperarPrimeiraTarefa(workflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Tarefa> recuperarProximasTarefas(UsoWorkflow usoWorkflow) {
		List<Tarefa> proximasTarefas = new ArrayList<Tarefa>();
		Tarefa tarefa = usoWorkflow.getTarefa();
		List<TransacaoTarefa> transacoesTarefa = tarefa.getTransacoesPosteriores();
		if (CollectionUtils.isNotEmpty(transacoesTarefa)) { //existem tarefas dentro da mesma atividade
			for (TransacaoTarefa transacaoTarefa : transacoesTarefa) {
				proximasTarefas.add(transacaoTarefa.getPosterior());
			}
		} else { //é tarefa final de atividade
			this.verificarProximasTarefasDoUsoPelaAtividade(tarefa.getAtividade(), proximasTarefas);
		}
		return proximasTarefas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Tarefa tarefa) throws NegocioException {
		Atividade atividade = this.getAtividadeBO().obter(tarefa.getAtividade().getId());
		this.validarSePodeAlterarWorkflow(atividade.getProcesso().getWorkflow());
		return this.tarefaDAO.salvar(tarefa);
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
	 * Recupera o BO de {@link Atividade}.
	 * 
	 * @return BO de {@link Atividade}
	 */
	private AtividadeBO getAtividadeBO() {
		if (this.atividadeBO == null) {
			this.atividadeBO = Utils.getBean(AtividadeBO.class);
		}
		return this.atividadeBO;
	}

	/**
	 * Recupera a lista de transações criadas para os tarefas.
	 * 
	 * @param fluxos fluxos definidos pelo usuário
	 * @return {@link List} de {@link TransacaoTarefa}
	 */
	private List<TransacaoTarefa> getTransacoes(String[] fluxos) {
		List<TransacaoTarefa> lista = new ArrayList<TransacaoTarefa>();
		if (fluxos != null) {
			TransacaoTarefa transacao;
			Tarefa tarefaAnterior;
			Tarefa tarefaPosterior;
			for (String fluxo : fluxos) {
				String[] tarefas = fluxo.split(","); //Formato: <origem>,<destino>

				transacao = new TransacaoTarefa();
				tarefaAnterior = new Tarefa();
				tarefaPosterior = new Tarefa();

				tarefaAnterior.setId(Integer.parseInt(tarefas[0]));
				tarefaPosterior.setId(Integer.parseInt(tarefas[1]));

				transacao.setAnterior(tarefaAnterior);
				transacao.setPosterior(tarefaPosterior);
				lista.add(transacao);
			}
		}
		return lista;
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
		List<Tarefa> lista = this.tarefaDAO.getByAtividade(idAtividade);

		if ((lista == null) || lista.isEmpty()) {
			throw new NegocioException("erro.fluxo.definicao.tarefa.vazia");
		}
		if (lista.size() == 1) {
			return;
		}

		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.tarefa");
		NegocioException exceptionIsolado = new NegocioException("erro.fluxo.isolado.tarefa");
		NegocioException exceptionFinal = new NegocioException("erro.fluxo.final.tarefa");
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, lista);

		this.validarFluxos(lista, exceptionIsolado, exceptionInicial, exceptionFinal, mapAnteriores,
				mapPosteriores);
	}

	/**
	 * Verifica quais são as próximas tarefas disponíveis para o {@link UsoWorkflow} a partir de uma nova
	 * {@link Atividade}.
	 * 
	 * @param atividade atividade atual do {@link UsoWorkflow}
	 * @param proximasTarefas lista com as próximas tarefas
	 */
	private void verificarProximasTarefasDoUsoPelaAtividade(Atividade atividade, List<Tarefa> proximasTarefas) {
		List<TransacaoAtividade> transacoesAtividade = atividade.getTransacoesPosteriores();
		if (CollectionUtils.isNotEmpty(transacoesAtividade)) {
			for (TransacaoAtividade transacaoAtividade : transacoesAtividade) {
				Atividade proximaAtividade = transacaoAtividade.getPosterior();
				for (Tarefa proximaTarefa : proximaAtividade.getTarefas()) {
					if (CollectionUtils.isEmpty(proximaTarefa.getTransacoesAnteriores())) { // é a tarefa inicial
						proximasTarefas.add(proximaTarefa);
						break;
					}
				}
			}
		} else { // é atividade final de processo
			this.verificarProximasTarefasDoUsoPeloProcesso(atividade.getProcesso(), proximasTarefas);
		}
	}

	/**
	 * Verifica quais são as próximas tarefas disponíveis para o {@link UsoWorkflow} a partir de um novo
	 * {@link Processo}.
	 * 
	 * @param processo processo atual do {@link UsoWorkflow}
	 * @param proximasTarefas lista com as próximas tarefas
	 */
	private void verificarProximasTarefasDoUsoPeloProcesso(Processo processo, List<Tarefa> proximasTarefas) {
		List<TransacaoProcesso> transacoesProcesso = processo.getTransacoesPosteriores();
		if (CollectionUtils.isNotEmpty(transacoesProcesso)) {
			for (TransacaoProcesso transacaoProcesso : transacoesProcesso) {
				Processo proximoProcesso = transacaoProcesso.getPosterior();
				for (Atividade proximaAtividade : proximoProcesso.getAtividades()) {
					List<TransacaoAtividade> transacoesAtividade = proximaAtividade.getTransacoesAnteriores();
					if (CollectionUtils.isEmpty(transacoesAtividade)) { // é atividade inicial
						List<Tarefa> tarefas = proximaAtividade.getTarefas();
						for (Tarefa proximaTarefa : tarefas) {
							if (CollectionUtils.isEmpty(proximaTarefa.getTransacoesAnteriores())) { // é tarefa inicial
								proximasTarefas.add(proximaTarefa);
								break;
							}
						}
						break;
					}
				}
			}
		}
	}
}
