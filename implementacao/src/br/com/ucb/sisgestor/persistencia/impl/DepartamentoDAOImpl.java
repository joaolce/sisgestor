/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação da interface de acesso a dados de {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public class DepartamentoDAOImpl extends BaseDAOImpl<Departamento, Integer> implements
		DepartamentoDAO {

	private static final DepartamentoDAO instancia = new DepartamentoDAOImpl();

	/**
	 * Máximo de departamentos retornados.<br>
	 * 
	 * @see {@link manterDepartamento.js}
	 */
	private static final int MAXIMO_DEPARTAMENTOS = 9;

	/**
	 * Cria uma nova instância do tipo {@link DepartamentoDAOImpl}.
	 */
	private DepartamentoDAOImpl() {
		super(Departamento.class);
	}

	/**
	 * Recupera a instância de {@link DepartamentoDAO}. pattern singleton.
	 * 
	 * @return {@link DepartamentoDAO}
	 */
	public static DepartamentoDAO getInstancia() {
		return instancia;
	}

	/**
	 * Encontra os departamentos pelo nome
	 * 
	 * @param nome
	 * @param pagina
	 * @return {@link List} de {@link Departamento}
	 */
	public List<Departamento> getByNome(String nome, Integer pagina) {
		Criteria criteria = this.montarCriterios(nome);
		super.adicionarPaginacao(criteria, pagina, MAXIMO_DEPARTAMENTOS);
		criteria.addOrder(Order.asc("this.nome"));
		return criteria.list();
	}

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param nome
	 * @return totalRegistros
	 */
	public Integer getTotalRegistros(String nome) {
		Criteria criteria = this.montarCriterios(nome);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * Monta os critérios
	 * 
	 * @param nome
	 * @return
	 */
	private Criteria montarCriterios(String nome) {
		Criteria criteria = this.getSession().createCriteria(Departamento.class);
		if (!nome.equals("")) {
			criteria.add(Restrictions.ilike("this.nome", nome));
		}
		return criteria;
	}

}
