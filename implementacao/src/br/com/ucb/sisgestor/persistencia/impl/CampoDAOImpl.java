/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.persistencia.CampoDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;

/**
 * Implementação da interface de acesso a dados de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
public class CampoDAOImpl extends BaseDAOImpl<Campo, Integer> implements CampoDAO {

	private static final CampoDAO	instancia	= new CampoDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link CampoDAOImpl}
	 */
	private CampoDAOImpl() {
		super(Campo.class);
	}

	/**
	 * Recupera a instância de {@link CampoDAO}. pattern singleton.
	 * 
	 * @return {@link CampoDAO}
	 */
	public static CampoDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, Integer idTipo) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, idTipo);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, idTipo);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Campo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}


	/**
	 * Monta os critérios para a paginação das atividades.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param idTipo identificação do processo
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, Integer idTipo) {
		Criteria criteria = this.getSession().createCriteria(Campo.class);
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if(idTipo != null){
			criteria.createAlias("this.tipo", "tipo");
			criteria.add(Restrictions.eq("tipo.id", idTipo));
		}
		return criteria;
	}
}
