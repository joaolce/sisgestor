/*
 * Projeto: sisgestor
 * Criação: 28/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.UsuarioBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.persistencia.UsuarioDAO;
import br.com.ucb.sisgestor.util.constantes.Constantes;
import br.com.ucb.sisgestor.util.dto.PesquisaManterUsuarioDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class UsuarioBOImpl extends BaseBOImpl<Usuario> implements UsuarioBO {

	private static final Log	LOG	= LogFactory.getLog(UsuarioBOImpl.class);
	private UsuarioDAO			usuarioDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Usuario usuario) throws NegocioException {
		try {
			this.usuarioDAO.atualizar(usuario);
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.usuario.login.repetido");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean enviarLembreteDeSenha(String login) throws NegocioException {
		Usuario usuario = this.usuarioDAO.getByLogin(login);
		if ((usuario != null) && StringUtils.isNotBlank(usuario.getEmail())) {
			usuario.setSenha(this.gerarSenha());
			this.usuarioDAO.atualizar(usuario);
			return this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR,
					Constantes.ASSUNTO_EMAIL_LEMBRETE_SENHA, usuario.getSenha(), usuario.getEmail());
		}
		LOG.info("Usuário: " + usuario.getLogin() + " sem email cadastrado, não enviado email de lembrete");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Usuario usuario) throws NegocioException {
		this.usuarioDAO.excluir(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Usuario> getByDepartamento(Departamento departamento) {
		return this.usuarioDAO.getByDepartamento(departamento.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual) {
		return this.usuarioDAO.getByLoginNomeDepartamento(login, nome, departamento, paginaAtual);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		PesquisaManterUsuarioDTO dto = (PesquisaManterUsuarioDTO) parametros;
		return this.usuarioDAO.getTotalRegistros(dto.getLogin(), dto.getNome(), dto.getDepartamento());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public Usuario obter(Integer pk) {
		return this.usuarioDAO.obter(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<Usuario> obterTodos() {
		return this.usuarioDAO.obterTodos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer salvar(Usuario usuario) throws NegocioException {
		usuario.setSenha(this.gerarSenha());
		try {
			Integer id = this.usuarioDAO.salvar(usuario);
			this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR, Constantes.ASSUNTO_EMAIL_NOVO_USUARIO,
					"Seja bem vindo ao <b>SisGestor</b> <br /> <p>Sua senha é: " + usuario.getSenha() + "</p>",
					usuario.getEmail());
			return id;
		} catch (ConstraintViolationException ce) {
			throw new NegocioException("erro.usuario.login.repetido");
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

	/**
	 * Gera uma senha para o usuário.
	 * 
	 * @return {@link String} da senha gerada
	 */
	private String gerarSenha() {
		StringBuilder senha = new StringBuilder("");
		char caracteres[] =
				{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
						't', 'u', 'v', 'w', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		while (senha.length() < 6) {
			if (random.nextBoolean()) { // caracter maiúsculo
				senha.append(Character.toUpperCase(caracteres[random.nextInt(35)]));
			} else {
				senha.append(caracteres[random.nextInt(35)]);
			}
		}
		return senha.toString();
	}
}
