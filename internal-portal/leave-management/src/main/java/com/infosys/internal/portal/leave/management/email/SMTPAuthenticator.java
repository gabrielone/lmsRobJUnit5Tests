package com.infosys.internal.portal.leave.management.email;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
	private String d_email, d_password;

	public SMTPAuthenticator(String d_email, String d_password) {
		super();
		this.d_email = d_email;
		this.d_password = d_password;

	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(d_email, d_password);
	}
}