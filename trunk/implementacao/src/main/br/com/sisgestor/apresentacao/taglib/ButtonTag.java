package br.com.sisgestor.apresentacao.taglib;

/**
 * Classe escrita para sobrescrever a ButtonTag original para desabilitar o bot�o caso o usu�rio n�o possua
 * uma das permiss�es de acesso
 * 
 * @author Jo�o L�cio
 * @since 05/02/2009
 */
public class ButtonTag extends SubmitTag {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getDefaultValue() {
		return "Click";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getElementOpen() {
		return "<input type=\"button\"";
	}
}
