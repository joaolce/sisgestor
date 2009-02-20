/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.mail.Email;
import br.com.ucb.sisgestor.mail.EmailSender;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
import br.com.ucb.sisgestor.util.constantes.Constantes;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaUsuarioDTO;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de negócio para {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 28/12/2008
 */
@Service("usuarioBO")
public class UsuarioBOImpl extends BaseBOImpl<Usuario, Integer> implements UsuarioBO {

	private UsuarioDAO	usuarioDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void atualizar(Usuario usuario) throws NegocioException {
		try {
			this.usuarioDAO.atualizar(usuario);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.usuario.login.repetido"); //NOPMD by João Lúcio - não é necessário ter causa exceção
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean enviarLembreteDeSenha(String login) throws NegocioException {
		Usuario usuario = this.usuarioDAO.getByLogin(login);
		if ((usuario != null) && StringUtils.isNotBlank(usuario.getEmail())) {
			try {
				Email email = new Email();
				email.setAssunto(Constantes.LEMBRETE_EMAIL_ASSUNTO);
				email.addDestinatariosTO(usuario.getEmail().trim());
				email.setRemetente("SisGestor");
				email.setCorpo(usuario.getSenha());
				EmailSender.getInstancia().send(email);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void excluir(Usuario usuario) throws NegocioException {
		this.usuarioDAO.excluir(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByDepartamento(Departamento departamento) {
		return this.usuarioDAO.getByDepartamento(departamento.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario getByLogin(String login) throws NegocioException {
		Usuario usuario = this.usuarioDAO.getByLogin(login);
		if (usuario == null) {
			throw new NegocioException("erro.usuarioNaoEncontrado");
		}
		return usuario;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		return this.usuarioDAO.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaUsuarioDTO dto = (PesquisaUsuarioDTO) parametros;
		return this.usuarioDAO.getTotalRegistros(dto.getLogin(), dto.getNome(), dto.getDepartamento());
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario obter(Integer pk) {
		return this.usuarioDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> obterTodos() {
		return this.usuarioDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void salvar(Usuario usuario) throws NegocioException {
		usuario.setSenha("123456");
		try {
			this.usuarioDAO.salvar(usuario);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.usuario.login.repetido"); //NOPMD by João Lúcio - não é necessário ter causa exceção
		}
	}

	/**
	 * Atribui o DAO de {@link Usuario}.
	 * 
	 * @param usuarioDAO DAO de {@link Usuario}
	 */
	@Autowired
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}
