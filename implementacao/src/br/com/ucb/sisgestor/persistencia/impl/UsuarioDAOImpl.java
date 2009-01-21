/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
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
 * Implementação da interface de acesso a dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario, Integer> implements UsuarioDAO {

	private static final UsuarioDAO	instancia	= new UsuarioDAOImpl();

	/**
	 * Cria uma nova instância do tipo {@link UsuarioDAOImpl}
	 */
	private UsuarioDAOImpl() {
		super(Usuario.class);
	}

	/**
	 * Recupera a instância de {@link UsuarioDAO}. pattern singleton.
	 * 
	 * @return {@link UsuarioDAO}
	 */
	public static UsuarioDAO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(login, nome, departamento);
		this.adicionarPaginacao(criteria, paginaAtual, MAXIMO_RESULTADOS);
		criteria.addOrder(Order.asc("nome"));
		return GenericsUtil.checkedList(criteria.list(), Usuario.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalRegistros(String login, String nome, Integer departamento) {
		Criteria criteria = this.montarCriteriosPaginacao(login, nome, departamento);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario recuperarPorLogin(String login) {
		Criteria criteria = this.getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login).ignoreCase());
		return (Usuario) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Order getOrdemLista() {
		return Order.asc("nome").ignoreCase();
	}

	/**
	 * Monta os critérios para a paginação dos usuários.
	 * 
	 * @param login parte do login do usuário
	 * @param nome parte do nome do usuário
	 * @param departamento identificador do departamento do usuário
	 * @return {@link Criteria}
	 */
	private Criteria montarCriteriosPaginacao(String login, String nome, Integer departamento) {
		Criteria criteria = this.getSession().createCriteria(Usuario.class);
		Conjunction conjunction = Restrictions.conjunction();
		if (StringUtils.isNotBlank(login)) {
			conjunction.add(Restrictions.like("login", login, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(nome)) {
			conjunction.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (departamento != null) {
			criteria.createAlias("this.departamento", "departamento");
			conjunction.add(Restrictions.eq("departamento.id", departamento));
		}
		criteria.add(conjunction);
		return criteria;
	}
}
