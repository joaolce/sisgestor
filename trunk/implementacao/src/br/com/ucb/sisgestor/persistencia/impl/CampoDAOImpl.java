/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.TipoCampoEnum;
import br.com.ucb.sisgestor.persistencia.CampoDAO;
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
 * Implementação da interface de acesso a dados de {@link Campo}.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
@Repository("campoDAO")
public class CampoDAOImpl extends BaseDAOImpl<Campo, Integer> implements CampoDAO {

	/**
	 * Cria uma nova instância do tipo {@link CampoDAOImpl}
	 */
	public CampoDAOImpl() {
		super(Campo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Campo> getByNomeTipo(String nome, Integer idTipo, Integer idWorkflow, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, idTipo, idWorkflow);
		this.adicionarPaginacao(criteria, paginaAtual, CampoDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Campo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, Integer idTipo, Integer idWorkflow) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, idTipo, idWorkflow);
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
	 * Monta os critérios para a paginação das atividades.
	 * 
	 * @param nome parte do nome da atividade
	 * @param descricao parte da descrição da atividade
	 * @param idTipo identificação do processo
	 * @param idWorkflow identificador do workflow
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, Integer idTipo, Integer idWorkflow) {
		Criteria criteria = this.getSession().createCriteria(Campo.class);
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (idTipo != null) {
			criteria.add(Restrictions.eq("this.tipo", TipoCampoEnum.getTipo(idTipo)));
		}
		criteria.add(Restrictions.eq("this.workflow.id", idWorkflow));
		return criteria;
	}
}
