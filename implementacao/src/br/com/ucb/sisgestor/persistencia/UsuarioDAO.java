/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.persistencia;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.Usuario;
import java.util.List;

/**
 * Interface para acesso aos dados de {@link Usuario}.
 * 
 * @author Jo�o L�cio
 * @since 01/01/2009
 */
public interface UsuarioDAO extends BaseDAO<Usuario, Integer> {

	/**
	 * Retorna um {@link List} de {@link Usuario} a partir do login, nome e departamento.
	 * 
	 * @param login parte do login do usu�rio
	 * @param nome parte do nome do usu�rio
	 * @param departamento identificador do departamento do usu�rio
	 * @param paginaAtual p�gina atual da pesquisa
	 * @return {@link List} de {@link Usuario}
	 */
	List<Usuario> getByLoginNomeDepartamento(String login, String nome, Integer departamento,
			Integer paginaAtual);

	/**
	 * Recupera o total de registros retornados pela consulta.
	 * 
	 * @param login parte do login do usu�rio
	 * @param nome parte do nome do usu�rio
	 * @param departamento identificador do departamento do usu�rio
	 * @return total de registros
	 */
	Integer getTotalRegistros(String login, String nome, Integer departamento);

	/**
	 * Recupera um {@link Usuario} a partir do seu login.
	 * 
	 * @param login login do usu�rio
	 * @return usu�rio encontrado
	 */
	Usuario recuperarPorLogin(String login);

	/**
	 * Recupera um {@link List} de {@link Usuario} do {@link Departamento}
	 * 
	 * @param departamento id do departamento
	 * @return Lista de usu�rios do departamento
	 */
	List<Usuario> getByDepartamento(Integer departamento);
}