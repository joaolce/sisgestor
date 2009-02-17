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
import br.com.ucb.sisgestor.persistencia.impl.UsuarioDAOImpl;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaUsuarioDTO;
import br.com.ucb.sisgestor.util.hibernate.HibernateUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Objeto de negócio para {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 28/12/2008
 */
public class UsuarioBOImpl extends BaseBOImpl<Usuario, Integer> implements UsuarioBO {

	private static final UsuarioBO	instancia	= new UsuarioBOImpl();
	private UsuarioDAO					dao;

	/**
	 * Cria uma nova instância do tipo {@link UsuarioBOImpl}.
	 */
	private UsuarioBOImpl() {
		this.dao = UsuarioDAOImpl.getInstancia();
	}

	/**
	 * Recupera a instância de {@link UsuarioBO}. <br />
	 * pattern singleton.
	 * 
	 * @return {@link UsuarioBO}
	 */
	public static UsuarioBO getInstancia() {
		return instancia;
	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizar(Usuario usuario) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.atualizar(usuario);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			throw new NegocioException("erro.usuario.login.repetido"); //NOPMD by João Lúcio - não é necessário ter causa exceção
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean enviarLembreteDeSenha(String login) throws NegocioException {
		Usuario usuario = this.dao.recuperarPorLogin(login);
		if ((usuario != null) && StringUtils.isNotBlank(usuario.getEmail())) {
			try {
				Email email = new Email();
				email.setAssunto("SisGestor - Lembrete de senha");
				email.addDestinatariosTO(usuario.getEmail());
				email.setRemetente("sisgestor");
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
	public void excluir(Usuario usuario) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			this.dao.excluir(usuario);
			HibernateUtil.commit(transaction);
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		return this.dao.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaUsuarioDTO dto = (PesquisaUsuarioDTO) parametros;
		return this.dao.getTotalRegistros(dto.getLogin(), dto.getNome(), dto.getDepartamento());
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario obter(Integer pk) {
		return this.dao.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> obterTodos() {
		return this.dao.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	public Usuario recuperarPorLogin(String login) throws NegocioException {
		Usuario usuario = this.dao.recuperarPorLogin(login);
		if (usuario == null) {
			throw new NegocioException("erro.usuarioNaoEncontrado");
		}
		return usuario;
	}

	/**
	 * {@inheritDoc}
	 */
	public void salvar(Usuario usuario) throws NegocioException {
		Transaction transaction = this.beginTransaction();
		try {
			usuario.setSenha("123456");
			this.dao.salvar(usuario);
			HibernateUtil.commit(transaction);
		} catch (ConstraintViolationException ce) {
			HibernateUtil.rollback(transaction);
			throw new NegocioException("erro.usuario.login.repetido"); //NOPMD by João Lúcio - não é necessário ter causa exceção
		} catch (Exception e) {
			HibernateUtil.rollback(transaction);
			this.verificaExcecao(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Usuario> getByDepartamento(Departamento departamento) {
		return this.dao.getByDepartamento(departamento.getId());
	}
}
