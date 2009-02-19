/*
 * Projeto: sisgestor
 * Criação: 16/02/2009 por Thiago
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação da interface de acesso a dados de {@link Tarefa}.
 * 
 * @author Thiago
 * @since 16/02/2009
 */
public class TarefaDAOImpl extends BaseDAOImpl<Tarefa, Integer> implements TarefaDAO {

	private static final TarefaDAO	instancia	= new TarefaDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link TarefaDAOImpl}
	 */
	private TarefaDAOImpl() {
		super(Tarefa.class);
	}

	/**
	 * Recupera a instância de {@link TarefaDAO}. pattern singleton.
	 * 
	 * @return {@link TarefaDAO}
	 */
	public static TarefaDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Tarefa> getByNomeDescricaoUsuario(String nome, String descricao, Integer usuario,
			Integer idAtividade, Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, usuario, idAtividade);
		this.adicionarPaginacao(criteria, paginaAtual, TarefaDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Tarefa.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String nome, String descricao, Integer usuario, Integer idAtividade) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, usuario, idAtividade);
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
	 * Monta os critérios para a paginação das tarefas.
	 * 
	 * @param nome parte do nome da tarefa
	 * @param descricao parte da descrição da tarefa
	 * @param idAtividade identificação da atividade
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String nome, String descricao, Integer usuario,
			Integer idAtividade) {
		Criteria criteria = this.createCriteria(Tarefa.class);
		criteria.add(Restrictions.eq("this.atividade.id", idAtividade));
		criteria.createAlias("this.atividade", "atividade");
		criteria.add(Restrictions.eq("atividade.id", idAtividade));
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
		}
		if (usuario != null) {
			criteria.createAlias("this.usuario", "usuario");
			criteria.add(Restrictions.eq("usuario.id", usuario));
			criteria.addOrder(Order.asc("usuario.nome"));
		}
		return criteria;
	}
}
