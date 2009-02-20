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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
@Repository("usuarioDAO")
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario, Integer> implements UsuarioDAO {

	/**
	 * Cria uma nova instância do tipo {@link UsuarioDAOImpl}.
	 */
	public UsuarioDAOImpl() {
		super(Usuario.class);
	}

	/**
	 * {@inheritDoc}
	 * */
	public List<Usuario> getByDepartamento(Integer departamento) {
		Criteria criteria = this.createCriteria(Usuario.class);
		criteria.createAlias("this.departamento", "departamento");
		criteria.add(Restrictions.eq("departamento.id", departamento));
		return GenericsUtil.checkedList(criteria.list(), Usuario.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario getByLogin(String login) {
		Criteria criteria = this.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login).ignoreCase());
		return (Usuario) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(login, nome, departamento);
		this.adicionarPaginacao(criteria, paginaAtual, QTD_REGISTROS_PAGINA);
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
		Criteria criteria = this.createCriteria(Usuario.class);
		if (StringUtils.isNotBlank(login)) {
			criteria.add(Restrictions.like("login", login, MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		}
		if (departamento != null) {
			criteria.createAlias("this.departamento", "departamento");
			criteria.add(Restrictions.eq("departamento.id", departamento));
		}
		return criteria;
	}
}
