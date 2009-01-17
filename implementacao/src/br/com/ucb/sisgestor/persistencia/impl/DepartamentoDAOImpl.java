/*
 * Projeto: sisgestor
 * Cria��o: 05/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa��o da interface de acesso a dados de {@link Departamento}.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
public class DepartamentoDAOImpl extends BaseDAOImpl<Departamento, Integer> implements DepartamentoDAO {

	private static final DepartamentoDAO	instancia	= new DepartamentoDAOImpl();


	/**
	 * Cria uma nova inst�ncia do tipo {@link DepartamentoDAOImpl}.
	 */
	private DepartamentoDAOImpl() {
		super(Departamento.class);
	}

	/**
	 * Recupera a inst�ncia de {@link DepartamentoDAO}. pattern singleton.
	 * 
	 * @return {@link DepartamentoDAO}
	 */
	public static DepartamentoDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer pagina) {
		Criteria criteria = this.montarCriteriosPaginacao(sigla, nome);
		this.adicionarPaginacao(criteria, pagina, MAXIMO_RESULTADOS);
		criteria.addOrder(Order.asc("sigla"));
		return GenericsUtil.checkedList(criteria.list(), Departamento.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String sigla, String nome) {
		Criteria criteria = this.montarCriteriosPaginacao(sigla, nome);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * Monta os crit�rios para a pagina��o dos departamentos.
	 * 
	 * @param sigla sigla do departamento
	 * @param nome nome do departamento
	 * @return
	 */
	private Criteria montarCriteriosPaginacao(String sigla, String nome) {
		Criteria criteria = this.getSession().createCriteria(Departamento.class);
		Conjunction conjunction = Restrictions.conjunction();
		if (StringUtils.isNotBlank(sigla)) {
			conjunction.add(Restrictions.like("sigla", sigla, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(nome)) {
			conjunction.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		criteria.add(conjunction);
		return criteria;
	}
}
