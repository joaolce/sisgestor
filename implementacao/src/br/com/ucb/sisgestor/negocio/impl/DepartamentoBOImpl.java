/*
 * Projeto: sisgestor
 * Criação: 05/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.DepartamentoDAO;
import br.com.ucb.sisgestor.util.DataUtil;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.dto.PesquisaManterDepartamentoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Departamento}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
@Service("departamentoBO")
public class DepartamentoBOImpl extends BaseBOImpl<Departamento> implements DepartamentoBO {

	private DepartamentoDAO	departamentoDAO;
	private UsuarioBO			usuarioBO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		this.verificarSiglaDepartamentoExistente(departamento);

		this.departamentoDAO.atualizar(departamento);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Departamento departamento) throws NegocioException {
		if (CollectionUtils.isNotEmpty(this.getDepartamentosFilhosAtivos(departamento))) {
			throw new NegocioException("erro.departamento.filhos");
		}
		if (CollectionUtils.isNotEmpty(this.getUsuarioBO().getByDepartamento(departamento))) {
			throw new NegocioException("erro.departamento.usuarios");
		}
		if (CollectionUtils.isNotEmpty(departamento.getAtividades())) {
			throw new NegocioException("erro.departamento.responsavel");
		}
		departamento.setDataHoraExclusao(DataUtil.getDataHoraAtual());
		this.departamentoDAO.atualizar(departamento); //Exclusão lógica
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Departamento> getBySiglaNome(String sigla, String nome, Integer paginaAtual) {
		return this.departamentoDAO.getBySiglaNome(sigla, nome, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterDepartamentoDTO dto = (PesquisaManterDepartamentoDTO) parametros;
		return this.departamentoDAO.getTotalRegistros(dto.getSigla(), dto.getNome());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Departamento obter(Integer pk) {
		return this.departamentoDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Departamento> obterTodos() {
		return this.departamentoDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departamento> obterTodosAtivos() {
		return this.departamentoDAO.obterTodosAtivos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Departamento departamento) throws NegocioException {
		this.verificaDepartamentoSuperior(departamento);
		this.verificarSiglaDepartamentoExistente(departamento);

		return this.departamentoDAO.salvar(departamento);
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
	 * Recupera os departamentos filhos que estão ativos.
	 * 
	 * @param departamento departamento a recuperar os filhos ativos
	 * @return {@link List} de {@link Departamento}
	 */
	private List<Departamento> getDepartamentosFilhosAtivos(Departamento departamento) {
		List<Departamento> departamentosFilhos = new ArrayList<Departamento>();
		for (Departamento departamentoFilho : departamento.getDepartamentosFilhos()) {
			if (departamentoFilho.getDataHoraExclusao() == null) {
				departamentosFilhos.add(departamentoFilho);
			}
		}
		return departamentosFilhos;
	}

	/**
	 * Recupera o BO de {@link Usuario}.
	 * 
	 * @return BO de {@link Usuario}
	 */
	private UsuarioBO getUsuarioBO() {
		if (this.usuarioBO == null) {
			this.usuarioBO = Utils.getBean(UsuarioBO.class);
		}
		return this.usuarioBO;
	}

	/**
	 * Verifica o {@link Departamento} superior do departamento.
	 * 
	 * @param departamento departamento a verificar
	 * @throws NegocioException caso departamento seja superior dele mesmo
	 */
	private void verificaDepartamentoSuperior(Departamento departamento) throws NegocioException {
		if ((departamento.getDepartamentoSuperior() != null)
				&& departamento.getDepartamentoSuperior().equals(departamento)) {
			throw new NegocioException("erro.departamento.superiorIgual");
		}
	}

	/**
	 * Verifica se a sigla já existe para um outro departamento cadastrado.
	 * 
	 * @param departamento departamento a verificar
	 * @throws NegocioException caso a sigla do departamento já exista
	 */
	private void verificarSiglaDepartamentoExistente(Departamento departamento) throws NegocioException {
		if (this.departamentoDAO.isSiglaUtilizada(departamento)) {
			throw new NegocioException("erro.departamento.sigla");
		}
	}
}
