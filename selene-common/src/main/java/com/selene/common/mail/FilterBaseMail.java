package com.selene.common.mail;

import jodd.mail.Email;
import jodd.mail.MailException;

public class FilterBaseMail extends BaseMail {

	protected volatile Email email;

	protected FilterBaseMail(Email email) {
		this.email = email;
	}

	@Override
	public boolean send() throws MailException {
		return false;
	}

	@Override
	public boolean receive() throws MailException {
		return false;
	}
}
