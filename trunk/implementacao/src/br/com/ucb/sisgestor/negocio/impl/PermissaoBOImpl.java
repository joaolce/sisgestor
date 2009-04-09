/*
 * Projeto: sisgestor
 * Cria��o: 19/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.negocio.PermissaoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.PermissaoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de neg�cio para {@link Permissao}.
 * 
 * @author Jo�o L�cio
 * @since 19/01/2009
 */
@Service("permissaoBO")
public class PermissaoBOImpl extends BaseBOImpl<Permissao> implements PermissaoBO {

	private PermissaoDAO	permissaoDAO;

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Permissao obj) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	public void excluir(Permissao obj) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Permissao obter(Integer pk) {
		return this.permissaoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Permissao> obterTodos() {
		return this.permissaoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Permissao obj) throws NegocioException {
		throw new UnsupportedOperationException("erro.operacaoNaoSuportada");
	}

	/**
	 * Atribui o DAO de {@link Permissao}.
	 * 
	 * @param permissaoDAO DAO de {@link Permissao}
	 */
	@Autowired
	public void setPermissaoDAO(PermissaoDAO permissaoDAO) {
		this.permissaoDAO = permissaoDAO;
	}
}
