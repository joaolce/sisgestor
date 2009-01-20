/*
 * Projeto: sisgestor
 * Cria��o: 19/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.negocio.PermissaoBO;
import br.com.ucb.sisgestor.persistencia.PermissaoDAO;
import br.com.ucb.sisgestor.persistencia.impl.PermissaoDAOImpl;
import java.util.List;

/**
 * Objeto de neg�cio para {@link Permissao}.
 * 
 * @author Jo�o L�cio
 * @since 19/01/2009
 */
public class PermissaoBOImpl extends BaseBOImpl<Permissao, Integer> implements PermissaoBO {

	private static final PermissaoBO	instancia	= new PermissaoBOImpl();
	private PermissaoDAO					dao;

	/**
	 * Cria uma nova inst�ncia do tipo {@link UsuarioBOImpl}.
	 */
	private PermissaoBOImpl() {
		this.dao = PermissaoDAOImpl.getInstancia();
	}

	/**
	 * Recupera a inst�ncia de {@link PermissaoBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link PermissaoBO}
	 */
	public static PermissaoBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Permissao obj) throws Exception {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 *{@inheritDoc}
	 */
	public void excluir(Permissao obj) throws Exception {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	public Permissao obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Permissao> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Permissao obj) throws Exception {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}
}
