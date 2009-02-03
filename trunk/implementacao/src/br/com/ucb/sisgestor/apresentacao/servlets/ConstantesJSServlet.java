package br.com.ucb.sisgestor.apresentacao.servlets;

import br.com.ucb.sisgestor.persistencia.BaseDAO;
import br.com.ucb.sisgestor.util.constantes.ConstantesRoles;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Constantes para o Javascript Classe de constantes acessada pelo javascript <br />
 * Finalidade: centralizar a utilização de constantes tanto pelo Java quanto pelo Javascript em um lugar
 * somente
 * 
 * A resposta dessa servlet é incluída no definicaoPrincipal.jsp
 */
public class ConstantesJSServlet extends HttpServlet {

	private static Map<String, Object>	constantes	= new HashMap<String, Object>();
	private static String					javascript;

	static {
		constantes.put("PERMISSAO_MINIMA", ConstantesRoles.PERMISSAO_MINIMA);
		constantes.put("MANTER_DEPARTAMENTO", ConstantesRoles.MANTER_DEPARTAMENTO);
		constantes.put("MANTER_USUARIO", ConstantesRoles.MANTER_USUARIO);
		constantes.put("QTD_REGISTROS_PAGINA", BaseDAO.QTD_REGISTROS_PAGINA);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		if (javascript == null) {
			StringBuffer script =
					new StringBuffer("<!--Constantes utilizadas pelos JS-->\n<script type=\"text/javascript\">\n");
			for (String key : constantes.keySet()) {
				Object valor = constantes.get(key);
				script.append("var ");
				script.append(key);
				script.append(" = ");
				if (valor instanceof Number) {
					script.append(valor);
				}
				if (valor instanceof String) {
					script.append('"');
					script.append(valor);
					script.append('"');
				}
				if (valor instanceof Character) {
					script.append('\'');
					script.append(valor);
					script.append('\'');
				}
				script.append(";\r\n");
			}
			script.append("</script>");
			javascript = script.toString();
		}
		response.getWriter().write(javascript);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}
}
