/*
 * Projeto: sisgestor
 * Cria��o: 28/12/2008 por Jo�o L�cio
 */
package br.com.sisgestor.negocio.impl;

import br.com.sisgestor.entidade.Departamento;
import br.com.sisgestor.entidade.Usuario;
import br.com.sisgestor.negocio.UsuarioBO;
import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.persistencia.UsuarioDAO;
import br.com.sisgestor.util.constantes.Constantes;
import br.com.sisgestor.util.dto.PesquisaManterUsuarioDTO;
import br.com.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objeto de neg�cio para {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 28/12/2008
 */
@Service("usuarioBO")
public class UsuarioBOImpl extends BaseBOImpl<Usuario> implements UsuarioBO {

	private static final Log LOG = LogFactory.getLog(UsuarioBOImpl.class);
	private UsuarioDAO usuarioDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void atualizar(Usuario usuario) throws NegocioException {
		this.validarSeLoginExiste(usuario);
		this.usuarioDAO.atualizar(usuario);
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
			return this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR, Constantes.ASSUNTO_EMAIL_LEMBRETE_SENHA, usuario
					.getSenha(), usuario.getEmail());
		}
		if (usuario == null) {
			LOG.info("Usu�rio n�o encontrado, n�o enviado email de lembrete");
		} else {
			LOG.info("Usu�rio: " + usuario.getLogin() + " sem email cadastrado, n�o enviado email de lembrete");
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(Usuario usuario) throws NegocioException {
		usuario.setDataHoraExclusao(dataUtil.getDataHoraAtual());
		this.usuarioDAO.atualizar(usuario); //Exclus�o l�gica
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
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento, Integer paginaAtual) {
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
		this.validarSeLoginExiste(usuario);
		Integer id = this.usuarioDAO.salvar(usuario);
		this.enviaEmail(Constantes.REMETENTE_EMAIL_SISGESTOR, Constantes.ASSUNTO_EMAIL_NOVO_USUARIO,
				"Seja bem vindo ao <b>SisGestor</b> <br /> <p>Sua senha �: " + usuario.getSenha() + "</p>", usuario
						.getEmail());
		return id;
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
	 * Gera uma senha para o usu�rio.
	 * 
	 * @return {@link String} da senha gerada
	 */
	private String gerarSenha() {
		StringBuilder senha = new StringBuilder("");
		char caracteres[] =
				{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
						'v', 'w', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		while (senha.length() < 6) {
			if (random.nextBoolean()) { // caracter mai�sculo
				senha.append(Character.toUpperCase(caracteres[random.nextInt(35)]));
			} else {
				senha.append(caracteres[random.nextInt(35)]);
			}
		}
		return senha.toString();
	}

	/**
	 * Valida se o login do usu�rio j� existe para outro usu�rio.
	 * 
	 * @param usuario usu�rio a validar
	 * @throws NegocioException caso o login j� existe para outro usu�rio
	 */
	private void validarSeLoginExiste(Usuario usuario) throws NegocioException {
		if (this.usuarioDAO.isLoginUtilizado(usuario)) {
			throw new NegocioException("erro.usuario.login.repetido");
		}
	}
}
