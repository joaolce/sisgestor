/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
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
	 * Recupera um {@link List} de {@link Departamento} ativos
	 * 
	 * @return lista de departamntos ativos
	 */
	public List<Departamento> obterTodosAtivos() {
		return GenericsUtil.checkedList(this.getCriteria().list(), Departamento.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("sigla").ignoreCase();
	}

	/**
	 * Cria um {@link Criteria} padrão para departamento
	 * 
	 * @return criteria
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
