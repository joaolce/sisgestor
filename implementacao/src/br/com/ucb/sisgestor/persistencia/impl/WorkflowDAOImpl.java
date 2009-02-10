/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.persistencia.WorkflowDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa��o da interface de acesso a dados de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public class WorkflowDAOImpl extends BaseDAOImpl<Workflow, Integer> implements WorkflowDAO {

	private static final WorkflowDAO	instancia	= new WorkflowDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link WorkflowDAOImpl}
	 */
	private WorkflowDAOImpl() {
		super(Workflow.class);
	}

	/**
	 * Recupera a inst�ncia de {@link WorkflowDAO}. pattern singleton.
	 * 
	 * @return {@link WorkflowDAO}
	 */
	public static WorkflowDAO getInstancia() {
		return instancia;
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, ativo);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
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
	 * Monta os crit�rios para a pagina��o dos workflows.
	 * 
	 * @param nome parte do nome do usu�rio
	 * @param descricao parte da descri��o do workflow
	 * @param ativo indica se o workflow est� ativo ou n�o
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Boolean ativo) {
		Criteria criteria = this.getSession().createCriteria(Workflow.class);
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		//TODO Decidir pela pesquisa - Combo ou checkbox
		//		if (departamento != null) {
		//			criteria.createAlias("this.departamento", "departamento");
		//			criteria.add(Restrictions.eq("departamento.id", departamento));
		//		}
		return criteria;
	}
}
