/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.ucb.sisgestor.persistencia.impl;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.persistencia.AnexoDAO;
import br.com.ucb.sisgestor.util.GenericsUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso a dados de {@link Anexo}.
 * 
 * @author Thiago
 * @since 07/04/2009
 */
@Repository("anexoDAO")
public class AnexoDAOImpl extends BaseDAOImpl<Anexo> implements AnexoDAO {

	/**
	 * Cria uma nova instância do tipo {@link AnexoDAOImpl}
	 */
	public AnexoDAOImpl() {
		super(Anexo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Anexo> getAnexosByUsoWorkflow(Integer idUsoWorkflow) {
		Criteria criteria = this.createCriteria(Anexo.class);
		criteria.add(Restrictions.eq("usoWorkflow.id", idUsoWorkflow));
		return GenericsUtil.checkedList(criteria.list(), Anexo.class);
	}
}
