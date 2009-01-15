/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public interface UsuarioBO extends BaseBO<Usuario, Integer> {

	/**
	 * Envia um email para o {@link Usuario} para lembrar a sua senha.
	 * 
	 * @param login login do usuário
	 * @return <code>true</code> caso foi enviada com sucesso, <code>false</code> em caso de insucesso
	 * @throws NegocioException
	 */
	public boolean enviarLembreteDeSenha(String login) throws NegocioException;

	/**
	 * Retorna um {@link List} de {@link Usuario} a partir da sigla e nome.
	 * 
	 * @param login
	 * @param nome
	 * @param departamento
	 * @param paginaAtual
	 * @return Retorna os usuários
	 */
	public List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual);

	/**
	 * 
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param login
	 * @param nome
	 * @param departamento
	 * @return total de registros
	 */
	public Integer getTotalRegistros(String login, String nome, Integer departamento);

	/**
	 * Recupera um usuário a partir do seu login.
	 * 
	 * @param login login do usuário
	 * @return usuário encontrado
	 * @throws NegocioException
	 */
	public Usuario recuperarPorLogin(String login) throws NegocioException;
}
