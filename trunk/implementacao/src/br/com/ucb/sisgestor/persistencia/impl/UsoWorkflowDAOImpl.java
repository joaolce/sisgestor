/*
 * Projeto: sisgestor
 * Cria��o: 30/03/2009 por Jo�o L�cio
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementa��o da interface de acesso a dados de {@link UsoWorkflow}.
 * 
 * @author Jo�o L�cio
 * @since 30/03/2009
 */
@Repository("usoWorkflowDAO")
public class UsoWorkflowDAOImpl extends BaseDAOImpl<UsoWorkflow> implements UsoWorkflowDAO {

	/**
	 * Cria uma nova inst�ncia do tipo {@link UsoWorkflowDAOImpl}.
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
		DetachedCriteria subCriteria = DetachedCriteria.forClass(HistoricoUsoWorkflow.class, "historico");
		subCriteria.setProjection(Projections.property("historico.usoWorkflow.id"));
		subCriteria.add(RestrictionsSGR.eqWithTransform("historico.dataHora", "YEAR", ano));

		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		criteria.setProjection(Projections.max("this.numero"));
		criteria.add(Property.forName("this.id").in(subCriteria));
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvarHistorico(HistoricoUsoWorkflow historicoUsoWorkflow) {
		this.getSession().save(historicoUsoWorkflow);
	}

	/**
	 * Monta os crit�rios para a pagina��o dos workflows.
	 * 
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(Usuario usuario) {
		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		criteria.add(Restrictions.eq("this.usoFinalizado", Boolean.FALSE));
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
