/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@Repository("workflowDAO")
public class WorkflowDAOImpl extends BaseDAOImpl<Workflow, Integer> implements WorkflowDAO {

	/**
	 * Cria uma nova instância do tipo {@link WorkflowDAOImpl}
	 */
	public WorkflowDAOImpl() {
		super(Workflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo,
			Boolean excluidos, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, ativo, excluidos);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Workflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, String descricao, Boolean ativo, Boolean excluidos) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, ativo, excluidos);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> recuperarPendentesIniciar(Usuario usuario) {
		Criteria criteria = this.createCriteria(Workflow.class);
		criteria.add(Restrictions.eq("this.ativo", Boolean.TRUE));

		criteria.createAlias("this.processos", "processo");
		criteria.add(Restrictions.isEmpty("processo.transacoesAnteriores"));

		criteria.createAlias("processo.atividades", "atividade");
		criteria.add(Restrictions.isEmpty("atividade.transacoesAnteriores"));

		criteria.createAlias("atividade.tarefas", "tarefa");
		criteria.add(Restrictions.isEmpty("tarefa.transacoesAnteriores"));
		criteria.add(Restrictions.eq("tarefa.usuario", usuario));

		return GenericsUtil.checkedList(criteria.list(), Workflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}

	/**
	 * Monta os critérios para a paginação dos workflows.
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @param excluidos indica se deve apresentar os workflows excluidos
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Boolean ativo, Boolean excluidos) {
		Criteria criteria = this.createCriteria(Workflow.class);
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		if (ativo != null) {
			criteria.add(Restrictions.eq("ativo", ativo));
		}
		if (!excluidos) {
			criteria.add(Restrictions.isNull("dataHoraExclusao"));
		}
		return criteria;
	}
}
