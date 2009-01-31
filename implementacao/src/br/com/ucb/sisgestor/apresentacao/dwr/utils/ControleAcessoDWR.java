/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.dwr.utils;

import br.com.ucb.sisgestor.util.constantes.DadosContexto;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultAccessControl;

/**
 * Controle de acesso do DWR, aqui é feito o controle de sessão e usuário do DWR, se o usuário não estiver
 * logado será exibido uma mensagem informando que é necessário fazer um novo login.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public class ControleAcessoDWR extends DefaultAccessControl {

	/**
	 * Executado a cada requisição ajax.
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
