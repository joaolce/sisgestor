package br.com.sisgestor.apresentacao.taglib;

/**
 * Classe escrita para sobrescrever a ButtonTag original para desabilitar o botão caso o usuário não possua
 * uma das permissões de acesso
 * 
 * @author João Lúcio
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
