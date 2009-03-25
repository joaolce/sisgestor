/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.WorkflowBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaWorkflowDTO;
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
public class WorkflowBOImpl extends BaseBOImpl<Workflow, Integer> implements WorkflowBO {

	private WorkflowDAO	workflowDAO;

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
		PesquisaWorkflowDTO dto = (PesquisaWorkflowDTO) parametros;
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo()) { //quando salva um workflow, ele ainda não contém processos
			throw new NegocioException("erro.workflowNaoAtivado.processo");
		}
		this.workflowDAO.salvar(workflow);
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
	 * Faz as validações para um {@link Workflow} seja ativado.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarAtivacaoDoWorkflow(Workflow workflow) throws NegocioException {
		List<Campo> campos = workflow.getCampos();
		if ((campos == null) || campos.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.campo");
		}
		//Workflow deve possuir ao menos um processo com atividade com tarefa.
		List<Processo> listaProcessos = workflow.getProcessos();
		if ((listaProcessos == null) || listaProcessos.isEmpty()) {
			throw new NegocioException("erro.workflowNaoAtivado.processo");
		}
		for (Processo processo : listaProcessos) {
			List<Atividade> listaAtividades = processo.getAtividades();
			if ((listaAtividades == null) || listaAtividades.isEmpty()) {
				throw new NegocioException("erro.workflowNaoAtivado.atividade", processo.getNome());
			}
			for (Atividade atividade : listaAtividades) {
				List<Tarefa> listaTarefas = atividade.getTarefas();
				if ((listaTarefas == null) || listaTarefas.isEmpty()) {
					throw new NegocioException("erro.workflowNaoAtivado.tarefa", atividade.getNome(), processo
							.getNome());
				}
				//Toda tarefa deve possuir um responsável por ela.
				for (Tarefa tarefa : listaTarefas) {
					if (tarefa.getUsuario() == null) {
						throw new NegocioException("erro.tarefaSemResponsavel", tarefa.getNome(), atividade
								.getNome(), processo.getNome());
					}
				}
			}
		}
	}

	/**
	 * Verifica que todas as atividades e tarefas do workflow tenham fluxo, caso tenha apenas um, não é
	 * verificado, pois ele já é inicial e final.
	 * 
	 * @param processo processo a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarTransacoesDasAtividades(Processo processo) throws NegocioException {
		List<Atividade> atividades = processo.getAtividades();
		if (atividades.size() > 1) {
			for (Atividade atividade : atividades) {
				List<TransacaoAtividade> transacoesAnteriores = atividade.getTransacoesAnteriores();
				List<TransacaoAtividade> transacoesPosteriores = atividade.getTransacoesPosteriores();
				if (((transacoesAnteriores == null) || transacoesAnteriores.isEmpty())
						&& ((transacoesPosteriores == null) || transacoesPosteriores.isEmpty())) {
					throw new NegocioException("erro.workflowNaoAtivado.atividadeIsolada", atividade.getNome(),
							processo.getNome());
				}
				this.validarTransacoesDasTarefas(atividade.getTarefas());
			}
		} else {
			this.validarTransacoesDasTarefas(atividades.get(0).getTarefas());
		}
	}

	/**
	 * Verifica que todas as tarefas do workflow tenham fluxo, caso tenha apenas um, não é verificado, pois ele
	 * já é inicial e final.
	 * 
	 * @param tarefas tarefas a validar
	 * @throws NegocioException caso seja violada uma regra
	 */
	private void validarTransacoesDasTarefas(List<Tarefa> tarefas) throws NegocioException {
		if (tarefas.size() > 1) {
			for (Tarefa tarefa : tarefas) {
				List<TransacaoTarefa> transacoesAnteriores = tarefa.getTransacoesAnteriores();
				List<TransacaoTarefa> transacoesPosteriores = tarefa.getTransacoesPosteriores();
				if (((transacoesAnteriores == null) || transacoesAnteriores.isEmpty())
						&& ((transacoesPosteriores == null) || transacoesPosteriores.isEmpty())) {
					throw new NegocioException("");
				}
			}
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
		List<Processo> processos = workflow.getProcessos();
		if (processos.size() > 1) {
			for (Processo processo : processos) {
				List<TransacaoProcesso> transacoesAnteriores = processo.getTransacoesAnteriores();
				List<TransacaoProcesso> transacoesPosteriores = processo.getTransacoesPosteriores();
				if (((transacoesAnteriores == null) || transacoesAnteriores.isEmpty())
						&& ((transacoesPosteriores == null) || transacoesPosteriores.isEmpty())) {
					throw new NegocioException("erro.workflowNaoAtivado.processoIsolado", processo.getNome());
				}
				this.validarTransacoesDasAtividades(processo);
			}
		} else {
			this.validarTransacoesDasAtividades(processos.get(0));
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
