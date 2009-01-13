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

	private static Properties		configuracao;
	private static Log				logger;
	private static final String	properties	= "settings.properties";
	private static EmailSender		sender;

	private Session					session;

	static {
		logger = LogFactory.getLog(EmailSender.class);
		InputStream resourceAsStream = EmailSender.class.getResourceAsStream(properties);
		configuracao = new Properties();
		try {
			configuracao.load(resourceAsStream);
		} catch (IOException e) {
			logger.error(e);
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
		if (sender == null) {
			sender = new EmailSender();
		}
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
	 * @throws Exception
	 */
	public void send(Email email) throws Exception {
		MimeMessage message = email.getMessage();
		try {
			Transport.send(message);
		} catch (Exception e) {
			logger.error("Alguma coisa deu errado no envio de email.", e);
			throw e;
		}
	}
}
