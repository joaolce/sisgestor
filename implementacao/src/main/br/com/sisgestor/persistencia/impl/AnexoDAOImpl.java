/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por Thiago
 */
package br.com.sisgestor.persistencia.impl;

import br.com.sisgestor.util.GenericsUtil;
import br.com.sisgestor.persistencia.AnexoDAO;
import br.com.sisgestor.entidade.Anexo;
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
