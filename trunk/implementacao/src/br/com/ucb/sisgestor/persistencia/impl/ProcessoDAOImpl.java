/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.persistencia.ProcessoDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação da interface de acesso a dados de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
public class ProcessoDAOImpl extends BaseDAOImpl<Processo, Integer> implements ProcessoDAO {

	private static final ProcessoDAO	instancia	= new ProcessoDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link ProcessoDAOImpl}
	 */
	private ProcessoDAOImpl() {
		super(Processo.class);
	}

	/**
	 * Recupera a instância de {@link ProcessoDAO}. pattern singleton.
	 * 
	 * @return {@link ProcessoDAO}
	 */
	public static ProcessoDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, idWorkflow);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA - 3);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Processo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, String descricao, Integer idWorkflow) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, idWorkflow);
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
	 * Monta os critérios para a paginação dos processos.
	 * 
	 * @param nome parte do nome do processo
	 * @param descricao parte da descrição do processo
	 * @param idWorkflow identificação do workflow
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Integer idWorkflow) {
		Criteria criteria = this.getSession().createCriteria(Processo.class);

		criteria.createAlias("this.workflow", "workflow");
		criteria.add(Restrictions.eq("workflow.id", idWorkflow));
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		return criteria;
	}
}
