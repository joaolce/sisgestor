/*
 * Projeto: SisGestor
 * Cria��o: 25/10/2008 por Jo�o L�cio
 */
package br.com.sisgestor.util.hibernate;

/**
 * Interface para enumera��es com c�digo e descri��o.
 * 
 * @author Jo�o L�cio
 * @since 25/10/2008
 */
public interface CodigoDescricao {

	/**
	 * Retorna a descri��o.
	 * 
	 * @return decricao do objeto
	 */
	String getDescricao();

	/**
	 * Retorna o identificador do objeto.
	 * 
	 * @return identificador do objeto
	 */
	Integer getId();
}
