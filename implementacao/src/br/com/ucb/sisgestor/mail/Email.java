package br.com.ucb.sisgestor.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean que representa um email.
 * 
 * @author Jo�o L�cio
 * @since 05/01/2009
 */
public class Email {

	private static final Properties	configuracao	= new Properties();
	private static final String		properties		= "settings.properties";
	private static Session				session;
	private static final Log			LOG				= LogFactory.getLog(Email.class);

	private MimeMessage					message;
	private Multipart						msg;

	static {
		InputStream resourceAsStream = Email.class.getResourceAsStream(properties);
		try {
			configuracao.load(resourceAsStream);
			//utilizando session a partir do jndi do jboss
			session = Session.class.cast(new InitialContext().lookup("java:/Mail"));
			//		session = Session.getDefaultInstance(configuracao);
		} catch (Exception e) {
			LOG.error(e); //NOPMD by Jo�o L�cio - apenas para logar
		}
	}

	/**
	 * Cria uma nova inst�ncia do tipo {@link Email}.
	 */
	public Email() {
		this.message = new MimeMessage(session);
		this.msg = new MimeMultipart();
	}

	/**
	 * Adiciona destinat�rios como c�pia, separados por ';'.
	 * 
	 * @param recipients destinat�rios do email
	 */
	public void addDestinatariosCC(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Adiciona destinat�rios como c�pia oculta, separados por ';'.
	 * 
	 * @param recipients destinat�rios do email
	 */
	public void addDestinatariosCCO(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Adiciona destinat�rios, separados por ';'.
	 * 
	 * @param recipients destinat�rios do email
	 */
	public void addDestinatariosTO(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Recupera o assunto do e-mail.
	 * 
	 * @return assunto do e-mail
	 * @throws MessagingException caso ocorra exce��o ao recuperar o assunto
	 */
	public String getAssunto() throws MessagingException {
		return this.message.getSubject();
	}

	/**
	 * Recupera o corpo do e-mail.
	 * 
	 * @return corpo do e-mail
	 * @throws MessagingException caso ocorra exce��o ao recuperar o conte�do do e-mail
	 */
	public String getCorpo() throws MessagingException {
		BodyPart bodyPart = this.msg.getBodyPart(0);
		try {
			return bodyPart.getContent().toString();
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * Recupera todos os destinat�rios do e-mail.
	 * 
	 * @return destinat�rios do e-mail separados por ';'
	 * @throws Exception caso ocorra exce��o ao recuperar destinat�rios
	 */
	public String getDestinatarios() throws Exception {
		Address[] addresses = this.getMessage().getAllRecipients();
		StringBuilder emails = new StringBuilder();
		for (Address address : addresses) {
			InternetAddress iaddress = (InternetAddress) address;
			String email = iaddress.getAddress();
			emails.append(email);
			emails.append(";");
		}
		return emails.toString();
	}

	/**
	 * Recupera o remetente do e-mail.
	 * 
	 * @return remetente do e-mail
	 * @throws Exception caso ocorra exce��o ao recuperar o remetente
	 */
	public String getRemetente() throws Exception {
		return this.message.getFrom()[0].toString();
	}

	/**
	 * Envia o {@link Email}.
	 * 
	 * @throws Exception caso ocorra algum erro no envio do email
	 */
	public void send() throws Exception {
		try {
			Transport.send(this.getMessage());
		} catch (Exception e) {
			LOG.error("Alguma coisa deu errado no envio de e-mail.", e);
			throw e;
		}
	}

	/**
	 * Atribui o assunto do e-mail.
	 * 
	 * @param assunto assunto do e-mail
	 */
	public void setAssunto(String assunto) {
		try {
			this.message.setSubject(assunto);
		} catch (MessagingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Atribui o corpo do e-mail.
	 * 
	 * @param corpo corpo do e-mail
	 */
	public void setCorpo(String corpo) {
		BodyPart txt = new MimeBodyPart();
		try {
			txt.setContent(corpo, configuracao.getProperty("mime_type"));
			this.msg.addBodyPart(txt);
		} catch (MessagingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Atribui o remetente do e-mail.
	 * 
	 * @param remetente remetente do e-mail
	 */
	public void setRemetente(String remetente) {
		try {
			this.message.setFrom(new InternetAddress(remetente));
		} catch (MessagingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Recupera a mensagem a ser enviada.
	 * 
	 * @return mensagem a ser enviada
	 */
	private MimeMessage getMessage() {
		try {
			this.message.setContent(this.msg);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return this.message;
	}
}
