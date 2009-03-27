/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2008 por Thiago
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaTarefaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de neg�cio para {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
@Service("tarefaBO")
public class TarefaBOImpl extends BaseBOImpl<Tarefa, Integer> implements TarefaBO {

	private TarefaDAO		tarefaDAO;
	private AtividadeDAO	atividadeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Tarefa tarefa) throws NegocioException {
		this.validarSePodeAlterarWorkflow(tarefa.getAtividade().getProcesso().getWorkflow());
		this.tarefaDAO.atualizar(tarefa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizarTransacoes(Integer idAtividade, String[] fluxos, String[] posicoes)
			throws NegocioException {

		List<TransacaoTarefa> transacoes = this.getTransacoes(fluxos);

		this.validarFluxo(transacoes, idAtividade);

		List<TransacaoTarefa> atual = this.tarefaDAO.recuperarTransacoesDaAtividade(idAtividade);

		if (atual != null) {
			//excluindo as transa��es atuais
			for (TransacaoTarefa transacao : atual) {
				this.tarefaDAO.excluirTransacao(transacao);
			}
			this.flush();
		}
		//inserindo as transa��es enviadas
		for (TransacaoTarefa transacao : transacoes) {
			this.tarefaDAO.salvarTransacao(transacao);
		}

		//Salvando as novas posi��es dos processos
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
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
		Atividade atividade = this.atividadeDAO.obter(tarefa.getAtividade().getId());
		this.validarSePodeAlterarWorkflow(atividade.getProcesso().getWorkflow());
		this.tarefaDAO.salvar(tarefa);
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
		//caso tenha apenas uma tarefa, ela j� � inicial e final.
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
	 * Finaliza a valida��o do fluxo e lan�a exce��o se fluxo for inv�lido
	 * 
	 * @param temTarefasIsolados {@link boolean} Indicador para ocorr�ncia de tarefas isoladas
	 * @param temInicio {@link boolean} Indicador para haver tarefa inicial
	 * @param temMaisDeUmInicio {@link boolean} Indicador para a ocorr�ncia de mais de uma tarefa inicial
	 * @param temFim {@link boolean} Indicador para a ocorr�ncia de pelo menos uma tarefa final
	 * @param exceptionIsolado Exce��o para o indicador de tarefas isoladas
	 * @param exceptionInicial Exce��o para o indicador de tarefas iniciais
	 * @throws NegocioException Exce��o a ser lan�ada
	 */
	private void finalizarValidacaoFluxos(boolean temTarefasIsoladas, boolean temInicio,
			boolean temMaisDeUmInicio, boolean temFim, NegocioException exceptionIsolado,
			NegocioException exceptionInicial) throws NegocioException {

		//N�o permite tarefas isoladas
		if (temTarefasIsoladas) {
			throw exceptionIsolado;
		}
		//N�o permite a inexist�ncia de um in�cio
		if (!temInicio) {
			throw exceptionInicial;
		}

		//N�o permite que se tenha mais de um in�cio
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//N�o permite a inexist�ncia de pelo menos um final
		if (!temFim) {
			throw new NegocioException("erro.fluxo.final.tarefa");
		}
	}

	/**
	 * Recupera a lista de transa��es criadas para os tarefas.
	 * 
	 * @param fluxos fluxos definidos pelo usu�rio
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
	 * Inicializa os mapas para a valida��o do fluxo.
	 * 
	 * @param transacoes transa��es de tarefas
	 * @param mapAnteriores {@link Map} para armazenar as transa��es anteriores
	 * @param mapPosteriores {@link Map} para armazenar as transa��es posteriores
	 * @param listaTarefas {@link List} com as tarefas
	 * @throws NegocioException caso n�o haja fluxo definido
	 */
	private void inicializarValidacaoFluxo(List<TransacaoTarefa> transacoes,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores, List<Tarefa> listaTarefas)
			throws NegocioException {
		//Valida para que haja defini��o de fluxos
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
	 * Realiza as valida��es das {@link TransacaoTarefa} informadas.
	 * 
	 * @param transacoes transa��es definidas pelo usu�rio
	 * @param idAtividade c�digo identificador da atividade
	 * @throws NegocioException caso regra de neg�cio seja violada
	 */
	private void validarFluxo(List<TransacaoTarefa> transacoes, Integer idAtividade) throws NegocioException {
		List<Tarefa> listaTarefas = this.tarefaDAO.getByAtividade(idAtividade);

		if (listaTarefas.size() == 1) {
			return;
		}

		NegocioException exceptionInicial = new NegocioException("erro.fluxo.inicial.tarefa");
		NegocioException exceptionIsolado = new NegocioException("erro.fluxo.isolado.tarefa");
		Map<Integer, Integer> mapAnteriores = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapPosteriores = new HashMap<Integer, Integer>();
		boolean temInicio = false;
		boolean temMaisDeUmInicio = false;
		boolean temFim = false;
		boolean temTarefasIsolados = false;
		Integer id;
		Integer tarefaAnterior;
		Integer tarefaPosterior;


		this.inicializarValidacaoFluxo(transacoes, mapAnteriores, mapPosteriores, listaTarefas);

		for (Tarefa tarefa : listaTarefas) {
			id = tarefa.getId();
			tarefaAnterior = mapAnteriores.get(id);
			tarefaPosterior = mapPosteriores.get(id);
			if ((tarefaAnterior == null) && (tarefaPosterior == null)) {
				temTarefasIsolados = true;
				exceptionIsolado.putValorDevolvido("id" + id, id.toString());
			}
			if (tarefaPosterior == null) {
				temFim = true;
			}
			if (tarefaAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (temInicio) {
					temMaisDeUmInicio = true;
				}
				temInicio = true;
			}
			if (tarefaPosterior == null) {
				temFim = true;
			}
		}

		this.finalizarValidacaoFluxos(temTarefasIsolados, temInicio, temMaisDeUmInicio, temFim,
				exceptionIsolado, exceptionInicial);
	}

	/**
	 * Verifica se o {@link Workflow} pode ser alterado, para n�o poder ocorrer altera��o nas tarefas.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} n�o possa ser alterado
	 */
	private void validarSePodeAlterarWorkflow(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo() || (workflow.getDataHoraExclusao() != null)) {
			throw new NegocioException("erro.workflow.alterar");
		}
	}
}
