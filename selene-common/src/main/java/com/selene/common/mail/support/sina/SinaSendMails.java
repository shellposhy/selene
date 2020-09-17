package com.selene.common.mail.support.sina;

import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

import com.selene.common.constants.util.EMailBodyType;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpSslServer;

/**
 * The utilities for Sina mails send
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class SinaSendMails extends Email {
	private static int SMTP_PORT = 465;
	private static String SMTP_HOST = "smtp.sina.com";
	private static String ENCODE = "UTF-8";
	private static SmtpSslServer smtpSslServer = SmtpSslServer.create(SMTP_HOST, SMTP_PORT);

	/**
	 * Create mail
	 * 
	 * @param subject
	 * @param type
	 *            {@code EMailBodyType}
	 * @param body
	 * @param from
	 * @param cc
	 * @param to
	 */
	public static Email mail(String subject, EMailBodyType type, String body, String from, String cc, String... to) {
		checkArgument(
				type != null && !isNullOrEmpty(subject) && !isNullOrEmpty(from) && to != null && !isNullOrEmpty(body));
		return type == EMailBodyType.Text
				? Email.create().subject(subject, ENCODE).from(from).cc(cc).to(to).addText(body, ENCODE)
				: Email.create().subject(subject, ENCODE).from(from).cc(cc).to(to).addHtml(body, ENCODE);
	}

	/**
	 * Sina send mails
	 * 
	 * @param username
	 *            Sina user account
	 * @param password
	 *            Sina user authorization code
	 * @param mails
	 */
	public static boolean send(String username, String password, Email... mails) {
		checkArgument(mails != null && !isNullOrEmpty(username) && !isNullOrEmpty(password));
		smtpSslServer.authenticateWith(username, password);
		if (mails != null && mails.length > 0) {
			SendMailSession sendMailSession = smtpSslServer.createSession();
			sendMailSession.open();
			for (Email email : mails) {
				sendMailSession.sendMail(email);
			}
			sendMailSession.close();
		}
		return true;
	}
}
