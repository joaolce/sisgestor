/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.persistencia.AtividadeDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação da interface de acesso a dados de {@link Atividade}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class AtividadeDAOImpl extends BaseDAOImpl<Atividade, Integer> implements AtividadeDAO {

	private static final AtividadeDAO	instancia	= new AtividadeDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link AtividadeDAOImpl}
	 */
	private AtividadeDAOImpl() {
		super(Atividade.class);
	}

	/**
	 * Recupera a instância de {@link AtividadeDAO}. pattern singleton.
	 * 
	 * @return {@link AtividadeDAO}
	 */
	public static AtividadeDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Atividade> getByNomeDescricaoDepartamento(String nome, String descricao, Integer departamento,
			Integer idProcesso, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, departamento, idProcesso);
		this.adicionarPaginacao(criteria, paginaAtual, AtividadeDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Atividade.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, String descricao, Integer departamento, Integer idProcesso) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, departamento, idProcesso);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}


	/**
	 * Monta os critérios para a paginação dos atividades.
	 * 
	 * @param nome parte do nome do atividade
	 * @param descricao parte da descrição do atividade
	 * @param idProcesso identificação do processo
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Integer departamento,
			Integer idProcesso) {
		Criteria criteria = this.createCriteria(Atividade.class);
		criteria.createAlias("this.processo", "processo");
		criteria.add(Restrictions.eq("processo.id", idProcesso));
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		if (departamento != null) {
			criteria.createAlias("this.departamento", "departamento");
			criteria.add(Restrictions.eq("departamento.id", departamento));
			criteria.addOrder(Order.asc("departamento.sigla"));
		}
		return criteria;
	}
}
