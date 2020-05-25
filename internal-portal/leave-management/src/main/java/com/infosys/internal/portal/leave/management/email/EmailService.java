package com.infosys.internal.portal.leave.management.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailService {

	private String email = null;
	private String password = null;
	private String host = "smtp.gmail.com";
	private String port = "465";
	private Properties props;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public EmailService(String email, String password) {
		this.email = email;
		this.password = password;
		setProperties();

	}

	public void sendEmail(String to, String subject, String text) {
		try {
			Authenticator auth = new SMTPAuthenticator(email, password);
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(text);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);
		} catch (Exception mex) {
			mex.printStackTrace();
			LOGGER.info("context", mex);
		}
	}

	// setting propreties
	void setProperties() {
		this.props = new Properties();
		props.put("mail.smtp.user", email);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
	}
}
