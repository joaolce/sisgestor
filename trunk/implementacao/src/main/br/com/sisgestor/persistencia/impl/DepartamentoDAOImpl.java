/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.sisgestor.persistencia.impl;

import br.com.sisgestor.util.GenericsUtil;
import br.com.sisgestor.persistencia.DepartamentoDAO;
import br.com.sisgestor.entidade.Departamento;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
@Repository("departamentoDAO")
public class DepartamentoDAOImpl extends BaseDAOImpl<Departamento> implements DepartamentoDAO {

	/**
	 * Cria uma nova instância do tipo {@link DepartamentoDAOImpl}.
	 */
	public DepartamentoDAOImpl() {
		super(Departamento.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer pagina) {
		Criteria criteria = this.montarCriteriosPaginacao(sigla, nome);
		this.adicionarPaginacao(criteria, pagina, QTD_REGISTROS_PAGINA);
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
	 * {@inheritDoc}
	 */
	public boolean isSiglaUtilizada(Departamento departamento) {
		Criteria criteria = this.getCriteria();
		criteria.setProjection(Projections.count("id"));

		criteria.add(Restrictions.eq("sigla", departamento.getSigla()));
		if (departamento.getId() != null) {
			criteria.add(Restrictions.ne("id", departamento.getId()));
		}

		Integer total = (Integer) criteria.uniqueResult();
		return !(Integer.valueOf(0).equals(total));
	}

	/**
	 * Recupera um {@link List} de {@link Departamento} ativos
	 * 
	 * @return lista de departamntos ativos
	 */
	public List<Departamento> obterTodosAtivos() {
		Criteria criteria = this.getCriteria();
		Order order = this.getOrdemLista();
		if (order != null) {
			criteria.addOrder(order);
		}
		return GenericsUtil.checkedList(criteria.list(), Departamento.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("this.sigla").ignoreCase();
	}

	/**
	 * Cria um {@link Criteria} padrão para departamento.
	 * 
	 * @return {@link Criteria} criado
	 */
	private Criteria getCriteria() {
		Criteria criteria = this.createCriteria(Departamento.class);
		criteria.add(Restrictions.isNull("dataHoraExclusao"));
		return criteria;
	}

	/**
	 * Monta os critérios para a paginação dos departamentos.
	 * 
	 * @param sigla sigla do departamento
	 * @param nome nome do departamento
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String sigla, String nome) {
		Criteria criteria = this.getCriteria();
		if (StringUtils.isNotBlank(sigla)) {
			criteria.add(Restrictions.like("sigla", sigla, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		return criteria;
	}
}
