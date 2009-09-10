/*
 * Projeto: sisgestor
 * Criação: 01/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Usuario}.
 * 
 * @author João Lúcio
 * @since 01/01/2009
 */
public interface UsuarioDAO extends BaseDAO<Usuario> {

	/**
	 * Recupera um {@link List} de {@link Usuario} do {@link Departamento}
	 * 
	 * @param departamento id do departamento
	 * @return Lista de usuários do departamento
	 */
	List<Usuario> getByDepartamento(Integer departamento);

	/**
	 * Recupera um {@link Usuario} a partir do seu login.
	 * 
	 * @param login login do usuário
	 * @return usuário encontrado
	 */
	Usuario getByLogin(String login);

	/**
	 * Retorna um {@link List} de {@link Usuario} a partir do login, nome e departamento.
	 * 
	 * @param login parte do login do usuário
	 * @param nome parte do nome do usuário
	 * @param departamento identificador do departamento do usuário
	 * @param paginaAtual página atual da pesquisa
	 * @return {@link List} de {@link Usuario}
	 */
	List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param login parte do login do usuário
	 * @param nome parte do nome do usuário
	 * @param departamento identificador do departamento do usuário
	 * @return total de registros encontrados
	 */
	Integer getTotalRegistros(String login, String nome, Integer departamento);

	/**
	 * Verifica se o login do usuário já está em uso.
	 * 
	 * @param usuario usuário a ser verificado
	 * @return <code>true</code>, se já está em uso;<code>false</code>, se ainda não.
	 */
	boolean isLoginUtilizado(Usuario usuario);
}
