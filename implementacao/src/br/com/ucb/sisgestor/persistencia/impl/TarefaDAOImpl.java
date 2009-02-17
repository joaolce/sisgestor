/*
 * Projeto: sisgestor
 * Cria��o: 16/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.persistencia.TarefaDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa��o da interface de acesso a dados de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class TarefaDAOImpl extends BaseDAOImpl<Tarefa, Integer> implements TarefaDAO {

	private static final TarefaDAO	instancia	= new TarefaDAOImpl();

	/**
	 * Cria uma nova inst�ncia do tipo {@link TarefaDAOImpl}
	 */
	private TarefaDAOImpl() {
		super(Tarefa.class);
	}

	/**
	 * Recupera a inst�ncia de {@link TarefaDAO}. pattern singleton.
	 * 
	 * @return {@link TarefaDAO}
	 */
	public static TarefaDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Tarefa> getByNomeDescricao(String nome, String descricao, Integer idAtividade,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, idAtividade);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Tarefa.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}


	/**
	 * Monta os crit�rios para a pagina��o dos tarefas.
	 * 
	 * @param nome parte do nome do tarefa
	 * @param descricao parte da descri��o do tarefa
	 * @param idWorkflow identifica��o do atividade
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Integer idAtividade) {
		Criteria criteria = this.getSession().createCriteria(Tarefa.class);
		criteria.add(Restrictions.eq("this.atividade.id", idAtividade));
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		return criteria;
	}
}