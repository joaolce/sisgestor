/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@Repository("usoWorkflowDAO")
public class UsoWorkflowDAOImpl extends BaseDAOImpl<UsoWorkflow, Integer> implements UsoWorkflowDAO {

	/**
	 * Cria uma nova instância do tipo {@link UsoWorkflowDAOImpl}.
	 */
	public UsoWorkflowDAOImpl() {
		super(UsoWorkflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(Usuario usuario) {
		Criteria criteria = this.montarCriteriosPaginacao(usuario);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Workflow> recuperarPendentesIniciar(Usuario usuario) {
		//TODO Implemetar query para recupera os pendentes de serem inicializados
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(usuario);
		this.adicionarPaginacao(criteria, paginaAtual, UsoWorkflowDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("dataHoraInicio"));
		return GenericsUtil.checkedList(criteria.list(), UsoWorkflow.class);
	}

	/**
	 * Monta os critérios para a paginação dos workflows.
	 * 
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(Usuario usuario) {
		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		criteria.createAlias("this.tarefa", "tarefa");
		criteria.add(Restrictions.eq("tarefa.usuario", usuario));
		criteria.add(Restrictions.isNull("this.dataHoraFim"));
		criteria.addOrder(Order.desc("this.dataHoraInicio"));
		return criteria;
	}
}
