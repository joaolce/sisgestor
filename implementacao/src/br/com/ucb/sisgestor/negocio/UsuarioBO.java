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
	 * @throws NegocioException caso uma regra de negócio seja violada
	 */
	boolean enviarLembreteDeSenha(String login) throws NegocioException;

	/**
	 * Retorna um {@link List} de {@link Usuario} a partir dos parâmetros informados.
	 * 
	 * @param login parte do login do usuário
	 * @param nome parte do nome do usuário
	 * @param departamento identificador do departamento do usuário
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna os usuários
	 */
	List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual);

	/**
	 * Recupera um usuário a partir do seu login.
	 * 
	 * @param login login do usuário
	 * @return usuário encontrado
	 * @throws NegocioException caso uma regra de negócio seja violada
	 */
	Usuario recuperarPorLogin(String login) throws NegocioException;
}
