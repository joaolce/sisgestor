/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

/**
 * Interface para enumerações com código e descrição.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public interface CodigoDescricao {

	/**
	 * Retorna o código
	 * 
	 * @return codigo do objeto.
	 */
	Integer getCodigo();

	/**
	 * Retorna a descrição
	 * 
	 * @return decricao do objeto
	 */
	String getDescricao();
}
