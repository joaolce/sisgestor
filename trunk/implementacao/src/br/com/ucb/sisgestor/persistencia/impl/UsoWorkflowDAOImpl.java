/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import br.com.ucb.sisgestor.util.hibernate.criterions.RestrictionsSGR;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
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
public class UsoWorkflowDAOImpl extends BaseDAOImpl<UsoWorkflow> implements UsoWorkflowDAO {

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
	public List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(usuario);
		this.adicionarPaginacao(criteria, paginaAtual, UsoWorkflowDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("this.dataHoraInicio"));
		return GenericsUtil.checkedList(criteria.list(), UsoWorkflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer recuperarUltimoNumeroDoAno(Integer ano) {
		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		criteria.setProjection(Projections.property("this.numero"));
		criteria.setMaxResults(1);

		criteria.add(RestrictionsSGR.eqWithTransform("this.dataHoraInicio", "YEAR", ano));
		criteria.addOrder(Order.desc("this.numero"));
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow) {
		this.getSession().save(historicoUsoWorkflow);
	}

	/**
	 * Monta os critérios para a paginação dos workflows.
	 * 
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(Usuario usuario) {
		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		Disjunction disjunction = Restrictions.disjunction();
		criteria.createAlias("this.tarefa", "tarefa");
		disjunction.add(Restrictions.eq("tarefa.usuario", usuario));
		if (usuario.getChefe()) {
			criteria.createAlias("tarefa.atividade", "atividade");
			disjunction.add(Restrictions.eq("atividade.departamento", usuario.getDepartamento()));
		}
		criteria.add(disjunction);
		return criteria;
	}
}
