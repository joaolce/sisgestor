/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.ProcessoDAO;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaProcessoDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
@Service("processoBO")
public class ProcessoBOImpl extends BaseWorkflowBOImpl<Processo, Integer> implements ProcessoBO {

	private ProcessoDAO	processoDAO;
	private WorkflowDAO	workflowDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Processo processo) throws NegocioException {
		this.validarSePodeAlterarWorkflow(processo.getWorkflow());
		this.processoDAO.atualizar(processo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idWorkflow, String[] fluxos, String[] posicoes)
			throws NegocioException {
		Workflow workflow = this.workflowDAO.obter(idWorkflow);
		this.validarSePodeAlterarWorkflow(workflow);

		List<TransacaoProcesso> transacoes = this.getTransacoes(fluxos);
		this.validarFluxo(transacoes, idWorkflow);

		List<TransacaoProcesso> atual = this.processoDAO.recuperarTransacoesDoWorkflow(idWorkflow);

		if (atual != null) {
			//excluindo as transações atuais
			for (TransacaoProcesso transacao : atual) {
				this.processoDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transações enviadas
		for (TransacaoProcesso transacao : transacoes) {
			this.processoDAO.salvarTransacao(transacao);
		}

		//Salvando as novas posições dos processos
		Processo processo;
		String[] processos;
		for (String posicao : posicoes) {
			processos = posicao.split(","); //Formato: <id>,<left>,<top>
			processo = this.processoDAO.obter(Integer.valueOf(processos[0]));

			processo.setLeft(Integer.valueOf(processos[1]));
			processo.setTop(Integer.valueOf(processos[2]));

			this.processoDAO.atualizar(processo);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Processo processo) throws NegocioException {
		this.validarSePodeAlterarWorkflow(processo.getWorkflow());
		List<Atividade> lista = processo.getAtividades();
		//Não permite excluir um processo que contém atividades
		if ((lista != null) && !lista.isEmpty()) {
			throw new NegocioException("erro.processo.atividades");
		}
		this.processoDAO.excluir(processo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow,
			Integer paginaAtual) {
		return this.processoDAO.getByNomeDescricao(nome, descricao, idWorkflow, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> getByWorkflow(Integer workflow) {
		return this.processoDAO.getByWorkflow(workflow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaProcessoDTO dto = (PesquisaProcessoDTO) parametros;
		return this.processoDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getIdWorkflow());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Processo obter(Integer pk) {
		return this.processoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Processo> obterTodos() {
		return this.processoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Processo processo) throws NegocioException {
		Workflow workflow = this.workflowDAO.obter(processo.getWorkflow().getId());
		this.validarSePodeAlterarWorkflow(workflow);
		this.processoDAO.salvar(processo);
	}

	/**
	 * Atribui o DAO de {@link Processo}.
	 * 
	 * @param processoDAO DAO de {@link Processo}
	 */
	@Autowired
	public void setProcessoDAO(ProcessoDAO processoDAO) {
		this.processoDAO = processoDAO;
	}

	/**
	 * Atribui o DAO de {@link Workflow}.
	 * 
	 * @param workflowDAO DAO de {@link Workflow}
	 */
	@Autowired
	public void setWorkflowDAO(WorkflowDAO workflowDAO) {
		this.workflowDAO = workflowDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public boolean temFluxoDefinido(Integer idWorkflow) {
		List<Processo> processos = this.getByWorkflow(idWorkflow);
		List<TransacaoProcesso> anteriores;
		List<TransacaoProcesso> posteriores;
		//caso tenha apenas um processo, ele já é inicial e final.
		if ((processos != null) && (processos.size() > 1)) {
			for (Processo processo : processos) {
				anteriores = processo.getTransacoesAnteriores();
				posteriores = processo.getTransacoesPosteriores();
				if (((anteriores == null) || anteriores.isEmpty())
						&& ((posteriores == null) || posteriores.isEmpty())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Recupera a lista de transações criadas para os processos.
	 * 
	 * @param fluxos fluxos definidos pelo usuário
	 * @return {@link List} de {@link TransacaoProcesso}
	 */
	private List<TransacaoProcesso> getTransacoes(String[] fluxos) {
		List<TransacaoProcesso> lista = new ArrayList<TransacaoProcesso>();
		if (fluxos != null) {
			TransacaoProcesso transacao;
			Processo processoAnterior;
			Processo processoPosterior;
			for (String fluxo : fluxos) {
				String[] processos = fluxo.split(","); //Formato: <origem>,<destino>

				transacao = new TransacaoProcesso();
				processoAnterior = new Processo();
				processoPosterior = new Processo();

				processoAnterior.setId(Integer.parseInt(processos[0]));
				processoPosterior.setId(Integer.parseInt(processos[1]));

				transacao.setAnterior(processoAnterior);
				transacao.setPosterior(processoPosterior);
				lista.add(transacao);
			}
		}
		return lista;
	}

	/**
	 * Inicializa os mapas para a validação do fluxo.
	 * 
	 * @param transacoes transações de processo
	 * @param mapAnteriores {@link Map} para armazenar as transações anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transações posteriores
	 * @param listaProcessos {@link List} com os processos
	 * @throws NegocioException caso não haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoProcesso> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores,
			List<Processo> listaProcessos) throws NegocioException {
		//Valida para que haja definição de fluxos
		if ((listaProcessos != null) && !listaProcessos.isEmpty()
				&& ((transacoes != null) && transacoes.isEmpty())) {
			throw new NegocioException("erro.fluxo.definicao.processo");
		}

		for (Processo processo : listaProcessos) {
			mapAnteriores.put(processo.getId(), null);
			mapPosteriores.put(processo.getId(), null);
		}

		Integer anterior;
		Integer posterior;
		for (TransacaoProcesso transacaoProcesso : transacoes) {
			anterior = transacaoProcesso.getAnterior().getId();
			posterior = transacaoProcesso.getPosterior().getId();

			mapAnteriores.put(posterior, anterior);
			mapPosteriores.put(anterior, posterior);
		}
	}

	/**
	 * Realiza as validações das {@link TransacaoProcesso} informadas.
	 * 
	 * @param transacoes transações definidas pelo usuário
	 * @param idWorkflow código identificador do workflow
	 * @throws NegocioException caso regra de negócio seja violada
	 */
	private void validarFluxo(List<TransacaoProcesso> transacoes, Integer idWorkflow) throws NegocioException {
		List<Processo> lista = this.processoDAO.getByWorkflow(idWorkflow);

		if (lista.size() == 1) {
			return;
		}

		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.processo");
		NegocioException exceptionIsolado = new NegocioException("erro.fluxo.isolado.processo");
		NegocioException exceptionFinal = new NegocioException("erro.fluxo.final.processo");
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();

		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, lista);

		this.finalizarValidacaoFluxos(lista, exceptionIsolado, exceptionInicial, exceptionFinal, mapAnteriores,
				mapPosteriores);
	}

	/**
	 * Verifica se o {@link Workflow} pode ser alterado, para não poder ocorrer alteração nos processos.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} não possa ser alterado
	 */
	private void validarSePodeAlterarWorkflow(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo() || (workflow.getDataHoraExclusao() != null)) {
			throw new NegocioException("erro.workflow.alterar");
		}
	}
}
