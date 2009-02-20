/*
 * Projeto: sisgestor
 * Cria��o: 05/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.util.dto.PesquisaDepartamentoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de neg�cio para {@link Departamento}.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
@Service("departamentoBO")
public class DepartamentoBOImpl extends BaseBOImpl<Departamento, Integer> implements DepartamentoBO {

	private DepartamentoDAO	departamentoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		try {
			this.departamentoDAO.atualizar(departamento);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by Jo�o L�cio - n�o � necess�rio ter causa exce��o
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Departamento departamento) throws NegocioException {
		if ((departamento.getDepartamentosFilhos() != null) && !departamento.getDepartamentosFilhos().isEmpty()) {
			throw new NegocioException("erro.departamento.filhos");
		}
		if ((departamento.getUsuarios() != null) && !departamento.getUsuarios().isEmpty()) {
			throw new NegocioException("erro.departamento.usuarios");
		}
		if (this.isDepartamentoResponsavelAtividade(departamento)) {
			throw new NegocioException("erro.departamento.responsavel");
		}
		this.departamentoDAO.excluir(departamento);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual) {
		return this.departamentoDAO.getBySiglaNome(sigla, nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaDepartamentoDTO dto = (PesquisaDepartamentoDTO) parametros;
		return this.departamentoDAO.getTotalRegistros(dto.getSigla(), dto.getNome());
	}

	/**
	 * {@inheritDoc}
	 */
	public Departamento obter(Integer pk) {
		return this.departamentoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> obterTodos() {
		return this.departamentoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		try {
			this.departamentoDAO.salvar(departamento);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.departamento.sigla"); //NOPMD by Jo�o L�cio - n�o � necess�rio ter causa exce��o
		}
	}

	/**
	 * Atribui o DAO de {@link Departamento}.
	 * 
	 * @param departamentoDAO DAO de {@link Departamento}
	 */
	@Autowired
	public void setDepartamentoDAO(DepartamentoDAO departamentoDAO) {
		this.departamentoDAO = departamentoDAO;
	}

	/**
	 * Verifica se o departamento possui alguma atividade subordinada.
	 * 
	 * @param departamento Departamento a ser verificado
	 * @return <code>true</code>, se tiver;<br>
	 *         <code>false</code>, se n�o.
	 */
	private boolean departamentoTemAtividades(Departamento departamento) {
		List<Atividade> listaAtividades = departamento.getAtividades();

		if ((listaAtividades != null) && !listaAtividades.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se o departamento est� respons�vel por alguma atividade
	 * 
	 * @param departamento Departamento a ser verificado
	 * @return <code>true</code>, se est�;<br>
	 *         <code>false</code>, se n�o.
	 */
	private boolean isDepartamentoResponsavelAtividade(Departamento departamento) {
		return this.departamentoTemAtividades(departamento);
	}

	/**
	 * Verifica o {@link Departamento} superior do departamento.
	 * 
	 * @param departamento departamento a verificar
	 * @throws NegocioException caso departamento seja superior dele mesmo
	 */
	private void verificaDepartamentoSuperior(Departamento departamento) throws NegocioException {
		if ((departamento.getDepartamentoSuperior() != null)
				&& departamento.getDepartamentoSuperior().getId().equals(departamento.getId())) {
			throw new NegocioException("erro.departamento.superiorIgual");
		}
	}
}
