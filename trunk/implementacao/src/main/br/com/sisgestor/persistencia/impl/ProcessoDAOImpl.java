/*
 * Projeto: sisgestor
 * Criação: 11/02/2009 por Thiago
 */
package br.com.sisgestor.persistencia.impl;

import br.com.sisgestor.util.GenericsUtil;
import br.com.sisgestor.persistencia.ProcessoDAO;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.TransacaoProcesso;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface de acesso aos dados de {@link Processo}.
 * 
 * @author Thiago
 * @since 11/02/2009
 */
@Repository("processoDAO")
public class ProcessoDAOImpl extends BaseDAOImpl<Processo> implements ProcessoDAO {

	/**
	 * Cria uma nova instância do tipo {@link ProcessoDAOImpl}
	 */
	public ProcessoDAOImpl() {
		super(Processo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluirTransacao(TransacaoProcesso transacao) {
		this.getSession().delete(transacao);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Processo> getByNomeDescricao(String nome, String descricao, Integer idWorkflow,
			Integer paginaAtual) {
		Criteria criteria = this.montarCriteriosPaginacao(nome, descricao, idWorkflow);
		this.adicionarPaginacao(criteria, paginaAtual, ProcessoDAO.QTD_REGISTROS_PAGINA);
		criteria.addOrder(this.getOrdemLista());
		return GenericsUtil.checkedList(criteria.list(), Processo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Processo> getByWorkflow(Integer idWorkflow) {
		Criteria criteria = this.createCriteria(Processo.class);

		criteria.createAlias("this.workflow", "workflow");
		criteria.add(Restrictions.eq("workflow.id", idWorkflow));
		criteria.addOrder(this.getOrdemLista());
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
	public List<TransacaoProcesso> recuperarTransacoesDoWorkflow(Integer idWorkflow) {
		Criteria criteria = this.createCriteria(TransacaoProcesso.class);
		criteria.createAlias("this.anterior", "processoAnterior");
		criteria.createAlias("processoAnterior.workflow", "workflow");
		criteria.add(Restrictions.eq("workflow.id", idWorkflow));
		return GenericsUtil.checkedList(criteria.list(), TransacaoProcesso.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvarTransacao(TransacaoProcesso transacao) {
		this.getSession().save(transacao);
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
		Criteria criteria = this.createCriteria(Processo.class);

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
