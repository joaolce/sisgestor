package br.com.ucb.sisgestor.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe responsável por envios de {@link Email}.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public class EmailSender {

	private static Properties			configuracao;
	private static final Log			LOG			= LogFactory.getLog(EmailSender.class);
	private static final String		properties	= "settings.properties";
	private static final EmailSender	sender		= new EmailSender();

	private Session						session;

	static {
		InputStream resourceAsStream = EmailSender.class.getResourceAsStream(properties);
		configuracao = new Properties();
		try {
			configuracao.load(resourceAsStream);
		} catch (IOException e) {
			LOG.error(e); //NOPMD by João Lúcio - apenas para logar
		}
	}

	/**
	 * Cria uma nova instância do tipo {@link EmailSender}.
	 */
	private EmailSender() {
		this.session = Session.getDefaultInstance(configuracao);
	}

	/**
	 * Recupera as configurações dos emails.
	 * 
	 * @return configurações dos emails
	 */
	public static Properties getConfiguracao() {
		return configuracao;
	}

	/**
	 * Recupera a instância única de {@link EmailSender}.
	 * 
	 * @return instância de {@link EmailSender}
	 */
	public static EmailSender getInstancia() {
		return sender;
	}

	/**
	 * Recupera o mime-type dos emails.
	 * 
	 * @return mime-type dos emails
	 */
	public static String getMimeType() {
		return configuracao.getProperty("mime_type");
	}

	/**
	 * Recupera a sessão de email.
	 * 
	 * @return sessão de email
	 */
	public Session getSession() {
		return this.session;
	}

	/**
	 * Envia um {@link Email}.
	 * 
	 * @param email email a ser enviado
	 * @throws Exception caso ocorra algum erro no envio do email
	 */
	public void send(Email email) throws Exception {
		MimeMessage message = email.getMessage();
		try {
			Transport.send(message);
		} catch (Exception e) {
			LOG.error("Alguma coisa deu errado no envio de e-mail.", e);
			throw e;
		}
	}
}
