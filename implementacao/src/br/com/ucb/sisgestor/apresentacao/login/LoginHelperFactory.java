/*
 * Projeto: SisGestor
 * Criação: 09/12/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.login;


import java.util.ResourceBundle;

/**
 * TODO DOCUMENT ME!
 * 
 * @author João Lúcio
 * @since 09/12/2008
 */
public class LoginHelperFactory {

	private static final int	AMBIENTE_DESENVOLVIMENTO	= 0;

	private static final int	AMBIENTE_HOMOLOGACAO			= 1;

	private static final int	AMBIENTE_PRODUCAO				= 2;

	private static int			applicationServer				= -1;

	private static int			ambiente							= -1;

	static {
		detectApplicationServer();
		detectEnvironment();
	}

	private LoginHelperFactory() {
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public static int getApplicationServer() {
		if (applicationServer == -1) {
			detectApplicationServer();
		}
		return applicationServer;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public static LoginHelper getLoginHelper() {
		return new JBoss4LoginHelper();
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public static boolean isDesenvolvimento() {
		return ambiente == 0;
	}

	/**
	 * TODO DOCUMENT ME!
	 * 
	 * @return d
	 */
	public static boolean isHomologacao() {
		return ambiente == 1;
	}

	private static void detectApplicationServer() {
		applicationServer = LoginConstants.JBOSS4_SERVER;
	}

	private static void detectEnvironment() {
		ResourceBundle bundle = ResourceBundle.getBundle("bc_login");
		String env = bundle.getString("bacen.security.ambiente");
		if ("DESEN".equals(env)) {
			ambiente = 0;
		} else if ("HOMOLOGA".equals(env)) {
			ambiente = 1;
		} else if ("PROD".equals(env)) {
			ambiente = 2;
		}
	}

	private static boolean findClass(String className) {
		boolean result = false;
		if ((className != null) && !"".equals(className)) {
			try {
				if (Class.forName(className) != null) {
					result = true;
				}
			} catch (ClassNotFoundException ignore) {
				result = false;
			}
		}
		return result;
	}

	private static boolean isJBoss42() {
		return findClass("org.jboss.web.tomcat.security.login.WebAuthentication");
	}

	private static boolean isWeblogic() {
		return findClass("weblogic.servlet.security.ServletAuthentication");
	}

	private static boolean isWebSphere() {
		return findClass("com.ibm.websphere.security.auth.WSSubject");
	}
}
