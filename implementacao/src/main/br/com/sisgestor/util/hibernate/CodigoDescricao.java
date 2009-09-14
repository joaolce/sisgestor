/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.sisgestor.util.hibernate;

/**
 * Interface para enumerações com código e descrição.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public interface CodigoDescricao {

	/**
	 * Retorna a descrição.
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
