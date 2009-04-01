/*
 * Projeto: sisgestor
 * Criação: 30/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.persistencia.UsoWorkflowDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link UsoWorkflow}.
 * 
 * @author João Lúcio
 * @since 30/03/2009
 */
@Repository("usoWorkflowDAO")
public class UsoWorkflowDAOImpl extends BaseDAOImpl<UsoWorkflow, Integer> implements UsoWorkflowDAO {

	/**
	 * Cria uma nova instância do tipo {@link UsoWorkflowDAOImpl}.
	 */
	public UsoWorkflowDAOImpl() {
		super(UsoWorkflow.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<UsoWorkflow> recuperarPendentesUsuario(Usuario usuario) {
		Criteria criteria = this.createCriteria(UsoWorkflow.class);
		criteria.createAlias("this.tarefa", "tarefa");
		criteria.add(Restrictions.eq("tarefa.usuario", usuario));
		criteria.add(Restrictions.isNull("this.dataHoraFim"));
		criteria.addOrder(Order.desc("this.dataHoraInicio"));
		return GenericsUtil.checkedList(criteria.list(), UsoWorkflow.class);
	}
}
