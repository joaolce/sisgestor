/*
 * Projeto: SisGestor
 * Cria��o: 25/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.dwr.utils;

import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultAccessControl;

/**
 * Controle de acesso do DWR, aqui � feito o controle de sess�o e usu�rio do DWR, se o usu�rio n�o estiver
 * logado ser� exibido uma mensagem informando que � necess�rio fazer um novo login.
 * 
 * @author Jo�o L�cio
 * @since 25/10/2008
 */
public class ControleAcessoDWR extends DefaultAccessControl {

	/**
	 * Executado a cada requisi��o ajax.
	 * 
	 * @see org.directwebremoting.impl.DefaultAccessControl#assertIsExecutable(String, String)
	 */
	@Override
	protected void assertIsExecutable(String scriptName, String methodName) throws SecurityException {
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
		if (req.getSession().getAttribute(DadosContexto.USUARIOSESSAO) == null) {
			throw new SessionExpiredException("mensagem.naologado");
		}
		super.assertIsExecutable(scriptName, methodName);
	}
}
