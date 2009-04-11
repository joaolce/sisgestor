/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.AtividadeBO;
import br.com.ucb.sisgestor.negocio.ProcessoBO;
import br.com.ucb.sisgestor.negocio.TarefaBO;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.dto.PesquisaManterWorkflowDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@Service("workflowBO")
public class WorkflowBOImpl extends BaseBOImpl<Workflow> implements WorkflowBO {

	private WorkflowDAO	workflowDAO;
	private ProcessoBO	processoBO;
	private AtividadeBO	atividadeBO;
	private TarefaBO		tarefaBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Workflow workflow) throws NegocioException {
		Workflow workflowAtual = this.workflowDAO.obterAntigo(workflow.getId());
		this.verificarWorkflowExcluido(workflowAtual);
		if (workflow.getAtivo()) { //ativando o workflow
			this.validarAtivacaoDoWorkflow(workflowAtual);
			this.validarTransacoesDosProcessos(workflowAtual);
		} else if (workflowAtual.getAtivo()) { //está desativando o workflow
			throw new NegocioException("erro.workflowDestivar");
		}
		this.workflowDAO.atualizar(workflow);
	}

	/**
	 *{@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void copiar(Integer idWorkflow) throws NegocioException {
		Workflow workflow = this.workflowDAO.obterAntigo(idWorkflow);
		Workflow workflowNovo = new Workflow();
		try {
			workflowNovo.setNome("Cópia de - " + workflow.getNome());
			workflowNovo.setDescricao(workflow.getDescricao());
			workflowNovo.setAtivo(Boolean.FALSE);
		} catch (Exception e) {
			throw new NegocioException("erro.workflow.copiar");
		}
		this.workflowDAO.salvar(workflowNovo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Workflow workflow) throws NegocioException {
		this.verificarWorkflowExcluido(workflow);
		workflow.setAtivo(Boolean.FALSE);
		workflow.setDataHoraExclusao(DataUtil.getDataHoraAtual());
		this.workflowDAO.atualizar(workflow); //Exclusão lógica
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo,
			Boolean excluidos, Integer paginaAtual) {
		return this.workflowDAO.getByNomeDescricaoAtivo(nome, descricao, ativo, excluidos, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterWorkflowDTO dto = (PesquisaManterWorkflowDTO) parametros;
		return this.workflowDAO.getTotalRegistros(dto.getNome(), dto.getDescricao(), dto.getAtivo(), dto
				.getExcluidos());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Workflow obter(Integer pk) {
		return this.workflowDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Workflow> obterTodos() {
		return this.workflowDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Workflow> recuperarPendentesIniciar() {
		return this.workflowDAO.recuperarPendentesIniciar(Utils.getUsuario());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Integer salvar(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo()) { //quando salva um workflow, ele ainda não contém processos
			throw new NegocioException("erro.workflowNaoAtivado.processo");
		}
		return this.workflowDAO.salvar(workflow);
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
	 * Atribui o BO de {@link Processo}.
	 * 
	 * @param processoBO BO de {@link Processo}
	 */
	@Autowired
	public void setProcessoBO(ProcessoBO processoBO) {
		this.processoBO = processoBO;
	}

	/**
	 * Atribui o BO de {@link Tarefa}.
	 * 
	 * @param tarefaBO BO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaBO(TarefaBO tarefaBO) {
		this.tarefaBO = tarefaBO;
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
	 * Faz as validações para as {@link Atividade}s para a ativação do {@link Workflow}.
	 * 
	 * @param processo {@link Processo} a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validaAtividadesParaAtivacao(Processo processo) throws NegocioException {
		List<Atividade> listaAtividades = processo.getAtividades();
		if ((listaAtividades == null) || listaAtividades.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.atividade", processo.getNome());
		}
		for (Atividade atividade : listaAtividades) {
			this.validaTarefasParaAtivacao(atividade);
		}
	}

	/**
	 * Faz as validações para os {@link Campo}s para a ativação do {@link Workflow}.
	 * 
	 * @param workflow {@link Workflow} a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validaCamposParaAtivacao(Workflow workflow) throws NegocioException {
		List<Campo> campos = workflow.getCampos();
		if ((campos == null) || campos.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.campo");
		}
	}

	/**
	 * Faz as validações para os {@link Processo}s para a ativação do {@link Workflow}.
	 * 
	 * @param workflow {@link Workflow} a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validaProcessosParaAtivacao(Workflow workflow) throws NegocioException {
		List<Processo> listaProcessos = workflow.getProcessos();
		if ((listaProcessos == null) || listaProcessos.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.processo");
		}
		for (Processo processo : listaProcessos) {
			this.validaAtividadesParaAtivacao(processo);
		}
	}

	/**
	 * Faz as validações para que um {@link Workflow} seja ativado.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarAtivacaoDoWorkflow(Workflow workflow) throws NegocioException {
		this.validaCamposParaAtivacao(workflow);
		this.validaProcessosParaAtivacao(workflow);
	}

	/**
	 * Verifica que todas as atividades e tarefas do workflow tenham fluxo, caso tenha apenas um, não é
	 * verificado, pois ele já é inicial e final.
	 * 
	 * @param processo processo a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarTransacoesDasAtividades(Processo processo) throws NegocioException {
		if (this.atividadeBO.temFluxoDefinido(processo.getId())) {
			List<Atividade> atividades = processo.getAtividades();
			for (Atividade atividade : atividades) {
				this.validarTransacoesDasTarefas(atividade);
			}
		} else {
			throw new NegocioException("erro.workflowNaoAtivado.atividade.isolada", processo.getNome());
		}
	}

	/**
	 * Verifica que todas as tarefas do workflow tenham fluxo, caso tenha apenas um, não é verificado, pois ele
	 * já é inicial e final.
	 * 
	 * @param tarefas tarefas a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarTransacoesDasTarefas(Atividade atividade) throws NegocioException {
		if (!this.tarefaBO.temFluxoDefinido(atividade.getId())) {
			throw new NegocioException("erro.workflowNaoAtivado.tarefa.isolada", atividade.getNome());
		}
	}

	/**
	 * Verifica que todos os processos, atividades, tarefas do workflow tenham fluxo, caso tenha apenas um, não
	 * é verificado, pois ele já é inicial e final.
	 * 
	 * @param workflow workflow a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarTransacoesDosProcessos(Workflow workflow) throws NegocioException {
		if (this.processoBO.temFluxoDefinido(workflow.getId())) {
			List<Processo> processos = workflow.getProcessos();
			for (Processo processo : processos) {
				this.validarTransacoesDasAtividades(processo);
			}
		} else {
			throw new NegocioException("erro.workflowNaoAtivado.processo.isolado");
		}
	}

	/**
	 * Faz as validações para as {@link Tarefa}s para a ativação do {@link Workflow}.
	 * 
	 * @param atividade {@link Atividade} a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validaTarefasParaAtivacao(Atividade atividade) throws NegocioException {
		List<Tarefa> listaTarefas = atividade.getTarefas();
		if ((listaTarefas == null) || listaTarefas.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.tarefa", atividade.getNome(), atividade
					.getProcesso().getNome());
		}
		//Toda tarefa deve possuir um responsável por ela.
		for (Tarefa tarefa : listaTarefas) {
			if (tarefa.getUsuario() == null) {
				throw new NegocioException("erro.tarefaSemResponsavel", tarefa.getNome(), atividade.getNome(),
						atividade.getProcesso().getNome());
			}
		}
	}

	/**
	 * Verifica se um {@link Workflow} encontra-se excluído, caso esteja lança exceção.
	 * 
	 * @param workflow workflow a verificar
	 * @throws NegocioException caso o workflow esteja excluído
	 */
	private void verificarWorkflowExcluido(Workflow workflow) throws NegocioException {
		if (workflow.getDataHoraExclusao() != null) {
			throw new NegocioException("erro.workflowExcluido");
		}
	}
}
