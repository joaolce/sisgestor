package br.com.ucb.sisgestor.mail;

import java.io.IOException;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean que representa um email.
 * 
 * @author João Lúcio
 * @since 05/01/2009
 */
public class Email {

	private static Log	logger;

	private MimeMessage	message;
	private Multipart		msg;

	static {
		logger = LogFactory.getLog(Email.class);
	}

	/**
	 * Cria uma nova instância do tipo {@link Email}
	 */
	public Email() {
		this.message = new MimeMessage(EmailSender.getInstancia().getSession());
		this.msg = new MimeMultipart();
	}

	/**
	 * Adiciona destinatários como cópia, separados por ';'.
	 * 
	 * @param recipients destinatários do email
	 */
	public void addRecipientesCC(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Adiciona destinatários como cópia oculta, separados por ';'.
	 * 
	 * @param recipients destinatários do email
	 */
	public void addRecipientesCCO(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Adiciona destinatários, separados por ';'.
	 * 
	 * @param recipients destinatários do email
	 */
	public void addRecipientesTO(String recipients) {
		if (StringUtils.isNotBlank(recipients)) {
			try {
				String[] enderecos = recipients.split(";");
				for (String endereco : enderecos) {
					this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(endereco.trim()));
				}
			} catch (MessagingException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Recupera o assunto do email.
	 * 
	 * @return assunto do email
	 * @throws MessagingException
	 */
	public String getAssunto() throws MessagingException {
		return this.message.getSubject();
	}

	/**
	 * Recupera o corpo do email.
	 * 
	 * @return corpo do email
	 * @throws MessagingException
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
	 * Recupera todos os destinatários do email.
	 * 
	 * @return destinatários do email
	 * @throws Exception
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
	 * Recupera a mensagem a ser enviada.
	 * 
	 * @return mensagem a ser enviada
	 */
	public MimeMessage getMessage() {
		try {
			this.message.setContent(this.msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return this.message;
	}

	/**
	 * Recupera o remetente do email.
	 * 
	 * @return remetente do email
	 * @throws Exception
	 */
	public String getRemetente() throws Exception {
		return this.message.getFrom()[0].toString();
	}

	/**
	 * Atribui o assunto do email.
	 * 
	 * @param assunto assunto do email
	 */
	public void setAssunto(String assunto) {
		try {
			this.message.setSubject(assunto);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Atribui o corpo do email.
	 * 
	 * @param corpo corpo do email
	 */
	public void setCorpo(String corpo) {
		BodyPart txt = new MimeBodyPart();
		try {
			txt.setContent(corpo, EmailSender.getMimeType());
			this.msg.addBodyPart(txt);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Atribui o remetente do email.
	 * 
	 * @param remetente remetente do email
	 */
	public void setRemetente(String remetente) {
		try {
			this.message.setFrom(new InternetAddress(remetente));
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
